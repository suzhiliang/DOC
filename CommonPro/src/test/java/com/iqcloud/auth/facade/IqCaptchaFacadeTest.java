//package com.iqcloud.auth.facade;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.iqcloud.auth.dto.IQCloudInfoDto;
//import com.iqcloud.auth.dto.module.IdsAndiDto;
//import com.iqcloud.auth.entity.IqCaptcha;
//
//@ContextConfiguration(locations = "classpath:root-context.xml")
//public class IqCaptchaFacadeTest extends AbstractJUnit4SpringContextTests {
//	@Autowired
//	private IIqCaptchaFacade iqCaptchaFacade;
//	
////	/*
////	 *  插入用户验证码信息
////	 */
////	@Test
////	public void insertSelective(){
////		IqCaptcha iqCaptcha = new IqCaptcha();
////		iqCaptcha.setId("591A6BF3DC6644E6A4AB87751407D526");
////		//iqCaptcha.setUserId("DAAD5ACD32CC4D1E9FC7DC35A139D571");
////		iqCaptcha.setUserName("33333333334");
////		//IdDto idDto = new IdDto();
////		//idDto.setId("DAAD5ACD32CC4D1E9FC7DC35A139D571");
////		//iqUserBinding.setId("111");
////		//iqUserBinding.setUserId("DAAD5ACD32CC4D1E9FC7DC35A139D571");
////		//iqUserBinding.setEmail("865534357");
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(iqCaptcha)));
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////
////		IQCloudInfoDto iqCloudInfoDto2 = iqCaptchaFacade.updateByPrimaryKeySelective(iqCloudInfoDto);
////		
////		//IQCloudInfoDto iqCloudInfoDto2 = iqCaptchaFacade.insertSelective(iqCloudInfoDto);
////
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////		
////	}
//	
//	/*
//     * 通过userName和purpose获取验证信息
//     */
//	@Test
//	public void selectCodeByUserNameAndPurpose(){
//		System.out.println("查询用户验证信息...");
//		
//		IdsAndiDto idsAndiDto = new IdsAndiDto();
//		idsAndiDto.setId1("33333333334");
//		idsAndiDto.setId2(1);
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(idsAndiDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iqCaptchaFacade.selectCodeByUserNameAndPurpose(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
//
//}
