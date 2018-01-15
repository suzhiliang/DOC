package com.iqcloud.common.util;

import java.io.File;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

public class OfficeToPdf {
	/**
	 * 将Office文档转换为PDF. 需要安装OpenOffice
	 * 
	 * @param inputFilePath
	 *            源文件,绝对路径. 可以是Office2003-2007全部格式的文档, 包括.doc, .docx, .xls,
	 *            .xlsx, .ppt, .pptx等.
	 * @param outputFilePath
	 *            目标文件.绝对路径.
	 */
	public static File office2pdf(File inputFile, String outputFilePath, int port) {
		File outputFile = null;
		OfficeManager officeManager = null;
		try {
			System.out.println("openOffice 端口号---> " + port);

			System.out.println("源文件路径---> " + inputFile.getPath());
			System.out.println("目标文件---> " + outputFilePath);
			DefaultOfficeManagerConfiguration config = new DefaultOfficeManagerConfiguration();
			String openOfficeHOME = FileControl.getPath("main", "openOfficeHOME");
			config.setOfficeHome(openOfficeHOME);
			// 设置转换端口，默认为8100
			config.setPortNumber(port);
			// 设置任务执行超时为60分钟
			config.setTaskExecutionTimeout(1000 * 60 * 60L);
			// 设置任务队列超市为24小时
			config.setTaskQueueTimeout(1000 * 60 * 60 * 24L);
		    officeManager = config.buildOfficeManager();

			officeManager.start();
			System.out.println("openOffice 服务启动成功!...");

			OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);

			System.out.println("开始判断源文件是否存在！...");

			if (inputFile.exists()) {// 找不到源文件，则返回
				System.out.println("开始判断目标目标文件及路径的合法性！...");

				outputFile = new File(outputFilePath);
				if (!outputFile.getParentFile().exists()) { // 假如目标路径不存在, 则新建该路径
					outputFile.getParentFile().mkdirs();
				}

				System.out.println("结束判断目标目标文件及路径的合法性！...");

				System.out.println("开始转化!...");

				converter.convert(inputFile, outputFile);

				System.out.println("结束转化!...");
			}

			if (null != officeManager) {
				System.out.println("开始结束openoffice服务!...");

				officeManager.stop();

				System.out.println("结束openOffice服务成功!...");

				officeManager = null;
			}
		} catch (Exception e) {
			outputFile = null;
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			System.out.println("try catch finally 中的finally执行!...");
			if (null != officeManager) {
				officeManager.stop();
				officeManager = null;
			}
		}

		return outputFile;
	}

}
