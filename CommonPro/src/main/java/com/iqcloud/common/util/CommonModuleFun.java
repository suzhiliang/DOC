package com.iqcloud.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class CommonModuleFun {

	/*
	 * 由请求对象获取请求参数列表
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> getParameterMap(HttpServletRequest request) {
		Map properties = request.getParameterMap();
		// 返回值Map
		Map<String, String> returnMap = new HashMap<String, String>();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}

	/*
	 * 获取动态参数列表(不包含特权码参数accessToken)
	 */
	public static String getUrlDynamicWithOutAccessToken(HttpServletRequest request) {
		// 获取请求参数
		Map<String, String> paramMap = getParameterMap(request);
		String newParam = "";
		for (String key : paramMap.keySet()) {
			if ("accessToken".equals(key)) {
				continue;
			}

			System.out.println(key + " = " + paramMap.get(key));

			if ("".equals(newParam)) {
				newParam = newParam + key + "=" + paramMap.get(key);
			} else {
				if ("referer".equals(key)) {
					newParam = key + "=" + paramMap.get(key) + "&" + newParam;
				} else {
					newParam = newParam + "&" + key + "=" + paramMap.get(key);
				}
			}
		}

		if (!("".equals(newParam))) {
			newParam = "?" + newParam;
		}

		// if ("?".equals(newParam)) {
		// newParam = "";
		// }

		// Map<String, String> paramMap = getParameterMap(request);
		// String newParam = "?";
		// for (String key: paramMap.keySet()) {
		// if ("accessToken".equals(key)){
		// continue;
		// }
		// if ("?".equals(newParam)){
		// newParam = newParam + key + "=" + paramMap.get(key);
		// }else{
		// newParam = newParam + "&" + key + "=" + paramMap.get(key);
		// }
		// }
		//
		// if ("?".equals(newParam)) {
		// newParam = "";
		// }

		return newParam;
	}
}
