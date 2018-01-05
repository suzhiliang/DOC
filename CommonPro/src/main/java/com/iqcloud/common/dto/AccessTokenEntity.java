package com.iqcloud.common.dto;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/*
 * 特权码实体
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AccessTokenEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5722980795697335762L;

	/*
	 * 特权码
	 */
	private String accessToken;

	/*
	 * 系统用户名
	 */
	private String userName;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
