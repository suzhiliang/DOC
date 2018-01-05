package com.iqcloud.redis.data;

import java.util.Set;

import com.iqcloud.redis.client.JedisTemp;

import redis.clients.jedis.Jedis;

public class RedisPub {
	private JedisTemp jt;

	public RedisPub() {
		this.jt = new JedisTemp();
	}

	public void pubChannel(String channel, String message) {
		Jedis res = (Jedis) this.jt.getJedisPool().getResource();
		res.publish(channel, message);
		Set<String> sets = res.smembers("redis:sub:consume:sets");
		for (String s : sets)
			res.lpush(s, new String[] { message });
	}
}
