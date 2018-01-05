package com.iqcloud.common.dto;

/**
 * 存放oppenoffice启动的端口号的值和是否被占用
 * 
 * @Company Ruida
 * @author: SuZL
 * @data: 2016年12月2日
 */
public class OppenOfficePortDto {
	private Integer port;// 端口号
	private Integer state;// 占用状态0-未被占用1--正被占用着

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
