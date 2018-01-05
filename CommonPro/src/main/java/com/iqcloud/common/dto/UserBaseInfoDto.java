package com.iqcloud.common.dto;

import java.util.HashMap;

public class UserBaseInfoDto {

	/*
	 * 键值 redis 中的key
	 */
	private String key;

	/*
	 * 用户id
	 */
	private String userId;

	/*
	 * 用户名
	 */
	private String userName;

	/*
	 * 特权码
	 */
	private String accessToken;

	/*
	 * 创建的时间(时间戳)
	 */
	private Long createTime;

	/*
	 * 登录类型 类型 0(网页端，默认), 1(桌面端), 2(pad端)，3(手机端-Android),
	 * 4(手机端-IOS)，5(手机端-win)，9999(其他端)
	 */
	private Integer loginType;

	/*
	 * 其他类型值
	 */
	private HashMap<String, Object> otherValueMap;

	public UserBaseInfoDto() {
		otherValueMap = new HashMap<String, Object>();
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public HashMap<String, Object> getOtherValueMap() {
		return otherValueMap;
	}

	public void setOtherValueMap(HashMap<String, Object> otherValueMap) {
		this.otherValueMap = otherValueMap;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}
}