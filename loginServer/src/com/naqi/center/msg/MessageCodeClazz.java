package com.naqi.center.msg;

public class MessageCodeClazz {

	public static ThreadLocal<Class<?>> messageCode = new ThreadLocal<Class<?>>();

	public static void set(Class<?> msc) {
		messageCode.set(msc);
	}

	public static Class<?> get() {
		return messageCode.get();
	}
}
