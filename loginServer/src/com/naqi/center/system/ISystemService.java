/*
 * File name : ISystemService.java
 * Created on : Nov 21, 2012 10:42:51 AM
 * Creator : 韩峰
 */
package com.naqi.center.system;

import java.util.Date;

/**
 * <pre>
 * Description : TODO
 * @author 韩峰
 * </pre>
 */
public interface ISystemService {
	
	/**
	 * 获得上次停服日期
	 * @return
	 */
	public Date getLastShutdownDate();
}

