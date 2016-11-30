package com.naqi.center.Process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Message;
import com.naqi.center.msg.proto.ProtoBuf;
import com.naqi.entity.Player;


/**
 * 帐号注册检测
 * 
 * @author hanfeng
 * 
 */
public class LoginProcessor1001 extends MsgProcessor {

	private final Logger logger = LoggerFactory.getLogger(LoginProcessor1001.class);

	@Override
	public void process(Player player, Message request) {
		ProtoBuf.CLCLogin10001 loginMessage = (ProtoBuf.CLCLogin10001)request;
		logger.debug(player.getRemoteAddress());
		if(loginMessage.getAccount() != null && loginMessage.getPassword() != null && "123".equals(loginMessage.getPassword())){
			ProtoBuf.CCLLogin10002.Builder loginBack = ProtoBuf.CCLLogin10002.newBuilder();
			loginBack.setRet(1);
			
			player = new Player();
			player.setAccount(loginMessage.getAccount());
			
			
			ProtoBuf.PlayerInfo.Builder playerInfo = ProtoBuf.PlayerInfo.newBuilder();
			playerInfo.setAccount(player.getAccount());
			playerInfo.setGold(10000);
			playerInfo.setName("77k");
			playerInfo.setPlayerIndex(10000);
			
		}
		
	}


	@Override
	public boolean isThreadProcessor() {
		return true;
	}

}
