package com.naqi.center.server.conn;

import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.google.protobuf.Message;
import com.naqi.center.msg.MessageFactory;
import com.naqi.center.msg.MsgConstant;
import com.naqi.center.util.GameUtil;
import com.naqi.entity.Player;


/**
 * 解码器
 * 
 * @author hanfeng
 * 
 */
public class ClientDecoder extends CumulativeProtocolDecoder {

	private static Logger logger = Logger.getLogger(ClientDecoder.class);

	/**
	 * 消息最大长度
	 */
	private static final int PACKAGE_MAX_LENGTH = 1024 * 5;
	
	@Override
	protected boolean doDecode(IoSession session, IoBuffer iobuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
		iobuffer.order(ByteOrder.LITTLE_ENDIAN);
		iobuffer.mark();
		try {
			logger.debug("接受到消息，开始解码:" + session.getRemoteAddress());
			
			byte firestByte = iobuffer.get();
			if(firestByte == 71){
				iobuffer.reset();
				byte[] inbytes = iobuffer.array();
				String m = new String(inbytes,Charset.forName("ISO8859-1"));
				String ret = getSecWebSocketAccept(m);
				session.write(ret.getBytes());
				return true;
			}else if (iobuffer.remaining() > MsgConstant.RECEIVE_HEAD_LEN) {
				logger.debug("消息长度小于消息头，返回，长度:" + iobuffer.remaining());
				//是否结束 1 结束
				int finish = firestByte < 0 ? 1 : 0;
				/**
				 * 帧类型
				 * | 0      | Continuation Frame                
				 *-+--------+-----------------------------------
				 * | 1      | Text Frame                        a
				 *-+--------+-----------------------------------
				 * | 2      | Binary Frame                      
				 *-+--------+-----------------------------------
				 * | 8      | Connection Close Frame            
				 * -+--------+----------------------------------
				 * | 9      | Ping Frame                        
				 * -+--------+----------------------------------
				 * | 10     | Pong Frame                        
				 */
				int opcode = firestByte&15;
				if(opcode == 8){
					GameUtil.closeSessionAndLog(session, iobuffer, "webSocket 关闭  opcode = 8!!!" );
					return true;
				}
				//第二个字节
				byte secondByte = iobuffer.get();
				//数据的长度
				int dataLength = secondByte&0x7F;
				if(dataLength == 126){
					dataLength = iobuffer.getShort();
				}else if(dataLength == 127){
					dataLength = iobuffer.getInt();
				}
				byte[] maskingKeys = null;
				//负数  mask == 1
				if(secondByte < 0){
					maskingKeys = new byte[4];
					iobuffer.get(maskingKeys);
				}
				byte[] dataBytes = new byte[dataLength];
				iobuffer.get(dataBytes);
				for (int i = 0 ; i < dataBytes.length ; i++) {
					dataBytes[i] = (byte) (dataBytes[i] ^ maskingKeys[i%4]);
				}
				System.out.println(new String(dataBytes));
				return true;
			}
			
			return false;
//			if (iobuffer.remaining() < MsgConstant.RECEIVE_HEAD_LEN) {
//				logger.debug("消息长度小于消息头，返回，长度:" + iobuffer.remaining());
//				return false;
//			}
////			// 1.消息开始标记位(byte 1)
////			byte beginTag = iobuffer.get();
////			/**
////			 * 消息开始标记位错误
////			 */
////			if (beginTag != MsgConstant.SIGN) {
////				logger.error("消息标记位错误,断掉连接");
////				GameUtil.closeSessionAndLog(session, iobuffer, "消息标记位错误,标记位:" + beginTag);
////				return false;
////			}
//			// 2.消息长度(short 2)
//			short length = iobuffer.getShort();
//			if (length < 0 || length > PACKAGE_MAX_LENGTH) { 
//				logger.error("消息长度不合法，断掉连接");
//				GameUtil.closeSessionAndLog(session, iobuffer, "消息长度不合法,长度:" + length);
//				return false;
//			}
//			// 减3是因为前面已经读了个short
//			else if (length >= MsgConstant.RECEIVE_HEAD_LEN && length - 2 <= iobuffer.remaining()) {
//				// 开始校验合法数据
//				// 3.消息号(short 2)
//				short code = iobuffer.getShort();
//				Player player = (Player) session.getAttribute("player");
//				if (player == null && code != 1001 ) {
//					GameUtil.closeSessionAndLog(session, iobuffer, "GamePlayer 为空,消息号:" + iobuffer.getShort());
//					return false;
//				}
//				int oldLimit = iobuffer.limit();
//				iobuffer.limit(iobuffer.position() + length  - MsgConstant.RECEIVE_HEAD_LEN);
//				int bodyLen = iobuffer.remaining();
//				byte[] body = new byte[bodyLen];
//				// 4.消息体
//				iobuffer.get(body);
//				iobuffer.limit(oldLimit);
//				try {
//					Message msg = MessageFactory.getInstance().createMessage(code, body);
//					if (msg != null) {
//						if (logger.isDebugEnabled()) {
//							logger.debug("消息解析完成:" + msg.getClass() + " ip:" + session.getRemoteAddress());
//						}
//						protocolDecoderOutput.write(msg);
//					} else {
//						logger.error("GameDecoder.doDecode() createMessage is null,code is:" + code);
//					}
//				} catch (Exception e) {
//					logger.error("GameDecoder.doDecode()，断掉连接", e);
//					GameUtil.closeSessionAndLog(session, iobuffer, "消息体错误");
//					return false;
//				}
//				return true;
//			} else {
//				iobuffer.reset();
//				logger.debug("消息长度不合法，直接返回，等待下一次解析,长度:" + length + "buffer剩余长度:" + iobuffer.remaining());
//				return false;
//			}
		} catch (Exception e) {
			logger.error("GameDecoder.doDecode()，断掉连接", e);
			e.printStackTrace();
			return false;
		}
	}
	
