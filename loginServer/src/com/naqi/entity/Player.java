package com.naqi.entity;

public class Player {

	private String remoteAddress;
	private int msgIndex;			//消息序号
	private String account;
	private String password;
	

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public int getMsgIndex() {
		return msgIndex;
	}

	public void setMsgIndex(int msgIndex) {
		this.msgIndex = msgIndex;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
