package com.iqcloud.common.dto;

import java.io.Serializable;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class IQCloudInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3750316911935277326L;

	/**
	 * 消息头
	 */
	private String msgHead;

	/**
	 * 消息体
	 */
	private String msgBody;

	/**
	 * 消息头
	 */
	private Object jsonHead;

	/**
	 * 消息体
	 */
	private Object jsonBody;

	public String getMsgHead() {
		return msgHead;
	}

	public void setMsgHead(String msgHead) {
		this.msgHead = msgHead;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public Object getJsonHead() {
		return jsonHead;
	}

	public void setJsonHead(Object jsonHead) {
		this.jsonHead = jsonHead;
	}

	public Object getJsonBody() {
		return jsonBody;
	}

	public void setJsonBody(Object jsonBody) {
		this.jsonBody = jsonBody;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
