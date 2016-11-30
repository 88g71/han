package com.naqi.entity;

 
 

public class CenterUser 
{
	//
	private String userID="";
	private String account="";
	private String machineCode="";
	private String qqOpenID ="";
	private String wxOpenID ="";
	private String serverID="";
	private long loginDate=0L;
	private String tableNameSuffix="";
	private String playerID="";
	private String playerName="";
	//
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getMachineCode() {
		return machineCode;
	}
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	public String getServerID() {
		return serverID;
	}
	public void setServerID(String serverID) {
		this.serverID = serverID;
	}
	public String getTableNameSuffix() {
		return tableNameSuffix;
	}
	public void setTableNameSuffix(String tableNameSuffix) {
		this.tableNameSuffix = tableNameSuffix;
	}
 
	public String getPlayerID() {
		return playerID;
	}
	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public long getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(long loginDate) {
		this.loginDate = loginDate;
	}
	
	public String getQqOpenID() {
		return qqOpenID; 
	}
	public void setQqOpenID(String qqOpenID) {
		this.qqOpenID = qqOpenID;
	}
	public String getWxOpenID() {
		return wxOpenID;
	}
	public void setWxOpenID(String wxOpenID) {
		this.wxOpenID = wxOpenID;
	}
 
	 
 
}
