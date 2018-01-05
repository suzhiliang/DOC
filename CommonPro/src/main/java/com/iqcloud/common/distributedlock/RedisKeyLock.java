package com.iqcloud.common.distributedlock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

/*
 * 基于redis的分布式锁
 */
public class RedisKeyLock {

	private final static long ACCQUIRE_LOCK_TIMEOUT_IN_MS = 10 * 1000;
	private final static int EXPIRE_IN_SECOND = 60;// 锁失效时间
	private final static long WAIT_INTERVAL_IN_MS = 100;
	private static RedisKeyLock lock;
	private JedisPool jedisPool;

	private RedisKeyLock(JedisPool pool) {
		this.jedisPool = pool;
	}

	public static RedisKeyLock getInstance(JedisPool pool) {
		if (lock == null) {
			lock = new RedisKeyLock(pool);
		}
		return lock;
	}

	/*
	 * 获取锁
	 */
	public boolean lock(final String redisKey) {
		Jedis resource = null;
		boolean flag = false;
		try {
			long now = System.currentTimeMillis();
			resource = jedisPool.getResource();
			long timeoutAt = now + ACCQUIRE_LOCK_TIMEOUT_IN_MS;

			while (true) {
				String expireAt = String.valueOf(now + EXPIRE_IN_SECOND * 1000);
				long ret = resource.setnx(redisKey, expireAt);
				if (ret == 1) {// 已获取锁
					flag = true;
					break;
				} else {// 未获取锁，重试获取锁
					String oldExpireAt = resource.get(redisKey);
					if (oldExpireAt != null && Long.parseLong(oldExpireAt) < now) {
						oldExpireAt = resource.getSet(redisKey, expireAt);
						if (Long.parseLong(oldExpireAt) < now) {
							flag = true;
							break;
						}
					}
				}
				if (timeoutAt < now) {
					break;
				}
				Thread.sleep(WAIT_INTERVAL_IN_MS);
			}
			if (!flag) {
				throw new RuntimeException("canot acquire lock now ...");
			}
		} catch (JedisException je) {
			je.printStackTrace();
			je.printStackTrace();
			if (resource != null) {
				jedisPool.returnBrokenResource(resource);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resource != null) {
				jedisPool.returnResource(resource);
			}
		}
		return flag;
	}

	/*
	 * 释放锁
	 */
	public boolean unlock(final String redisKey) {
		Jedis resource = null;
		boolean bOK = false;
		try {
			resource = jedisPool.getResource();

			// 判断是否失效，如果失效（超时）不删除，不失效（没有超时）就删除
			// 特别要注意，按我理解的，即使是这样“加保险”的判断，在高并发下，应该还是会出问题，比如：
			// C0：为当前调用该函数端，C1为另一应用在获取这把锁, 高并发下会出现如下情况:
			// 第一步：C0，判断超时
			// 第二步：C0，发送“删除该键”的命令
			// 第三步：C1，判断超时
			// 第四步：C1，发送“设置锁时间”的命名
			// 第五步：C1：设置成功，获取锁
			// 第六步：C0：删除锁成功
			// 如果按照这个顺序执行，那么就会出问题，最后C0把正常的锁给删了（C1的锁）
			long now = System.currentTimeMillis();
			String oldExpireAt = resource.get(redisKey);
			if (null != oldExpireAt) {
				if ((oldExpireAt != null) && (Long.parseLong(oldExpireAt) < now)) {// 超时不删除

				} else {
					resource.del(redisKey);
				}
			}

			// resource.del(redisKey);

			bOK = true;
		} catch (JedisException je) {
			je.printStackTrace();
			if (resource != null) {
				jedisPool.returnBrokenResource(resource);
			}
			bOK = false;
		} catch (Exception e) {
			e.printStackTrace();
			bOK = false;
		} finally {
			if (resource != null) {
				jedisPool.returnResource(resource);
			}
		}

		return bOK;
	}
}
