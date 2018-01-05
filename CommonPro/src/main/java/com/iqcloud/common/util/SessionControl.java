package com.iqcloud.common.util;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iqcloud.common.consts.ConstantLanguage;
import com.iqcloud.common.consts.ConstantRedis;
import com.iqcloud.common.consts.ConstantSession;
import com.iqcloud.common.dto.IQCloudInfoDto;
import com.iqcloud.common.dto.UserBaseInfoDto;
import com.iqcloud.redis.client.JredisClient;
import com.iqcloud.redis.client.SingleJredisPool;
import redis.clients.jedis.Jedis;

public class SessionControl {

	private static RestTemplate restTemplate = new RestTemplate();
	private static String url = FileControl.getPath("main", "IQService");

	/*
	 * 取出session值
	 */
	public static UserBaseInfoDto getUserInfoByRedis(HttpServletRequest request) {
		UserBaseInfoDto userBaseInfo = null;
		Cookie[] cookies = request.getCookies();
		if ((cookies != null) && (cookies.length > 0)) {
			for (Cookie cookie : cookies) {
				if ("userInfo".equals(cookie.getName())) {
					String userKey = cookie.getValue();
					int lifecycle = LifecycleConfig.getLifecycle();
					String userBaseInfoString = JredisClient.getValueFromFuzzyKey(userKey, lifecycle);
					if (null != userBaseInfoString) {
						userBaseInfo = (UserBaseInfoDto) JSONObject.parseObject(userBaseInfoString,
								UserBaseInfoDto.class);
					}
					break;
				}
			}
		}

		return userBaseInfo;
	}

	/*
	 * 登出
	 */
	public static boolean logout(HttpServletRequest request, UserBaseInfoDto userInfo) {
		UserBaseInfoDto userBaseInfo = null;
		if (null != userInfo) {
			userBaseInfo = userInfo;
		} else {
			userBaseInfo = getUserInfoByRedis(request);
		}
		userInfo = userBaseInfo;

		boolean result = false;
		if (null != userBaseInfo) {
			result = JredisClient.delete(userBaseInfo.getKey());
		} else {
			Cookie[] cookies = request.getCookies();
			if ((cookies != null) && (cookies.length > 0)) {
				for (Cookie cookie : cookies) {
					if ("userInfo".equals(cookie.getName())) {
						String userKey = cookie.getValue();
						result = JredisClient.deleteFromFuzzyKey(userKey);
						break;
					}
				}
			}
		}

		return result;
	}

	/*
	 * 通过特权码获取session值
	 */
	public static UserBaseInfoDto getUserInfoByAccessToken(String accessToken) {
		UserBaseInfoDto userBaseInfo = null;
		int lifecycle = LifecycleConfig.getLifecycle();
		String userBaseInfoString = JredisClient.getValueFromFuzzyKey(accessToken, lifecycle);
		if (null != userBaseInfoString) {
			userBaseInfo = (UserBaseInfoDto) JSONObject.parseObject(userBaseInfoString, UserBaseInfoDto.class);
		}
		return userBaseInfo;
	}

	/*
	 * 获取用户其他信息值
	 */
	public static Object getUserInfoOtherValue(HttpServletRequest request, String key, UserBaseInfoDto userInfo) {
		UserBaseInfoDto userBaseInfo = null;
		if (null != userInfo) {
			userBaseInfo = userInfo;
		} else {
			userBaseInfo = getUserInfoByRedis(request);
		}
		userInfo = userBaseInfo;

		if (null == userBaseInfo) {
			return null;
		} else {
			return userBaseInfo.getOtherValueMap().get(key);
		}
	}

	/*
	 * 设置其他值(session 值)
	 */
	public static boolean setUserInfoOtherValue(HttpServletRequest request, String key, Object value,
			UserBaseInfoDto userInfo) {
		UserBaseInfoDto userBaseInfo = userInfo;
		if (null != userInfo) {
			userBaseInfo = userInfo;
		} else {
			userBaseInfo = getUserInfoByRedis(request);
		}

		int lifecycle = LifecycleConfig.getLifecycle();

		if (null == userBaseInfo) {
			return false;
		} else {
			if (null == value) {// 空值，当成是删除
				userBaseInfo.getOtherValueMap().remove(key);
			} else {
				userBaseInfo.getOtherValueMap().put(key, value);
			}

			JredisClient.setValueToCache(userBaseInfo.getKey(), JSONObject.toJSONString(userBaseInfo), lifecycle);

			userInfo = userBaseInfo;

			return true;
		}
	}

