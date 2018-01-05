package com.iqcloud.common.filter.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSONObject;
import com.iqcloud.common.consts.ConstantRedis;
import com.iqcloud.common.util.CommonFun;
import com.iqcloud.common.util.SessionControl;
import com.iqcloud.redis.client.SingleJredisPool;
import redis.clients.jedis.Jedis;

/*
 * 共用拦截器处理
 */
public class PageLogicCommon extends RightLogicBase {

	public void logicOperate(HttpServletRequest request, String defaultRedirect, JSONObject jsonPages,
			PageFilterDataInfo pageFilterDataInfo) {
		super.logicOperate(request, defaultRedirect, jsonPages, pageFilterDataInfo);
		String resourceUrl = CommonFun.getHttpUrl(request);
		if (pageFilterDataInfo.isHasRedirect()) {// 需要跳转
			if (pageFilterDataInfo.getRedirectUrl().equals(defaultRedirect)
					|| pageFilterDataInfo.getRedirectUrl().equals(teachHomePage)) {// 需要跳转且跳转到首页、教学首页
				if (pageFilterDataInfo.getRedirectUrl().equals(defaultRedirect)) {// 首页
					// 先看下有没有设置特别定制的首页
					String homePageUrl = SessionControl.getHomePageUrl(request, pageFilterDataInfo.getUserBaseInfo());
					System.out.println("homePageUrl==> " + homePageUrl);
					if ((null != homePageUrl) && (!"".equals(homePageUrl))) {
						pageFilterDataInfo.setHasRedirect(true);
						pageFilterDataInfo.setNewUrl(homePageUrl);
						return;
					}
				}

				// 获取用户基本角色
				JSONObject jsonBaseRole = SessionControl.getUserBaseRole(request, pageFilterDataInfo.getUserBaseInfo());
				// 根据基本角色获取也许访问的默认地址
				String roleId = jsonBaseRole.getString("id"); // 基本角色id
				String key = ConstantRedis.BasicRoleUrlRightInfo_Root + "*" + roleId;

				System.out.println("key==> " + key);

				Jedis jedis = null;
				try {
					jedis = SingleJredisPool.jredisPoolINS.getJedis();
					Set<String> keysSet = jedis.keys(key);
					List<String> keyItems = new ArrayList<String>(keysSet);
					if (keyItems.size() <= 0) {// 没有默认的访问地址，则直接退出
						String newUrl = resourceUrl + pageFilterDataInfo.getRedirectUrl() + "?permiss=" + "1";
						SessionControl.logout(request, pageFilterDataInfo.getUserBaseInfo());
						return;
					}

					String defaultKey = keyItems.get(0);
					String defaultRequestUrl = jedis.get(defaultKey);

					System.out.println("defaultRequestUrl==> " + defaultRequestUrl);

					if (defaultRequestUrl.equals(pageFilterDataInfo.getRequestUri())) {// 防止死循环，默认的访问地址与当前拦截地址一样，则通过
						pageFilterDataInfo.setHasRedirect(false);
					} else {// 跳转到新的页面
						String newUrl = resourceUrl + defaultRequestUrl;
						pageFilterDataInfo.setNewUrl(newUrl);
					}
				} finally {
					if (jedis != null) {
						SingleJredisPool.jredisPoolINS.freeJedis(jedis);
					}
				}
			}
		} else if ("/IQCloudMainPro/resources/login/login.jsp".equals(pageFilterDataInfo.getRequestUri())) {
			pageFilterDataInfo.setHasRedirect(true);
			String newUrl = resourceUrl + defaultRedirect;
			pageFilterDataInfo.setNewUrl(newUrl);
		}
	}

}
