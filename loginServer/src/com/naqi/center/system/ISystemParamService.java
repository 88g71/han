/*
 * File name : ISystemParamService.java
 * Created on : Mar 26, 2012 7:30:29 PM
 * Creator : 韩峰
 */
package com.naqi.center.system;

/**
 * <pre>
 * Description : 系统参数
 * @author 韩峰
 * </pre>
 */
public interface ISystemParamService {	
	public final String DEBUG_MODE = "DEBUG_MODE";						//调试模式开启
	public final String ROLE_INIT_VIPEXP = "ROLE_INIT_VIPEXP";			//角色初始化vip exp
	public final String ROLE_INIT_VIPLV = "ROLE_INIT_VIPLV";			//角色初始化vip lv
	public final String SERVICE_NAME ="SERVICE_NAME";                   //服务器名称
	public final String ITF_PARTNER_LIST = "partners";					//合作伙伴列表
	public final String FIGHT_RESULT_PATH="FIGHT_RESULT_PATH";          //设置战报路径

	
	
	
	/**
	 * 得到系统参数
	 * @param key
	 * @return
	 */
	public String getStringSystemParam(String key);
	
	/**
	 * 得到系统参数
	 * @param key
	 * @return
	 */
	public Integer getIntegerSystemParam(String key);
	/**
	 * Double
	 * @param key
	 * @return
	 */
	public Double getDoubleSystemParam(String key);
	/**
	 * Float
	 * @param key
	 * @return
	 */
	public Float getFloatSystemParam(String key);
	/**
	 * 得到系统参数
	 * @param key
	 * @return
	 */
	public Long getLongSystemParam(String key);
		
	/**
	 * 查询enum的Id
	 * @param enumName
	 * @return
	 */
	public Integer getEnmuId(String enumName);
	
	/**
	 * 查询枚举描述
	 * @param enumName
	 * @return
	 */
	public String getEnmuDesc(String enumName);
	
	/**
	 * 根据次数得到所消耗的资源
	 * @param num
	 * @param type
	 * @return
	 */
	public Integer getResourceByNum(int num,int type);
}


