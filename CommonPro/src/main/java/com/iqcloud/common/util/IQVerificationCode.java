package com.iqcloud.common.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iqcloud.redis.client.JredisClient;

public class IQVerificationCode {
	private Color color1 = null;
	private Color color2 = null;
	
	/*
	 * 获取颜色
	 */
	/*public void getRandColor() {
		  Random random = new Random();
		  
		  int r = random.nextInt(200);
		  int g = random.nextInt(200);
		  int b = random.nextInt(200);
		  
//		  System.out.println(r + ", " + g + ", " + b);
		  
//		  color1 = new Color(r, g, b);
		  color2 = new Color(255 - r, 255 - g, 255 - b);
	}*/
	
	/*
	 * 获取验证码
	 */
	public void getVerificationCode(HttpSession session, HttpServletRequest request, HttpServletResponse response, String sessionName){
		  // 禁止缓存
		  response.setHeader("Pragma", "No-cache");
		  response.setHeader("Cache-Control", "No-cache");
		  response.setDateHeader("Expires", 0);
		  // 指定生成的响应是图片
//		  response.setContentType("image/jpeg");
		  response.setContentType("image/png");
//		  int width = 90;
		  int width = 100;
		  int height = 45;
		  BufferedImage image = new BufferedImage(width, height,
		    BufferedImage.TYPE_INT_RGB);    	// 创建BufferedImage类的对象
		  Graphics g = image.getGraphics();     // 创建Graphics类的对象
		  Random random = new Random();      	// 实例化一个Random对象
//		  Font mFont = new Font("Serif", Font.BOLD, 24);  	// 通过Font构造字体
		  Font mFont = new Font("Serif", Font.BOLD, 28);  	// 通过Font构造字体
//		  getRandColor();
		  //color1 = new Color(221,221,221);
		  color1 = new Color(240,240,240);
		  g.setColor(color1);     			// 改变图形的当前颜色为随机生成的颜色
		  g.fillRect(0, 0, width , height);     			// 绘制一个填色矩形
		  
		  // 随机线条
		  Graphics2D myG2D = (Graphics2D) g;
		  myG2D.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		  myG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		  
		  color1 = new Color(200,200,200);
		  g.setColor(color1);     			// 改变图形的当前颜色为随机生成的颜色
		  g.drawRect(0, 0, width, height);
		  
		  for (int i = 0; i < 5; i++) {// 画随机线
			  int x1 = (int) Math.round(width * 0.10 * Math.random()); 
			  int y1 = (int) Math.round(height * Math.random());
			  
			  int x2 = (int) Math.round(width * 0.9 + (width * 0.10 * Math.random()));
			  int y2 = (int) Math.round((height * Math.random()));
			
			  Color color = new Color((int) Math.round(255 * Math.random()), (int) Math.round(255 * Math.random()), (int) Math.round(255 * Math.random()));
			  myG2D.setColor(color); 
			
			  myG2D.drawLine(x1, y1, x2, y2);
		  }
		  
//		  for (int i = 0; i < 5; i++) {// 随机点
//			  
//			  int x1 = (int) Math.round(width * 0.1 + (width * 0.8 * Math.random()));
//			  int y1 = (int) Math.round(height * 0.1 + (height * 0.8 * Math.random()));
//			
//			  Color color = new Color((int) Math.round(255 * Math.random()), (int) Math.round(255 * Math.random()), (int) Math.round(255 * Math.random()));
//			  myG2D.setColor(color); 
//			
//			  myG2D.fillArc(x1 - 3, y1 - 3, 6, 6, 0, 360);
//		  }
		  
		  

		  // 生成并输出随机的验证文字  
		  g.setFont(mFont);  
		  String sRand="";
//		  int itmp=0;
		  int iCount = 0;
		  String sn = "13456789ABCDEFGHJKMNPQRSTUVWXY";
		  for(int i = 0; i < 4; i++){
			  int index = random.nextInt(30);
			  if (index > 7){
				  iCount++;
			  }
			  sRand = sRand + sn.charAt(index);
			  
			  /*
			  if(random.nextInt(2)==1){  
				  itmp = random.nextInt(26) + 65; // 生成A~Z的字母
				  if(itmp==73 || itmp==76 || itmp==79 || itmp==90)
					  continue;
				  iCount++;
			  }else{
				  itmp = random.nextInt(10) + 48; // 生成0~9的数字
				  if(itmp==48 || itmp==50)
					  continue;
			  }
			  char ctmp=(char)itmp;
			  sRand += String.valueOf(ctmp) ;
			  */
		  }
		  
		  switch (random.nextInt(4)) {
	        case 0:
	        	color2 = Color.blue;
	            break;
	        case 1:
	        	color2 = Color.black;
	            break;
	        case 2:
	        	color2 = Color.red;
	            break;

	        case 3:
	        	color2 = Color.magenta;
	            break;
			}
		  
		  g.setColor(color2);
		  if (iCount >= 3){
			  g.drawString(sRand, 8, 30);
		  }else{
			  g.drawString(sRand, 13, 30);
		  }
		String  verificationCode = UUIDUtils.getUUID();
		// 将用户ID对应的随机码放入cookie中
		Cookie cookie = new Cookie("verificationCode", verificationCode);
		cookie.setPath("/");
		response.addCookie(cookie);
		String redisKey = "IQCloud:VerificationCode:" + verificationCode;
		JredisClient.setValueToCache(redisKey, sRand, 60);
		System.out.println("登录时验证码---------------------------------------》"+sRand);
		  // 将生成的验证码保存到Session中
		  //session.setAttribute(sessionName, sRand);
		  g.dispose();
		  try {
//			ImageIO.write(image, "JPEG", response.getOutputStream());
			  ImageIO.write(image, "PNG", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
