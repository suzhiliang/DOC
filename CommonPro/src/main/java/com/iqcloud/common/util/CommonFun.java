package com.iqcloud.common.util;

import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iqcloud.common.ConfigRead.IQCloudConfig;
import com.iqcloud.common.dto.AccessTokenEntity;
import com.iqcloud.common.dto.IQCloudInfoDto;
import com.iqcloud.common.dto.LoginDto;
import com.iqcloud.common.dto.LoginUserInfoDto;
import com.iqcloud.common.dto.MsgDto;
import com.iqcloud.common.dto.ReturnMsgDto;
import com.iqcloud.common.dto.UserBaseInfoDto;
import com.iqcloud.redis.client.JredisClient;

public class CommonFun {

	/*
	 * 获取用户id
	 */
	public static String getUserId(IQCloudInfoDto iqCloudInfoDto) {
		try {
			UserBaseInfoDto userBaseInfoDto = JSON.parseObject(iqCloudInfoDto.getMsgHead(), UserBaseInfoDto.class);
			if ((userBaseInfoDto.getUserId()).equals("-1")) {
				return null;
			}
			return userBaseInfoDto.getUserId();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	/*
	 * 获取用户实体
	 */
	public UserBaseInfoDto getUserInfoByIQCloudInfo(IQCloudInfoDto iqCloudInfoDto) {
		try {
			UserBaseInfoDto userBaseInfoDto = JSON.parseObject(iqCloudInfoDto.getMsgHead(), UserBaseInfoDto.class);
			return userBaseInfoDto;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	/*
	 * 获取一个新的UUID
	 */
	public static String getOneUUID() {
		UUID uuid = UUID.randomUUID();
		String sUuid = uuid.toString();
		String sNewUuid = sUuid.replaceAll("-", "");
		sNewUuid = sNewUuid.toUpperCase();
		return sNewUuid;
	}

	/*
	 * 载入结果返回值
	 */
	public static IQCloudInfoDto getIQCloudInfoDto(int type, String msg) {
		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
		MsgDto msgDto = new MsgDto();
		msgDto.setRtnCode(type);
		if (type == -1) {
			msgDto.setMsg("系统出问题，调用失败!");
		} else {
			msgDto.setMsg(msg);
		}

		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(msgDto)));
		return iqCloudInfoDto;
	}

	/**
	 * 获取分类根目录ID
	 * 
	 * @return
	 */
	public static String getCategoryRootId() {

		String rootId = FileControl.getPath("main", "categoryResourceRootId");

		return rootId;
	}

	/*
	 * 获取服务名
	 */
	public static String getServerName(HttpServletRequest request) {
		String serverName = request.getHeader("serverName");
		return serverName;
	}

	/*
	 * 获取服务端口号
	 */
	public static int getServerPort(HttpServletRequest request) {
		String portStr = request.getHeader("serverPort");
		int serverPort = 80;
		try {
			serverPort = Integer.valueOf(portStr);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return serverPort;
	}

	/*
	 * 获取http host Url
	 */
	public static String getHttpUrl(HttpServletRequest request) {
		String httpUrl = "";
		String serverName = getServerName(request);
		int serverPort = getServerPort(request);

		if (80 == serverPort) {
			httpUrl = "http://" + serverName;
		} else {
			httpUrl = "http://" + serverName + ":" + serverPort;
		}

		// if ((null == httpUrl) || ("".equals(httpUrl.trim()))){
		// httpUrl = FileControl.getPath("main", "hostHttpUrl");
		// }

		System.out.println("httpUrl==> " + httpUrl);

		return httpUrl;
	}

	/*
	 * 获取本地局域网 http host Url
	 */
	public static String getLocalAreaHttpUrl(HttpServletRequest request) {
		String httpUrl = "";
		String serverName = request.getHeader("localAreaHost");
		int serverPort = getServerPort(request);

		if (80 == serverPort) {
			httpUrl = "http://" + serverName;
		} else {
			httpUrl = "http://" + serverName + ":" + serverPort;
		}

		System.out.println("localAreaHost==> " + httpUrl);

		return httpUrl;
	}

	/*
	 * 设置统计代码参数
	 */
	public static void setModelCnzzValue(Model model, HttpServletRequest request) {
		// 统计代码
		String cnzz = IQCloudConfig.getParamValueNotOS("cnzz", "javascript");
		model.addAttribute("cnzz", cnzz);
		String url = getLocalAreaHttpUrl(request) + request.getContextPath() + "/cnzz";

		System.out.println("cnzzUrl -> " + url);

		// String url = "http://" + "127.0.0.1" + ":" + request.getLocalPort() +
		// request.getContextPath() + "/cnzz";
		model.addAttribute("cnzzUrl", url);
	}

	/*
	 * 获取登类型
	 */
	public static Integer getLoginType(HttpServletRequest request) {
		UserBaseInfoDto userBaseInfoDto = SessionControl.getUserInfoByRedis(request);
		Integer loginType = -1;
		if (null != userBaseInfoDto) {
			loginType = userBaseInfoDto.getLoginType();
		}

		return loginType;
	}

	/*
	 * 往iqCloudInfoDto加host头
	 */
	public static void setIQCloudInfoDtoHostHead(IQCloudInfoDto iqCloudInfoDto, HttpServletRequest request) {
		Object headObj = iqCloudInfoDto.getJsonHead();
		JSONObject jsonHead = null;
		if (null == headObj) {
			jsonHead = new JSONObject();
		} else {
			jsonHead = JSON.parseObject(JSON.toJSONString(headObj));
		}

		String hostUrl = jsonHead.getString("hostUrl");
		if ((null == hostUrl) || ("".equals(hostUrl.trim()))) {// 地址不存在, 获取
			hostUrl = getHttpUrl(request);
			jsonHead.put("hostUrl", hostUrl);
			iqCloudInfoDto.setJsonHead(jsonHead);
		}
	}

	/*
	 * 从iqCloudInfoDto获取hostUrl
	 */
	public static String getHostUrlFromIQCloudInfoDto(IQCloudInfoDto iqCloudInfoDto) {
		Object headObj = iqCloudInfoDto.getJsonHead();
		JSONObject jsonHead = null;
		if (null != headObj) {
			jsonHead = JSON.parseObject(JSON.toJSONString(headObj));
			return jsonHead.getString("hostUrl");
		}

		return null;
	}

	/**
	 * 判断是否是展会北京十一学校
	 * 
	 * @Title: getIsExhibitionBjsySch
	 * @Description:
	 * @param request
	 * @return
	 * @return: boolean
	 */
	public static boolean getIsExhibitionBjsySch(HttpServletRequest request) {
		boolean isBjsy = false;

		String userName = SessionControl.getUserInfoUserName(request, null);
		if (null != userName) {
			// 是否是北京十一学校账号名
			boolean isBjsyUserName = (userName.indexOf("bnds") >= 0);

			String hostUrl = com.iqcloud.common.util.CommonFun.getHttpUrl(request);
			boolean isIqcedu = ((hostUrl.indexOf("www.iqcedu.com") > 0) || (hostUrl.indexOf("zkbtest.iqcedu.com") > 0)
					|| (hostUrl.indexOf("zkb.iqcedu.com") > 0));

			isBjsy = isBjsyUserName && isIqcedu;
		}

		return isBjsy;
	}

	public static void initIQCloudInfoHead(IQCloudInfoDto iQCloudInfoDto, HttpServletRequest request) {

		UserBaseInfoDto userBaseInfo = null;

		userBaseInfo = SessionControl.getUserInfoByRedis(request);

		if (userBaseInfo == null) {
			String userId = null;
			String userName = null;
			iQCloudInfoDto.setMsgHead("{\"userId\":\"" + userId + "\",\"userName\":\"" + userName + "\"}");
		} else {

			JSONObject jsonHead = JSONObject.parseObject(JSON.toJSONString(userBaseInfo));
			iQCloudInfoDto.setJsonHead(jsonHead);

			iQCloudInfoDto.setMsgHead("{\"userId\":\"" + userBaseInfo.getUserId() + "\",\"userName\":\""
					+ userBaseInfo.getUserName() + "\"}");
		}
	}

	/**
	 * 用户登录验证
	 * 
	 * @Title: loginValidate
	 * @Description: TODO
	 * @param loginUserInfoDto
	 * @param request
	 * @param response
	 * @return
	 * @return: ReturnMsgDto
	 */
	public static JSONObject loginValidate(LoginUserInfoDto loginUserInfoDto, HttpServletRequest request,
			HttpServletResponse response) {
		RestTemplate restTemplate = new RestTemplate();
		String url = FileControl.getPath("main", "IQService");

		ReturnMsgDto returnMsg = new ReturnMsgDto();

		IQCloudInfoDto iQCloudInfoDto = new IQCloudInfoDto();
		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();

		String userName = loginUserInfoDto.getUserName();
		String password = loginUserInfoDto.getPassword();
		String verCode = loginUserInfoDto.getVerCode();

		// 用户输入控制
		if (userName == null) {
			returnMsg.setRtnCode(-1);
			returnMsg.setMessage("用户名不能为空");
			return JSON.parseObject(JSON.toJSONString(returnMsg));
		}

		if (password == null) {
			returnMsg.setRtnCode(-1);
			returnMsg.setMessage("密码不能为空");
			return JSON.parseObject(JSON.toJSONString(returnMsg));
		}

		if (verCode == null) {
			returnMsg.setRtnCode(-1);
			returnMsg.setMessage("验证码不能为空");
			return JSON.parseObject(JSON.toJSONString(returnMsg));
		}

		/*
		 * String verCodeImg = (String)session.getAttribute("logincode");
		 * session.removeAttribute("logincode");
		 */

		// 读取配置文件，看是否需要校验图形验证码（有时需要对网站进行压力测试时，就得把“校验图形验证码”功能关闭掉，因此写进配置文件方便修改）
		boolean checkVerificationCode = true;
		String sCheckVerificationCode = IQCloudConfig.getParamValueNotOS("main", "checkVerificationCode");
		if (null != sCheckVerificationCode) {
			try {
				checkVerificationCode = Boolean.valueOf(sCheckVerificationCode);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("图形验证码标志位没读到");
			}
		}

		if (checkVerificationCode) {// 需要校验图形验证码
			String verCodeImg = null;
			String verificationCodeId = null;
			Cookie[] cookies = request.getCookies();
			if ((cookies != null) && (cookies.length > 0)) {
				for (Cookie cookie : cookies) {
					if ("verificationCode".equals(cookie.getName())) {
						verificationCodeId = cookie.getValue();
						break;
					}
				}
			}

			String redisKey = "IQCloud:VerificationCode:" + verificationCodeId;
			verCodeImg = JredisClient.getValueFromFuzzyKey(redisKey);
			System.out.println("输入的验证码----------------------------------------------------》" + verCode);
			System.out.println("校验的验证码----------------------------------------------------》" + verCodeImg);
			if (!verCode.equalsIgnoreCase(verCodeImg)) {
				returnMsg.setRtnCode(-1);
				returnMsg.setMessage("验证码错误");
				return JSON.parseObject(JSON.toJSONString(returnMsg));
			}
		}

		LoginDto loginDto = new LoginDto();
		loginDto.setUserName(userName);
		loginDto.setUserPwd(password);

		iQCloudInfoDto.setJsonBody(loginDto);

		IQCloudInfoDto iqCloudInfoDtoRtn = restTemplate.postForObject(url + "/IQAuthController/login", iQCloudInfoDto,
				IQCloudInfoDto.class);

		MsgDto msgDto = JSONObject.parseObject(JSONObject.toJSONString(iqCloudInfoDtoRtn.getJsonBody()), MsgDto.class);

		Integer rtnCode = msgDto.getRtnCode();

		if (rtnCode == 1) {
			// 用户名或密码错误
			returnMsg.setRtnCode(-1);
			returnMsg.setMessage("用户名或密码错误");
			return JSON.parseObject(JSON.toJSONString(returnMsg));
		} else if (rtnCode == 2) {
			// 激活码过期、暂停、作废
			returnMsg.setRtnCode(-1);
			returnMsg.setMessage("激活码过期、暂停、作废");
			return JSON.parseObject(JSON.toJSONString(returnMsg));
		} else if (rtnCode == -1) {
			// 系统调用出错
			returnMsg.setRtnCode(-1);
			returnMsg.setMessage("系统调用出错");
			return JSON.parseObject(JSON.toJSONString(returnMsg));
		}

		// 若登录成功，获取特权码，放入凭证中
		accessTokenEntity = JSONObject.parseObject(JSONObject.toJSONString(msgDto.getMsgBody()),
				AccessTokenEntity.class);
		System.out.println("accessTokenEntity:" + accessTokenEntity);
		System.out.println("accessToken:" + accessTokenEntity.getAccessToken());

		// 通过特权吗获取userBasinfoDto
		UserBaseInfoDto userBaseInfoDto = SessionControl.getUserInfoByAccessToken(accessTokenEntity.getAccessToken());

		String theKey = userBaseInfoDto.getKey();
		String[] keys = theKey.split(":");
		String cookieValue = keys[2]; // cookie值

		// 将用户ID对应的随机码放入cookie中
		Cookie cookie = new Cookie("userInfo", cookieValue);
		cookie.setPath("/");
		response.addCookie(cookie);

		// 将登陆类型也写入cookie中
		Cookie cookie2 = null;
		String type = (null == userBaseInfoDto.getLoginType()) ? "0" : userBaseInfoDto.getLoginType().toString();
		cookie2 = new Cookie("loginType", type);

		cookie2.setPath("/");
		response.addCookie(cookie2);

		returnMsg.setRtnCode(0);

		return JSON.parseObject(JSON.toJSONString(returnMsg));
	}
}
