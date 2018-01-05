//package com.iqcloud.auth.facade;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.ibatis.annotations.Param;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.iqcloud.auth.dto.IQCloudInfoDto;
//import com.iqcloud.auth.dto.module.AccessTokenEntity;
//import com.iqcloud.auth.dto.module.IdsAndAtrriParamDto;
//
//@ContextConfiguration(locations = "classpath:root-context.xml")
//public class IqRoleFacadeTest extends AbstractJUnit4SpringContextTests{
//	@Autowired
//	private IIqRoleFacade iIqRoleFacade;
//	
//	@Test
//	public void test(){
//		System.out.println("测试...");
//	}
//	
//	/*
//	 * 获取用户基本角色
//	 */
//	@Test
//	public void selectUserRoleByAccessToken(){
//		System.out.println("获取用户基本角色...");
//		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
//		accessTokenEntity.setAccessToken("D733C3B1A32246CF8DA39D3A8084CC76");
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenEntity)));
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto1 = iIqRoleFacade.selectUserRoleByAccessToken(iqCloudInfoDto);
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//	}
//	
//	/*
//	 * 获取用户所有角色
//	 */
//	@Test
//	public void selectUserBindRolesByAccessToken(){
//		System.out.println("获取用户所有角色");
//		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
//		accessTokenEntity.setAccessToken("D733C3B1A32246CF8DA39D3A8084CC76");
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenEntity)));
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto1 = iIqRoleFacade.selectUserBindRolesByAccessToken(iqCloudInfoDto);
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//	}
//	
//	/*
//	 * 根据用户id获取用户基本角色
//	 */
//	@Test
//	public void selectUserRoleUserIds(){
//		System.out.println("根据用户id获取用户基本角色");
//		
//		List<String> userIds = new ArrayList<String>();
//		userIds.add("A59092BD685944FBAAC528D34B5F17F4");
//		userIds.add("18407BE0D8BF4BFEB4B9B6FA1F2D467E");
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(JSONObject.parseArray(JSON.toJSONString(userIds), String.class));
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto1 = iIqRoleFacade.selectUserRoleUserIds(iqCloudInfoDto);
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//	}
//	
//	/*
//     * 根据分类类型 及 字段 获取需要的角色数组
//     */
//	@Test
//    public void selectByClassTypeAndFields(){
//		System.out.println("selectByClassTypeAndFields");
//		
//		List<String> fields = new ArrayList<String>();
//		fields.add("id");
//		fields.add("role_name");
//		fields.add("create_time");
//		
//		List<Integer> classTypes = new ArrayList<Integer>();
//		classTypes.add(3);
//		classTypes.add(4);
//		
////		List<Integer> roleTypes = new ArrayList<Integer>();
////		roleTypes.add(301);
////		roleTypes.add(402);
//		
//		IdsAndAtrriParamDto idsAndAtrriParamDto = new IdsAndAtrriParamDto();
//		idsAndAtrriParamDto.setFields(fields);
//		idsAndAtrriParamDto.setIntValues1(classTypes);
////		idsAndAtrriParamDto.setIntValues2(roleTypes);
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(idsAndAtrriParamDto);
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto1 = iIqRoleFacade.selectByClassTypeAndFields(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//	}
//}
//
//
//
//
//
//
//
//
//
