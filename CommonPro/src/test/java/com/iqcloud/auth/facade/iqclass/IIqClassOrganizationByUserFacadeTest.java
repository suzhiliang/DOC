//package com.iqcloud.auth.facade.iqclass;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//
//import com.alibaba.fastjson.JSON;
//import com.iqcloud.auth.dto.IQCloudInfoDto;
//import com.iqcloud.auth.dto.module.IdDto;
//import com.iqcloud.auth.dto.module.IdsDto;
//
//@ContextConfiguration(locations = "classpath:root-context.xml")
//public class IIqClassOrganizationByUserFacadeTest extends AbstractJUnit4SpringContextTests {
//	//@Autowired
//	//private IIqUserDutyOrgSubFacade iqUserDutyOrgSubFacade;
//	@Autowired
//	private IIqClassOrganizationUsersFacade iqOrganizationByUserFacade;
//	/*
//	 * 根据老师登录的特权码获取老师所教的相关信息
//     */
//	@Test
//	public void selectSubByUserId(){
//		System.out.println("根据老师登录的特权码获取老师所教的相关信息");
//		//System.out.println("---------------------");
//		IdDto idDto = new IdDto();
//		//idsDto.setId2("6196BA2B2CA34F25A008B101517CB6B3");
//		//idDto.setId("9BA6D393F9E14D8C9F6FABFA5BD6D5BE");
//		idDto.setId("62420BD8308F432D880240B50BA3E625");
//		//idDto.setId("F3CB45F66A4A496D952101AFD6CCF5B1");
//
//		//IqUsers iqUser =new IqUsers();
//		//iqUser.setId("DAAD5ACD32CC4D1E9FC7DC35A139D571");
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(idDto);
//		
//	System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		//IQCloudInfoDto iqCloudInfoDto2 = iqUserDutyOrgSubFacade.selectSubByUserId(iqCloudInfoDto);
//	   IQCloudInfoDto iqCloudInfoDto2 =iqOrganizationByUserFacade.selectTeachClassInfoByUserId(iqCloudInfoDto);
//		//IQCloudInfoDto iqCloudInfoDto5 = iIqOrganizationFacade.selectOrgAllMembers(iqCloudInfoDto);
//		//IQCloudInfoDto iqCloudInfoDto6 = iIqOrganizationFacade.selectOrgAllMembers(iqCloudInfoDto);
//		//IQCloudInfoDto iqCloudInfoDto3 = iIqOrganizationFacade.selectOrgAllStudentMembers(iqCloudInfoDto);
//		//IQCloudInfoDto iqCloudInfoDto4 = iIqOrganizationFacade.selectOrgAllTeacherMembers(iqCloudInfoDto);
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//	//+","+JSON.toJSONString(iqCloudInfoDto5)+","+JSON.toJSONString(iqCloudInfoDto6));
//	}
//}