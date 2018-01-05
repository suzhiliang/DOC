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

/*
 * 公共模块控制器
 */
@Controller
@RequestMapping(value="/common/")
public class CommonPageController {
	/*
	 * 空白页面
	 */
	@RequestMapping(value = "emptyPage", method = RequestMethod.GET)
	public void emptyPage(Locale locale, Model model, HttpServletResponse response) {
		try {
			String content = "";
			response.setContentType("text/html;charset=utf-8");
			OutputStream out = response.getOutputStream();
			out.write(content.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
