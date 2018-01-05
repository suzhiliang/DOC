package com.iqcloud.common.language;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.iqcloud.common.consts.ConstantRedis;
import com.iqcloud.redis.client.SingleJredisPool;

import redis.clients.jedis.Jedis;

/*
 * 多语言操作类
 */
public class IQMultiLanguage {

	/**
	 * 批量获取
	 * 
	 * @param languageCode
	 *            : 语言类型编码
	 * @param functionCode
	 *            : 功能模块编码
	 * @return
	 */
	public static HashMap<String, String> getBatch(String languageCode,
			String functionCode) {
		Jedis jedis = null;
		HashMap<String, String> hashMap = null;

		try {
			jedis = SingleJredisPool.jredisPoolINS.getJedis();
			Set<String> keys = jedis.keys(ConstantRedis.Language_Root + ":"
					+ languageCode + ":" + functionCode + ":" + "*");
			String[] keyArray = keys.toArray(new String[keys.size()]);

			if ((null != keyArray) && (keyArray.length > 0)) {
				List<String> listValue = jedis.mget(keyArray);
				if ((null != listValue)
						&& (keyArray.length == listValue.size())) {
					hashMap = new HashMap<String, String>();
					for (int i = 0; i < keyArray.length; i++) {
						String key = keyArray[i];
						String value = listValue.get(i);

						String[] keyItem = key.split(":");
						String theKey = keyItem[keyItem.length - 1];

						hashMap.put(theKey, value);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				SingleJredisPool.jredisPoolINS.freeJedis(jedis);
			}
		}

		return hashMap;
	}

	/**
	 * 单个获取
	 * 
	 * @param languageCode
	 *            : 语言类型编码
	 * @param functionCode
	 *            : 功能模块编码
	 * @param code
	 *            : 节点编码
	 * @return
	 */
	public static String getItem(String languageCode, String functionCode,
			String code) {
		Jedis jedis = null;
		String value = null;
		try {
			jedis = SingleJredisPool.jredisPoolINS.getJedis();
			String key = ConstantRedis.Language_Root + ":" + languageCode + ":"
					+ functionCode + ":" + code;
			value = jedis.get(key);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				SingleJredisPool.jredisPoolINS.freeJedis(jedis);
			}
		}

		return value;
	}
}
