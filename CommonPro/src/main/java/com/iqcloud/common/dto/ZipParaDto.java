package com.iqcloud.common.dto;

/**
 * zip压缩所需的参数
 * 
 * @author admin
 *
 */
public class ZipParaDto {
	private String zipName; // 放入zip压缩包的文件名称
	private String zipFilePath; // 放入zip压缩包的文件

	public String getZipName() {
		return zipName;
	}

	public void setZipName(String zipName) {
		this.zipName = zipName;
	}

	public String getZipFilePath() {
		return zipFilePath;
	}

	public void setZipFilePath(String zipFilePath) {
		this.zipFilePath = zipFilePath;
	}

}
