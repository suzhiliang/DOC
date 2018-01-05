package com.iqcloud.common.datasource;

import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

public class DataSourceAdvice implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {

	Logger logger = org.slf4j.LoggerFactory.getLogger(DataSourceAdvice.class);
	private String slaveSourceName;

	private String nowMethodName = null;

	public String getSlaveSourceName() {
		return slaveSourceName;
	}

	public void setSlaveSourceName(String slaveSourceName) {
		this.slaveSourceName = slaveSourceName;
	}

	@Override
	public void before(Method method, Object[] arg1, Object arg2) throws Throwable {
		System.out.println("DataSourceAdvice:before---> " + method);

		// if (nowMethodName != null) {
		// DataSourceSwitcher.setMaster();
		// return;
		// }
		//
		String methodName = method.getName();
		// nowMethodName = methodName;

		if (methodName.startsWith("add") || methodName.startsWith("save") || methodName.startsWith("del")
				|| methodName.startsWith("update") || methodName.startsWith("insert") || methodName.startsWith("cancel")
				|| methodName.startsWith("create")) {
			DataSourceSwitcher.setMaster();
		} else {
			DataSourceSwitcher.setDataSource(slaveSourceName);
		}
	}

	public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable {
		System.out.println("DataSourceAdvice:afterThrowing---> " + method);

		nowMethodName = null;
		DataSourceSwitcher.setMaster();
	}

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("DataSourceAdvice:afterReturning---> " + method);

		// String methodName = method.getName();
		// if (methodName.equals(nowMethodName)) {
		// nowMethodName = null;
		// }

		DataSourceSwitcher.setMaster();
	}
}
