package com.iqcloud.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.iqcloud.common.ConfigRead.IQCloudConfig;
import com.iqcloud.common.consts.Constants;
import com.iqcloud.common.dto.FileLoadPathDto;
import com.iqcloud.common.dto.ReturnMsgDto;
import com.iqcloud.common.dto.UploadFileParaDto;
import com.iqcloud.common.dto.ViewPathDTO;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

public class FileControl {

	private static final Logger logger = LoggerFactory.getLogger(FileControl.class);

	private static final int SUCCESS = 0;
	private static final int FAILURE = -2;
	private static final int NOT_CONVENT_FORMAT = -3;

	private static UploadFileParaDto fileCopy(Map.Entry<String, MultipartFile> entity, String ctxPath,
			String targetFile) {
		UploadFileParaDto fileMsg = new UploadFileParaDto();
		String orfileName = null;
		String filepath = null;
		String targetFileName = null;
		String fileExt = null;
		String fileMD5 = null;
		String tempName = null;

		MultipartFile mf = entity.getValue();

		// 上传的文件名称
		orfileName = mf.getOriginalFilename();

		targetFileName = orfileName.substring(orfileName.lastIndexOf("/") + 1);
		fileExt = orfileName.substring(orfileName.lastIndexOf(".") + 1);
		tempName = targetFileName.substring(0, targetFileName.lastIndexOf("."));
		String fileUuid = UUID.randomUUID().toString().replace("-", "");

		targetFile = targetFile + tempName + "-" + fileUuid + "." + fileExt;
		targetFile = targetFile.replaceAll("[| |(|)|！|&|!|@|#|%|,|?|;|'|￥|……|：|~|$|^|<|>|\\||\\+|=]*", "");

		// 服务器保存文件路径
		filepath = ctxPath + targetFile;

		File uploadFile = new File(filepath);

		try {
			readFile(mf.getInputStream(), uploadFile);

			fileMD5 = FileMD5.getFileMD5String(filepath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		fileMsg.setOriginFile(orfileName);
		fileMsg.setFilePath(targetFile);
		fileMsg.setFileName(targetFileName);
		fileMsg.setFileExt(fileExt);
		fileMsg.setFileSize(Tools.converSizeFm(mf.getSize()));
		fileMsg.setFileMD5(fileMD5);

		return fileMsg;
	}

	// 复制文件
	public static void copyFile(String fromPath, String toPath) throws IOException {
		File fromFile = new File(fromPath);
		File toFile = new File(toPath);
		readFile(fromFile, toFile);
	}

	public static long readFile(final File infile, final File outfile) throws IOException {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			in = openInputStream(infile);
			out = openOutputStream(outfile);
			return IOUtils.copy(in, out);
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}

	// 复制文件
	public static long readFile(InputStream is, final File outfile) throws IOException {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			in = new BufferedInputStream(is);
			out = openOutputStream(outfile);
			return IOUtils.copy(in, out);
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}

	@SuppressWarnings("unused")
	private static void readToFile(final InputStream input, final OutputStream output, final long size)
			throws IOException {
		if (size < 0) {
			throw new IllegalArgumentException("Size must be equal or greater than zero: " + size);
		}

		byte[] data = new byte[4096 * 4096];
		long offset = 0;
		int readed = 0;

		while ((readed = input.read(data, 0, data.length)) != -1) {
			offset += readed;
			output.write(data, 0, readed);
			output.flush();
		}

		if (offset != size) {
			throw new IOException("Unexpected readed size. current: " + offset + ", excepted: " + size);
		}
	}

	// 删除文件
	public static void delFile(final String filepath) throws IOException {
		File file = new File(filepath);

		if (file.exists()) {
			if (file.canRead() == false) {
				throw new IOException("File '" + file + "' cannot be read");
			}
		} else {
			throw new FileNotFoundException("File '" + file + "' does not exist");
		}

		FileUtils.deleteQuietly(file);
	}

	/**
	 * 
	 * @param zipName
	 *            放入zip包的文件名称
	 * @param zipFile
	 *            放入zip包的文件
	 * @param zos
	 */
	public static void fileToZip(String zipName, ZipOutputStream zos, BufferedInputStream bis, byte[] bufs)
			throws IOException {
		ZipEntry zipEntry = null;

		// 创建ZIP实体，并添加进压缩包
		zipEntry = new ZipEntry(zipName);
		zos.putNextEntry(zipEntry);

		int read = 0;

		// 读取待压缩的文件并写进压缩包里
		while ((read = bis.read(bufs, 0, bufs.length)) != -1) {
			zos.write(bufs, 0, read);
		}
	}

	private static UploadFileParaDto fileCopySaveFileNameOnlyChar(Map.Entry<String, MultipartFile> entity,
			String ctxPath, String targetFile) {
		UploadFileParaDto fileMsg = new UploadFileParaDto();
		String orfileName = null;
		String filepath = null;
		String targetFileName = null;
		String fileExt = null;
		String fileMD5 = null;
		// String tempName = null;

		MultipartFile mf = entity.getValue();

		// 上传的文件名称
		orfileName = mf.getOriginalFilename();

		targetFileName = orfileName.substring(orfileName.lastIndexOf("/") + 1);
		fileExt = orfileName.substring(orfileName.lastIndexOf(".") + 1);
		// tempName = targetFileName.substring(0,
		// targetFileName.lastIndexOf("."));
		String fileUuid = UUID.randomUUID().toString().replace("-", "");

		targetFile = targetFile + fileUuid + "." + fileExt;
		targetFile = targetFile.replaceAll("[| |(|)|！|&|!|@|#|%|,|?|;|'|￥|……|：|~|$|^|<|>|\\||\\+|=]*", "");

		// 服务器保存文件路径
		filepath = ctxPath + targetFile;

		File uploadFile = new File(filepath);

		try {
			readFile(mf.getInputStream(), uploadFile);

			fileMD5 = FileMD5.getFileMD5String(filepath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		fileMsg.setOriginFile(orfileName);
		fileMsg.setFilePath(targetFile);
		fileMsg.setFileName(targetFileName);
		fileMsg.setFileExt(fileExt);
		fileMsg.setFileSize(Tools.converSizeFm(mf.getSize()));
		fileMsg.setFileMD5(fileMD5);

		return fileMsg;
	}

	public static List<UploadFileParaDto> uploadSaveFileNameOnlyChar(HttpServletRequest request, String ctxPath,
			String otherPath) {
		List<UploadFileParaDto> fileMsgs = new ArrayList<UploadFileParaDto>();
		UploadFileParaDto fileMsg = null;

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		String dirPath = ctxPath + otherPath;

		// 创建文件夹
		File file = new File(dirPath);
		if (!file.exists()) {
			file.mkdirs();
			file.setExecutable(true);
			file.setReadable(true);
			file.setWritable(true);

			if (!isWindowsOS()) {// linux
									// 系统下要设置权限，否则Web端创建出来的目录，IQClass端通过ftp无权限上传文件上来
				try {
					dirPath = dirPath.substring(0, dirPath.lastIndexOf("/"));
					Runtime.getRuntime().exec("chmod -R 777 " + dirPath);
					Runtime.getRuntime().exec("chmod -R 777 " + dirPath + "/*");
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			fileMsg = fileCopySaveFileNameOnlyChar(entity, ctxPath, otherPath);

			fileMsgs.add(fileMsg);
		}

		return fileMsgs;
	}

	/**
	 * 文件的格式是否在在线预览支持的格式里面
	 */
	private static void fileConventer(String rootPath, ReturnMsgDto returnMsg) {
		if (FileControl.isWindowsOS())
			FileConventer.FileView(rootPath, 1, returnMsg);
		else
			FileConventer.FileView(rootPath, 2, returnMsg);

		return;
	}

	/**
	 * 将文件转换为swf文件
	 * 
	 * @param goodsRDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ReturnMsgDto fileConvent(String filePath, String fileName) throws Exception {
		ReturnMsgDto returnMsg = new ReturnMsgDto();

		String txtName = null;
		String targetFile = null;

		String logMsg = null;
		int logType = -2;

		File rootFile = new File(filePath);

		if (!rootFile.exists()) {
			logger.info("****" + fileName + "文件不存在****");
			logType = FAILURE;
			logMsg = fileName + "文件不存在！";

			returnMsg.setRtnCode(logType);
			returnMsg.setMessage(logMsg);

			return returnMsg;
		}

		txtName = filePath.substring(filePath.lastIndexOf(".") + 1);

		if ("swf".equalsIgnoreCase(txtName)) {
			logger.info("****" + fileName + "文件已经是swf格式，无需转换****");
			logType = SUCCESS;
			logMsg = fileName + "文件已经是swf格式，无需转换！";

			returnMsg.setRtnCode(logType);
			returnMsg.setMessage(logMsg);
		} else if (!IsEqual(txtName)) {
			logger.info("****" + fileName + "文件的格式非在线预览支持的格式****");
			logType = NOT_CONVENT_FORMAT;
			logMsg = fileName + "文件的格式非在线预览支持的格式！";

			returnMsg.setRtnCode(logType);
			returnMsg.setMessage(logMsg);
		} else {
			fileConventer(filePath, returnMsg);

			if (returnMsg.getRtnCode() == SUCCESS && null != returnMsg.getResultObject()) {
				Map<String, String> map = (Map<String, String>) returnMsg.getResultObject();
				targetFile = map.get("swfPath");
				targetFile = targetFile.replaceAll("\\\\", "/");

				targetFile = targetFile.replaceAll("%", "%25");
				targetFile = targetFile.replaceAll("#", "%23");
				map.put("swfPath", targetFile);
				returnMsg.setResultObject(map);
			}

		}

		return returnMsg;
	}

	public static List<UploadFileParaDto> upload(HttpServletRequest request, String ctxPath, String otherPath) {
		List<UploadFileParaDto> fileMsgs = new ArrayList<UploadFileParaDto>();
		UploadFileParaDto fileMsg = null;

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		String dirPath = ctxPath + otherPath;

		// 创建文件夹
		File file = new File(dirPath);
		if (!file.exists()) {
			file.mkdirs();
			file.setExecutable(true);
			file.setReadable(true);
			file.setWritable(true);

			if (!isWindowsOS()) {// linux
									// 系统下要设置权限，否则Web端创建出来的目录，IQClass端通过ftp无权限上传文件上来
				try {
					dirPath = dirPath.substring(0, dirPath.lastIndexOf("/"));
					Runtime.getRuntime().exec("chmod -R 777 " + dirPath);
					Runtime.getRuntime().exec("chmod -R 777 " + dirPath + "/*");
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}

		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			fileMsg = fileCopy(entity, ctxPath, otherPath);

			fileMsgs.add(fileMsg);
		}

		return fileMsgs;
	}

	/**
	 * 根据文件总表返回的信息获取文件上传信息
	 * 
	 * @param request
	 * @return
	 */
	public static UploadFileParaDto getUploadFileInfoByFileTotle(JSONObject jsonObject, String rootPath,
			String fileName) {
		UploadFileParaDto uploadFileParaDto = new UploadFileParaDto();
		JSONObject fileJson = JSONObject.parseObject(JSONObject.toJSONString(jsonObject.get("resultObject")));
		String filePath = fileJson.getString("filePath");
		if ((filePath == null) || (!new File(rootPath + filePath).exists())) {
			return null;
		}
		uploadFileParaDto.setId(fileJson.getString("id"));
		uploadFileParaDto.setFilePath(fileJson.getString("filePath"));
		uploadFileParaDto.setFileMD5(fileJson.getString("fileMd5"));
		uploadFileParaDto.setFileSize(Tools.converSizeFm(fileJson.getLong("fileSize")));
		uploadFileParaDto.setMappingPath(fileJson.getString("videoMappingPath"));
		uploadFileParaDto.setTargetFile(fileJson.getString("targetFile"));
		uploadFileParaDto.setThumbnail(fileJson.getString("viewImgPath"));
		uploadFileParaDto.setFileExt(
				fileJson.getString("filePath").substring(fileJson.getString("filePath").lastIndexOf(".") + 1));

		if (fileName != null && !fileName.trim().equals("")) {
			uploadFileParaDto.setFileName(fileName);
		} else if (fileJson.getString("filePath").lastIndexOf("-") != -1) {
			uploadFileParaDto.setFileName(
					fileJson.getString("filePath").substring(fileJson.getString("filePath").lastIndexOf("/") + 1,
							fileJson.getString("filePath").lastIndexOf("-")));
		} else {
			uploadFileParaDto.setFileName(
					fileJson.getString("filePath").substring(fileJson.getString("filePath").lastIndexOf("/") + 1,
							fileJson.getString("filePath").lastIndexOf(".")));
		}

		return uploadFileParaDto;
	}

	// 网页上直接下载文件
	public static ResponseEntity<byte[]> downLoadFile(String fileName, String path) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		String fileName1 = URLEncoder.encode(fileName, "UTF-8");
		headers.setContentDispositionFormData("attachment", fileName1);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(path)), headers, HttpStatus.OK);
	}

	// 下载文件到指定的目录
	public static void downLoad(HttpServletRequest request, HttpServletResponse response, String fileName,
			String fromPath) throws IOException {

		fileName = FileControl.encodeFileName(request, fileName);

		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader("Content-disposition", "attachment;" + fileName);

		File infile = new File(fromPath);
		response.setContentLength((int) infile.length());
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			in = openInputStream(infile);
			out = new BufferedOutputStream(response.getOutputStream());
			// readToFile(in, out, infile.length());
			IOUtils.copy(in, out);
		} catch (IOException e) {

			final String exceptionName = e.getClass().getName();

			if (exceptionName.equals("ClientAbortException") || e.getMessage().equals("ClientAbortException")) {
				logger.warn(
						"[ClientAbortException],maybe user use Thunder soft or abort client soft download,exceptionName:[{}],exception message:[{}] ,request User-Agent:[{}]",
						exceptionName, e.getMessage());
			} else {
				logger.error("[download exception],exception name: " + exceptionName, e);
				throw e;
			}
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);

		}
	}

	private static BufferedInputStream openInputStream(final File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file + "' exists but is a directory");
			}
			if (file.canRead() == false) {
				throw new IOException("File '" + file + "' cannot be read");
			}
		} else {
			throw new FileNotFoundException("File '" + file + "' does not exist");
		}

		return new BufferedInputStream(new FileInputStream(file));
	}

	private static BufferedOutputStream openOutputStream(final File file) throws IOException {
		if (file.exists()) {
			if (file.canRead() == false) {
				throw new IOException("File '" + file + "' cannot be read");
			}
		} else {
			file.createNewFile();
		}

		return new BufferedOutputStream(new FileOutputStream(file));
	}

	/*
	 * 读取配置文件
	 */
	public static ResourceBundle getConfig(String configName) {
		ResourceBundle rb = ResourceBundle.getBundle(configName);
		return rb;
	}

	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}

	/**
	 * 获取配置文件中的路径名称
	 * 
	 * @param name
	 * @return
	 */
	public static String getPath(String section, String key) {
		String path = IQCloudConfig.getParamValue(section, key);
		return path;
	}

	/**
	 * 文件的格式是否在在线预览支持的格式里面
	 */
	private static boolean IsEqual(String txtName) {
		for (String format : Constants.FORMAT_SUPPORT) {
			if (txtName.equalsIgnoreCase(format))
				return true;
		}

		return false;
	}

	/**
	 * 不同浏览器，不同的文件名
	 * 
	 * @param request
	 * @param fileName
	 * @return
	 */
	public static String encodeFileName(HttpServletRequest request, String fileName) {
		String userAgent = request.getHeader("User-Agent");
		String rtn = "";
		try {
			String new_filename = URLEncoder.encode(fileName, "UTF8");
			// 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
			rtn = "filename=\"" + new_filename + "\"";
			if (userAgent != null) {
				userAgent = userAgent.toLowerCase();
				// IE浏览器，只能采用URLEncoder编码
				if (userAgent.indexOf("msie") != -1) {
					rtn = "filename=\"" + new_filename + "\"";
				}
				// Opera浏览器只能采用filename*
				else if (userAgent.indexOf("opera") != -1) {
					rtn = "filename*=UTF-8''" + new_filename;
				}
				// Safari浏览器，只能采用ISO编码的中文输出
				else if (userAgent.indexOf("safari") != -1) {
					rtn = "filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1") + "\"";
				}
				// Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
				else if (userAgent.indexOf("applewebkit") != -1) {
					/*
					 * new_filename = MimeUtility .encodeText(fileName, "UTF8",
					 * "B");
					 */
					rtn = "filename=\"" + new_filename + "\"";
				}
				// FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
				else if (userAgent.indexOf("mozilla") != -1) {
					rtn = "filename*=UTF-8''" + new_filename;
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return rtn;
	}

	/**
	 * 转换视频文件
	 * 
	 * @param request
	 * @return
	 */
	public static ReturnMsgDto uploadVideoCommon(String filePath, FileLoadPathDto fileLoadPathDto) throws Exception {

		ReturnMsgDto returnMsgDto = new ReturnMsgDto();

		String viewMappingPath = null;
		String path = null; // 绝对路径

		// ffmpeg转换工具路径
		String ffmpegPath = fileLoadPathDto.getFfmpegPath();
		// qtFaststart转换工具路径
		String qtFaststartPath = fileLoadPathDto.getQtFaststart();

		// 存放根路径：D:/glusterfsConsume/
		String rootPath = fileLoadPathDto.getRootPath();

		// 云资源文件访问地址
		// String rootAddress = fileLoadPathDto.getRootAddress();

		path = rootPath + filePath;

		// 转换视频格式
		String tempName = filePath.substring(0, filePath.lastIndexOf("."));
		String fileExt = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();

		String thumbnailPath = rootPath + tempName + "_png.png";
		// 预览图url全路径
		String viewImageMappingPath = tempName + "_png.png";
		// String viewImageMappingPath = rootAddress + tempName +"_png.png";

		FFMpegUtil ffMpegUtil = new FFMpegUtil(ffmpegPath, qtFaststartPath, path);

		// 除了MP4格式编码h264的文件，其他文件都需要转换
		boolean ifTransfer = true;
		// if ("mp4".equalsIgnoreCase(fileExt)) {
		// String code = ffMpegUtil.getVCodeC();
		// if(code.startsWith("h264")) {
		// viewMappingPath = rootAddress + filePath;
		// viewMappingPath = filePath;
		// ifTransfer = false;
		// }
		//
		// }

		if ("mp3".equalsIgnoreCase(fileExt)) {
			viewMappingPath = filePath;
			ifTransfer = false;

		}
		if (ifTransfer) {
			String newFileName = tempName + "_mp4.mp4";
			String outputPath = rootPath + newFileName;

			// 视频文件预览路径
			// viewMappingPath = rootAddress + newFileName;
			// 视频转换
			viewMappingPath = newFileName;
			ffMpegUtil.videoTransfer(outputPath);
			ffMpegUtil.makeScreenCut(thumbnailPath, "160*120");
		}

		// 若截图不存在，则预览图使用类型图片
		File thumbnailFile = new File(thumbnailPath);
		if (!thumbnailFile.exists()) {

			viewImageMappingPath = "IQCloudCommon/commonVideoImg.png";
		}

		ViewPathDTO viewPathDTO = new ViewPathDTO();

		viewImageMappingPath = viewImageMappingPath.replaceAll("%", "%25");
		viewImageMappingPath = viewImageMappingPath.replaceAll("#", "%23");

		viewMappingPath = viewMappingPath.replaceAll("%", "%25");
		viewMappingPath = viewMappingPath.replaceAll("#", "%23");

		viewPathDTO.setViewImageMappingPath(viewImageMappingPath);
		viewPathDTO.setViewMappingPath(viewMappingPath);

		returnMsgDto.setRtnCode(0);
		returnMsgDto.setResultObject(viewPathDTO);

		return returnMsgDto;
	}

	/**
	 * @Title: getFileEncode
	 * @Description: 利用第三方开源包cpdetector获取文件编码格式
	 * @param path
	 * @return
	 * @return: String
	 */
	public static String getFileEncode(String path) {
		/*
		 * detector是探测器，它把探测任务交给具体的探测实现类的实例完成。
		 * cpDetector内置了一些常用的探测实现类，这些探测实现类的实例可以通过add方法 加进来，如ParsingDetector、
		 * JChardetFacade、ASCIIDetector、UnicodeDetector。
		 * detector按照“谁最先返回非空的探测结果，就以该结果为准”的原则返回探测到的
		 * 字符集编码。使用需要用到三个第三方JAR包：antlr.jar、chardet.jar和cpdetector.jar
		 * cpDetector是基于统计学原理的，不保证完全正确。
		 */
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		/*
		 * ParsingDetector可用于检查HTML、XML等文件或字符流的编码,构造方法中的参数用于
		 * 指示是否显示探测过程的详细信息，为false不显示。
		 */
		detector.add(new ParsingDetector(false));
		/*
		 * JChardetFacade封装了由Mozilla组织提供的JChardet，它可以完成大多数文件的编码
		 * 测定。所以，一般有了这个探测器就可满足大多数项目的要求，如果你还不放心，可以
		 * 再多加几个探测器，比如下面的ASCIIDetector、UnicodeDetector等。
		 */
		detector.add(JChardetFacade.getInstance());// 用到antlr.jar、chardet.jar
		// ASCIIDetector用于ASCII编码测定
		detector.add(ASCIIDetector.getInstance());
		// UnicodeDetector用于Unicode家族编码的测定
		detector.add(UnicodeDetector.getInstance());
		java.nio.charset.Charset charset = null;
		File f = new File(path);
		try {
			charset = detector.detectCodepage(f.toURI().toURL());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (charset != null)
			return charset.name();
		else
			return "UTF-8";
	}
}
