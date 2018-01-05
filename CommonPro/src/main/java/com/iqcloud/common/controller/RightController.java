package com.iqcloud.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iqcloud.common.dto.IQCloudInfoDto;
import com.iqcloud.common.dto.MsgDto;
import com.iqcloud.common.util.SessionControl;

/*
 * 权限控制器
 */
@Controller
@RequestMapping(value = "/rightController")
public class RightController {
	/*
	 * 判断是否有权限
	 */
	@RequestMapping(value = "/hasTheRight", method = RequestMethod.POST)
	public @ResponseBody IQCloudInfoDto hasTheRight(@RequestBody IQCloudInfoDto iqCloudInfoDto, HttpServletRequest request) {
		IQCloudInfoDto iqCloudInfoDtoResult = new IQCloudInfoDto();
		
		JSONObject jsonBody = JSON.parseObject(JSON.toJSONString(iqCloudInfoDto.getJsonBody()));
		String rightCode = jsonBody.getString("rightCode");
		
		String accessToken = jsonBody.getString("accessToken");
		if (null == accessToken){
			accessToken = SessionControl.getUserInfoaccessToken(request, null);
		}
		
		boolean hasRight = SessionControl.hasTheRight(rightCode, accessToken, null);
		
		MsgDto msgDto = new MsgDto();
		msgDto.setRtnCode(0);
		msgDto.setMsg("判断成功!");
		msgDto.setMsgBody(hasRight);
		iqCloudInfoDtoResult.setJsonBody(msgDto);
		
		return iqCloudInfoDtoResult;
	}
}
