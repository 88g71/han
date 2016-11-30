package com.naqi.center.msg;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.protobuf.Message;


/**
 * 配置消息号对应的消息类，消息的时间间隔
 * 
 * @author hanfeng
 * 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MsgConfig {

	/**
	 * 消息号对应的消息
	 * 
	 * @return
	 */
	public Class<? extends Message> message();

	/**
	 * 该消息允许的最小时间间隔
	 * 
	 * @return
	 */
	public int delay() default 190;
}
