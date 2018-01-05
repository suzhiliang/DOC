package com.iqcloud.redis.client;

import redis.clients.jedis.Jedis;

public abstract interface JedisActionNoResult {
	public abstract void action(Jedis paramJedis);
}
