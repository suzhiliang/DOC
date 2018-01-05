package com.iqcloud.common.dto;

public class PreViewPathDTO {

	private Integer exceptionCode;// 异常编号
	private String viewImageMappingPath; // 视频预览图路径
	private String viewMappingPath; // 视频预览路径

	private String flashMappingPath; // flash文件预览路径

	private String imgMappingPath; // 图片预览路径

	private String swfMappingPath; // 原始swf文件预览路径

	private String swfImageMappingPath; // swf文件图片预览路径

	private Integer fileType;// 文件类型（1-视屏文件2-图片文件3-swf文件4-其他文件格式）

	public String getViewImageMappingPath() {
		return viewImageMappingPath;
	}

	public void setViewImageMappingPath(String viewImageMappingPath) {
		this.viewImageMappingPath = viewImageMappingPath;
	}

	public String getViewMappingPath() {
		return viewMappingPath;
	}

	public void setViewMappingPath(String viewMappingPath) {
		this.viewMappingPath = viewMappingPath;
	}

	public String getFlashMappingPath() {
		return flashMappingPath;
	}

	public void setFlashMappingPath(String flashMappingPath) {
		this.flashMappingPath = flashMappingPath;
	}

	public String getImgMappingPath() {
		return imgMappingPath;
	}

	public void setImgMappingPath(String imgMappingPath) {
		this.imgMappingPath = imgMappingPath;
	}

	public String getSwfMappingPath() {
		return swfMappingPath;
	}

	public void setSwfMappingPath(String swfMappingPath) {
		this.swfMappingPath = swfMappingPath;
	}

	public Integer getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(Integer exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getSwfImageMappingPath() {
		return swfImageMappingPath;
	}

	public void setSwfImageMappingPath(String swfImageMappingPath) {
		this.swfImageMappingPath = swfImageMappingPath;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

}
