package com.iqcloud.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import com.iqcloud.common.consts.ConstantPaths;
import com.iqcloud.common.consts.Constants;
import com.iqcloud.common.dto.FileLoadPathDto;
import com.iqcloud.common.dto.PreViewPathDTO;
import com.iqcloud.common.dto.ReturnMsgDto;
import com.iqcloud.common.dto.ShareTaskCompleteInfoDto;
import com.iqcloud.common.dto.ViewPathDTO;
import com.iqcloud.common.dto.ZipParaDto;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.rtf.RtfWriter2;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@SuppressWarnings("restriction")
public class Tools {

	public static String converSizeFm(long size, String format) {
		String ressize = "";
		DecimalFormat df = new DecimalFormat(format);
		int count = 0;
		double value = (double) size;

		while (value >= 1024) {
			count++;
			value = value / 1024;
		}

		ressize = String.valueOf(df.format(value));

		switch (count) {
		case 0:
			ressize += " B";
			break;
		case 1:
			ressize += " KB";
			break;
		case 2:
			ressize += " M";
			break;
		case 3:
			ressize += " G";
			break;
		case 4:
			ressize += " T";
			break;
		}

		return ressize;
	}

	// 默认格式版本
	public static String converSizeFm(long size) {
		String ressize = "";
		String format = "#0.00";
		DecimalFormat df = new DecimalFormat(format);
		int count = 0;
		double value = (double) size;

		while (value >= 1024) {
			count++;
			value = value / 1024;
		}

		ressize = String.valueOf(df.format(value));

		switch (count) {
		case 0:
			ressize += " B";
			break;
		case 1:
			ressize += " KB";
			break;
		case 2:
			ressize += " M";
			break;
		case 3:
			ressize += " G";
			break;
		case 4:
			ressize += " T";
			break;
		}

		return ressize;
	}

	/**
	 * 文件大小由字符串换算成长整型
	 * 
	 * @param sizeStr
	 * @return
	 */
	public static Long converSizeFm(String sizeStr) {
		Long size = null;

		String[] str = sizeStr.split(" ");
		Double value = Double.parseDouble(str[0]);
		String ext = str[1];

		switch (ext) {
		case "B":
			size = (long) (value * 1);
			break;
		case "KB":
			size = (long) (value * 1024);
			break;
		case "M":
			size = (long) (value * 1024 * 1024);
			break;
		case "G":
			size = (long) (value * 1024 * 1024 * 1024);
			break;
		case "T":
			size = (long) (value * 1024 * 1024 * 1024 * 1024);
			break;
		}

		return size;
	}

	/**
	 * 文件大小由字符串换算成长整型
	 * 
	 * @param sizeStr
	 * @return
	 */
	public static Short getFileSuffixType(String FileSuffix) {
		Short suffix = null;

		if ("png".equalsIgnoreCase(FileSuffix) || "jpg".equalsIgnoreCase(FileSuffix)
				|| "gif".equalsIgnoreCase(FileSuffix) || "jpeg".equalsIgnoreCase(FileSuffix)) {
			suffix = 1;
		} else if ("txt".equalsIgnoreCase(FileSuffix) || "doc".equalsIgnoreCase(FileSuffix)
				|| "docx".equalsIgnoreCase(FileSuffix) || "pdf".equalsIgnoreCase(FileSuffix)
				|| "xls".equalsIgnoreCase(FileSuffix) || "xlsx".equalsIgnoreCase(FileSuffix)
				|| "ppt".equalsIgnoreCase(FileSuffix)) {
			suffix = 2;
		} else if ("mp3".equalsIgnoreCase(FileSuffix) || "mp4".equalsIgnoreCase(FileSuffix)
				|| "avi".equalsIgnoreCase(FileSuffix) || "wma".equalsIgnoreCase(FileSuffix)
				|| "wav".equalsIgnoreCase(FileSuffix) || "asf".equalsIgnoreCase(FileSuffix)
				|| "rmvb".equalsIgnoreCase(FileSuffix) || "mpg".equalsIgnoreCase(FileSuffix)
				|| "mov".equalsIgnoreCase(FileSuffix) || "wmv".equalsIgnoreCase(FileSuffix)
				|| "flv".equalsIgnoreCase(FileSuffix)) {
			suffix = 3;
		} else {
			suffix = 4;
		}

		return suffix;
	}

