package com.iqcloud.common.datasource;

import org.springframework.util.Assert;

/**
 * 通过线程封闭保证数据源名称安全
 * 
 * @author
 *
 */
public class DataSourceSwitcher {
	private final static ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setDataSource(String dataSource) {
		Assert.notNull(dataSource, "dataSource cannot be null");
		contextHolder.set(dataSource);
	}

	public static String getDataSource() {
		return (String) contextHolder.get();
	}

	public static void setMaster() {
		clearDataSource();
	}

	public static void clearDataSource() {
		contextHolder.remove();
	}
}
