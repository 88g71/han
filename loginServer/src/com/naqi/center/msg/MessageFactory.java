package com.naqi.center.msg;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Message;


/**
 * 创建消息工厂类,提供解码器根据消息号和消息体生成 客户端请求的消息
 * 
 * @author hanfeng
 * 
 */
public class MessageFactory {

	private static MessageFactory instance;

	private Map<Short, Class<? extends Message>> messages = new HashMap<Short, Class<? extends Message>>();
	/**
	 * 每个消息对应的延迟时间(单位：毫秒)
	 */
	private Map<Short, Integer> messageDelay = new ConcurrentHashMap<Short, Integer>();
	/**
	 * 消息的默认延时
	 */
	private static final int DEFAULT_DELAY = 500;
	
	private static Logger logger = LoggerFactory.getLogger(MessageFactory.class);

	private MessageFactory() {
	}

	public static MessageFactory getInstance() {
		if (instance == null) {
			instance = new MessageFactory();
		}
		return instance;
	}

	/**
	 * 根据消息号创建一个消息并封装消息体
	 */
	public Message createMessage(short code, byte[] body) {
		try {
			Class<? extends Message> cls = getMessageClass(code);
		    if (cls == null) {
		      throw new RuntimeException("command:" + code + "没有对应的实现类");
		    }
		    Class[] paramsTypes = { byte[].class };
		    Message obj = (Message)cls.getMethod("parseFrom", paramsTypes).invoke(null,body);
			return obj;
		} catch (Exception e) {
			logger.error("MessageFactory.createMessage() ", e);
			return null;
		}
	}

	/**
	 * 注册每个消息号对应的延时
	 * 
	 * @param code
	 * @param delay
	 */
	public void registerMsgDelay(short code, int delay) {
		messageDelay.put(code, delay);
	}

	/**
	 * 取得每个消息号的延时时间
	 * 
	 * @param code
	 * @return
	 */
	public int getMsgDelay(short code) {
		Integer delay = messageDelay.get(code);
		if (delay == null) {
			return DEFAULT_DELAY;
		}
		return delay.intValue();
	}

	public void init() {
		Field[] fields = MessageCodeClazz.get().getFields();
		for (Field field : fields) {
			MsgConfig config = field.getAnnotation(MsgConfig.class);
			if (config != null) {
				try {
					short code = field.getShort(MessageCodeClazz.messageCode);
					Class<? extends Message> clazz = config.message();
					registerMessage(code, clazz);
					registerMsgDelay(code, config.delay());
				} catch (IllegalArgumentException e) {
					logger.error("MessageFactory.init() ", e);
				} catch (IllegalAccessException e) {
					logger.error("MessageFactory.init() ", e);
				}
			}
		}
	}

	/**
	 * 注册消息号对应的消息类
	 * 
	 * @param code
	 * @param clazz
	 */
	public void registerMessage(short code, Class<? extends Message> clazz) {
		messages.put(code, clazz);
	}

	public Class<? extends Message> getMessageClass(int command) {
		return messages.get(command);
	}

}
