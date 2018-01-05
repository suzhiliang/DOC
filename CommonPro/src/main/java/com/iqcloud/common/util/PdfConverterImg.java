package com.iqcloud.common.util;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.imageio.ImageIO;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

/**
 * 
 * 
 * Title: jobplus <br>
 * Description: <br>
 * Copyright: suzhoupingjia Copyright (C) 2016 <br>
 * 
 * @author <a href="mailto:anan.wang@jobplus.com.cn">WangFei(Anan.wang)</a><br>
 * @e-mail: anan.wang@jobplus.com.cn <br>
 * @version 1.0 <br>
 * @creatdate Sep 2, 2016 9:25:47 AM <br>
 *
 */
public class PdfConverterImg {

	public static boolean changePdfToImg(File pdfFile, String imgPath) {

		try {

			File file = pdfFile;

			RandomAccessFile raf = new RandomAccessFile(file, "r");

			FileChannel channel = raf.getChannel();

			MappedByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
			PDFFile pdffile = new PDFFile(buf);

			PDFPage page = pdffile.getPage(1);

			Rectangle rect = new Rectangle(0, 0, ((int) page.getBBox().getWidth()), ((int) page
					.getBBox().getHeight()));

			Image img = page.getImage(rect.width, rect.height, rect,

			null,

			true,

			true

			);

			BufferedImage tag = new BufferedImage(rect.width, rect.height,
					BufferedImage.TYPE_INT_RGB);

			tag.getGraphics().drawImage(img, 0, 0, rect.width, rect.height, null);

			File imgOutFile = new File(imgPath);
			// 如果文件夹不存在则创建
			// 判断目标文件所在的目录是否存在
			if (!imgOutFile.getParentFile().exists()) {
				// 如果目标文件所在的目录不存在，则创建父目录
				imgOutFile.getParentFile().mkdirs();
			}
			FileOutputStream out = new FileOutputStream(imgOutFile); // 输出到文件流
			ImageIO.write(tag, "png", out);  

			out.close();

			channel.close();

			raf.close();

			unmap(buf);// 如果要在转图片之后删除pdf，就必须要这个关闭流和清空缓冲的方法

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static void unmap(final Object buffer) {


		AccessController.doPrivileged(new PrivilegedAction<Object>() {

			@SuppressWarnings("restriction")
			public Object run() {

				try {

					Method getCleanerMethod = buffer.getClass().getMethod("cleaner", new Class[0]);

					getCleanerMethod.setAccessible(true);

					sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod.invoke(buffer,
							new Object[0]);

					cleaner.clean();

				} catch (Exception e) {

					e.printStackTrace();

				}

				return null;

			}

		});

	}
}
