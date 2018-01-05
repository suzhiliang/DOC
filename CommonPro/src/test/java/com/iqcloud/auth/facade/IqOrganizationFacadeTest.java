//package com.iqcloud.auth.facade;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.iqcloud.auth.dto.IQCloudInfoDto;
//import com.iqcloud.auth.dto.module.AccessTokenEntity;
//import com.iqcloud.auth.dto.module.AccessTokenParamDto;
//import com.iqcloud.auth.dto.module.AddOrgDto;
//import com.iqcloud.auth.dto.module.ArrayListTemplateDto;
//import com.iqcloud.auth.dto.module.CommonDto;
//import com.iqcloud.auth.dto.module.IdDto;
//import com.iqcloud.auth.dto.module.IdsAndAtrriParamDto;
//import com.iqcloud.auth.dto.module.IdsDto;
//import com.iqcloud.auth.dto.module.OrgIdRoleIdUserIdsDto;
//import com.iqcloud.auth.dto.module.OrgMemberIdsDto;
//import com.iqcloud.auth.dto.module.PagingDto;
//import com.iqcloud.auth.entity.IqOrganization;
//import com.iqcloud.auth.entity.IqUsers;
//
//
//@ContextConfiguration(locations = "classpath:root-context.xml")
//public class IqOrganizationFacadeTest extends AbstractJUnit4SpringContextTests{
//	@Autowired
//	private IIqOrganizationFacade iIqOrganizationFacade;
////	
////	//@Autowired
////	//private IIqUsersFacade iIqUsersFacade;
////	
////	@Test
////	public void test(){
////		System.out.println("测试......");
////	}	
////	
////	/*
////	 * 插入一个组织机构
////	 */
////	@Test
////	public void insertOneOrg(){
////		System.out.println("插入一个组织机构...");
////		
////		IqOrganization iqOrganization = new IqOrganization();
////		iqOrganization.setOrganizationalName("测试模拟学校-11");
////		iqOrganization.setInfo("测试模拟学校-11");
////		iqOrganization.setLimitClass(50);
////		iqOrganization.setLimitStudent(2000);
////		iqOrganization.setLimitTeacher(200);
////		
////		AddOrgDto addOrgDto = new AddOrgDto();
////		addOrgDto.setOrgPId("0");
////		addOrgDto.setIqOrganization(iqOrganization);
////		
////		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
////		accessTokenEntity.setAccessToken("13BC018B9D234F969FDD1C821FCC14D4");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonHead(accessTokenEntity);
////		iqCloudInfoDto.setJsonBody(addOrgDto);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto2 = iIqOrganizationFacade.insertOneOrg(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////	}
////	
////	/*
////	 * 修改一个组织机构实体
////	 */
////	@Test
////	public void updateOneOrg(){
////		System.out.println("修改一个组织机构实体");
////		
////		IqOrganization iqOrganization = new IqOrganization();
////		iqOrganization.setId("7AEAE3FC7A674DB1B6F7D2F53EF45331");
////		iqOrganization.setOrganizationalName("测试模拟学校-13...");
////		iqOrganization.setInfo("测试模拟学校-13...");
////		
////		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
////		accessTokenEntity.setAccessToken("13BC018B9D234F969FDD1C821FCC14D4");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonHead(accessTokenEntity);
////		iqCloudInfoDto.setJsonBody(iqOrganization);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto2 = iIqOrganizationFacade.updateOneOrg(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////	}
////	
////	/*
////	 * 删除一个组织机构
////	 */
////	@Test
////	public void deleteOrg(){
////		System.out.println("删除组织机构");
////		
////		List<String> orgIdList = new ArrayList<String>();
////		orgIdList.add("B026903199C84977945983100F9341D7");
////		orgIdList.add("567E8E6F2AE5441EA0151DBBA3A8EC5F");
////		ArrayListTemplateDto<String> arrayListTemplateDto = new ArrayListTemplateDto<String>();
////		arrayListTemplateDto.setDataList(orgIdList);
////		
////		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
////		accessTokenEntity.setAccessToken("13BC018B9D234F969FDD1C821FCC14D4");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonHead(accessTokenEntity);
////		iqCloudInfoDto.setJsonBody(arrayListTemplateDto);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto2 = iIqOrganizationFacade.deleteOrg(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////	}
////	
////	/*
////	 * 获取组织机构实体
////	 */
////	@Test
////	public void selectOrgEntityList(){
////		System.out.println("查询组织实体...");
////		
////		List<String> orgIdList = new ArrayList<String>();
////		orgIdList.add("5F73652021634BB8808297A0534263B7");
////		orgIdList.add("76F71FE4EC05401799E8AE2C4FDF9204");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(orgIdList);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto2 = iIqOrganizationFacade.selectOrgEntityList(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////	}
//	
////	/*
////	 * 获取组织机构下所有成员
////	 */
////	@Test
////	public void selectOrgAllMembers(){
////		System.out.println("获取组织机构所有成员");
////		IdDto idDto = new IdDto();
////		idDto.setId("05058FA6C7A3403C8BAF01653DA53FA9");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(idDto);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto2 = iIqOrganizationFacade.selectOrgAllMembers(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////	}
////	
////	/*
////	 * 获取组织机构下的所有学生
////	 */
////	@Test
////	public void selectOrgAllStudentMembers() {
////		System.out.println("获取组织机构学生成员");
////		IdDto idDto = new IdDto();
////		idDto.setId("05058FA6C7A3403C8BAF01653DA53FA9");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(idDto);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto2 = iIqOrganizationFacade.selectOrgAllStudentMembers(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////	}
////	
////	/*
////	 * 获取组织下的所有老师
////	 */
////	@Test
////	public void selectOrgAllTeacherMembers(){
////		System.out.println("获取组织机构老师成员");
////		
////		IdDto idDto = new IdDto();
////		idDto.setId("05058FA6C7A3403C8BAF01653DA53FA9");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(idDto);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto2 = iIqOrganizationFacade.selectOrgAllTeacherMembers(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////	}
////	
////	/*
////	 * 插入指定组织角色
////	 */
////	@Test
////	public void insertOrgAppointMembers(){
////		System.out.println("插入指定组织角色");
////		
////		List<String> userIds = new ArrayList<String>();
////		userIds.add("08227CAF96F14622BA3482BF43A6D441");
////		userIds.add("D1FAC7AA59AC4A539A9A519FF146E028");
////		OrgIdRoleIdUserIdsDto orgIdRoleIdUserIdsDto = new OrgIdRoleIdUserIdsDto();
////		orgIdRoleIdUserIdsDto.setUserIdList(userIds);
////		orgIdRoleIdUserIdsDto.setOrgId("05058FA6C7A3403C8BAF01653DA53FA9");
////		orgIdRoleIdUserIdsDto.setRoleId("204E1DFC6DD3496A94A9CA2326B277CD");
////		
////		AccessTokenParamDto accessTokenParamDto = new AccessTokenParamDto();
////		accessTokenParamDto.setAccessToken("B7CFC46A338F4400863C8B917DE1DBDB");
////		accessTokenParamDto.setParamObj(orgIdRoleIdUserIdsDto);
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(accessTokenParamDto);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto2 = iIqOrganizationFacade.insertOrgAppointMembers(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////	}
////	
////	/*
////	 * 获取班级组织机构中的成员（班主任、班长、科任老师等）
////	 */
////	@Test
////	public void selectOrgAppointMembers(){
////		System.out.println("获取班级组织机构中的成员（班主任、班长、科任老师等）");
////		
////		CommonDto commonDto = new CommonDto();
////		commonDto.setParamString1("05058FA6C7A3403C8BAF01653DA53FA9");
////		
////		// 班主任
//////		commonDto.setParamInteger1(304);
////		
////		// 班长
//////		commonDto.setParamInteger1(302);
////		
////		// 科任老师
////		commonDto.setParamInteger2(4);
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(commonDto);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto2 = iIqOrganizationFacade.selectOrgAppointMembers(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////		
////	}
////	 /*
////	 * 添加、修改组织机构管理员
////	 */
////	@Test
////	public void updateOrgAdmin(){
////		System.out.println("添加、修改组织机构管理员");
////		
////		CommonDto commonDto = new CommonDto();
////		commonDto.setParamString1("D1D7B4F0BA814DB6BE05122452F9F933");
////		commonDto.setParamString2("08227CAF96F14622BA3482BF43A6D441");
////		
////		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
////		
////		accessTokenEntity.setAccessToken("4C8A6BBA104A4CBD9F8D376EC447009D");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonHead(accessTokenEntity);
////
////		iqCloudInfoDto.setJsonBody(commonDto);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////  	IQCloudInfoDto iqCloudInfoDto2 = iIqOrganizationFacade.updateOrgAdmin(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////	}
//	
//
//////	
////	/*
////	 * 添加组织机构成员
////	 */
////	@Test
////	public void insertOrgMembers(){
////		System.out.println("添加组织机构成员");
////		
////		OrgMemberIdsDto orgMemberIdsDto = new OrgMemberIdsDto();
////		orgMemberIdsDto.setOrgId("6E77B6CF4A4C4C36AA1BBD7DE0F6278F");
////		List<String> userIds = new ArrayList<String>();
////		userIds.add("441CAC930B3F43C681A5500930957B37");
////		orgMemberIdsDto.setUserIds(userIds);
////		
////		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
////		accessTokenEntity.setAccessToken("CF0F0749345247D9B7798F2A765D0DD1");
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonHead(accessTokenEntity);
////		iqCloudInfoDto.setJsonBody(orgMemberIdsDto);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto2 = iIqOrganizationFacade.insertOrgMembers(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////	}
//	
//
//	/*
//
////	/*
////	 * 删除组织机构成员
////	 */
////	@Test
////	public void deleteOrgMembers(){
////		System.out.println("删除组织机构成员");
////		
////		OrgMemberIdsDto orgMemberIdsDto = new OrgMemberIdsDto();
////		orgMemberIdsDto.setOrgId("6E77B6CF4A4C4C36AA1BBD7DE0F6278F");
////		List<String> userIds = new ArrayList<String>();
////		userIds.add("441CAC930B3F43C681A5500930957B37");
////		orgMemberIdsDto.setUserIds(userIds);
////		
////		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
////		accessTokenEntity.setAccessToken("4C8A6BBA104A4CBD9F8D376EC447009D");
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonHead(accessTokenEntity);
////		
////		iqCloudInfoDto.setJsonBody(orgMemberIdsDto);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto2 = iIqOrganizationFacade.deleteOrgMembers(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////	}
////    /*
////	 * 添加、修改组织机构管理员
////	 */
////	@Test
////	public void updateOrgAdmin(){
////		System.out.println("添加、修改组织机构管理员");
////		
////		CommonDto commonDto = new CommonDto();
////		commonDto.setParamString1("05058FA6C7A3403C8BAF01653DA53FA9");
////		commonDto.setParamString2("08227CAF96F14622BA3482BF43A6D441");
////		
////		AccessTokenParamDto accessTokenParamDto = new AccessTokenParamDto();
////		accessTokenParamDto.setAccessToken("B7CFC46A338F4400863C8B917DE1DBDB");
////		accessTokenParamDto.setParamObj(commonDto);
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(accessTokenParamDto);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto2 = iIqOrganizationFacade.updateOrgAdmin(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////	}
////
////	/*
////=======
////	/*
////	 * 添加组织机构成员
////	 */
////	@Test
////	public void insertOrgMembers(){
////		System.out.println("添加组织机构成员");
////		
////		OrgMemberIdsDto orgMemberIdsDto = new OrgMemberIdsDto();
////		orgMemberIdsDto.setOrgId("05058FA6C7A3403C8BAF01653DA53FA9");
////		List<String> userIds = new ArrayList<String>();
////		userIds.add("441CAC930B3F43C681A5500930957B37");
////		orgMemberIdsDto.setUserIds(userIds);
////		
////		AccessTokenParamDto accessTokenParamDto = new AccessTokenParamDto();
////		accessTokenParamDto.setAccessToken("B7CFC46A338F4400863C8B917DE1DBDB");
////		accessTokenParamDto.setParamObj(orgMemberIdsDto);
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(accessTokenParamDto);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto2 = iIqOrganizationFacade.insertOrgMembers(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////	}
////	
////	/*
////	 * 删除组织机构成员
////	 */
////	@Test
////	public void deleteOrgMembers(){
////		System.out.println("删除组织机构成员");
////		
////		OrgMemberIdsDto orgMemberIdsDto = new OrgMemberIdsDto();
////		orgMemberIdsDto.setOrgId("05058FA6C7A3403C8BAF01653DA53FA9");
////		List<String> userIds = new ArrayList<String>();
////		userIds.add("441CAC930B3F43C681A5500930957B37");
////		orgMemberIdsDto.setUserIds(userIds);
////		
////		AccessTokenParamDto accessTokenParamDto = new AccessTokenParamDto();
////		accessTokenParamDto.setAccessToken("B7CFC46A338F4400863C8B917DE1DBDB");
////		accessTokenParamDto.setParamObj(orgMemberIdsDto);
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(accessTokenParamDto);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto2 = iIqOrganizationFacade.deleteOrgMembers(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////	}
////	
////	/*
////>>>>>>> .r3871
////	 * 获取目录树
////	 */
////	@Test
////	public void selectOrgTreeChild(){
////		CommonDto commonDto = new CommonDto();
////		commonDto.setParamString1("0");		// 组织id
////		commonDto.setParamInteger1(2);		// 层级  0：无限制
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(commonDto);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto2 = iIqOrganizationFacade.selectOrgTreeChild(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////		
////	}
////	
////	
//////	 // 获取组织机构下所有成员
//     
//	@Test
//	public void Test(){
//		//System.out.println("获取组织机构所有成员");
//		System.out.println("---------------------");
//		IdsDto idsDto = new IdsDto();
//		idsDto.setId1("F3CB45F66A4A496D952101AFD6CCF5B1");
//		idsDto.setId2("可欣");
//		//idDto.setId("D1D7B4F0BA814DB6BE05122452F9F933");
//		//idDto.setId("DAAD5ACD32CC4D1E9FC7DC35A139D571");
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(idsDto);
//	
//	 System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//
//		//IQCloudInfoDto iqCloudInfoDto3 = iIqOrganizationFacade.selectClassOrgByUserId(iqCloudInfoDto);
//
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		//IQCloudInfoDto iqCloudInfoDto2 = iIqOrganizationFacade.selectOrgByUserId(iqCloudInfoDto);
//
//		//IQCloudInfoDto iqCloudInfoDto5 = iIqOrganizationFacade.selectOrgAllMembers(iqCloudInfoDto);
//		//IQCloudInfoDto iqCloudInfoDto3 = iIqOrganizationFacade.selectOrgAllMembers(iqCloudInfoDto);
//		IQCloudInfoDto iqCloudInfoDto3 = iIqOrganizationFacade.selectOrgAllStudentMembers(iqCloudInfoDto);
//		//IQCloudInfoDto iqCloudInfoDto4 = iIqOrganizationFacade.selectOrgAllMembers(iqCloudInfoDto);
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto3));
//	//+","+JSON.toJSONString(iqCloudInfoDto5)+","+JSON.toJSONString(iqCloudInfoDto6));
//	}
//
//
//	
//	// 获取组织机构实体
//	 
//	@Test
//	public void selectOrgEntityList(){
//		System.out.println("查询组织实体...");
//		
//		List<String> orgIdList = new ArrayList<String>();
//		//orgIdList.add("F3CB45F66A4A496D952101AFD6CCF5B1");
//		//orgIdList.add("D1D7B4F0BA814DB6BE05122452F9F933");
//		IdDto id =new IdDto();
//		//id.setId("DAAD5ACD32CC4D1E9FC7DC35A139D571");
//		id.setId("050732C4E18F4D1F9463E1E442B90EE1");
//
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(id);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		//selectOrgAllEntityList
//	  // IQCloudInfoDto iqCloudInfoDto2 = iIqOrganizationFacade.selectParentOrgAllEntityList(iqCloudInfoDto);
//		//IQCloudInfoDto iqCloudInfoDto2 = iIqOrganizationFacade.selectOrgAllEntityList(iqCloudInfoDto);
//		//IQCloudInfoDto iqCloudInfoDto2 = iIqOrganizationFacade.selectChildOrgAllEntityList(iqCloudInfoDto);
//		IQCloudInfoDto iqCloudInfoDto2 = iIqOrganizationFacade.selectOrgByUserId(iqCloudInfoDto);
//
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//
//	}
//	
//
//	/*
//	 * 获取用户属性名
//	 
//	@Test
//	public void selectMembersByOrgIdsAndUserFields(){
//		System.out.println("selectMembersByOrgIdsAndUserFields");
//		List<String> orgIds = new ArrayList<String>();
//		orgIds.add("6196BA2B2CA34F25A008B101517CB6B3");
//		orgIds.add("75E0FC3FF72241129824DE1C211E6641");
//		
//		List<String> fields = new ArrayList<String>();
//		fields.add("user_name");
//		fields.add("real_name");
//		fields.add("seat_number");
//		
//		IdsAndAtrriParamDto userAtrriParamDto = new IdsAndAtrriParamDto();
//		userAtrriParamDto.setFields(fields);
//		userAtrriParamDto.setIds(orgIds);
//		userAtrriParamDto.setBoolParam1(false);
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(userAtrriParamDto)));
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto1 = iIqOrganizationFacade.selectMembersByOrgIdsAndUserFields(iqCloudInfoDto);
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//	}
//	
////	/*
////	 * 由组织获取改组织的管理员实体
////	 */
////	@Test
////	public void selectAdminUserEntityByOrdId(){
////		System.out.println("selectAdminUserEntityByOrdId");
////		IdDto idDto = new IdDto();
////		idDto.setId("F3CB45F66A4A496D952101AFD6CCF5B1");
////		
////
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(idDto);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto1 = iIqOrganizationFacade.selectAdminUserEntityByOrdId(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
////	}
////
////	
////	/*
////	 * 根据分页信息获取组织机构下的信息
////	 */
////	@Test
////	public void selectPagingChildOrgInfoAndUserTypeNumAndAdminInfo(){
////		System.out.println("selectPagingChildOrgInfoAndUserTypeNumAndAdminInfo");
////		
////		PagingDto pagingDto = new PagingDto();
////		pagingDto.setPageItemCount(10);
////		pagingDto.setPageNum(1);
////		pagingDto.setParamString1("D1D7B4F0BA814DB6BE05122452F9F933");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(pagingDto);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto1 = iIqOrganizationFacade.selectPagingChildOrgInfoAndUserTypeNumAndAdminInfo(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
////	}
//}

