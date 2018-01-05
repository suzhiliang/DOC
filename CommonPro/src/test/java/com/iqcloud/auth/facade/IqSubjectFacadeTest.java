//package com.iqcloud.auth.facade;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//
//import com.alibaba.fastjson.JSON;
//import com.iqcloud.auth.dto.IQCloudInfoDto;
//import com.iqcloud.auth.dto.module.IdDto;
//
//@ContextConfiguration(locations = "classpath:root-context.xml")
//public class IqSubjectFacadeTest extends AbstractJUnit4SpringContextTests{
//	
//	@Autowired
//	private IIqSubjectFacade iqSubjectFacade;
//	
//
//	@Test
//	public void IqSubjectTest(){
//		//System.out.println("获取组织机构所有成员");
//		System.out.println("---------------------");
//		IdDto idDto = new IdDto();
//		idDto.setId("F3CB45F66A4A496D952101AFD6CCF5B1");
//		//idDto.setId("D1D7B4F0BA814DB6BE05122452F9F933");
//		//idDto.setId("DAAD5ACD32CC4D1E9FC7DC35A139D571");
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(idDto);
//	
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//
//		IQCloudInfoDto iqCloudInfoDto3 = iqSubjectFacade.selectSubByOrganization(iqCloudInfoDto);
//       
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto3));
//	}
//	
////	/*
////	 * 获取所有学科列表
////	 */
////	@Test
////	public void selectAllSubjects(){
////		System.out.println("selectAllSubjects");
////		
////		IQCloudInfoDto iqCloudInfoDto = iqSubjectFacade.selectAllSubjects(null);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto));
////	}
//
//}
