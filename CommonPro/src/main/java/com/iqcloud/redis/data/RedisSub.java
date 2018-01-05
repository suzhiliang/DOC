package com.iqcloud.redis.data;

import com.iqcloud.redis.client.JedisTemp;

import redis.clients.jedis.Jedis;

public class RedisSub {
	private String[] channels;
	private JedisTemp jt;
	private RedisAdapter redisAdapter;
	private String consumeName;

	public RedisSub(String[] channels, RedisAdapter redisAdapter,
			String consumeName) {
		this.channels = channels;
		this.jt = new JedisTemp();
		this.redisAdapter = redisAdapter;
		this.consumeName = consumeName;
	}

	public void subChannel() {
		Jedis res = (Jedis) this.jt.getJedisPool().getResource();
		JredisChanelListener jcl = new JredisChanelListener(this.redisAdapter,
				this.consumeName);
		res.subscribe(jcl, this.channels);
	}
}
