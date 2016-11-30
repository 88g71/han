package com.naqi.center.Process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Message;
import com.naqi.center.msg.proto.ProtoBuf;
import com.naqi.entity.CenterUser;
import com.naqi.entity.Player;


/**
 * 帐号注册检测
 * 
 * @author hanfeng
 * 
 */
public class RegProcessor extends MsgProcessor {

	private final Logger logger = LoggerFactory.getLogger(RegProcessor.class);

	@Override
	public void process(Player player, Message request) {
		ProtoBuf.CLCLogin10001 loginMessage = (ProtoBuf.CLCLogin10001)request;
		logger.debug(player.getRemoteAddress());
		String name = loginMessage.getAccount().trim();
		String password = loginMessage.getAccount().trim();
		if (logger.isDebugEnabled()) {
			logger.debug("注册帐号,帐号:{},密码:{}", name, password);
		}
		
		
		
		
	}


	@Override
	public boolean isThreadProcessor() {
		return true;
	}

}
