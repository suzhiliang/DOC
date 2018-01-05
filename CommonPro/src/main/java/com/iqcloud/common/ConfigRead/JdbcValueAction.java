package com.iqcloud.common.ConfigRead;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.iqcloud.common.distributedlock.Action;
import com.iqcloud.redis.client.SingleJredisPool;
import redis.clients.jedis.Jedis;

public class JdbcValueAction implements Action {

	private String jdbcValue;

	private String jdbcValueOriginal;
	private String jdbcKey;

	public JdbcValueAction(String sJdbcValue, String sJdbcKey) {
		jdbcValueOriginal = sJdbcValue;
		jdbcKey = sJdbcKey;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		try {
			// 用分号隔开
			String[] theValueArray = jdbcValueOriginal.split(";");

			if (theValueArray.length <= 0) {
				return;
			}

			jdbcValue = null;
			Jedis jedis = null;
			try {
				jedis = SingleJredisPool.jredisPoolINS.getJedis();
				String redisKeyRoot = "IQCloud:DB:" + jdbcKey + ":";
				int count = 0;
				Set<String> keys = jedis.keys(redisKeyRoot + "*");
				if (null == keys) {// redis不存在，这是第一次添加数据源
					jdbcValue = theValueArray[0];
					count = 1;
				} else {
					List<String> keyList = new ArrayList<String>();
					keyList.addAll(keys);

					// 将每个连接数据源的连接数放置在哈希表里
					HashMap<String, String> hashMapRedis = new HashMap<String, String>();
					for (int i = 0; i < keyList.size(); i++) {
						String key = keyList.get(i);
						String value = jedis.get(key);
						hashMapRedis.put(key, value);
					}

					// 将数字组内的内容放置在里面
					HashMap<String, Integer> hashMapCount = new HashMap<String, Integer>();
					for (int i = 0; i < theValueArray.length; i++) {
						String jdbcKey = theValueArray[i];
						String key = redisKeyRoot + jdbcKey;
						if (hashMapRedis.containsKey(key)) {
							String valueStr = hashMapRedis.get(key);
							hashMapCount.put(jdbcKey, Integer.valueOf(valueStr));
						} else {
							jdbcValue = jdbcKey;
							break;
						}
					}

					int countValue = -999;
					if (null == jdbcValue) {
						Iterator iter = hashMapCount.entrySet().iterator();
						while (iter.hasNext()) {
							Map.Entry entry = (Map.Entry) iter.next();
							Object key = entry.getKey();
							Object val = entry.getValue();

							int tmpValue = Integer.valueOf(val.toString());
							if (countValue < 0) {
								jdbcValue = key.toString();
								countValue = tmpValue;
							} else if (tmpValue < countValue) {
								jdbcValue = key.toString();
								countValue = tmpValue;
							}
						}
					}

					if (countValue > 0) {
						count = countValue + 1;
					} else {
						count++;
					}
				}

				String key = redisKeyRoot + jdbcValue;
				jedis.set(key, String.valueOf(count));
			} finally {
				if (jedis != null) {
					SingleJredisPool.jredisPoolINS.freeJedis(jedis);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public String getJdbcValue() {
		return jdbcValue;
	}
}
