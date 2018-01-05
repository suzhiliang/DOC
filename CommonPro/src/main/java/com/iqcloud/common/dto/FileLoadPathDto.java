package com.iqcloud.common.dto;

import com.iqcloud.common.util.FileControl;

/**
 * 视频文件转换的保存路径
 * 
 * @author admin
 *
 */
public class FileLoadPathDto {
	private static String ffmpegPathCommon = FileControl.getPath("main",
			"ffmpegPath");
	private static String qtFaststartCommon = FileControl.getPath("main",
			"qtFaststartPath"); // MP4移动metadata工具
	private static String rootPathCommon = FileControl.getPath("save_map",
			"resourceRootPath.upload.dir");

	private String ffmpegPath; // ffmpeg转换工具路径
	private String qtFaststart;// qtFaststart工具
	// private String rootAddress; //根目录映射路径
	private String rootPath; // 根目录路径

	public FileLoadPathDto() {

		this.ffmpegPath = ffmpegPathCommon;
		this.qtFaststart = qtFaststartCommon;
		this.rootPath = rootPathCommon;
	}

	public String getFfmpegPath() {
		return ffmpegPath;
	}

	public void setFfmpegPath(String ffmpegPath) {
		this.ffmpegPath = ffmpegPath;
	}

	public String getQtFaststart() {
		return qtFaststart;
	}

	public void setQtFaststart(String qtFaststart) {
		this.qtFaststart = qtFaststart;
	}

	/*
	 * public String getRootAddress() { return rootAddress; } public void
	 * setRootAddress(String rootAddress) { this.rootAddress = rootAddress; }
	 */
	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

}
