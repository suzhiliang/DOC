//package com.iqcloud.auth.facade;
//
//import java.util.Date;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.iqcloud.auth.dto.IQCloudInfoDto;
//import com.iqcloud.auth.dto.module.AccessTokenUpdateDto;
//import com.iqcloud.auth.dto.module.AccessTokenValueDto;
//import com.iqcloud.auth.dto.module.IdDto;
//
//@ContextConfiguration(locations = "classpath:root-context.xml")
//public class IqAccessTokenFacadeTest extends AbstractJUnit4SpringContextTests{
//	 @Autowired
//	private IIqAccessTokenFacade iIqAccessTokenFacade;
//	
//	@Test
//	public void test(){
//		System.out.println("test...");
//	}
//	
//	/*
//	 * 创建特权码完全实体
//	 */
//	@Test
//	public void insertOneNewAccess(){
//		IdDto idDto = new IdDto();
//		idDto.setId("DAAD5ACD32CC4D1E9FC7DC35A139D571");
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(idDto)));
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqAccessTokenFacade.insertOneNewAccess(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//		
//	}
//}
//	
////	/*
////	 * 获取特权码完全实体
////	 */
////	@Test
////	public void selectAccessTokenDto(){
////		String accessToken = "1B63886211504F37B81C243DA343F730";
////		AccessTokenValueDto accessTokenValueDto = new AccessTokenValueDto();
////		accessTokenValueDto.setAccessToken(accessToken);
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenValueDto)));
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto2 = iIqAccessTokenFacade.selectAccessTokenDto(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////	}
////	
////	/*
////	 * 获取特权码实体
////	 */
////	@Test
////	public void selectAccessTokenEntity(){
////		AccessTokenValueDto accessTokenValueDto = new AccessTokenValueDto();
////		accessTokenValueDto.setAccessToken("1B63886211504F37B81C243DA343F730");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenValueDto)));
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto2 = iIqAccessTokenFacade.selectAccessTokenEntity(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////	}
////	
////	/*
////	 * 通过特权码获取用户id
////	 */
////	@Test
////	public void selectUserIdByAccessToken(){
////		AccessTokenValueDto accessTokenValueDto = new AccessTokenValueDto();
////		accessTokenValueDto.setAccessToken("1B63886211504F37B81C243DA343F730");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenValueDto)));
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto2 = iIqAccessTokenFacade.selectUserIdByAccessToken(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////	}
////	
////	/*
////	 * 更新特权码生命周期
////	 */
////	@Test
////	public void updateAccessTokenLifeCycle(){
////		AccessTokenUpdateDto accessTokenUpdateDto = new AccessTokenUpdateDto();
////		accessTokenUpdateDto.setAccessToken("1B63886211504F37B81C243DA343F730");
////		accessTokenUpdateDto.setLifeCycle((new Date()).getTime());
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenUpdateDto)));
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto2 = iIqAccessTokenFacade.updateAccessTokenLifeCycle(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////	}
////}
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
