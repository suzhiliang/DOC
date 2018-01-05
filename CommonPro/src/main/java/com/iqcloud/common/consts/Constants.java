package com.iqcloud.common.consts;

public final class Constants {

	// 分类根目录ID
	// public static final Long CATEGORY_ROOTID = (long)110000;

	/* 文件在线预览支持的文件格式 */
	public static final String[] FORMAT_SUPPORT = { "odt", "ods", "odp", "doc", "docx", "xls", "xlsx", "ppt", "pptx",
			"txt", "pdf" };

	public static final String SUCCESS_CONVENT = "转换成功";

	public static final String FAIL_CONVENT = "转换失败或未转换";

	public static final String DOWNLOAD_FILE_NOTEXIT = "下载的文件不存在！";

	public static final String DOWNLOAD_SUCCESS = "成功获取下载的文件！";

	public static final String PREFIX_SHAREURL = "sharesearch.html?fileid=";

	// 默认显示的每页的页数
	public static final int DEFAULT_PAGE_SIZE = 10;

	public static final String DEFAULT_TYPEFILEID = "70";

	public static final Short DEFAULT_FILECLASSIFY = 4;

	// 显示时间，精确到天
	public static final String DATE_FORMAT_DAY = "yyyy-MM-dd";

	// 显示时间，精确到月
	public static final String DATE_FORMAT_MONTH = "yyyy-MM";

	// 显示时间，精确到天
	public static final String DATE_FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";

	// 显示时间,只显示时分
	public static final String DATE_FORMAT_THREE = "HH:mm";

	// 显示时间,只显示时分秒
	public static final String DATE_FORMAT_TIME = "HH:mm:ss";

	// 异常情况提示信息
	public static final String EXCEPTION_MESSAGE = "异常情况，请联系管理员！";

	// 老师角色类别
	public static final Integer ROLE_TEACHER = 3;

	// 学生角色类别
	public static final Integer ROLE_STUDENT = 4;

	// 共有知识点根目录ID
	public static final String KPOINT_ROOT_CATID = "1000170";

	// 校本知识点根目录ID
	public static final String SCHOOL_ROOT_CATID = "1001790";

	// 视频文件的格式列表
	public static final String[] VIDEO_EXT = { "rm", "rmvb", "mpeg1", "mpeg2", "mpeg3", "mpeg4", "mov", "mtv", "dat",
			"wmv", "avi", "3gp", "amv", "dmv", "flv", "mp4", "asf", "mkv", "mpg", "asx", "vob", "dat", "wmv9", "mp3",
			"wav", "wma", "qsv", "mts" };

	// 不支持转换的视屏格式
	public static final String[] NOT_SUPPORT_VIDEO_EXT = { "qsv", "mts" };

	// 图片文件的格式列表
	public static final String[] IMAGE_EXT = { "bmp", "dib", "rle", "emf", "gif", "jpg", "jpeg", "jpe", "jif", "pcx",
			"dcx", "pic", "png", "tga", "tif", "tiffxif", "wmf", "jfif" };

	// 分享成功的图片名称
	public static final String SUCCESSIMG = "Success.png";

	// 分享失败的图片名称
	public static final String FAILIMG = "Fail.png";

	// 星期在字典中propid值
	public static final Integer WEEK_DICPROPID = 4;

	// 视频缩略图
	public static final String VIDEO_THUMB_SIZE = "160*120";

	// 成功提示代号
	public static final int SUCCESS_CODE = 0;

	// 其他错误提示代号
	public static final int ERROR_CODE = -1;

	// 参数为空提示代号
	public static final int ERROR_CODE2 = -3;

	// 其他错误提示代号
	public static final int ERROR_CODE3 = -2;

	// 参数为空的错误提示信息
	public static final String ERROR_MESSAGE = "访问的参数为空";

	// 云网盘目录在字典中的类别值
	public static final Integer CLOUDDISK_DISK_TYPE = 4;

	// 班牌显示的开始时间
	public static final String BEGIN_SHOW_TIME = "00:00";
}
