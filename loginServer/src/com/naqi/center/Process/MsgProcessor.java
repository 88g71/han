package com.naqi.center.Process;

import com.google.protobuf.Message;
import com.naqi.entity.Player;

/**
 * 消息处理器的基类
 * 
 * @author hanfeng
 * 
 */
public abstract class MsgProcessor {
	public abstract void process(Player player, Message request);

	/**
	 * 是否多线程处理器，如果是多线程处理器，该消息的处理会执行 在多线程中，使用时注意消息是否是线程安全的
	 * 
	 * @return
	 */
	public boolean isThreadProcessor() {
		return false;
	}

	/**
	 * 处理器是否需要权限认证后才能使用，登录前的处理器不需要权限认证
	 * 
	 * @return
	 */
	public boolean isNeedAuth() {
		return false;
	}

	/**
	 * 处理器是否是游戏中的，如果是游戏中的角色必须是Gaming状态
	 * 
	 * @return
	 */
	public boolean isOnGaming() {
		return false;
	}
}
