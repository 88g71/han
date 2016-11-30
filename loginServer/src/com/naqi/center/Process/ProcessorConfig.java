package com.naqi.center.Process;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置消息的处理器
 * 
 * @author hanfeng
 * 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProcessorConfig {
	/**
	 * 处理器
	 * 
	 * @return
	 */
	Class<? extends MsgProcessor> value();
}
