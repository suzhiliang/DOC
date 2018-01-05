package com.iqcloud.redis.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadConfigUtil
{
	private String configLocation;
	private static ReadConfigUtil readConfigUtil = null;

	private ReadConfigUtil(String configLocation) { this.configLocation = configLocation; }

	public static ReadConfigUtil getInstance(String configLocation){
		if (readConfigUtil == null) {
			readConfigUtil = new ReadConfigUtil(configLocation);
		}
		return readConfigUtil;
	}
	
	public String readInfoByKey(String key) {
		File file = new File(this.configLocation);
		String configValue = null;
		try {
			InputStream fis = new FileInputStream(file);
			Properties p = new Properties();
			p.load(fis);
			configValue = (String)p.get(key);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return configValue;
	}
}
