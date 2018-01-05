package com.iqcloud.redis.client;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.JedisSentinelPool;

public enum SingleJedisSentinelPool{
	jredisSentinelPoolINS;

	private JedisSentinelPool jedisSentinelPool;

	private SingleJedisSentinelPool() { singlePool(); }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void singlePool() {
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
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
    
		poolConfig.setTimeBetweenEvictionRunsMillis(rc.getTimeBetweenEvictionRunsMillis().intValue());

		if (rc.getMaxWaitMillis() != null) {
			poolConfig.setMaxWaitMillis(rc.getMaxWaitMillis().intValue());
		}
		
    	String masterName = rc.getMasterName();
    	String slaveInfo = rc.getSlaveInfo();
    	String[] slaves = slaveInfo.split(";");
    	Set sentinels = new HashSet();
    	for (String s : slaves) {
    		sentinels.add(s);
    	}
    	this.jedisSentinelPool = new JedisSentinelPool(masterName, sentinels, poolConfig);
	}
	
	public JedisSentinelPool getJedisSentinelPool() {
		return this.jedisSentinelPool;
	}
	
	public void setJedisSentinelPool(JedisSentinelPool jedisSentinelPool) {
		this.jedisSentinelPool = jedisSentinelPool;
	}
}
