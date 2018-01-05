package com.iqcloud.common.authaop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

import com.alibaba.fastjson.JSONObject;
import com.iqcloud.common.dto.IQCloudInfoDto;
import com.iqcloud.common.dto.MsgDto;

public class AuthAdvice implements MethodBeforeAdvice, AfterReturningAdvice,
		ThrowsAdvice, MethodInterceptor {

	private AuthJudgeBasic judgeObj;

	public AuthJudgeBasic getJudgeObj() {
		return judgeObj;
	}

	public void setJudgeObj(AuthJudgeBasic judgeObj) {
		this.judgeObj = judgeObj;
	}

	@Override
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		// TODO Auto-generated method stub

	}

	public void afterThrowing(Method method, Object[] args, Object target,
			Exception ex) throws Throwable {

	}

	@Override
	public Object invoke(MethodInvocation arg0) throws Throwable {
		// TODO Auto-generated method stub

		// 获取参数列表
		// Object[] args = arg0.getArguments();

		// 获取方法名
		// String methodName = arg0.getMethod().getName();

		// 获取方法路径名
		// public com.iqcloud.common.dto.IQCloudInfoDto
		// com.iqcloud.auth.facade.impl.IqUsersFacadeImpl.login(com.iqcloud.common.dto.IQCloudInfoDto)
		String methodNamePath = arg0.getMethod().toGenericString();

		// 获取： om.iqcloud.common.dto.IQCloudInfoDto
		// com.iqcloud.auth.facade.impl.IqUsersFacadeImpl.login(com.iqcloud.common.dto.IQCloudInfoDto)
		int pos1 = methodNamePath.indexOf("com.");
		String tmpStr = methodNamePath.substring(pos1 + 1);

		// 获取：com.iqcloud.auth.facade.impl.IqUsersFacadeImpl.login(com.iqcloud.common.dto.IQCloudInfoDto)
		pos1 = tmpStr.indexOf("com.");
		tmpStr = tmpStr.substring(pos1);

		// 获取：com.iqcloud.auth.facade.impl.IqUsersFacadeImpl.login
		pos1 = tmpStr.indexOf("(");
		tmpStr = tmpStr.substring(0, pos1);

		String theMethodNamePath = tmpStr;

		// 是否有访问权限
		boolean hasAuth = true;
		if (null != judgeObj) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("methodNamePath", theMethodNamePath);
			Object[] args = arg0.getArguments();
			if (args.length > 0) {
				jsonObject.put("param", args[0]);
			}
			hasAuth = judgeObj.getHasAuth(jsonObject);
		}

		Object returnObj = null;
		if (hasAuth) {
			returnObj = arg0.proceed();
		} else {
			IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
			MsgDto msg = new MsgDto();
			msg.setRtnCode(-2);
			msg.setMsg("没有权限访问该功能！");
			iqCloudInfoDto.setJsonBody(msg);
			returnObj = iqCloudInfoDto;
		}
		return returnObj;
	}

}
