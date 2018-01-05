package com.iqcloud.common.filter.logic;

import com.iqcloud.common.dto.UserBaseInfoDto;

/*
 * 页面拦截器数据包
 */
public class PageFilterDataInfo {

	/*
	 * 是否需要跳转
	 */
	private boolean hasRedirect = false;

	/*
	 * 当前请求地址
	 */
	private String requestUri = null;

	/*
	 * 跳转地址 没加http及host, 例如：/IQCloudMainPro/resources/ruidaHtml/index.jsp
	 */
	private String redirectUrl;

	/*
	 * 跳转地址 有加
	 * http及host，例如：http://192.168.4.126/IQCloudMainPro/resources/ruidaHtml
	 * /index.jsp
	 */
	private String newUrl;

	/*
	 * session用户信息
	 */
	private UserBaseInfoDto userBaseInfo;

	public boolean isHasRedirect() {
		return hasRedirect;
	}

	public void setHasRedirect(boolean hasRedirect) {
		this.hasRedirect = hasRedirect;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getNewUrl() {
		return newUrl;
	}

	public void setNewUrl(String newUrl) {
		this.newUrl = newUrl;
	}

	public UserBaseInfoDto getUserBaseInfo() {
		return userBaseInfo;
	}

	public void setUserBaseInfo(UserBaseInfoDto userBaseInfo) {
		this.userBaseInfo = userBaseInfo;
	}
}
