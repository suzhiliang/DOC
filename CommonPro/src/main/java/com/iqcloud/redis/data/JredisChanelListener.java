package com.iqcloud.redis.data;

import com.iqcloud.redis.client.JedisTemp;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class JredisChanelListener extends JedisPubSub {
	private RedisAdapter redisAdapter;
	private String consumeName;
	private JedisTemp jt;

	public JredisChanelListener(RedisAdapter redisAdapter, String consumeName) {
		this.redisAdapter = redisAdapter;
		this.jt = new JedisTemp();
		this.consumeName = consumeName;
	}

	public void onMessage(String channel, String message) {
		Jedis res = (Jedis) this.jt.getJedisPool().getResource();
		StringBuilder sbd = new StringBuilder();
		sbd.append("redis:sub:consume:");
		sbd.append(this.consumeName);
		sbd.append(channel);
		res.rpop(sbd.toString());
		try {
			this.redisAdapter.insertMessage(channel, message);
		} catch (Exception e) {
			res.lpush(sbd.toString(), new String[] { message });
		}
	}

	public void onPMessage(String pattern, String channel, String message) {
		System.out.println(channel + "=" + message);
	}

	public void onSubscribe(String channel, int subscribedChannels) {
		Jedis res = (Jedis) this.jt.getJedisPool().getResource();
		StringBuilder sbd = new StringBuilder();
		sbd.append("redis:sub:consume:");
		sbd.append(this.consumeName);
		sbd.append(channel);
		Boolean ifMember = res.sismember("redis:sub:consume:sets",
				sbd.toString());
		if (!ifMember.booleanValue()) {
			res.sadd("redis:sub:consume:sets", new String[] { sbd.toString() });
		} else {
			Long listSize = res.llen(sbd.toString());
			String mesage = null;
			for (long i = 0L; i < listSize.longValue(); i += 1L) {
				try {
					mesage = res.rpop(sbd.toString());
					this.redisAdapter.insertMessage(channel, mesage);
				} catch (Exception e) {
					if (mesage != null)
						res.lpush(sbd.toString(), new String[] { mesage });
				}
			}
		}
	}

	public void onUnsubscribe(String channel, int subscribedChannels) {
		Jedis res = (Jedis) this.jt.getJedisPool().getResource();
		StringBuilder sbd = new StringBuilder();
		sbd.append("redis:sub:consume:");
		sbd.append(this.consumeName);
		sbd.append(channel);
		Boolean ifMember = res.sismember("redis:sub:consume:sets",
				sbd.toString());
		if (!ifMember.booleanValue()) {
			res.srem("redis:sub:consume:sets", new String[] { sbd.toString() });
			res.del(sbd.toString());
		}
	}

	public void onPUnsubscribe(String pattern, int subscribedChannels) {

	}

	public void onPSubscribe(String pattern, int subscribedChannels) {

	}
}
