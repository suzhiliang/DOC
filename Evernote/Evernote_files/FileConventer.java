package com.iqcloud.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.iqcloud.common.consts.ConstantRedis;
import com.iqcloud.common.dto.OppenOfficePortDto;
import com.iqcloud.common.dto.ReturnMsgDto;
import com.iqcloud.redis.client.JredisClient;

public class FileConventer {
	private static final Logger logger = LoggerFactory.getLogger(FileConventer.class);
	private static final int SUCCESS = 0;
	private static final int FAILURE = -2;
	private static final int EXCEPTION = -1;
	// 在服务器中的oppenoffice的端口号rediskey值
	private static String portKey = null;
	// oppenoffice的端口号的值
	private static String portValue = FileControl.getPath("main", "openOfficePort");

	private static String pdf2swfPath = FileControl.getPath("main", "pdf2swfPath");

	// 转换缩列图工具路劲
	private static String swfrenderPath = FileControl.getPath("main", "swfrenderPath");

	// oppenOffice启动指令
	private static String openOfficeHOME = FileControl.getPath("main", "openOfficeHOME");

	// openOffice对应的在redis端口号使用情况锁
	private static Integer redisOpenOfficePortLock = 0;

	private static String rootPath = FileControl.getPath("save_map", "resourceRootPath.upload.dir");

	public static void FileView(String fileString, int env, ReturnMsgDto returnMsg) {
		int environment = env;
		String converfilename = fileString.replaceAll("\\\\", "/");
		iniFile(converfilename, environment, returnMsg);
		return;
	}

