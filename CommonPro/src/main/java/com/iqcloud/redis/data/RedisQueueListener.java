package com.iqcloud.redis.data;

import com.alibaba.fastjson.JSON;
import com.iqcloud.redis.client.JedisTemp;

import java.util.List;

import redis.clients.jedis.Jedis;

public class RedisQueueListener implements Runnable {
	private String queueKey;
	@SuppressWarnings("unused")
	private RedisAdapter redisAdapter;

	@SuppressWarnings({ "rawtypes", "unused" })
	public void run() {
		JedisTemp jt = new JedisTemp();
		while (true) {
			Jedis res = (Jedis) jt.getJedisPool().getResource();
			List sss = res.brpop(this.queueKey);
			RedisDataMessage localRedisDataMessage = (RedisDataMessage) JSON
					.parseObject((String) sss.get(0), RedisDataMessage.class);
		}
	}
}
