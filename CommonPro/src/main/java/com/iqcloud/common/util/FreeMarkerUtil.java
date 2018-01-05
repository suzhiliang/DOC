package com.iqcloud.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import sun.misc.BASE64Encoder;

/**
 * Copyright © 2017RUIDA. All rights reserved.
 * 
 * @ClassName: FreeMarkerUtil
 * @Description: freemark工具
 * @author: SZL
 * @date: 2017年10月27日 上午9:55:48
 */
@SuppressWarnings("restriction")
public class FreeMarkerUtil {

	private Configuration configuration = null;

	/**
	 * 构造方法
	 */
	public FreeMarkerUtil(Configuration configuration) {
		try {
			this.configuration = configuration;
			configuration.setDefaultEncoding("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据类路径获取模板
	 * 
	 * @param templatePath
	 * @param templateName
	 * @return
	 * @throws IOException
	 */
	private Template getTemplate(String templatePath, String templateName) throws IOException {
		configuration.setDirectoryForTemplateLoading(new File(templatePath));
		Template t = configuration.getTemplate(templateName);
		t.setEncoding("UTF-8");
		return t;
	}

	/**
	 * 生成word文档
	 * 
	 * @param templatePath
	 * @param templateName
	 * @param dataMap
	 * @param out
	 */
	public void write(String templatePath, String templateName, Map<String, Object> dataMap, Writer out) {
		try {
			Template t = getTemplate(templatePath, templateName);
			t.process(dataMap, out);
			out.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @Title: creatImageDatas
	 * @Description: 构造图片数据
	 * @param themeKey
	 * @param imageKey
	 * @param imageNameKey
	 * @param imageDateKey
	 * @param rIdsKey
	 * @param filePaths
	 * @return: Map<String,Object>
	 */
	public Map<String, Object> creatImageDatas(String themeKey, String imageKey, String imageNameKey,
			String imageDateKey, String rIdsKey, String... filePaths) {
		List<Map<String, Object>> imageDates = new ArrayList<Map<String, Object>>();
		Map<String, Object> imageDate = null;
		int i = 0;
		String suffix = null;
		String base64 = null;
		Integer themeNum = null;
		String theme = null;
		String image = null;
		StringBuffer themeBuffer = new StringBuffer();
		StringBuffer imageBuffer = new StringBuffer();
		List<String> rIds = new ArrayList<String>();
		for (String filePath : filePaths) {
			i++;
			suffix = filePath.substring(filePath.lastIndexOf("."));
			imageDate = new HashMap<String, Object>();
			Date t2 = new Date();
			imageDate.put(imageNameKey, "/word/media/image" + i + suffix);
			base64 = imageToBase64(filePath);
			System.out.println("转换的base64-----------》" + base64);
			imageDate.put(imageDateKey, base64);
			System.out.println("转换第" + i + "张图片用时----------->" + (new Date().getTime() - t2.getTime()));
			themeNum = i + 6;
			theme = "<Relationship Id=\"rId" + themeNum
					+ "\"  Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument\" Target=\"word/document.xml\" />"
					+ "\n";
			themeBuffer.append(theme);
			image = "<Relationship Id=\"rId" + themeNum
					+ "\"  Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/image\" Target=\"media/image"
					+ i + suffix + " />\n";
			imageBuffer.append(image);
			rIds.add("rId" + themeNum.toString());
			imageDates.add(imageDate);
		}
		Map<String, Object> imageMap = new HashMap<String, Object>();
		imageMap.put("imageDateKey", imageDates);
		imageMap.put("themeKey", theme);
		imageMap.put("imageKey", image);
		imageMap.put("rIdsKey", rIds);
		return imageMap;
	}

	/**
	 * @Title: imageToBase64
	 * @Description: 图片转换base64
	 * @param imagePath
	 * @return: void
	 */
	private String imageToBase64(String imagePath) {
		BASE64Encoder base64Encoder = new BASE64Encoder();
		InputStream in = null;
		byte[] date = null;
		try {
			in = new FileInputStream(new File(imagePath));
			date = new byte[in.available()];
			in.read(date);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return base64Encoder.encode(date);
	}

}