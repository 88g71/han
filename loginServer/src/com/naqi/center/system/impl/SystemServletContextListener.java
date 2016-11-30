/*
 * File name : SystemServletContextListener.java
 * Created on : Jun 14, 2012 1:14:38 PM
 * Creator : 韩峰
 */
package com.naqi.center.system.impl;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.gxlu.mercury.extension.core.MercuryExtensionPlatform;
import com.naqi.center.msg.MessageCodeClazz;
import com.naqi.center.msg.MessageFactory;
import com.naqi.center.msg.proto.CenterMsgCodeConstant;
import com.naqi.center.system.ISystemExtension;

/**
 * <pre>
 * Description : 监听ServletContext事件
 * @author 韩峰
 * </pre>
 */
public class SystemServletContextListener extends org.springframework.web.context.ContextLoaderListener implements ServletContextListener{
	
	public static boolean isStartUp = false;
	
	private Logger logger = LoggerFactory.getLogger(SystemServletContextListener.class);
	
	
	public void contextDestroyed(ServletContextEvent event) {
		isStartUp = false;
		logger.info("游戏服务器开始关闭。。。");
		Object[] systemExtensionArray = MercuryExtensionPlatform.getExtensionInstance(ISystemExtension.class.getName());
		if (systemExtensionArray != null && systemExtensionArray.length > 0) {
			for (Object obj : systemExtensionArray) {
				try{
					ISystemExtension systemExtension = (ISystemExtension) obj;
					systemExtension.onShutDown();
				}catch(Exception e){
					logger.error("shutDown error method",e);
				}
			}
		}
		super.contextDestroyed(event);
	}
	
	@Transactional
	public void contextInitialized(ServletContextEvent event) {
		try{
		super.contextInitialized(event);
		Object[] systemExtensionArray = MercuryExtensionPlatform.getExtensionInstance(ISystemExtension.class.getName());
		if (systemExtensionArray != null && systemExtensionArray.length > 0) {
			for (Object obj : systemExtensionArray) {
				ISystemExtension systemExtension = (ISystemExtension) obj;
				systemExtension.onStartUp();
			}
		}
		MessageCodeClazz.set(CenterMsgCodeConstant.class);
		MessageFactory.getInstance().init();
		isStartUp = true;
		logger.info("游戏服务器启动完毕。。。");
		}catch(Exception e){
			logger.error("contextInitialized error method",e);
		}
	}
}

