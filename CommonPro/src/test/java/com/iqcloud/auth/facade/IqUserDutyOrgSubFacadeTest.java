//package com.iqcloud.auth.facade;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//
//import com.alibaba.fastjson.JSON;
//import com.iqcloud.auth.dto.IQCloudInfoDto;
//import com.iqcloud.auth.dto.module.IdsDto;
//import com.iqcloud.auth.facade.iqclass.IIqClassOrganizationUsersFacade;
//
//@ContextConfiguration(locations = "classpath:root-context.xml")
//public class IqUserDutyOrgSubFacadeTest extends AbstractJUnit4SpringContextTests {
//	//@Autowired
//	//private IIqUserDutyOrgSubFacade iqUserDutyOrgSubFacade;
//	@Autowired
//	private IIqClassOrganizationUsersFacade iqOrganizationUsersFacade;
//	/*
//	 * 根据用户id获取用户所教的学科
//     */
//	@Test
//	public void selectSubByUserId(){
//		System.out.println("用户所教的学科员");
//		//System.out.println("---------------------");
//		IdsDto idsDto = new IdsDto();
//		//idsDto.setId2("6196BA2B2CA34F25A008B101517CB6B3");
//		//idsDto.setId1("DAAD5ACD32CC4D1E9FC7DC35A139D571");
//		//idDto.setId("D1D7B4F0BA814DB6BE05122452F9F933");
//		//idDto.setId("F3CB45F66A4A496D952101AFD6CCF5B1");
//
//		//IqUsers iqUser =new IqUsers();
//		//iqUser.setId("DAAD5ACD32CC4D1E9FC7DC35A139D571");
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(idsDto);
//		
//	System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		//IQCloudInfoDto iqCloudInfoDto2 = iqUserDutyOrgSubFacade.selectSubByUserId(iqCloudInfoDto);
//	   IQCloudInfoDto iqCloudInfoDto2 =iqOrganizationUsersFacade.selectTeachClassInfoByUserId(iqCloudInfoDto);
//		//IQCloudInfoDto iqCloudInfoDto5 = iIqOrganizationFacade.selectOrgAllMembers(iqCloudInfoDto);
//		//IQCloudInfoDto iqCloudInfoDto6 = iIqOrganizationFacade.selectOrgAllMembers(iqCloudInfoDto);
//		//IQCloudInfoDto iqCloudInfoDto3 = iIqOrganizationFacade.selectOrgAllStudentMembers(iqCloudInfoDto);
//		//IQCloudInfoDto iqCloudInfoDto4 = iIqOrganizationFacade.selectOrgAllTeacherMembers(iqCloudInfoDto);
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	//+","+JSON.toJSONString(iqCloudInfoDto5)+","+JSON.toJSONString(iqCloudInfoDto6));
//	}
//	/*@Test
//	public void selectByPrimaryKey() {
//		System.out.println("获取学科信息");
//		// System.out.println("---------------------");
//		IdDto idDto = new IdDto();
//
//		idDto.setId("242900D53C1611E5BC3A001D0998CE9B");
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(idDto);
//
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//
//		IQCloudInfoDto iqCloudInfoDto2 = iqUserDutyOrgSubFacade
//				.selectByPrimaryKey(iqCloudInfoDto);
//
//		System.out
//				.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//
//	}*/
//}
