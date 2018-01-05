package com.iqcloud.common.dto;

import java.io.Serializable;

/**
 * 上传文件返回的文件参数
 * 
 * @author admin
 *
 */
public class UploadFileParaDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 853121250084534761L;
	
	private String id;//记录id
	private String originFile; // 原始文件
	private String filePath; // 处理后的文件
	private String fileExt; // 文件的后缀名
	private String fileName;// 处理后的文件的名称
	private String fileMD5; // 文件的MD5
	private String fileSize; // 文件大小
	private String thumbnail; // 视频缩略图，非视频值为空
	private String mappingPath; // 视频预览的临时路径
	private String targetFile; //swf预览文件路径
	private Short fileType; // 上传文件的类型

	public String getOriginFile() {
		return originFile;
	}

	public void setOriginFile(String originFile) {
		this.originFile = originFile;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileMD5() {
		return fileMD5;
	}

	public void setFileMD5(String fileMD5) {
		this.fileMD5 = fileMD5;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getMappingPath() {
		return mappingPath;
	}

	public void setMappingPath(String mappingPath) {
		this.mappingPath = mappingPath;
	}

	public Short getFileType() {
		return fileType;
	}

	public void setFileType(Short fileType) {
		this.fileType = fileType;
	}

	public String getTargetFile() {
		return targetFile;
	}

	public void setTargetFile(String targetFile) {
		this.targetFile = targetFile;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
