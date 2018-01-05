package com.iqcloud.common.dto;

import java.io.Serializable;

public class ReturnMsgDto implements Serializable {
	private static final long serialVersionUID = 6605941299039539840L;

	private Integer rtnCode; // 返回状态
	private Integer resultInt;
	private String message; // 返回消息
	private Object resultObject; // 返回的对象
	private Long total; // 总记录数量

	public Integer getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(Integer rtnCode) {
		this.rtnCode = rtnCode;
	}

	public Integer getResultInt() {
		return resultInt;
	}

	public void setResultInt(Integer resultInt) {
		this.resultInt = resultInt;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResultObject() {
		return resultObject;
	}

	public void setResultObject(Object resultObject) {
		this.resultObject = resultObject;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
