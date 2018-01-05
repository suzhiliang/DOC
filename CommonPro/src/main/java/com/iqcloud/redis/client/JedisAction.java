package com.iqcloud.redis.client;

import redis.clients.jedis.Jedis;

public abstract interface JedisAction<T> {
	public abstract T action(Jedis paramJedis);
}