	/*
	 * 获取用户名
	 */
	public static String getUserInfoUserName(HttpServletRequest request, UserBaseInfoDto userInfo) {
		UserBaseInfoDto userBaseInfo = userInfo;
		if (null != userInfo) {
			userBaseInfo = userInfo;
		} else {
			userBaseInfo = getUserInfoByRedis(request);
		}
		userInfo = userBaseInfo;

		if (null == userBaseInfo) {
			return null;
		} else {
			return userBaseInfo.getUserName();
		}
	}

	/*
	 * 获取用户id
	 */
	public static String getUserInfoUserId(HttpServletRequest request, UserBaseInfoDto userInfo) {
		UserBaseInfoDto userBaseInfo = userInfo;

		if (null != userInfo) {
			userBaseInfo = userInfo;
		} else {
			userBaseInfo = getUserInfoByRedis(request);
		}
		userInfo = userBaseInfo;

		if (null == userBaseInfo) {
			return null;
		} else {
			return userBaseInfo.getUserId();
		}
	}

	/*
	 * 获取特权码
	 */
	public static String getUserInfoaccessToken(HttpServletRequest request, UserBaseInfoDto userInfo) {
		UserBaseInfoDto userBaseInfo = null;
		if (null != userInfo) {
			userBaseInfo = userInfo;
		} else {
			userBaseInfo = getUserInfoByRedis(request);
		}
		userInfo = userBaseInfo;

		if (null == userBaseInfo) {
			return null;
		} else {
			return userBaseInfo.getAccessToken();
		}
	}

	/*
	 * 获取用户真实姓名
	 */
	public static String getUserRealName(HttpServletRequest request, UserBaseInfoDto userInfo) {
		UserBaseInfoDto userBaseInfo = userInfo;

		String userRealName = (String) SessionControl.getUserInfoOtherValue(request, ConstantSession.realName,
				userBaseInfo);
		if ((null != userRealName) && (!"".equals(userBaseInfo))) {
			return userRealName;
		}

		String userId = getUserInfoUserId(request, userBaseInfo);
		if (null == userId) {
			return null;
		}

		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();

		// 用户id
		JSONArray userIds = new JSONArray();
		userIds.add(userId);

		// 属性字段
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("real_name", "realName");

		jsonObject.put("userId", userId);
		iqCloudInfoDto.setJsonBody(jsonObject);

		// 引用
		IQCloudInfoDto iqCloudInfoDto2 = restTemplate.postForObject(url + "/userInfoController/selectByUserId",
				iqCloudInfoDto, IQCloudInfoDto.class);
		JSONObject jsonBody = JSON.parseObject(JSON.toJSONString(iqCloudInfoDto2.getJsonBody()), JSONObject.class);
		if (0 == jsonBody.getIntValue("rtnCode")) {
			userRealName = jsonBody.getJSONObject("msgBody").getString("realName");
			if ((null != userRealName) && (!"".equals(userRealName))) {
				SessionControl.setUserInfoOtherValue(request, ConstantSession.realName, userRealName, userBaseInfo);
			} else {
				SessionControl.setUserInfoOtherValue(request, ConstantSession.realName, "null", userBaseInfo);
			}
		}

		userInfo = userBaseInfo;

		return userRealName;
	}

