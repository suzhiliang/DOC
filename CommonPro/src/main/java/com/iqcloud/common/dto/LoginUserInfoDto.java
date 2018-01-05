package com.iqcloud.common.dto;

public class LoginUserInfoDto {

	
	private String userName; //用户名
	
	private String password; //密码
	
	private String verCode; //验证码

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVerCode() {
		return verCode;
	}

	public void setVerCode(String verCode) {
		this.verCode = verCode;
	}
	
	
	
}
