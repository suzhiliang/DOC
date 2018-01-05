package com.iqcloud.common.dto;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class LoginDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8917141953747203596L;
	
	/*
	 * 登录名（可以是用户名、绑定的邮箱、手机号码等）
	 */
	private String userName;
	
	/*
	 * 密码(密文)
	 */
	private String userPwd;
	
	/*
	 * 类型  0(网页端，默认), 1(桌面端), 2(pad端)，3(手机端-Android), 4(手机端-IOS)，5(手机端-win)，9999(其他端)
	 */
	private Integer type;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}






