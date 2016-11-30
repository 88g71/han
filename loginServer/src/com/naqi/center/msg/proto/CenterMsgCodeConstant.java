package com.naqi.center.msg.proto;

import com.naqi.center.Process.LoginProcessor1001;
import com.naqi.center.Process.ProcessorConfig;
import com.naqi.center.Process.RegProcessor;
import com.naqi.center.msg.MsgConfig;


public class CenterMsgCodeConstant {

	/**
	 * 帐号登陆
	 */
	@MsgConfig(message = ProtoBuf.CLCLogin10001.class)
	@ProcessorConfig(LoginProcessor1001.class)
	public static final short C_LOGIN = 1001;
	/**
	 * 帐号注册
	 */
	@MsgConfig(message = ProtoBuf.CLCLogin10001.class)
	@ProcessorConfig(RegProcessor.class)
	public static final short C_REG = 1002;
}
