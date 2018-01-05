package com.iqcloud.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iqcloud.common.ConfigRead.IQCloudConfig;
import com.iqcloud.common.dto.IQCloudInfoDto;
import com.iqcloud.common.filter.logic.PageFilterDataInfo;
import com.iqcloud.common.filter.logic.RightLogicBase;

/*
 * 页面访问权限拦截过滤器
 */
public class PageFilter implements Filter {

	/*
	 * 模块名称
	 */
	@SuppressWarnings("unused")
	private static String moduleName;

	/*
	 * 默认跳转
	 */
	private static String defaultRedirect;

	/*
	 * 发送http对象
	 */
	private RestTemplate restTemplate = new RestTemplate();

	/*
	 * 需要监听的页面链表
	 */
	private static JSONObject jsonPages = null;

	/*
	 * 业务逻辑对象
	 */
	private RightLogicBase rightLogicBase = null;

	/*
	 * 拦截过滤器初始化
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		moduleName = filterConfig.getInitParameter("moduleName");
		defaultRedirect = filterConfig.getInitParameter("defaultRedirect");
		if (null == defaultRedirect) {
			defaultRedirect = IQCloudConfig.getParamValue("url", "indexUrl");
		}

		try {
			ServletContext sc = filterConfig.getServletContext();
			XmlWebApplicationContext cxt = (XmlWebApplicationContext) WebApplicationContextUtils
					.getWebApplicationContext(sc);
			if ((null != cxt) && (null != cxt.getBean("rightLogic")) && (null == rightLogicBase)) {
				rightLogicBase = (RightLogicBase) cxt.getBean("rightLogic");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/*
	 * 初始化需要监听的页面列表
	 */
	public void initPageList() {
		String iqServiceUrl = IQCloudConfig.getParamValue("main", "IQService");

		jsonPages = new JSONObject();
		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
		IQCloudInfoDto iqCloudInfoDto2 = restTemplate.postForObject(
				iqServiceUrl + "/iqRightInfoJudgeController/getPageFilterUrlInfo", iqCloudInfoDto,
				IQCloudInfoDto.class);
		JSONObject jsonObject1 = JSON.parseObject(JSON.toJSONString(iqCloudInfoDto2), JSONObject.class);
		if (0 == jsonObject1.getJSONObject("jsonBody").getInteger("rtnCode")) {
			try {
				JSONObject jsonBody = jsonObject1.getJSONObject("jsonBody");
				jsonPages = jsonBody.getJSONObject("msgBody");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		if (null == rightLogicBase) {// 不需要拦截处理任何内容
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			if (null == jsonPages) {// 需要过滤的页面链表还没创建, 创建并获取需要拦截的页面链表
				initPageList();
			}

			HttpServletRequest request = (HttpServletRequest) servletRequest;
			HttpServletResponse response = (HttpServletResponse) servletResponse;

			String requestUrl = request.getRequestURI(); // 页面地址

			if (requestUrl.indexOf("pressureTest") > 0) {// 压力测试地址，跳过
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}

			// 例外不拦截网址
			if (("/IQCloudMainPro/resources/V7.0.0/topDisk.jsp".equals(requestUrl))
					|| ("/IQCloudMainPro/resources/V7.0.0/topDiskHome.jsp".equals(requestUrl))
					|| ("/IQCloudMainPro/resources/V7.0.0/topHome.jsp".equals(requestUrl))) {
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}

			PageFilterDataInfo pageFilterDataInfo = new PageFilterDataInfo();
			rightLogicBase.logicOperate(request, defaultRedirect, jsonPages, pageFilterDataInfo);
			if (pageFilterDataInfo.isHasRedirect()) {// 需要跳转
				System.out.println("PageFilter  rightLogicBase.getNewUrl()===> " + pageFilterDataInfo.getNewUrl());
				response.sendRedirect(pageFilterDataInfo.getNewUrl());
			} else {// 不需要跳转
				filterChain.doFilter(servletRequest, servletResponse);
			}
		}
	}

	@Override
	public void destroy() {

	}
}
