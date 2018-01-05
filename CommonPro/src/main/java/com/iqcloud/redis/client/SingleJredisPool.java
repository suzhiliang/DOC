package com.iqcloud.redis.client;

import com.iqcloud.common.ConfigRead.IQCloudConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public enum SingleJredisPool {
	jredisPoolINS;

	private JedisPool jedisPool;

	private SingleJredisPool() {
		singleJedisPool();
	}

	private void singleJedisPool() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		RedisConfigInfo rc = new RedisConfigInfo();

		if (rc.getMaxTotal() != null) {
			poolConfig.setMaxTotal(rc.getMaxTotal().intValue());
		}

		if (rc.getMaxIdle() != null) {
			poolConfig.setMaxIdle(rc.getMaxIdle().intValue());
		}

		if (rc.getMinIdle() != null) {
			poolConfig.setMinIdle(rc.getMinIdle().intValue());
		}

		if (rc.getTimeBetweenEvictionRunsMillis() != null) {
			poolConfig.setTimeBetweenEvictionRunsMillis(rc.getTimeBetweenEvictionRunsMillis().intValue());
		}
		// poolConfig.setTimeBetweenEvictionRunsMillis(rc.getTimeBetweenEvictionRunsMillis().intValue());

		if (rc.getMaxWaitMillis() != null) {
			poolConfig.setMaxWaitMillis(rc.getMaxWaitMillis().intValue());
		}

		this.jedisPool = new JedisPool(poolConfig, rc.getHost(), rc.getPort().intValue());
	}

	public JedisPool getJedisPool() {
		return this.jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public Jedis getJedis() {
		Jedis jedis = (Jedis) getJedisPool().getResource();

		// redis 密码
		if (null != jedis) {
			String passwd = IQCloudConfig.getParamValue("redis", "passwd");
			if ((null != passwd) && (!"".equals(passwd.trim()))) {
				jedis.auth(passwd);
			}
		}

		return jedis;
	}

	public void freeJedis(Jedis jedis) {
		getJedisPool().returnResource(jedis);
	}
}
