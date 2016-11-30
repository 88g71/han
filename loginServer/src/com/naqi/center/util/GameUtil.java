package com.naqi.center.util;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameUtil {
	
	private static Logger logger = LoggerFactory.getLogger(GameUtil.class);
	//关闭连接
	public static void closeSessionAndLog(IoSession session, IoBuffer iobuffer, String log) {
		session.closeNow();
		logger.debug("断掉角色连接,原因:" + log + ",帐号IP:{},消息内容:{}", session.getRemoteAddress(), getIoBufferStr(iobuffer));
	}
	//字节
	public static byte[] getIoBufferStr(IoBuffer ioBuffer) {
		int len = ioBuffer.remaining();
		byte[] content = new byte[len];
		ioBuffer.get(content);
		return content;
	}
}
