package com.iqcloud.common.controller;

import java.util.Locale;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.iqcloud.common.ConfigRead.IQCloudConfig;

/**
 * 配置文件控制器
 */
@Controller
public class ConfigController {

	/*
	 * 重新加载配置文件
	 */
	@RequestMapping(value = "/reloadConfig", method = RequestMethod.GET)
	public @ResponseBody String reloadConfig(Locale locale, Model model, HttpServletResponse response) {
		System.out.println(IQCloudConfig.getParamValue("main", "ftpUrl"));

		boolean reloadOK = IQCloudConfig.reloadConfig();

		System.out.println(IQCloudConfig.getParamValue("main", "ftpUrl"));

		if (reloadOK) {
			return "Success";
		} else {
			return "Fail!";
		}
	}

}
