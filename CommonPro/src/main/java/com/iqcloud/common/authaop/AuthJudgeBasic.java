package com.iqcloud.common.authaop;

import com.alibaba.fastjson.JSONObject;

/*
 * 权限判断基类
 */
public abstract class AuthJudgeBasic {
	// 判断是否有权限
	public abstract boolean getHasAuth(JSONObject jsonParam);
}
