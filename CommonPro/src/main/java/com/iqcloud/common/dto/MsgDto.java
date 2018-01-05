package com.iqcloud.common.dto;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/*
 * 消息体
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MsgDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3156648714478834455L;

	/*
	 * 0: 成功
	 */
	private Integer rtnCode;

	/*
	 * 结果值
	 */
	private Integer rtnResult;

	/*
	 * 消息
	 */
	private String msg;

	/*
	 * 总数
	 */
	private Long total;

	/*
	 * 消息体
	 */
	private Object msgBody;

	public Integer getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(Integer rtnCode) {
		this.rtnCode = rtnCode;
	}

	public Integer getRtnResult() {
		return rtnResult;
	}

	public void setRtnResult(Integer rtnResult) {
		this.rtnResult = rtnResult;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(Object msgBody) {
		this.msgBody = msgBody;
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
