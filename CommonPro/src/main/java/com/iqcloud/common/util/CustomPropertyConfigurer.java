package com.iqcloud.common.util;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.iqcloud.common.ConfigRead.IQCloudConfig;

public class CustomPropertyConfigurer extends PropertyPlaceholderConfigurer{

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		// TODO Auto-generated method stub
		
		// dubbo缓存
		HashMap<String, String> addDubboCacheHashMap = new HashMap<String, String>();
		for(Entry<Object, Object> entry:props.entrySet()){
			String key = String.valueOf(entry.getKey());
			if ((key.indexOf("dubbocache") > 0) && (key.indexOf("#") < 0)){
				String addKey = key.substring(4);
				String addValue = IQCloudConfig.getParamValue("dubbo", addKey);
				
				addDubboCacheHashMap.put(addKey, addValue);
			}
		}
		
		for (Entry<String, String> entry : addDubboCacheHashMap.entrySet()) {
			String key = String.valueOf(entry.getKey());
			String value = String.valueOf(entry.getValue());
			props.put(key, value);
		}
		
		super.processProperties(beanFactoryToProcess, props);
	}
}