	public static String getSecWebSocketKey(String req) {
		Pattern p = Pattern.compile("^(Sec-WebSocket-Key:).+",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		Matcher m = p.matcher(req);
		if (m.find()) {
			String foundstring = m.group();
			return foundstring.split(":")[1].trim();
		} else {
			return null;
		}

	}

	

	public static String base64Encode(byte[] input) {
		return new String(Base64Util.encode(input));
	}
	public static String getSecWebSocketAccept(String key) {
		String secKey = getSecWebSocketKey(key);

		String guid = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
		secKey += guid;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(secKey.getBytes("utf-8"), 0, secKey.length());
			byte[] sha1Hash = md.digest();
			secKey = base64Encode(sha1Hash);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		String rtn = "HTTP/1.1 101 Switching Protocols\r\nUpgrade: websocket\r\nConnection: Upgrade\r\nSec-WebSocket-Accept: "
				+ secKey + "\r\n\r\n";
		System.out.println(rtn);
		return rtn;
	}
	
	public static String decode(byte[] receivedDataBuffer)
			throws UnsupportedEncodingException {
		String result = null;

		// 计算非空位置
		int lastStation = receivedDataBuffer.length - 1;
		
		// 利用掩码对org-data进行异或
		int frame_masking_key = 1;
		for (int i = 6; i <= lastStation; i++) {
			frame_masking_key = i % 4;
			frame_masking_key = frame_masking_key == 0 ? 4 : frame_masking_key;
			frame_masking_key = frame_masking_key == 1 ? 5 : frame_masking_key;
			receivedDataBuffer[i] = (byte) (receivedDataBuffer[i] ^ receivedDataBuffer[frame_masking_key]);
		}

		result = new String(receivedDataBuffer, 6, lastStation - 5, "UTF-8");

		return result;

	}
	
}