	/*
	 * 获取用户所在的学校
	 */
	public static String getSchoolId(HttpServletRequest request, UserBaseInfoDto userInfo) {
		UserBaseInfoDto userBaseInfo = userInfo;

		// 学校id
		Object schoolIdObj = SessionControl.getUserInfoOtherValue(request, ConstantSession.schoolId, userBaseInfo);
		Object userIdObj = SessionControl.getUserInfoUserId(request, userBaseInfo);
		String schoolId = null;
		if (null == schoolIdObj) {
			// 用户id
			if ((null != userIdObj)) {
				// 根据用户id获取学校id
				String userId = String.valueOf(userIdObj);
				HashMap<String, Object> jsonBody = new HashMap<String, Object>();
				jsonBody.put("id", userId);
				IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
				iqCloudInfoDto.setJsonBody(jsonBody);
				IQCloudInfoDto iqCloudInfoDto2 = restTemplate.postForObject(
						url + "/organizationController/selectSchoolOrgByUserId", iqCloudInfoDto, IQCloudInfoDto.class);
				JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(iqCloudInfoDto2), JSONObject.class);

				if (0 == jsonObject.getJSONObject("jsonBody").getInteger("rtnCode")) {
					schoolId = jsonObject.getJSONObject("jsonBody").getJSONArray("msgBody").getJSONObject(0)
							.getString("id");
				}
			}
			if (null != schoolId) {
				SessionControl.setUserInfoOtherValue(request, ConstantSession.schoolId, schoolId, userBaseInfo);
			}
		}

		userInfo = userBaseInfo;

		if (userIdObj != null) {
			if (null != schoolIdObj) {
				schoolId = schoolIdObj.toString();
			}
		}

