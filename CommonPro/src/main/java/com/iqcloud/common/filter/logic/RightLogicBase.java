package com.iqcloud.common.filter.logic;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iqcloud.common.ConfigRead.IQCloudConfig;
import com.iqcloud.common.consts.ConstantRedis;
import com.iqcloud.common.util.CommonFun;
import com.iqcloud.common.util.CommonModuleFun;
import com.iqcloud.common.util.SessionControl;
import com.iqcloud.redis.client.SingleJredisPool;
import redis.clients.jedis.Jedis;

/*
 * 跳转处理逻辑基类
 */
@Component
public class RightLogicBase {

	/*
	 * 默认跳转
	 */
	protected String defaultRedirect;

	/*
	 * 教学平台首页
	 */
	protected String teachHomePage = "/IQCloudMainPro/teachHome.jsp";

	/*
	 * IQService 服务地址
	 */
	protected String iqServiceUrl = IQCloudConfig.getParamValue("main", "IQService");

	/*
	 * 登录地址
	 */
	protected String loginUrl = IQCloudConfig.getParamValueNotOS("url", "loginUrl");

	/*
	 * 需要监听的页面链表
	 */
	protected JSONObject jsonPages = null;

	/*
	 * 没权限时是否需要登出
	 */
	protected boolean needLogout = true;

	public boolean isNeedLogout() {
		return needLogout;
	}

	public void setNeedLogout(boolean needLogout) {
		this.needLogout = needLogout;
	}

