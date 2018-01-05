package com.iqcloud.common.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.alibaba.fastjson.JSON;
import com.iqcloud.common.language.IQMultiLanguage;
import com.iqcloud.common.util.SessionControl;

/**
 * 多语言控制器
 */
@Controller
public class LanguageController {

	/*
	 * 重新加载配置文件
	 */
	@RequestMapping(value = "/getLanguage", method = RequestMethod.GET)
	public void getLanguage(Locale locale, Model model, HttpServletResponse response, HttpServletRequest request) {
		String returnLangStr = null;

		// 语言编码
		String langCode = request.getParameter("langKey");
		if ((null == langCode) || ("".equals(langCode.trim()))) {
			langCode = SessionControl.getNowEnabledLanguage(request, null);
		}
		langCode = ((null == langCode) || ("".equals(langCode.trim()))) ? "chinese" : langCode.trim().toLowerCase();
		if ("zh-cn".equals(langCode)) {
			langCode = "chinese";
		} else if ("en".equals(langCode)) {
			langCode = "english";
		}

		// 变量编码
		String langVarName = request.getParameter("langVarName");
		langVarName = ((null == langVarName) || ("".equals(langVarName.trim()))) ? "gLanguage" : langVarName.trim();

		// 地址编码
		String urlCode = request.getParameter("urlCode");
		System.out.println("urlCode===> " + urlCode);
		if ((null == urlCode) || ("".equals(urlCode.trim()))) {
			returnLangStr = "var " + langVarName + "=" + "{" + "\"langErrortHint\":" + "\"后台读取不到语言配置！\"" + "}";
		}

		if (null == returnLangStr) {
			// 语言编码
			HashMap<String, String> langMap = IQMultiLanguage.getBatch(langCode, urlCode);
			if (null != langMap) {
				returnLangStr = "var " + langVarName + "=" + JSON.toJSONString(langMap);
			}
		}

		if ((null == returnLangStr) || ("".equals(returnLangStr.trim()))) {
			returnLangStr = "var " + langVarName + "=" + "{" + "\"langErrortHint\":" + "\"后台读取不到语言配置！\"" + "}";
		}

		response.setContentType("application/javascript; charset=utf-8");
		OutputStream out;
		try {
			out = response.getOutputStream();
			out.write(returnLangStr.getBytes("utf-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