	/**
	 * 将html内容转化为word文档
	 * 
	 * @param content
	 * @param wordPath
	 */
	public static void htmlToWord(String content, String wordPath) {
		Document document = null;
		OutputStream out = null;
		Paragraph paragraph = new Paragraph();
		StyleSheet ss = new StyleSheet();
		List<PdfPTable> htmlList = new ArrayList<PdfPTable>();

		try {
			out = new FileOutputStream(wordPath);
			document = new Document(PageSize.A4);
			RtfWriter2.getInstance(document, out);
			document.open();

			wordHandle(content, document, paragraph, ss, htmlList);
		} catch (IOException e) {

			e.printStackTrace();
		} catch (DocumentException e) {

			e.printStackTrace();
		} finally {

			try {
				if (document != null)
					document.close();
				if (out != null)
					out.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static void wordHandle(String content, Document document, Paragraph paragraph, StyleSheet ss,
			List<PdfPTable> htmlList) throws IOException, DocumentException {
		String context = null;
		String image = null;
		int index;
		int endIndex;

		String imagePath = FileControl.getPath("save_map", "resourceRootPath.upload.dir")
				+ ConstantPaths.HEAD_IMAGE_TEMP_PATH;
		String accessImagePath = FileControl.getPath("save_map", "rootPath.access.address")
				+ ConstantPaths.HEAD_IMAGE_TEMP_PATH;

		if ((index = content.indexOf("<img")) != -1) {
			// 文档处理
			if (index != 0) {
				context = content.substring(0, index);
				System.out.println("context:" + context);

				ss = new StyleSheet();
				htmlList = HTMLWorker.parseToList(new StringReader(context), ss);
				for (int i = 0; i < htmlList.size(); i++) {
					Element e = (com.lowagie.text.Element) htmlList.get(i);
					paragraph.add(e);
				}

				document.add(paragraph);
				paragraph.clear();
			}

			// 图片处理
			endIndex = content.indexOf("/>", index) + 2;
			image = content.substring(index, endIndex);
			System.out.println("image:" + image);

			Float width = null;
			Float height = null;
			String src = null;
			String alt = null;

			Integer imgIndex = null;
			String url = null;

			if ((imgIndex = image.indexOf("src")) != -1) {
				url = image.substring(imgIndex + 5);

				src = url.substring(0, url.indexOf("\""));
				System.out.println("src:" + src);
				src = src.replaceAll(accessImagePath, imagePath);
			}

			if ((imgIndex = image.indexOf("width")) != -1) {
				url = image.substring(imgIndex + 7);

				width = Float.parseFloat(url.substring(0, url.indexOf("\"")));
			}

			if ((imgIndex = image.indexOf("height")) != -1) {
				url = image.substring(imgIndex + 8);

				height = Float.parseFloat(url.substring(0, url.indexOf("\"")));
			}

			if ((imgIndex = image.indexOf("alt")) != -1) {
				url = image.substring(imgIndex + 5);

				alt = url.substring(0, url.indexOf("\""));
			}

			Image png = Image.getInstance(src);
			if (alt != null)
				png.setAlt(alt);

			if (width != null)
				png.scaleAbsoluteWidth(width);

			if (height != null)
				png.scaleAbsoluteHeight(height);

			document.add(png);

			content = content.substring(endIndex);
			System.out.println("content:" + content);

			wordHandle(content, document, paragraph, ss, htmlList);
		} else {
			if (!"".equals(content)) {
				ss = new StyleSheet();
				htmlList = HTMLWorker.parseToList(new StringReader(content), ss);
				for (int i = 0; i < htmlList.size(); i++) {
					Element e = (com.lowagie.text.Element) htmlList.get(i);
					paragraph.add(e);
				}

				document.add(paragraph);
				paragraph.clear();
			}

			return;
		}
	}

	public static String getDiffTimeMsg(Date fromDate, Date toDate) {
		String diffTime = null;

		Long fromTime = fromDate.getTime();
		Long toTime = toDate.getTime();
		Long differ = Math.abs(toTime - fromTime) / (1000 * 3600); // 时间差，精确到小时

		if (differ <= 0) {
			diffTime = "1小时内";
		} else if (differ >= 1 && differ <= 24) {
			diffTime = differ + "小时前";
		} else if (differ > 24 && differ <= (24 * 5)) {
			diffTime = differ / 24 + "天前";
		} else {
			diffTime = fromDate.toString();
		}

		return diffTime;
	}

	/**
	 * 获取文件的编码
	 * 
	 * @param file
	 * @return
	 */
	public static String getCharset(File file) {
		BufferedInputStream bis = null;
		String first = null;

		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			byte[] b = new byte[10];
			bis.read(b, 0, b.length);
			first = toHex(b);
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}

		// 这里可以看到各种编码的前几个字符是什么，gbk编码前面没有多余的
		String code = null;

		if (first.startsWith("EFBBBF")) {
			code = "UTF-8";
		} else if (first.startsWith("FEFF")) {
			code = "UTF-16BE";
		} else if (first.startsWith("FFFE")) {
			code = "Unicode";
		} else {
			code = "GBK";
		}

		return code;
	}

	private static String toHex(byte[] byteArray) {
		int i;
		StringBuffer buf = new StringBuffer("");
		int len = byteArray.length;
		for (int offset = 0; offset < len; offset++) {
			i = byteArray[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString().toUpperCase();
	}

	public static int converRoleType(String roleTypeString) {
		int roleType = 0;

		switch (roleTypeString) {
		case "STUDENT":
			roleType = 1;
			break;
		case "TEACHER":
			roleType = 2;
			break;
		case "PARENT":
			roleType = 3;
			break;
		case "CLASS":
			roleType = 4;
			break;
		}

		return roleType;
	}

	/**
	 * 预览文件转换
	 * 
	 * @param filePath
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static PreViewPathDTO fileConvent(String filePath, String fileName, String commonSwfFile) {
		PreViewPathDTO preViewPathDTO = new PreViewPathDTO();
		ReturnMsgDto returnMsg = new ReturnMsgDto();
		try {
			filePath = filePath.replaceAll("\\\\", "/");

			String rootPath = FileControl.getPath("save_map", "resourceRootPath.upload.dir");
			// 资源的swf文件被访问地址

			String fileExt = filePath.substring(filePath.lastIndexOf(".") + 1);
			int extType = 0;

			// 判断文件是否是视频文件
			for (String videoExt : Constants.VIDEO_EXT) {

				if (videoExt.equalsIgnoreCase(fileExt)) {
					extType = 1;
					break;
				}
			}

			if (extType != 1) {
				// 判断文件是否是图片文件
				for (String imageExt : Constants.IMAGE_EXT) {

					if (imageExt.equalsIgnoreCase(fileExt)) {
						extType = 2;
						break;
					}
				}
			}

			if ("swf".equalsIgnoreCase(fileExt)) {
				extType = 3;
			}
			preViewPathDTO.setFileType(extType);
			if (extType == 1) { // 视频文件转换
				FileLoadPathDto fileLoadPathDto = new FileLoadPathDto();
				returnMsg = FileControl.uploadVideoCommon(filePath, fileLoadPathDto);
				ViewPathDTO viewPathDTO = (ViewPathDTO) returnMsg.getResultObject();
				preViewPathDTO.setViewImageMappingPath(viewPathDTO.getViewImageMappingPath());
				preViewPathDTO.setViewMappingPath(viewPathDTO.getViewMappingPath());
			} else if (extType == 2) { // 图片预览文件
				preViewPathDTO.setImgMappingPath(filePath);
			} else if (extType == 3) {
				// swf文件获取截图
				String thumbnailPath = FileConventer.cutSwfToThumbnail(rootPath + filePath);
				preViewPathDTO.setSwfImageMappingPath(thumbnailPath.substring(rootPath.length()));
				preViewPathDTO.setSwfMappingPath(filePath);
			} else { // 其他文件转换
				extType = 4;
				String path = rootPath + filePath; // 获取资源文件存放的全路径
				System.out.println("=============path:" + path + "================");
				returnMsg = FileControl.fileConvent(path, fileName);
				Map<String, String> map = (Map<String, String>) returnMsg.getResultObject();
				String thumbnailPath = map.get("thumbnailPath");
				String targetFile = map.get("swfPath");
				preViewPathDTO.setExceptionCode(returnMsg.getRtnCode());
				if (targetFile != null) {
					targetFile = targetFile.substring(rootPath.length());
				} else {
					targetFile = commonSwfFile;
				}
				if (thumbnailPath != null) {
					preViewPathDTO.setSwfImageMappingPath(thumbnailPath);
				}
				preViewPathDTO.setFlashMappingPath(targetFile);
			}
			preViewPathDTO.setFileType(extType);
		} catch (Exception e) {
			preViewPathDTO.setExceptionCode(-5);
			preViewPathDTO.setFileType(-5);
			System.out.println("预览文件转换文件转换异常----------------》");
		}
		return preViewPathDTO;
	}

	public static boolean createMark(String filePath, String outFilePath,
			ShareTaskCompleteInfoDto shareTaskCompleteInfo) {
		// ImageIcon imgIcon = new ImageIcon(filePath);
		BufferedImage theImg;
		try {
			theImg = ImageIO.read(new File(filePath));
		} catch (IOException e1) {

			e1.printStackTrace();
			return false;
		}

		// java.awt.Image theImg = imgIcon.getImage();
		// Image可以获得 输入图片的信息
		int width = theImg.getWidth(null);
		int height = theImg.getHeight(null);

		// 800 800 为画出图片的大小
		BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// 2d 画笔
		Graphics2D g = bimage.createGraphics();
		g.setBackground(Color.white);

		// 画出图片-----------------------------------
		g.drawImage(theImg, 0, 0, null);
		// 画出图片-----------------------------------

		// --------对要显示的文字进行处理--------------
		Integer isSuccess = shareTaskCompleteInfo.getIsSuccess();
		// Color color = Color.RED;
		Color color = new Color(238, 123, 135);
		g.setColor(color);

		/*
		 * if (isSuccess == 1) { String markContent =
		 * shareTaskCompleteInfo.getTaskTitle();// 任务的名称 String fontTypeS =
		 * "宋体"; AttributedCharacterIterator iter = setFontType(markContent,
		 * fontTypeS, 35);
		 * 
		 * Color color = Color.RED; g.setColor(color);
		 * 
		 * g.drawString(iter, 250, 200); }
		 */

		/*
		 * Color markContentColor = Color.YELLOW; g.setColor(markContentColor);
		 */

		String fontType = "微软雅黑";
		int fontSize = 40;
		int fontSize2 = 24;

		String markContent1 = shareTaskCompleteInfo.getIntegral().toString();// 任务积分
		AttributedCharacterIterator iter1 = setFontType(markContent1, fontType, fontSize);

		String markContent2 = shareTaskCompleteInfo.getIntegralSort().toString();// 积分班级排名
		AttributedCharacterIterator iter2 = setFontType(markContent2, fontType, fontSize);

		String markContent3 = shareTaskCompleteInfo.getCompleteNum().toString() + "/"
				+ shareTaskCompleteInfo.getTaskNum().toString();// 任务完成个数
		AttributedCharacterIterator iter3 = setFontType(markContent3, fontType, fontSize2);

		/*
		 * String markContent4 =
		 * shareTaskCompleteInfo.getTaskNum().toString();// 任务个数
		 * AttributedCharacterIterator iter4 = setFontType(markContent4,
		 * fontType,fontSize2);
		 */

		String markContent4 = shareTaskCompleteInfo.getQuestionNum().toString();// 学生提问个数
		AttributedCharacterIterator iter4 = setFontType(markContent4, fontType, fontSize2);

		g.drawString(iter1, 620, 215);

		g.drawString(iter2, 628, 265);

		g.drawString(iter3, 340, 360);

		g.drawString(iter4, 760, 360);

		if (isSuccess == 1) {

			String markContent5 = shareTaskCompleteInfo.getCompleteSort().toString();// 任务提交及时率
			AttributedCharacterIterator iter5 = setFontType(markContent5, fontType, fontSize2);
			g.drawString(iter5, 562, 360);
		}

		// AttributedCharacterIterator iter5 = null;
		// AttributedCharacterIterator iter6 = null;
		// AttributedCharacterIterator iter7 = null;

		/*
		 * if (isSuccess == 1) {
		 * 
		 * String markContent5 = shareTaskCompleteInfo.getCompleteSort()
		 * .toString();// 任务提交及时率 iter5 = setFontType(markContent5, fontType,
		 * 15);
		 * 
		 * String markContent6 = shareTaskCompleteInfo.getCompleteTime()
		 * .toString();// 任务完成用时 iter6 = setFontType(markContent6, fontType,
		 * 15);
		 * 
		 * String markContent7 =
		 * shareTaskCompleteInfo.getQuestionNum().toString();// 学生提问个数 iter7 =
		 * setFontType(markContent7, fontType, 15); }
		 */

		// ----------------------

		/*
		 * if (isSuccess == 1) { g.drawString(iter1, 420, 265);
		 * 
		 * g.drawString(iter2, 450, 308);
		 * 
		 * g.drawString(iter3, 348, 378);
		 * 
		 * g.drawString(iter4, 383, 378);
		 * 
		 * g.drawString(iter5, 368, 407);
		 * 
		 * g.drawString(iter6, 305, 434);
		 * 
		 * g.drawString(iter7, 383, 434); } else {
		 * 
		 * g.drawString(iter1, 420, 222);
		 * 
		 * g.drawString(iter2, 420, 265);
		 * 
		 * g.drawString(iter3, 328, 320);
		 * 
		 * g.drawString(iter4, 360, 320); }
		 */

		// 添加水印的文字和设置水印文字出现的内容 ----位置
		g.dispose();// 画笔结束
		try {
			// 输出 文件 到指定的路径
			FileOutputStream out = new FileOutputStream(outFilePath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);

			param.setQuality(1, true);
			encoder.encode(bimage, param);
			out.close();
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	private static AttributedCharacterIterator setFontType(String markContent, String fontType, int fontSize) {

		AttributedCharacterIterator iter = null;

		AttributedString ats = new AttributedString(markContent);
		Font f = new Font(fontType, Font.PLAIN, fontSize);
		ats.addAttribute(TextAttribute.FONT, f, 0, markContent.length());
		iter = ats.getIterator();

		return iter;
	}

	public static Integer getWeekNum(String weekName) {

		Integer weekNum = 0;

		switch (weekName) {
		case "星期一":
			weekNum = 1;
			break;
		case "星期二":
			weekNum = 2;
			break;
		case "星期三":
			weekNum = 3;
			break;
		case "星期四":
			weekNum = 4;
			break;
		case "星期五":
			weekNum = 5;
			break;
		case "星期六":
			weekNum = 6;
			break;
		case "星期天":
			weekNum = 7;
			break;
		}

		return weekNum;
	}

	/**
	 * @Title: getLastTimeOfMonth
	 * @Description: 获取月份的最后一天
	 * @param date
	 * @return: Date
	 */
	public static Date getLastTimeOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	/**
	 * @Title: getLastTimeOfMonth
	 * @Description: 获取月份的第一天
	 * @param date
	 * @return: Date
	 */
	public static Date getFirstTimeOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	public static String getWeekName(int weekNum) {

		String weekName = null;

		switch (weekNum) {
		case 0:
			weekName = "星期一";
			break;
		case 1:
			weekName = "星期二";
			break;
		case 2:
			weekName = "星期三";
			break;
		case 3:
			weekName = "星期四";
			break;
		case 4:
			weekName = "星期五";
			break;
		case 5:
			weekName = "星期六";
			break;
		case 6:
			weekName = "星期天";
			break;
		}

		return weekName;
	}

	/**
	 * 获得星期的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getWeekDate(Date date, int week) {

		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DAY);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		calendar.add(Calendar.DATE, -day_of_week + week);

		Date formatDate = null;

		try {
			formatDate = sdf.parse(sdf.format(calendar.getTime()));
		} catch (ParseException e) {

			e.printStackTrace();
		}

		return formatDate;

	}

	/**
	 * 时间的减法
	 * 
	 * @param dateStr
	 * @param days
	 * @return
	 */
	public static Date getDateDecrease(Date date, int days) {

		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DAY);

		Date formatDate = null;

		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - days);
			formatDate = sdf.parse(sdf.format(calendar.getTime()));
		} catch (ParseException e) {

			e.printStackTrace();
		}

		return formatDate;

	}

	public static int getWeekOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int week = cal.get(Calendar.DAY_OF_WEEK) - 2;

		// 星期天
		if (week == -1) {
			week = 6;
		}

		return week;
	}

	// 获取时间所在的周
	public static int getWeekOfYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int week = c.get(Calendar.WEEK_OF_YEAR);

		return week;
	}

