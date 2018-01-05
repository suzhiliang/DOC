package com.iqcloud.redis.data;

public abstract interface RedisAdapter {
	public abstract void insertMessage(String paramString1, String paramString2)
			throws Exception;
}
