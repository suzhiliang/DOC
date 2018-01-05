package com.iqcloud.common.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSONObject;
import com.iqcloud.common.ConfigRead.IQCloudConfig;
import com.iqcloud.common.dto.IQCloudInfoDto;
import com.iqcloud.common.dto.MsgDto;
import com.iqcloud.common.dto.UserBaseInfoDto;
import com.iqcloud.common.util.CommonFun;
import com.iqcloud.common.util.CommonModuleFun;
import com.iqcloud.common.util.FileControl;
import com.iqcloud.common.util.SessionControl;
import com.iqcloud.redis.client.JredisClient;

public class IsLoginFilter implements Filter {

	private String loginUrl;

	private RestTemplate restTemplate = new RestTemplate();
	private String url = FileControl.getPath("main", "IQService");

	// 用户绑定邮箱，激活地址
	private String userEmailBindingUrl = IQCloudConfig.getParamValue("url", "UserEmailBindingUrl");
	// 公有资源URL
	private static String publishResourceUrl = IQCloudConfig.getParamValueNotOS("url", "publishResourceUrl");

	private static String publishResourceHost = FileControl.getPath("main", "publishResourceHost");

	public void init(FilterConfig filterConfig) throws ServletException {
		loginUrl = IQCloudConfig.getParamValueNotOS("url", "loginUrl");
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		String requestUri = request.getRequestURI();
		String resourceUrl = CommonFun.getHttpUrl(request);
		String referer = resourceUrl + requestUri;

		if (requestUri.indexOf("pressureTest") > 0) {// 压力测试地址，跳过
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		// 获取请求参数
		Map<String, String> paramMap = CommonModuleFun.getParameterMap(request);
		// 获取特权码
		String accessToken = (String) paramMap.get("accessToken");

		if ((null != publishResourceUrl) && requestUri.indexOf(publishResourceUrl) != -1) {
			try {
				String reyzSchoolIdKey = null;
				Cookie[] cookies = request.getCookies();// 这样便可以获取一个cookie数组
				if (cookies != null && cookies.length > 0) {
					for (Cookie cookie : cookies) {
						if ("reyzSchoolId".equals(cookie.getName())) {
							reyzSchoolIdKey = cookie.getValue();
							// 从Redis中获取userId
							String reyzSchoolId = JredisClient.getValueFromFuzzyKey(reyzSchoolIdKey);
							JredisClient.setValueToCache(reyzSchoolIdKey, reyzSchoolId, 6 * 60 * 60);
							if ((reyzSchoolId != null) && (!"".equals(reyzSchoolId))) {
								filterChain.doFilter(servletRequest, servletResponse);
								return;
							}
						}
					}
				}
				IQCloudInfoDto iQCloudInfoDtoReturn = new IQCloudInfoDto();
				IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
				String schoolId = SessionControl.getSchoolId(request, null);
				String voucherKey = request.getParameter("voucherKey");
				Map<String, String> map = new HashMap<String, String>();
				map.put("schoolId", schoolId);
				map.put("voucherKey", voucherKey);
				iqCloudInfoDto.setJsonBody(map);
				iQCloudInfoDtoReturn = restTemplate.postForObject(
						publishResourceHost
								+ "/IQAuthManage/authCodeManageController/getVoucherVisitForPublishResource",
						iqCloudInfoDto, IQCloudInfoDto.class);
				MsgDto msgDto = JSONObject.parseObject(JSONObject.toJSONString(iQCloudInfoDtoReturn.getJsonBody()),
						MsgDto.class);
				if (msgDto != null && msgDto.getRtnCode() == 0) {
					JSONObject msgObject = JSONObject.parseObject(JSONObject.toJSONString(msgDto.getMsgBody()));
					voucherKey = msgObject.getString("voucherKey");
					String cooValue = CommonFun.getOneUUID();
					Cookie cookie = new Cookie("reyzSchoolId", cooValue);
					cookie.setPath("/");
					response.addCookie(cookie);
					JredisClient.setValueToCache(cooValue, msgObject.getString("rdyzSchoolId"), 6 * 60 * 60);
				}
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			} catch (Exception e) {

			}
		}

		// 账号绑定邮箱登录， 用户激活打开邮箱链接...
		if (requestUri.equals(userEmailBindingUrl)) {
			// if
			// (requestUri.equals("/IQCloudResources/resources/html/filter/bindEmail.html")){
			try {
				String userName = paramMap.get("userName");
				String code = paramMap.get("code");
				if (code == null) {
					code = paramMap.get(";code");
				}
				IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
				IQCloudInfoDto iQCloudInfoDtoReturn = new IQCloudInfoDto();
				HashMap<String, Object> hashMap = new HashMap<String, Object>();
				hashMap.put("userName", userName);
				hashMap.put("code", code);
				iqCloudInfoDto.setJsonBody(hashMap);
				iQCloudInfoDtoReturn = restTemplate.postForObject(
						url + "/IqUserGroupController/selectRecordByUserNameAndCode", iqCloudInfoDto,
						IQCloudInfoDto.class);

				if (null != iQCloudInfoDtoReturn) {
					JSONObject jsonObject = JSONObject
							.parseObject(JSONObject.toJSONString(iQCloudInfoDtoReturn.getJsonBody()));
					String userPwd = jsonObject.getString("msgBody");
					iqCloudInfoDto = new IQCloudInfoDto();
					hashMap = new HashMap<String, Object>();
					hashMap.put("userName", userName);
					hashMap.put("userPwd", userPwd);
					iqCloudInfoDto.setJsonBody(hashMap);
					IQCloudInfoDto login = restTemplate.postForObject(url + "/userInfoController/login", iqCloudInfoDto,
							IQCloudInfoDto.class);

					JSONObject jsonObject2 = JSONObject.parseObject(JSONObject.toJSONString(login.getJsonBody()));

					// {"msg":"登录成功!","msgBody":{"accessToken":"AED84D6733BC497391F2097AB954D1D0","userName":"A0U1w095"},"rtnCode":0}
					if (0 == jsonObject2.getInteger("rtnCode")) {// 登录成功
						JSONObject msgBody = jsonObject2.getJSONObject("msgBody");
						accessToken = msgBody.getString("accessToken");
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				accessToken = null;
			}
		}

		if ((accessToken != null) && (!("".equals(accessToken)))) {
			// 通过特权吗获取userBasinfoDto
			UserBaseInfoDto userBaseInfoDto = SessionControl.getUserInfoByAccessToken(accessToken);
			if (null == userBaseInfoDto) {// 说明特权码过期或者非法，跳转到登录页面
				String newParam = CommonModuleFun.getUrlDynamicWithOutAccessToken(request);
				response.sendRedirect(resourceUrl + loginUrl + "?referer=" + requestUri + newParam);
				return;
			}

			String theKey = userBaseInfoDto.getKey();
			String[] keys = theKey.split(":");
			String cookieValue = keys[2]; // cookie值

			// 将用户ID对应的随机码放入cookie中
			Cookie cookie = new Cookie("userInfo", cookieValue);
			cookie.setPath("/");
			response.addCookie(cookie);

			// 将登陆类型也写入cookie中
			Cookie cookie2 = null;
			// if (0 != userBaseInfoDto.getLoginType()) {// 非网页端登陆, 告诉前端是哪边转登过来
			// cookie2 = new Cookie("loginType", "1");
			// } else {// 0 网页端正常登陆
			// cookie2 = new Cookie("loginType", "0");
			// }
			String type = (null == userBaseInfoDto.getLoginType()) ? "0" : userBaseInfoDto.getLoginType().toString();
			cookie2 = new Cookie("loginType", type);

			cookie2.setPath("/");
			response.addCookie(cookie2);

			if (requestUri.equals(userEmailBindingUrl)) {
				// if
				// (requestUri.equals("/IQCloudResources/resources/html/filter/bindEmail.html")){//
				// 如果有邮箱绑定
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			} else {
				// 将请求参数中的 accessToken去掉
				String newParam = CommonModuleFun.getUrlDynamicWithOutAccessToken(request);
				// response.sendRedirect(requestUri + newParam);
				System.out.println("地址带特权码===> " + CommonFun.getHttpUrl(request) + requestUri + newParam);
				response.sendRedirect(CommonFun.getHttpUrl(request) + requestUri + newParam);
			}

		} else {
			boolean flag = false;
			String userKey = null;
			Cookie[] cookies = request.getCookies();// 这样便可以获取一个cookie数组
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if ("userInfo".equals(cookie.getName())) {
						userKey = cookie.getValue();

						// 从Redis中获取userId
						String userBaseInfoString = JredisClient.getValueFromFuzzyKey(userKey);
						if ((userBaseInfoString != null) && (!"".equals(userBaseInfoString))) {
							UserBaseInfoDto userBaseInfo = JSONObject.parseObject(userBaseInfoString,
									UserBaseInfoDto.class);
							if (userBaseInfo != null) {
								flag = true;
								filterChain.doFilter(servletRequest, servletResponse);
								return;
							}
						}
					}
				}
			}

			if (!flag) {// 跳转到登录页面
				// 将请求参数中的 accessToken去掉
				String newParam = CommonModuleFun.getUrlDynamicWithOutAccessToken(request);

				String theDomainMustRedirectToThirdLogin = IQCloudConfig.getParamValueNotOS("url",
						"theDomainMustRedirectToThirdLogin");
				String serviceName = CommonFun.getServerName(request);

				if ((null != theDomainMustRedirectToThirdLogin)
						&& (theDomainMustRedirectToThirdLogin.indexOf(serviceName) >= 0)) {
					String[] redirectAttris = theDomainMustRedirectToThirdLogin.split("#");
					if ((null != redirectAttris) && (redirectAttris.length >= 0)) {
						String redirectAttriItemNormal = null;
						for (int i = 0; i < redirectAttris.length; i++) {
							String redirectAttriItem = redirectAttris[i];
							redirectAttriItem = redirectAttriItem.trim();
							if (redirectAttriItem.indexOf(serviceName) >= 0) {
								redirectAttriItemNormal = redirectAttriItem;
								break;
							}
						}

						String[] redirectAttriItemValues = redirectAttriItemNormal.split(";");

						if (requestUri.equals(redirectAttriItemValues[1])) {
							filterChain.doFilter(servletRequest, servletResponse);
							return;
						} else {
							String redirectUrl = CommonFun.getHttpUrl(request) + redirectAttriItemValues[1];
							response.sendRedirect(redirectUrl);
						}
					} else {
						response.sendRedirect(resourceUrl + loginUrl + "?referer=" + referer + newParam);
					}
				} else {
					response.sendRedirect(resourceUrl + loginUrl + "?referer=" + referer + newParam);
				}
			}
		}
	}

	public void destroy() {

	}
}
