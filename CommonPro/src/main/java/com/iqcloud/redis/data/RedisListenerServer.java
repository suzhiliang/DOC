package com.iqcloud.redis.data;

public class RedisListenerServer {
	public void startServer() {
		Thread td = new Thread(new RedisQueueListener());
		td.start();
	}
}
