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
//import com.iqcloud.auth.dto.module.IdDto;
//import com.iqcloud.auth.entity.IqUserBinding;
//
//@ContextConfiguration(locations = "classpath:root-context.xml")
//public class IqUserBindingTest extends AbstractJUnit4SpringContextTests {
//	@Autowired
//	private IIqUserBindingFacade iqUserBindingFacade;
//	
//	/*
//	 * 插入用户绑定信息
//	 */
//	@Test
//	public void selectUserQuestionByUserId(){
//		//IqUserBinding iqUserBinding = new IqUserBinding();
//		IdDto idDto = new IdDto();
//		idDto.setId("DAAD5ACD32CC4D1E9FC7DC35A139D571");
//		//iqUserBinding.setId("F8DD05B818AA4058AF6F1B6B0B83633D");
//		//iqUserBinding.setUserId("DAAD5ACD32CC4D1E9FC7DC35A139D571");
//		//iqUserBinding.setEmail("864434357");
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(idDto)));
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		//IQCloudInfoDto iqCloudInfoDto2 = iqUserBindingFacade.insertSelective(iqCloudInfoDto);
//
//		//IQCloudInfoDto iqCloudInfoDto2 = iqUserBindingFacade.updateByPrimaryKeySelective(iqCloudInfoDto);
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iqUserBindingFacade.selectUserBindingByUserId(iqCloudInfoDto);
//
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//		
//	}
//}
//	
//	
//	
//
//	
//
