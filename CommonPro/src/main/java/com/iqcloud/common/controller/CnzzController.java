package com.iqcloud.common.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.iqcloud.common.ConfigRead.IQCloudConfig;

/**
 * cnzz 统计控制器
 */
@Controller
public class CnzzController {

	/*
	 * 统计代码网址
	 */
	@RequestMapping(value = "/cnzz", method = RequestMethod.GET)
	public void cnzz(Locale locale, Model model, HttpServletResponse response) {
		try {
			String cnzz = IQCloudConfig.getParamValueNotOS("cnzz", "javascript");
			if (null == cnzz) {
				cnzz = "";
			}
			response.setContentType("text/html;charset=utf-8");
			OutputStream out = response.getOutputStream();
			out.write(cnzz.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