	public static void iniFile(String filePath, int environment, ReturnMsgDto returnMsg) {
		System.out.println("--------------iniFile----------------");
		Map<String, String> pathMap = new HashMap<String, String>();
		String txtName = null;
		File pdfFile = null;
		File conventSwfFile = null;
		File tempPdf = null;
		File docFile = null;
		boolean hasWhitespace = false;
		String openOfficeStartCommon = openOfficeHOME;
		Integer port = null;
		System.out.println("-----------------iniFile 加锁----------------------");
		// 一直循环获取，如果获取失败，则等待一会（2秒）再获取，如此循环等待不能超过30分钟（900次），如果次数超过900次，则认为失败
		int count = 0;
		while ((null == port) && (count < 900)) {
			synchronized (redisOpenOfficePortLock) {
				port = getFreePort();
			}
			try {
				if (null == port) {
					System.out.println("端口号为空，线程睡眠 2000毫秒后，再重新获取可用端口号...");
					Thread.sleep(2000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count++;
		}
		System.out.println("-----------------iniFile 释放加锁----------------------");
		System.out.println("Oppefoffice端口号***********************" + port);
		if (port == null) {
			returnMsg.setRtnCode(EXCEPTION);
			System.out.println("************没有空闲得端口号***********************");
			returnMsg.setMessage("没有空闲得端口号");
			return;
		}
		openOfficeStartCommon = openOfficeStartCommon + " " + port;
		try {
			String pdfPath = FileControl.getPath("temp_url", "pdfPath");
			String swfTempPath = FileControl.getPath("temp_url", "swfPath");
			System.out.println("进入转换--------------------------》临时pdf:" + pdfPath);
			System.out.println("进入转换--------------------------》临时swf:" + pdfPath);
			UUID random = UUID.randomUUID(); // 随机文件名
			txtName = filePath.substring(filePath.lastIndexOf(".") + 1);
			String fileName = filePath.substring(0, filePath.lastIndexOf("."));
			// 解决txt文件中文乱码问题
			docFile = txtConvent(filePath, fileName, txtName);
			File swfFile = new File(fileName + ".swf" + "." + random + ".swf");
			// 如果文件名存在有空格的就把文件拷贝复制一份临时文件用于转换（文件名有空格转换不了）
			try {
				if (fileName.indexOf(" ") != -1) {
					conventSwfFile = new File(swfTempPath + random + ".swf");
					hasWhitespace = true;
				} else {
					conventSwfFile = swfFile;
				}
				/* 若文件存在且不是pdf文件，则转换为pdf文件 */
				if (docFile.exists()) {
					if (!txtName.equalsIgnoreCase("pdf")) {
						/* 生成一个随机文件名的pdf文件，转换后删除 */
						String pdfFilePath = pdfPath + random + ".pdf";
						pdfFile = OfficeToPdf.office2pdf(docFile, pdfFilePath, port);
						synchronized (redisOpenOfficePortLock) {
							// 释放oppenOffice端口号
							freeOccupyPort(port);
						}
						// oppenoffice请求超时则返回oppenoffic启动异常
						if (pdfFile == null) {
							System.out.println("****swf转换器异常，openoffice服务未启动！****");
							logger.info("****swf转换器异常，openoffice服务未启动！****");
							returnMsg.setRtnCode(EXCEPTION);
							returnMsg.setMessage("swf转换器异常，openoffice服务未启动，请与管理员联系！");
							return;
						}
					} else {
						synchronized (redisOpenOfficePortLock) {
							// 释放oppenOffice端口号
							freeOccupyPort(port);
						}
						// 如果文件名存在有空格的就把文件拷贝复制一份临时文件用于转换（文件名有空格转换不了）
						if (fileName.indexOf(" ") != -1) {
							tempPdf = new File(pdfPath + random + ".pdf");
							System.out.println("文件名出现空格复制一份到临时文件里面------------------》临时文件路径:" + tempPdf.getPath());
							System.out.println("文件名出现空格复制一份到临时文件里面------------------》原始文件路径:" + docFile.getPath());
							copyFile(docFile, tempPdf, returnMsg);
							hasWhitespace = true;
						} else {
							tempPdf = docFile;
						}
						pdfFile = tempPdf;
					}
				} else {
					synchronized (redisOpenOfficePortLock) {
						// 释放oppenOffice端口号
						freeOccupyPort(port);
					}
					System.out.println("****swf转换器异常，需要转换的文档不存在，无法转换****");
					logger.info("****swf转换器异常，需要转换的文档不存在，无法转换****");
					returnMsg.setRtnCode(FAILURE);
					returnMsg.setMessage("swf转换器失败，需要转换的文档不存在，无法转换！");
					return;
				}
			} catch (Exception e) {
				synchronized (redisOpenOfficePortLock) {
					// 释放oppenOffice端口号
					freeOccupyPort(port);
				}
				e.printStackTrace();
			}
			if (pdfFile.exists()) {
				//pdf转换成swf
				pdfToSwf(pdfFile, conventSwfFile, swfFile, tempPdf, txtName, pathMap, environment, hasWhitespace, returnMsg);
			} else {
				System.out.println("****pdf不存在,无法转换****" + "未知异常，请与管理员联系！");
				logger.info("****pdf不存在,无法转换****");
				returnMsg.setRtnCode(FAILURE);
				returnMsg.setMessage("pdf不存在,无法转换！");
				return;
			}

		} catch (Exception e) {
			System.out.println("转换swf 出现的 ****未知异常****");
			returnMsg.setRtnCode(FAILURE);
			returnMsg.setMessage("未知异常！");
			e.printStackTrace();
			return;
		} finally {
			if (!txtName.equalsIgnoreCase("pdf") && pdfFile != null && pdfFile.exists())
				pdfFile.delete();

			if (hasWhitespace && conventSwfFile != null && conventSwfFile.exists()) {
				conventSwfFile.delete();
			}
		}

	}

	// pdf转换成swf
	private static void pdfToSwf(File pdfFile, File conventSwfFile, File swfFile, File tempPdf, String txtName,
			Map<String, String> pathMap, int environment, Boolean hasWhitespace, ReturnMsgDto returnMsg) {
		System.out.println("开始转换pdf 文件-------------------------》:" + pdfFile.getPath());
		System.out.println("临时转换swf 文件  -------------------------》:" + conventSwfFile.getPath());
		BufferedReader br = null;
		Runtime r = Runtime.getRuntime();
		try {
			Process p = null;
			if (environment == 1) {
				p = r.exec(pdf2swfPath + " -z -b -s poly2bitmap " + pdfFile.getPath() + " -o "
						+ conventSwfFile.getPath() + " -T 9");
			} else {
				p = r.exec(pdf2swfPath + " " + pdfFile.getPath() + " -o " + conventSwfFile.getPath()
						+ " -T 9 -s poly2bitmap ");
			}
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println("执行结果：" + line);
			}
			if (hasWhitespace) {
				System.out.println("最后转换swf 文件  -------------------------》:" + swfFile.getPath());
				copyFile(conventSwfFile, swfFile, returnMsg);
				if (tempPdf != null) {
					tempPdf.delete();
				}
				conventSwfFile.delete();
			}
			if (swfFile.exists()) {
				System.err.println("****swf转换成功，文件输出：" + swfFile.getPath() + "****");
				String thumbnailPath = cutSwfToThumbnail(swfFile.getAbsolutePath());
				if (thumbnailPath != null) {
					pathMap.put("thumbnailPath", thumbnailPath.substring(rootPath.length()));
				}
				returnMsg.setRtnCode(SUCCESS);
				returnMsg.setMessage("swf转换成功！");
				pathMap.put("swfPath", swfFile.getAbsolutePath());
				returnMsg.setResultObject(pathMap);
			}
			if (!txtName.equalsIgnoreCase("pdf"))
				pdfFile.delete();
		} catch (IOException e) {
			System.out.println("最后转换swf 文件  -------------------------》:" + "未知异常，请与管理员联系！");
			returnMsg.setRtnCode(FAILURE);
			returnMsg.setMessage("未知异常，请与管理员联系！");
			e.printStackTrace();
		}
	}

	public static void copyFile(File sourceFile, File targetFile, ReturnMsgDto returnMsg) {
		FileInputStream input = null;
		BufferedInputStream inBuff = null;

		FileOutputStream output = null;
		BufferedOutputStream outBuff = null;

		try {
			input = new FileInputStream(sourceFile);
			inBuff = new BufferedInputStream(input);

			output = new FileOutputStream(targetFile);
			outBuff = new BufferedOutputStream(output);

			byte[] b = new byte[5120];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}

			outBuff.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			returnMsg.setRtnCode(FAILURE);
			returnMsg.setMessage("未知异常，请与管理员联系！");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			returnMsg.setRtnCode(FAILURE);
			returnMsg.setMessage("未知异常，请与管理员联系！");
			e.printStackTrace();
		} finally {
			try {
				if (inBuff != null) {
					inBuff.close();
					inBuff = null;
				}

				if (outBuff != null) {
					outBuff.close();
					outBuff = null;
				}

				if (output != null) {
					output.close();
					output = null;
				}

				if (input != null) {
					input.close();
					input = null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将txt文件转换为odt文件，解决中文乱码问题
	 * 
	 * @param filePath
	 * @param fileName
	 * @param txtName
	 * @return
	 */
	private static File txtConvent(String filePath, String fileName, String txtName) {
		File docFile = new File(filePath);

		if ("txt".equalsIgnoreCase(txtName)) {
			// File file = new File(filePath);
			File tempFile = null;

			// 获取txt文件的编码
			String fileCharset = Tools.getCharset(docFile);
			System.out.println("==========fileCharset:" + fileCharset + "==========");

			StringBuffer buffer = new StringBuffer();
			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			OutputStreamWriter osw = null;
			FileOutputStream fos = null;

			if (!"UTF-8".equalsIgnoreCase(fileCharset)) {
				try {
					String tempFileName = fileName + "Temp.txt";

					fis = new FileInputStream(docFile);
					isr = new InputStreamReader(fis, fileCharset);
					// isr = new InputStreamReader(fis);
					br = new BufferedReader(isr);
					String line = null;
					while ((line = br.readLine()) != null) {
						buffer.append(line);
						buffer.append("\r\n");
					}

					tempFile = new File(tempFileName);

					fos = new FileOutputStream(tempFile, true);
					osw = new OutputStreamWriter(fos, "UTF-8");
					osw.write(buffer.toString());
					osw.flush();

					isr.close();
					isr = null;
					fis.close();
					fis = null;
					fos.close();
					fos = null;
					osw.close();
					osw = null;

					// filePath = fileName + ".odt";
					// docFile = new File(filePath);
					// tempFile.renameTo(docFile);
					docFile = tempFile;
					System.out.println("==========docFile:" + tempFileName + "==========");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						if (br != null)
							br.close();

						if (isr != null)
							isr.close();

						if (fis != null)
							fis.close();

						if (fos != null)
							fos.close();

						if (osw != null)
							osw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}
		return docFile;
	}

	public static String convertThumbnailRuning(String filePath, Integer port) {
		System.out.println("--------------iniFile----------------");
		String txtName = null;
		File pdfFile = null;
		File tempPdf = null;
		File docFile = null;
		String openOfficeStartCommon = openOfficeHOME;
		String thumbnailPath = null;
		System.out.println("-----------------iniFile 释放加锁----------------------");
		System.out.println("Oppefoffice端口号***********************" + port);
		if (port == null) {
			return null;
		}
		openOfficeStartCommon = openOfficeStartCommon + " " + port;
		try {
			String pdfPath = FileControl.getPath("temp_url", "pdfPath");
			System.out.println("进入转换--------------------------》临时pdf:" + pdfPath);
			System.out.println("进入转换--------------------------》临时swf:" + pdfPath);
			try {
				UUID random = UUID.randomUUID(); // 随机文件名
				txtName = filePath.substring(filePath.lastIndexOf(".") + 1);
				String fileName = filePath.substring(0, filePath.lastIndexOf("."));
				// 解决txt文件中文乱码问题
				docFile = txtConvent(filePath, fileName, txtName);
				/* 若文件存在且不是pdf文件，则转换为pdf文件 */
				if (docFile.exists()) {
					if (!txtName.equalsIgnoreCase("pdf")) {
						/* 生成一个随机文件名的pdf文件，转换后删除 */
						String pdfFilePath = pdfPath + random + ".pdf";
						pdfFile = OfficeToPdf.office2pdf(docFile, pdfFilePath, port);
						// oppenoffice请求超时则返回oppenoffic启动异常
						if (pdfFile == null) {
							return null;
						}
					} else {
						// 如果文件名存在有空格的就把文件拷贝复制一份临时文件用于转换（文件名有空格转换不了）
						if (fileName.indexOf(" ") != -1) {
							tempPdf = new File(pdfPath + random + ".pdf");
							System.out.println("文件名出现空格复制一份到临时文件里面------------------》临时文件路径:" + tempPdf.getPath());
							System.out.println("文件名出现空格复制一份到临时文件里面------------------》原始文件路径:" + docFile.getPath());
							ReturnMsgDto returnMsg = new ReturnMsgDto();
							copyFile(docFile, tempPdf, returnMsg);
						} else {
							tempPdf = docFile;
						}
						pdfFile = tempPdf;
					}

				} else {
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (pdfFile.exists()) {
				System.out.println("开始转换pdf 文件-------------------------》:" + pdfFile.getPath());
				String tempName = filePath.substring(0, filePath.lastIndexOf("."));
				// 获取缩列图文件位置
				thumbnailPath = tempName + ".png" + "." + CommonFun.getOneUUID() + ".png";
				// 获取pdf的缩列图
				boolean flag = PdfConverterImg.changePdfToImg(pdfFile, thumbnailPath);
				if (!flag) {
					pdfFile.delete();
					return null;
				}
			} else {
				return null;
			}

		} catch (Exception e) {
			if (!txtName.equalsIgnoreCase("pdf") && pdfFile != null && pdfFile.exists())
				pdfFile.delete();
			return null;
		} finally {
			if (!txtName.equalsIgnoreCase("pdf") && pdfFile != null && pdfFile.exists())
				pdfFile.delete();
		}
		return thumbnailPath;
	}

	// 文件成转换缩列图
	public static String convertThumbnail(String filePath) {
		System.out.println("-----------------iniFile 加锁----------------------");
		// 一直循环获取，如果获取失败，则等待一会（2秒）再获取，如此循环等待不能超过30分钟（900次），如果次数超过900次，则认为失败
		int count = 0;
		Integer port = null;
		String thumbnailPath = null;
		try {
			while ((null == port) && (count < 900)) {
				synchronized (redisOpenOfficePortLock) {
					port = getFreePort();
				}
				try {
					if (null == port) {
						System.out.println("端口号为空，线程睡眠 2000毫秒后，再重新获取可用端口号...");
						Thread.sleep(2000);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count++;
			}
			convertThumbnailRuning(filePath, port);
		} finally {
			// TODO: handle finally clause
			synchronized (redisOpenOfficePortLock) {
				// 释放oppenOffice端口号
				freeOccupyPort(port);
			}
		}
		return thumbnailPath;
	}

	// 将swf截取缩列图
	public static String cutSwfToThumbnail(String filePath) {
		if (filePath == null || filePath.equals("") || !new File(filePath).exists()) {
			return null;
		}
		filePath = filePath.replaceAll("\\\\", "/");
		File swfFile = new File(filePath);
		String fileName = filePath.substring(0, filePath.lastIndexOf("."));
		String SwfPath = FileControl.getPath("temp_url", "swfPath");
		String swfFileName = null;
		if (filePath.indexOf(".swf") != -1) {
			swfFileName = filePath.substring(filePath.lastIndexOf("/"), filePath.indexOf(".swf"));
		}else if (filePath.indexOf(".Swf") != -1) {
			swfFileName = filePath.substring(filePath.lastIndexOf("/"), filePath.indexOf(".Swf"));
		}else if (filePath.indexOf(".SWf") != -1) {
			swfFileName = filePath.substring(filePath.lastIndexOf("/"), filePath.indexOf(".SWf"));
		}else if (filePath.indexOf(".SWF") != -1) {
			swfFileName = filePath.substring(filePath.lastIndexOf("/"), filePath.indexOf(".SWF"));
		}else {
			return null;
		}
		System.out.println("文件路径:--------------》"+filePath);
		String thumbnail = filePath.substring(0, filePath.lastIndexOf("/"))
				+ swfFileName;
		File tempSwf = null;
		File tempPng = null;
		String tempPngPath = null;
		if (fileName.indexOf(" ") != -1) {
			String tempSwfPath = SwfPath + UUID.randomUUID() + ".swf";
			tempPngPath = SwfPath + UUID.randomUUID() + ".png";
			tempPng = new File(tempPngPath);
			tempSwf = new File(tempSwfPath);
			System.out.println("文件名出现空格复制一份到临时文件里面------------------》临时文件路径:" + tempSwf.getPath());
			System.out.println("文件名出现空格复制一份到临时文件里面------------------》原始文件路径:" + swfFile.getPath());
			ReturnMsgDto returnMsg = new ReturnMsgDto();
			copyFile(swfFile, tempSwf, returnMsg);
			filePath = tempSwfPath;
		}
		// 获取缩列图文件位置
		String thumbnailPath = thumbnail + ".png" + "." + CommonFun.getOneUUID() + ".png";
		Runtime r = Runtime.getRuntime();
		Process p = null;
		BufferedReader br = null;
		try {
			if (tempPngPath != null) {
				File thumbnailFile = new File(thumbnailPath);
				p = r.exec(swfrenderPath + " " + filePath + " -o " + tempPngPath);
				ReturnMsgDto returnMsg = new ReturnMsgDto();
				System.out.println("临时png文件地址:--------------->" + tempPng);
				/*int i = 0;
				while (tempPng !=null && !tempPng.exists() &&  i<5) {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}*/
				br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line = null;
				while ((line = br.readLine()) != null) {
					System.out.println("执行结果：" + line);
				}
				if(tempPng.exists()){
					System.out.println("转换成功临时存放的png文件地址:--------------->" + tempPng);
					copyFile(tempPng, thumbnailFile, returnMsg);
				}else{
					thumbnailPath = null;
					System.out.println("转换失败临时存放的png文件地址不存在:--------------->" + tempPng);
				}
			} else {
				p = r.exec(swfrenderPath + " " + filePath + " -o " + thumbnailPath);
				br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line = null;
				while ((line = br.readLine()) != null) {
					System.out.println("执行结果：" + line);
				}
				System.out.println("转换成功存放的png文件地址:--------------->" + thumbnailPath);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (tempSwf != null && tempSwf.exists()) {
				tempSwf.delete();
			}
			if (tempPngPath != null && tempPng.exists()) {
				tempPng.delete();
			}
		}
		System.out.println("最后文件转换后路径:--------------->" + thumbnailPath);
		return thumbnailPath;
	}

	// 从redis中获取启动oppenoffic空闲的端口号
	private static Integer getFreePort() {
		String jsonPort = null;
		List<OppenOfficePortDto> list = new ArrayList<OppenOfficePortDto>();
		System.out.println("portKey---------------------------->" + portKey);
		// key值为空从初始化端口号
		if (portKey == null) {
			OppenOfficePortDto officePortDto = null;
			String[] ports = portValue.split(",");
			portKey = CommonFun.getOneUUID();
			for (String port : ports) {
				officePortDto = new OppenOfficePortDto();
				officePortDto.setPort(Integer.valueOf(port));
				officePortDto.setState(0);
				list.add(officePortDto);
			}
			list.get(0).setState(1);
			jsonPort = JSONObject.toJSONString(list);
			JredisClient.setValueToCache(ConstantRedis.OppenOffice_Port + ":" + portKey, jsonPort);
			return list.get(0).getPort();
		}
		list = JSONObject.parseArray(JredisClient.getValueFromCache(ConstantRedis.OppenOffice_Port + ":" + portKey),
				OppenOfficePortDto.class);
		for (OppenOfficePortDto officePort : list) {
			if (officePort.getState() == 0) {
				officePort.setState(1);
				jsonPort = JSONObject.toJSONString(list);
				JredisClient.setValueToCache(ConstantRedis.OppenOffice_Port + ":" + portKey, jsonPort);
				return officePort.getPort();
			}
		}
		return null;
	}

	// 释放转换完的端口号
	private static void freeOccupyPort(Integer port) {
		String jsonPort = null;
		List<OppenOfficePortDto> list = JSONObject.parseArray(
				JredisClient.getValueFromCache(ConstantRedis.OppenOffice_Port + ":" + portKey),
				OppenOfficePortDto.class);
		for (OppenOfficePortDto officePort : list) {
			if (officePort.getPort().equals(port)) {
				officePort.setState(0);
				jsonPort = JSONObject.toJSONString(list);
				JredisClient.setValueToCache(ConstantRedis.OppenOffice_Port + ":" + portKey, jsonPort);
			}
		}

	}

}
