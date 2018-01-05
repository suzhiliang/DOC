package com.iqcloud.redis.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

public class JredisClient {

	private static Jedis getJedis() {
		Jedis jedis = SingleJredisPool.jredisPoolINS.getJedis();
		return jedis;
	}

	public static void setValueToCache(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key, value);
		} finally {
			if (jedis != null) {
				SingleJredisPool.jredisPoolINS.freeJedis(jedis);
			}
		}
	}

	/**
	 * 设置redis的值，及生命周期
	 * 
	 * @param key
	 * @param value
	 * @param lifecycle
	 * @author xiewj
	 */
	public static void setValueToCache(String key, String value, int lifecycle) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key, value);
			jedis.expire(key, lifecycle);
		} finally {
			if (jedis != null) {
				SingleJredisPool.jredisPoolINS.freeJedis(jedis);
			}
		}
	}

	public static String getValueFromCache(String key) {
		Jedis jedis = null;
		String result = null;
		try {
			jedis = getJedis();
			result = jedis.get(key);
		} finally {
			if (jedis != null) {
				SingleJredisPool.jredisPoolINS.freeJedis(jedis);
			}
		}
		return result;
	}

	/**
	 * 获取redis的值，并设置它的生命周期
	 * 
	 * @param key
	 * @param lifecycle
	 * @return
	 * @author xiewj
	 */
	public static String getValueFromCache(String key, int lifecycle) {
		Jedis jedis = null;
		String result = null;
		try {
			jedis = getJedis();
			result = jedis.get(key);
			jedis.expire(key, lifecycle);
		} finally {
			if (jedis != null) {
				SingleJredisPool.jredisPoolINS.freeJedis(jedis);
			}
		}
		return result;
	}

	/*
	 * 根据模糊主键获取redis值，并设置它的生命周期, 只要有包含key关键字的都可以
	 */
	public static String getValueFromFuzzyKey(String key, int lifeCycle) {
		System.out.println("getValueFromFuzzyKey begin");

		Jedis jedis = null;
		String result = null;
		try {
			jedis = getJedis();

			Set<String> keySet = jedis.keys("*" + key + "*");

			System.out.println("\"*\" + key + \"*\"===> " + ("*" + key + "*"));
			System.out.println("keySet==> " + JSON.toJSONString(keySet));

			String keyValue = "";
			Iterator<String> it = keySet.iterator();
			if (it.hasNext()) {
				keyValue = it.next();
				result = jedis.get(keyValue);
				if (lifeCycle >= 0) {
					jedis.expire(keyValue, lifeCycle);
				}
			}
		} finally {
			// TODO: handle exception
			if (jedis != null) {
				SingleJredisPool.jredisPoolINS.freeJedis(jedis);
			}
		}

		System.out.println("getValueFromFuzzyKey end");

		return result;
	}

	/*
	 * 根据模糊主键获取redis值，并设置它的什么周期, 只要有包含key关键字的都可以
	 */
	public static String getValueFromFuzzyKey(String key) {
		Jedis jedis = null;
		String result = null;
		try {
			jedis = getJedis();

			Set<String> keySet = jedis.keys("*" + key + "*");
			String keyValue = "";
			Iterator<String> it = keySet.iterator();
			if (it.hasNext()) {
				keyValue = it.next();
				result = jedis.get(keyValue);
			}

		} finally {
			// TODO: handle exception
			if (jedis != null) {
				SingleJredisPool.jredisPoolINS.freeJedis(jedis);
			}
		}

		return result;
	}

	/*
	 * 删除(模糊匹配key)
	 */
	public static boolean deleteFromFuzzyKey(String key) {
		boolean result = true;
		Jedis jedis = null;
		try {
			jedis = getJedis();

			Set<String> keySet = jedis.keys("*" + key + "*");
			String keyValue = "";
			Iterator<String> it = keySet.iterator();
			if (it.hasNext()) {
				keyValue = it.next();
				jedis.del(keyValue);
			}

		} catch (Exception e) {
			// TODO: handle exception
			result = false;
		} finally {
			// TODO: handle exception
			if (jedis != null) {
				SingleJredisPool.jredisPoolINS.freeJedis(jedis);
			}
		}

		return result;
	}

	/*
	 * 删除
	 */
	public static boolean delete(String key) {
		boolean result = true;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.del(key);
		} catch (Exception e) {
			// TODO: handle exception
			result = false;
		} finally {
			// TODO: handle exception
			if (jedis != null) {
				SingleJredisPool.jredisPoolINS.freeJedis(jedis);
			}
		}

		return result;
	}

	/*
	 * 从模糊的key获取完整的key
	 */
	public static String getFullKeyFromFuzzyKey(String key) {
		Jedis jedis = null;
		String result = null;
		try {
			jedis = getJedis();

			Set<String> keySet = jedis.keys("*" + key + "*");
			Iterator<String> it = keySet.iterator();
			if (it.hasNext()) {
				result = it.next();
			}
		} finally {
			// TODO: handle exception
			if (jedis != null) {
				SingleJredisPool.jredisPoolINS.freeJedis(jedis);
			}
		}

		return result;
	}

	public static void setHashMapToCache(String mapName, Map<String, String> map) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.hmset(mapName, map);
		} finally {
			if (jedis != null)
				SingleJredisPool.jredisPoolINS.freeJedis(jedis);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, String> getHashMapFromCache(String mapName) {
		Jedis jedis = null;
		Map map = new HashMap();
		try {
			jedis = getJedis();
			Iterator iter = jedis.hkeys(mapName).iterator();
			while (iter.hasNext()) {
				String mapkey = (String) iter.next();
				List value = jedis.hmget(mapName, new String[] { mapkey });
				String mapValue = (String) value.get(0);
				map.put(mapkey, mapValue);
			}
		} finally {
			if (jedis != null) {
				SingleJredisPool.jredisPoolINS.freeJedis(jedis);
			}
		}
		return map;
	}

	public static void setListToCache(String listName, List<String> lists) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			for (String s : lists)
				jedis.lpush(listName, new String[] { s });
		} finally {
			if (jedis != null)
				SingleJredisPool.jredisPoolINS.freeJedis(jedis);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<String> getListFromCache(String listName) {
		Jedis jedis = null;
		List lists = new ArrayList();
		try {
			jedis = getJedis();
			List lists2 = jedis.lrange(listName, 0L, -1L);
			lists.addAll(lists2);
		} finally {
			if (jedis != null) {
				SingleJredisPool.jredisPoolINS.freeJedis(jedis);
			}
		}
		return lists;
	}

	public static void delInfo(String name) {
		Jedis jedis = null;
		try {
			String key = getFullKeyFromFuzzyKey(name);
			if (null != key) {
				jedis = getJedis();
				jedis.del(key);
			}
		} finally {
			if (jedis != null)
				SingleJredisPool.jredisPoolINS.freeJedis(jedis);
		}
	}
}
