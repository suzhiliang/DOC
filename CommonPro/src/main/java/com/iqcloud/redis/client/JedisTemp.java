package com.iqcloud.redis.client;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

public class JedisTemp
{
	private JedisPool jedisPool;

	public JedisTemp(){
		this.jedisPool = SingleJredisPool.jredisPoolINS.getJedisPool();
	}

	public <T> T execute(JedisAction<T> jedisAction) throws JedisException{
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = SingleJredisPool.jredisPoolINS.getJedis();
			return jedisAction.action(jedis);
		} catch (JedisConnectionException e) {
			broken = true;
			throw e;
		} finally {
			closeResource(jedis, broken);
		}
	}

	public void execute(JedisActionNoResult jedisActionNoResult){
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = SingleJredisPool.jredisPoolINS.getJedis();
			jedisActionNoResult.action(jedis);
		} catch (JedisConnectionException e) {
			broken = true;
			throw e;
		} finally {
			closeResource(jedis, broken);
		}
	}

	protected void closeResource(Jedis jedis, boolean connectionBroken){
		if (jedis != null){
		  	try {
			  if (connectionBroken)
				  this.jedisPool.returnBrokenResource(jedis);
			  else
				  this.jedisPool.returnResource(jedis);
		  	}
      		catch (Exception e) {
      			if ((jedis != null) && (jedis.isConnected())){
      				try {
      					try {
      						jedis.quit();
      					} catch (Exception localException1) {}
      					jedis.disconnect();
      				}
      				catch (Exception localException2){}
      			}
      		}
		}
	}

	public void flushDB() {
		execute(new JedisActionNoResult() {
			public void action(Jedis jedis) {
				jedis.flushDB();
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String get(final String key){
		return (String)execute(new JedisAction() {
			public String action(Jedis jedis) {
				return jedis.get(key);
			}});
	}

	public JedisPool getJedisPool() {
		return this.jedisPool;
	}
}