	/*
	 * 业务逻辑处理
	 */
	@SuppressWarnings({ "finally" })
	public void logicOperate(HttpServletRequest request, String defaultRedirect, JSONObject jsonPages,
			PageFilterDataInfo pageFilterDataInfo) {
		System.out.println("RightLogicBase--> logicOperate");

		this.defaultRedirect = defaultRedirect;
		this.jsonPages = jsonPages;

		if (null == this.jsonPages) {
			return;
		}

		String resourceUrl = CommonFun.getHttpUrl(request);
		// 页面地址
		pageFilterDataInfo.setRequestUri(request.getRequestURI());

		// 遇到类似 http://192.168.4.126/IQCloudMainPro/resources/ruidaHtml/
		// 代码逻辑里真正需要的应该是：/IQCloudMainPro/resources/ruidaHtml/index.jsp
		// 但是通过 request.getRequestURI()
		// 获取的却是：/IQCloudMainPro/resources/ruidaHtml/
		// 因此如下代码补上：index.jsp
		String servletPath = request.getServletPath();
		int pos1 = servletPath.lastIndexOf("/");
		String pageName = servletPath.substring(pos1 + 1);
		int pos2 = pageFilterDataInfo.getRequestUri().indexOf(pageName);
		if (pos2 < 0) {
			pageFilterDataInfo.setRequestUri(pageFilterDataInfo.getRequestUri() + pageName);
		}

		if ((pageFilterDataInfo.getRequestUri().indexOf("IQCloudResources") > 0)
				&& (pageFilterDataInfo.getRequestUri().indexOf("public") > 0)) {// 是公有资源相关地址直接放行，在登录拦截器已经有判断是否有权限访问共有资源了
			System.out.println("公有资源地址....");
			return;
		}

		if (pageFilterDataInfo.getRequestUri().equals(defaultRedirect)) {// 当前地址等于默认跳转地址
			pageFilterDataInfo.setRedirectUrl(defaultRedirect);
			pageFilterDataInfo.setHasRedirect(true);
			return;
		}

		if (pageFilterDataInfo.getRequestUri().equals(teachHomePage)) {// 教学平台首页
			pageFilterDataInfo.setRedirectUrl(teachHomePage);
			pageFilterDataInfo.setHasRedirect(true);
			return;
		}

		String redirectUrlDb = jsonPages.getString(pageFilterDataInfo.getRequestUri()); // 跳转地址

		if (((null == jsonPages) || (null == redirectUrlDb))) {// 说明此地址不在拦截的地址列表里，因此不需要拦截,
			int posAction = pageFilterDataInfo.getRequestUri().indexOf(".action");
			int posJsp = pageFilterDataInfo.getRequestUri().indexOf(".jsp");
			// int posJsp = -1;
			if ((posAction > 0) || (posJsp > 0)) {// 以action、jsp结尾的页面都要拦截
				pageFilterDataInfo.setHasRedirect(true);
				pageFilterDataInfo.setRedirectUrl(defaultRedirect);
				return;
			}

			pageFilterDataInfo.setHasRedirect(false);
			return;
		}

		// 获取用户绑定的所有角色id
		JSONArray jsonRoleIds = SessionControl.getUserRoleIds(request, null);
		if (null == jsonRoleIds) {// 获取不到角色id列表, 认为该用户还没登录，直接跳转到登录页面
			pageFilterDataInfo.setHasRedirect(true);
			String newParam = CommonModuleFun.getUrlDynamicWithOutAccessToken(request);
			pageFilterDataInfo.setRedirectUrl(loginUrl);
			String newUrl = resourceUrl + pageFilterDataInfo.getRedirectUrl() + "?referer="
					+ pageFilterDataInfo.getRequestUri() + newParam;
			pageFilterDataInfo.setNewUrl(newUrl);
			return;
		}

		// 用户id
		String userId = SessionControl.getUserInfoUserId(request, null);
		if (null == userId) {// 用户id获取不到，认为没有登录, 直接跳转到登录页
			pageFilterDataInfo.setHasRedirect(true);
			String newParam = CommonModuleFun.getUrlDynamicWithOutAccessToken(request);
			pageFilterDataInfo.setRedirectUrl(loginUrl);
			String newUrl = resourceUrl + pageFilterDataInfo.getRedirectUrl() + "?referer="
					+ pageFilterDataInfo.getRequestUri() + newParam;
			pageFilterDataInfo.setNewUrl(newUrl);
			return;
		}

		String[] keys = new String[jsonRoleIds.size() + 1];
		keys[0] = ConstantRedis.RightInfo_Root + ":" + pageFilterDataInfo.getRequestUri() + ":" + userId;
		for (int i = 0; i < jsonRoleIds.size(); i++) {
			keys[i + 1] = ConstantRedis.RightInfo_Root + ":" + pageFilterDataInfo.getRequestUri() + ":"
					+ jsonRoleIds.getString(i);
		}

		Jedis jedis = null;
		try {
			jedis = SingleJredisPool.jredisPoolINS.getJedis();

			List<String> values = jedis.mget(keys);
			pageFilterDataInfo.setHasRedirect(true);
			for (int i = 0; i < values.size(); i++) {
				if (values.get(i) != null) {
					pageFilterDataInfo.setHasRedirect(false);
					break;
				}
			}

			if (pageFilterDataInfo.isHasRedirect()) {// 没有此权限
				if (needLogout) {// 登出
					if (null != defaultRedirect) {
						String newUrl = resourceUrl + defaultRedirect;
						pageFilterDataInfo.setNewUrl(newUrl);
						needLogout = false;
					} else {
						SessionControl.logout(request, null);
					}
				}

				if ((null != redirectUrlDb) && (!("".equals(redirectUrlDb.trim())))) {// 有设置跳转地址
					if (redirectUrlDb.equals(pageFilterDataInfo.getRequestUri())) {// 跳转的地址等于当前地址
						String redirectUrl = defaultRedirect;
						pageFilterDataInfo.setRedirectUrl(redirectUrl);

						String newUrl = resourceUrl + redirectUrl; // 跳转到默认地址
						pageFilterDataInfo.setNewUrl(newUrl);
					} else {// 没有权限访问该页面，直接跳到登录页面, 并在地址里加上动态参数高数前端说：没有访问权限
						String redirectUrl = loginUrl;
						pageFilterDataInfo.setRedirectUrl(redirectUrl);

						String newUrl = resourceUrl + redirectUrl + "?permiss=" + "1";
						pageFilterDataInfo.setNewUrl(newUrl);
					}
				} else {// 没有设置跳转地址
					String redirectUrl = defaultRedirect;
					pageFilterDataInfo.setRedirectUrl(redirectUrl);

					String newUrl = resourceUrl + redirectUrl; // 跳转到默认地址
					pageFilterDataInfo.setNewUrl(newUrl);
				}

				// 获取session基础信息
				pageFilterDataInfo.setUserBaseInfo(SessionControl.getUserInfoByRedis(request));
			}
		} finally {
			if (jedis != null) {
				SingleJredisPool.jredisPoolINS.freeJedis(jedis);
			}
			return;
		}
	}
}
