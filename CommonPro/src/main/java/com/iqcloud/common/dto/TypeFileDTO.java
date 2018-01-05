package com.iqcloud.common.dto;

public class TypeFileDTO {

	private String typeFileID; // 文件类型ID
	private short fileClassify; // 文件类型分类

	public String getTypeFileID() {
		return typeFileID;
	}

	public void setTypeFileID(String typeFileID) {
		this.typeFileID = typeFileID;
	}

	public short getFileClassify() {
		return fileClassify;
	}

	public void setFileClassify(short fileClassify) {
		this.fileClassify = fileClassify;
	}

}
