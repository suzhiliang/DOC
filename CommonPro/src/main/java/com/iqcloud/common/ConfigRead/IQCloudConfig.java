package com.iqcloud.common.ConfigRead;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.iqcloud.common.distributedlock.DistributedLock;
import com.iqcloud.common.util.DesEncDec;

/**
 * 配置文件读取类
 * 
 * @author anan-pc
 *
 */

@Component
public class IQCloudConfig {

	// 读取配置文件对象
	private ConfigReader gIniReader = null;

	private static String configPath = "";

	// 单例
	private static IQCloudConfig instance = null;

	/*
	 * 构造函数
	 */
	public IQCloudConfig() {
		initIniReader();
	}

	/*
	 * 初始化读取配置文件对象
	 */
	private void initIniReader() {
		String configFileLocation = "/config.properties";

		Properties prop = new Properties();
		try {
			InputStream is = getClass().getResourceAsStream(configFileLocation);
			prop.load(is);
			if (isWindowsOS())
				configPath = prop.getProperty("win.configPath");
			else {
				configPath = prop.getProperty("lin.configPath");
			}
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.gIniReader = new ConfigReader(configPath);
	}

	/*
	 * 判断是否是window系统
	 */
	private boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}

	/*
	 * 使用内部类，线程安全
	 */
	private static class IQCloudConfigHolder {

		private static IQCloudConfig instance = new IQCloudConfig();
	}

	/*
	 * 获取单例对象
	 */
	private static IQCloudConfig getIntance() {
		/*
		 * if (instance == null) { synchronized (IQCloudConfig.class) { if
		 * (instance == null) { instance = new IQCloudConfig(); } } }
		 */

		if (instance == null) {
			instance = IQCloudConfigHolder.instance;
		}

		return instance;
	}

	/*
	 * 获取配置文件路径
	 */
	@SuppressWarnings("unused")
	private static String getConfigPathIncludePrefix(boolean includePrefix) {
		if (getIntance().isWindowsOS()) {
			if (includePrefix) {
				return ("file:/" + configPath);
			} else {
				return configPath;
			}
		} else {
			if (includePrefix) {
				return ("file:" + configPath);
			} else {
				return configPath;
			}
		}
	}

	/*
	 * 重新加载配置文件
	 */
	public static boolean reloadConfig() {
		boolean bOK = true;
		try {
			getIntance().initIniReader();
		} catch (Exception e) {
			// TODO: handle exception
			bOK = false;
		}

		return bOK;
	}

	/*
	 * 读取配置文件值(要考虑操作系统)
	 */
	public static String getParamValue(String section, String key) {
		String configName = null;

		if (getIntance().isWindowsOS()) { // windows系统
			configName = "win." + key;
		} else { // linux系统
			configName = "lin." + key;
		}

		List<String> valueLists = getIntance().gIniReader.get(section, configName);

		if (valueLists == null) {
			return null;
		} else {
			return valueLists.get(0);
		}
	}

	/*
	 * 不用考虑操作系统
	 */
	public static String getParamValueNotOS(String section, String key) {
		List<String> valueLists = getIntance().gIniReader.get(section, key);

		if (valueLists == null) {
			return null;
		} else {
			return valueLists.get(0);
		}
	}

	/*
	 * 不用考虑操作系统, 是否需要加解密
	 */
	public static String getParamValueNotOSWithDecode(String section, String key, String isDecKey) {
		List<String> valueLists = getIntance().gIniReader.get(section, key);
		List<String> isDecLists = getIntance().gIniReader.get(section, isDecKey);
		boolean isDec = false;
		if ((null != isDecLists) && (isDecLists.size() > 0)) {
			try {
				isDec = Boolean.valueOf(isDecLists.get(0));
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		if (valueLists == null) {
			return null;
		} else {
			String value = valueLists.get(0);
			if (isDec) {
				// 解密
				String decKey = "RUIDA%IQCLOUD@IQBOARD%&&";
				value = DesEncDec.decrypt(value, decKey);
				return value;
			}
			return value;
		}
	}

	/*
	 * 获取链表
	 */
	public static List<String> getStringListNotOS(String section, String key) {
		List<String> valueList = getIntance().gIniReader.get(section, key);
		return valueList;
	}

	/*
	 * 不用考虑操作系统, 是否需要加解密，数据库jdbc配置项专用(多个数据源)
	 */
	public static String getDbJdbcParamValueNotOSWithDecode(String section, String key, String isDecKey) {
		String theValue = "";
		List<String> valueLists = getIntance().gIniReader.get(section, key);
		List<String> isDecLists = getIntance().gIniReader.get(section, isDecKey);
		boolean isDec = false;
		if ((null != isDecLists) && (isDecLists.size() > 0)) {
			try {
				isDec = Boolean.valueOf(isDecLists.get(0));
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		if (valueLists == null) {
			return null;
		} else {
			String value = valueLists.get(0);
			if (isDec) {
				// 解密
				String decKey = "RUIDA%IQCLOUD@IQBOARD%&&";
				value = DesEncDec.decrypt(value, decKey);
				theValue = value;
			}
			theValue = value;
		}

		List<String> zookeeperConLists = getStringListNotOS("dubbo", "zookeeper.address");

		// zookeeper连接地址
		String zookeeperCon = null;
		if (zookeeperConLists.size() > 0) {
			zookeeperCon = zookeeperConLists.get(0);
		} else {
			return null;
		}

		int index = zookeeperCon.indexOf("//");
		zookeeperCon = zookeeperCon.substring(index + 2);

		JdbcValueAction jdbcValueAction = new JdbcValueAction(theValue, key);
		DistributedLock distributedLock = new DistributedLock(jdbcValueAction, true, zookeeperCon,
				"DBConnection:" + key, "jdbc");
		distributedLock.getLock(false);

		System.out.println("Begin...");
		System.out.println("jdbcKey==> " + key);
		System.out.println("jdbcValueAction.getJdbcValue()==> " + jdbcValueAction.getJdbcValue());
		System.out.println("End...");

		return jdbcValueAction.getJdbcValue();
	}
}