		return schoolId;
	}

	/*
	 * 获取用户所在的班级列表
	 */
	public static JSONArray getClassList(HttpServletRequest request, UserBaseInfoDto userInfo) {
		UserBaseInfoDto userBaseInfo = userInfo;

		// 用户id
		String userId = getUserInfoUserId(request, userBaseInfo);
		if ((null == userId)) {
			return null;
		}

		JSONArray jsonArray = null;
		Object jsonArrayObj = SessionControl.getUserInfoOtherValue(request, ConstantSession.classList, userBaseInfo);
		if (jsonArrayObj != null) {// 在Session里有缓存
			jsonArray = JSON.parseArray(JSON.toJSONString(jsonArrayObj));
			return jsonArray;
		}

		JSONObject jsonObjectParam = new JSONObject();
		jsonObjectParam.put("id", userId);
		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
		iqCloudInfoDto.setJsonBody(jsonObjectParam);

		IQCloudInfoDto iqCloudInfoDto2 = restTemplate.postForObject(
				url + "/organizationController/selectClassOrgByUserId", iqCloudInfoDto, IQCloudInfoDto.class);

		JSONObject jsonObject1 = JSON.parseObject(JSON.toJSONString(iqCloudInfoDto2), JSONObject.class);
		if (0 == jsonObject1.getJSONObject("jsonBody").getInteger("rtnCode")) {
			jsonArray = jsonObject1.getJSONObject("jsonBody").getJSONArray("msgBody");
			if (null != jsonArray) {
				SessionControl.setUserInfoOtherValue(request, ConstantSession.classList, jsonArray, userBaseInfo);
			}
		}

		userInfo = userBaseInfo;

		return jsonArray;
	}

	/*
	 * 获取用户所有角色
	 */
	public static JSONArray getUserAllRoles(HttpServletRequest request, UserBaseInfoDto userInfo) {
		UserBaseInfoDto userBaseInfo = userInfo;

		// 用户id
		String userId = getUserInfoUserId(request, userBaseInfo);
		if ((null == userId)) {
			return null;
		}

		JSONArray jsonRoles = (JSONArray) SessionControl.getUserInfoOtherValue(request, ConstantSession.roles,
				userBaseInfo);
		if (null != jsonRoles) {
			return jsonRoles;
		}

		JSONObject jsonObjectParam = new JSONObject();
		jsonObjectParam.put("userId", userId);
		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
		iqCloudInfoDto.setJsonBody(jsonObjectParam);

		IQCloudInfoDto iqCloudInfoDto2 = restTemplate.postForObject(
				url + "/roleController/selectUserBindRoleByAccessTokenOrUserId", iqCloudInfoDto, IQCloudInfoDto.class);

		JSONObject jsonObject1 = JSON.parseObject(JSON.toJSONString(iqCloudInfoDto2), JSONObject.class);
		if (0 == jsonObject1.getJSONObject("jsonBody").getInteger("rtnCode")) {
			JSONArray jsonArray = jsonObject1.getJSONObject("jsonBody").getJSONArray("msgBody");
			if (null != jsonArray) {
				SessionControl.setUserInfoOtherValue(request, ConstantSession.roles, jsonArray, userBaseInfo);
				userInfo = userBaseInfo;
				return jsonArray;
			}
		}
		return null;
	}

	/*
	 * 获取用户基本角色
	 */
	public static JSONObject getUserBaseRole(HttpServletRequest request, UserBaseInfoDto userInfo) {

		// 暂时直接走数据库
		// UserBaseInfoDto userBaseInfo = userInfo;
		//
		// // 用户id
		// String userId = getUserInfoUserId(request, userBaseInfo);
		// if ((null == userId)) {
		// return null;
		// }
		//
		// JSONObject jsonBaseRole1 = (JSONObject)
		// SessionControl.getUserInfoOtherValue(request,
		// ConstantSession.baseRole,
		// userBaseInfo);
		//
		// JSONObject jsonObjectParam = new JSONObject();
		// jsonObjectParam.put("userId", userId);
		// IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
		// iqCloudInfoDto.setJsonBody(jsonObjectParam);
		//
		// IQCloudInfoDto iqCloudInfoDto2 = restTemplate.postForObject(
		// url + "/roleController/selectUserRoleByAccessTokenOrUserId",
		// iqCloudInfoDto, IQCloudInfoDto.class);
		//
		// JSONObject jsonObject1 =
		// JSON.parseObject(JSON.toJSONString(iqCloudInfoDto2),
		// JSONObject.class);
		// if (0 == jsonObject1.getJSONObject("jsonBody").getInteger("rtnCode"))
		// {
		// JSONObject jsonBaseRole =
		// jsonObject1.getJSONObject("jsonBody").getJSONObject("msgBody");
		// if (null != jsonBaseRole) {
		// System.out.println("jsonBaseRole ===> " +
		// JSON.toJSONString(jsonBaseRole));
		//
		// SessionControl.setUserInfoOtherValue(request,
		// ConstantSession.baseRole, jsonBaseRole, userBaseInfo);
		// userInfo = userBaseInfo;
		// return jsonBaseRole;
		// }
		// }
		//
		// return null;

		UserBaseInfoDto userBaseInfo = userInfo;
		// 用户id
		String userId = getUserInfoUserId(request, userBaseInfo);
		if ((null == userId)) {
			return null;
		}
		JSONObject jsonBaseRole = (JSONObject) SessionControl.getUserInfoOtherValue(request, ConstantSession.baseRole,
				userBaseInfo);

		if (null != jsonBaseRole) {
			return jsonBaseRole;
		}

		JSONObject jsonObjectParam = new JSONObject();
		jsonObjectParam.put("userId", userId);
		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
		iqCloudInfoDto.setJsonBody(jsonObjectParam);

		IQCloudInfoDto iqCloudInfoDto2 = restTemplate.postForObject(
				url + "/roleController/selectUserRoleByAccessTokenOrUserId", iqCloudInfoDto, IQCloudInfoDto.class);

		JSONObject jsonObject1 = JSON.parseObject(JSON.toJSONString(iqCloudInfoDto2), JSONObject.class);
		if (0 == jsonObject1.getJSONObject("jsonBody").getInteger("rtnCode")) {
			jsonBaseRole = jsonObject1.getJSONObject("jsonBody").getJSONObject("msgBody");
			if (null != jsonBaseRole) {
				SessionControl.setUserInfoOtherValue(request, ConstantSession.baseRole, jsonBaseRole, userBaseInfo);
				userInfo = userBaseInfo;
				return jsonBaseRole;
			}
		}

		return null;

	}

	/*
	 * 获取用户绑定的所有角色id
	 */
	public static JSONArray getUserRoleIds(HttpServletRequest request, UserBaseInfoDto userInfo) {
		UserBaseInfoDto userBaseInfo = userInfo;

		// 用户id
		String userId = getUserInfoUserId(request, userBaseInfo);
		if ((null == userId)) {
			return null;
		}

		JSONArray jsonRoleIds = (JSONArray) SessionControl.getUserInfoOtherValue(request, ConstantSession.roleIds,
				userBaseInfo);
		if (null != jsonRoleIds) {
			return jsonRoleIds;
		}

		JSONArray jsonRoles = getUserAllRoles(request, userBaseInfo);
		if ((null != jsonRoles) && (jsonRoles.size() > 0)) {
			jsonRoleIds = new JSONArray();
			for (int i = 0; i < jsonRoles.size(); i++) {
				jsonRoleIds.add(jsonRoles.getJSONObject(i).getString("id"));
			}
			SessionControl.setUserInfoOtherValue(request, ConstantSession.roleIds, jsonRoleIds, userBaseInfo);
			userInfo = userBaseInfo;

			return jsonRoleIds;
		}

		return null;
	}

	/*
	 * 获取学科列表
	 */
	public static JSONArray getSubjectList(HttpServletRequest request, UserBaseInfoDto userInfo) {
		UserBaseInfoDto userBaseInfo = userInfo;

		// 将学科列表缓存到session里，先从session里取, 没有的话再从数据库里取
		JSONArray jsonArray = null;
		UserBaseInfoDto userBaseInfoDto = null;
		Object jsonArrayObj = SessionControl.getUserInfoOtherValue(request, ConstantSession.subjectList, userBaseInfo);
		if (jsonArrayObj != null) {// 在Session里有缓存
			jsonArray = JSON.parseArray(JSON.toJSONString(jsonArrayObj));
			return jsonArray;
		}

		// 学校id
		String schoolId = getSchoolId(request, userBaseInfoDto);
		if (null == schoolId) {// 学校id不存在，则说明学科列表页获取不到 ---> 学科是按学校(组织id)分类的
			return null;
		}

		IQCloudInfoDto iqCloudInfoDtoParam = new IQCloudInfoDto();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("schoolId", schoolId);
		iqCloudInfoDtoParam.setJsonBody(jsonObject);

		IQCloudInfoDto iqCloudInfoDtoResult = restTemplate.postForObject(
				url + "/subjectController/selectAllSubjectsByGroupCode", iqCloudInfoDtoParam, IQCloudInfoDto.class);

		JSONObject jsonObject2 = JSON.parseObject(JSON.toJSONString(iqCloudInfoDtoResult), JSONObject.class);
		if (0 == jsonObject2.getJSONObject("jsonBody").getInteger("rtnCode")) {
			jsonArray = jsonObject2.getJSONObject("jsonBody").getJSONArray("msgBody");
			// 将学科列表缓存到session里
			SessionControl.setUserInfoOtherValue(request, ConstantSession.subjectList, jsonArray, userBaseInfo);
			userInfo = userBaseInfo;
			return jsonArray;
		}

		return jsonArray;
	}

	public static String getValueByKey(String key) {
		String value = JredisClient.getValueFromFuzzyKey(key, -1);
		return value;
	}

	public static String getValueByKey(String key, int lifecycle) {
		String value = JredisClient.getValueFromFuzzyKey(key, lifecycle);
		return value;
	}

	/*
	 * 获取当前激活的语言
	 */
	public static String getNowEnabledLanguage(HttpServletRequest request, UserBaseInfoDto userInfo) {
		UserBaseInfoDto userBaseInfo = userInfo;

		// 将语言编码存到session里，先从session里取, 没有的话再从数据库里取
		JSONArray jsonArray = null;
		UserBaseInfoDto userBaseInfoDto = null;
		Object languageCodeObj = SessionControl.getUserInfoOtherValue(request, ConstantSession.languageCode,
				userBaseInfo);
		if (languageCodeObj != null) {// 在Session里有缓存, 直接取出来
			String languageCode = (String) languageCodeObj;
			return languageCode;
		}

		// 学校id
		String schoolId = getSchoolId(request, userBaseInfoDto);
		if (null == schoolId) {// 学校id不存在，则说明语言id
			return ConstantLanguage.LAN_CHINESE_CODE;
		}

		IQCloudInfoDto iqCloudInfoDtoParam = new IQCloudInfoDto();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("orgId", schoolId);
		iqCloudInfoDtoParam.setJsonBody(jsonObject);

		IQCloudInfoDto iqCloudInfoDtoResult = restTemplate.postForObject(
				url + "/iqLanguageController/getAllEnabledLanguageByOrgId", iqCloudInfoDtoParam, IQCloudInfoDto.class);

		String langCode = null;
		JSONObject jsonObject2 = JSON.parseObject(JSON.toJSONString(iqCloudInfoDtoResult), JSONObject.class);
		if (0 == jsonObject2.getJSONObject("jsonBody").getInteger("rtnCode")) {

			jsonArray = jsonObject2.getJSONObject("jsonBody").getJSONArray("msgBody");
			if ((null == jsonArray) || (jsonArray.size() <= 0)) {// 没有找到语言
				langCode = ConstantLanguage.LAN_CHINESE_CODE; // 直接认为默认为中文
			} else {
				JSONObject jsonLan = jsonArray.getJSONObject(0);
				langCode = jsonLan.getString("langCode");
			}

			// 将语言编码缓存到session里
			SessionControl.setUserInfoOtherValue(request, ConstantSession.languageCode, langCode, userBaseInfo);
			userInfo = userBaseInfo;
		}

		return langCode;
	}

	/*
	 * 从redis里删除当前语言配置项
	 */
	public static void deleteNowEnabledLanguageFromRedis(HttpServletRequest request, UserBaseInfoDto userInfo) {
		SessionControl.setUserInfoOtherValue(request, ConstantSession.languageCode, null, userInfo);
	}

	/*
	 * 获取首页地址
	 */
	public static String getHomePageUrl(HttpServletRequest request, UserBaseInfoDto userInfo) {
		String schoolId = getSchoolId(request, userInfo);
		if (null == schoolId) {
			return null;
		}

		String homePageUrl = null;
		Jedis jedis = null;
		try {
			jedis = SingleJredisPool.jredisPoolINS.getJedis();
			String keyPrefix = ConstantRedis.HomePageInfo_Root;
			String key = keyPrefix + ":" + schoolId;
			homePageUrl = jedis.get(key);
		} finally {
			if (jedis != null) {// 释放redis连接
				SingleJredisPool.jredisPoolINS.freeJedis(jedis);
			}
		}

		return homePageUrl;
	}

	/*
	 * 更新userBaseInfo
	 */
	public static boolean updateUserBaseInfo(UserBaseInfoDto userBaseInfo) {
		int lifecycle = LifecycleConfig.getLifecycle();
		JredisClient.setValueToCache(userBaseInfo.getKey(), JSONObject.toJSONString(userBaseInfo), lifecycle);
		return true;
	}
	
	/**
	 * 判断是否有编码rightCode指定的权限
	 * @param rightCode:权限编码
	 * @param accessToken:特权码(与参数userBaseInfo不能同时为空)
	 * @param userBaseInfo:session数据实体(与参数accessToken不能同时为空)
	 * @return
	 */
	public static boolean hasTheRight(String rightCode, String accessToken, UserBaseInfoDto userBaseInfo){
		boolean bHasRight = false;
		
		if ((null == rightCode) || ("".equals(rightCode.trim()))){
			return bHasRight;
		}
		
		UserBaseInfoDto theUserBaseInfo = null;
		
		if (null != userBaseInfo){
			theUserBaseInfo = userBaseInfo;
		}else{
			if (null != accessToken){
				theUserBaseInfo = getUserInfoByAccessToken(accessToken);
			}
		}
		
		if (null == theUserBaseInfo){
			return false;
		}
		
		// 角色id数组
		JSONArray jsonRoleIds = getUserRoleIds(null, theUserBaseInfo);
		
		// 用户id
		String userId = theUserBaseInfo.getUserId();

		String[] keys = new String[jsonRoleIds.size() + 1];
		keys[0] = ConstantRedis.RightInfo_Root + ":" + rightCode + ":" + userId;
		for (int i = 0; i < jsonRoleIds.size(); i++) {
			keys[i + 1] = ConstantRedis.RightInfo_Root + ":" + rightCode + ":"
					+ jsonRoleIds.getString(i);
		}
		
		Jedis jedis = null;
		try {
			jedis = SingleJredisPool.jredisPoolINS.getJedis();
			List<String> values = jedis.mget(keys);
			if ((null != values) && (values.size() > 0)){
				for (int i = 0; i < values.size(); i++) {
					if (values.get(i) != null){
						bHasRight = true;
						break;
					}
				}
			}else{
				bHasRight = false;
			}
		} finally {
			if (jedis != null) {
				SingleJredisPool.jredisPoolINS.freeJedis(jedis);
			}
		}
		
		return bHasRight;
	}
}