	public static String getChangeTime(String time, int minute) {

		String resultTime = null;
		String[] str = time.split(":");
		int hour = Integer.parseInt(str[0]); // 小时
		int min = Integer.parseInt(str[1]); // 分钟
		int num = min - minute; // 时间差
		String hourStr = null;
		String minStr = null;

		while (num < 0) {
			hour--;
			num = 60 + num;
			System.out.println("num:" + num);
		}

		min = num;

		/*
		 * if (num < 0) { hour--; min = 60 + num; } else { min = num; }
		 */

		if (hour <= 9) {
			hourStr = "0" + hour;
		} else {
			hourStr = "" + hour;
		}

		if (min <= 9) {
			minStr = "0" + min;
		} else {
			minStr = "" + min;
		}

		resultTime = hourStr + ":" + minStr;

		return resultTime;
	}

	/**
	 * 压缩多文件或文件夹
	 * 
	 * @param downloadPara
	 * @param zipFilePath
	 *            压缩包存放的临时路径
	 */
	public static String compressed(List<ZipParaDto> zipParaList, String zipFilePath) {
		String zipPath = null;
		String zipName = null;
		ZipOutputStream zos = null;
		FileOutputStream fos = null;
		CheckedOutputStream cos = null;
		File zipFile = null;
		File zipTargetFile = null;
		BufferedInputStream bis = null;
		FileInputStream fis = null;
		ZipEntry zipEntry = null;
		String path = null;
		List<String> entryNames = new ArrayList<String>(); // 存放已经添加到entry的文件名

		// 压缩多文件或文件夹
		try {
			zipTargetFile = new File(zipFilePath);
			fos = new FileOutputStream(zipTargetFile);
			cos = new CheckedOutputStream(fos, new CRC32());

			zos = new ZipOutputStream(new BufferedOutputStream(cos));
			byte[] bufs = new byte[1024 * 10];

			for (ZipParaDto zipParaDto : zipParaList) {
				zipName = zipParaDto.getZipFilePath().substring(zipParaDto.getZipFilePath().lastIndexOf("/") + 1);
				zipPath = zipParaDto.getZipFilePath();
				zipFile = new File(zipPath);

				// 若压缩的是空文件夹，则添加文件夹的目录
				if (zipPath.equals("") && !IsEntryExit(entryNames, zipName)) {
					zipEntry = new ZipEntry(zipName);
					zos.putNextEntry(zipEntry);
					entryNames.add(zipName);
					continue;
				}

				// 若磁盘不存在该文件，则不压缩该文件，但保留文件夹目录结构
				if (!zipFile.exists()) {
					zipName = zipName.substring(0, zipName.lastIndexOf("/") + 1);
					if (!IsEntryExit(entryNames, zipName)) {
						zipEntry = new ZipEntry(zipName);
						zos.putNextEntry(zipEntry);
						entryNames.add(zipName);
					}
					continue;
				}

				// 创建ZIP实体，并添加进压缩包
				zipEntry = new ZipEntry(zipName);
				zos.putNextEntry(zipEntry);

				int read = 0;

				// 读取待压缩的文件并写进压缩包里
				fis = new FileInputStream(zipFile);
				bis = new BufferedInputStream(fis, 1024 * 10);

				while ((read = bis.read(bufs, 0, bufs.length)) != -1) {
					zos.write(bufs, 0, read);
				}
			}

			// 压缩后的文件路径
			path = zipFilePath.substring(zipFilePath.lastIndexOf("/") + 1);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				if (null != zos)
					zos.close();
				if (null != bis)
					bis.close();
				if (null != fos)
					fos.close();
				if (null != fis)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		return path;
	}

	private static boolean IsEntryExit(List<String> entryNames, String zipName) {
		boolean flag = false;

		for (String entryName : entryNames) {
			if (entryName.equals(zipName))
				flag = true;
		}

		return flag;
	}

	/**
	 * @Title: getGrandSort
	 * @Description: 获取年级排序值
	 * @param gradeName
	 * @return: int
	 */
	public static int getGrandSort(String gradeName) {
		int code = 0;
		switch (gradeName) {
		case "小学":
			code = 10;
			break;
		case "初中":
			code = 20;
			break;
		case "高中":
			code = 30;
			break;
		default:
			code = 30;
			break;
		}
		return code;
	}

	/**
	 * @Title: getCourseSort
	 * @Description:获取课程排序值
	 * @param gradeSort
	 * @param courseName
	 * @return: int
	 */
	public static int getCourseSort(int gradeSort, String courseName) {
		switch (courseName) {
		case "语文":
			gradeSort += 0;
			break;
		case "数学":
			gradeSort += 1;
			break;
		case "英语":
			gradeSort += 2;
			break;
		case "物理":
			gradeSort += 3;
			break;
		case "化学":
			gradeSort += 4;
			break;
		case "生物":
			gradeSort += 5;
			break;
		case "政治":
			gradeSort += 6;
			break;
		case "历史":
			gradeSort += 7;
			break;
		case "地理":
			gradeSort += 8;
			break;
		default:
			gradeSort += 9;
			break;
		}
		return gradeSort;
	}

}
