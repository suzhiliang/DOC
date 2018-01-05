package com.iqcloud.common.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态选择数据库源
 * 
 * @author
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	/**
	 * 返回选择的数据源的名字
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		// TODO Auto-generated method stub
		return DataSourceSwitcher.getDataSource();
	}

}
