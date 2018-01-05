package com.iqcloud.redis.data;

public class AdapterFac {
	@SuppressWarnings("unused")
	public <T> void creatAdapterByType(T t) {
		if ((t instanceof MySqlRedisAdapter)) {
			MySqlRedisAdapter localMySqlRedisAdapter = new MySqlRedisAdapter();
		}
	}
}
