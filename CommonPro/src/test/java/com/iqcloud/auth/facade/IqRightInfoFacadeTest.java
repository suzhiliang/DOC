//package com.iqcloud.auth.facade;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//
//import com.alibaba.fastjson.JSON;
//import com.iqcloud.auth.dto.IQCloudInfoDto;
//import com.iqcloud.auth.dto.module.AccessTokenParamDto;
//import com.iqcloud.auth.dto.module.CommonDto;
//import com.iqcloud.auth.dto.module.UpdateOneRightDto;
//import com.iqcloud.auth.entity.IqRightInfo;
//
//@ContextConfiguration(locations = "classpath:root-context.xml")
//public class IqRightInfoFacadeTest extends AbstractJUnit4SpringContextTests{
//	@Autowired
//	private IIqRightInfoFacade iIqRightInfoFacade;
//	
//	@Test
//	public void test(){
//		System.out.println("测试......");
//	}
//	
//	/*
//	 * 插入一组权限记录
//	 */
//	@Test
//	public void insertOneRightRecord(){
//		IqRightInfo iqRightInfo = new IqRightInfo();
//		iqRightInfo.setCode("space:/IQSpace/resources/shishi/classAlbum.html");
//		iqRightInfo.setValue("1111111100000000");
//		iqRightInfo.setModuleType(0);
//		iqRightInfo.setObjectRightType(1);
//		iqRightInfo.setObjectRightId("08227CAF96F14622BA3482BF43A6D441");
//		
//		AccessTokenParamDto accessTokenParamDto = new AccessTokenParamDto();
//		accessTokenParamDto.setAccessToken("EEF17C906FBA4D1BB6E38DBCFD3E5227");
//		accessTokenParamDto.setParamObj(iqRightInfo);
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(accessTokenParamDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqRightInfoFacade.insertOneRightRecord(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
//	/*
//	 * 更新一个权限记录
//	 */
//	@Test
//	public void updateOneRightRecordByCodeAndObjectRightId(){
//		IqRightInfo iqRightInfo = new IqRightInfo();
//		iqRightInfo.setCode("space:/IQSpace/resources/shishi/classAlbum.html");
//		iqRightInfo.setValue("1111111100000000");
////		iqRightInfo.setModuleType(0);
////		iqRightInfo.setObjectRightType(1);
//		iqRightInfo.setObjectRightId("08227CAF96F14622BA3482BF43A6D441");
//		
//		AccessTokenParamDto accessTokenParamDto = new AccessTokenParamDto();
//		accessTokenParamDto.setAccessToken("EEB0B45F7DC64E83A85343422DA4379E");
//		accessTokenParamDto.setParamObj(iqRightInfo);
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(accessTokenParamDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqRightInfoFacade.updateOneRightRecordByCodeAndObjectRightId(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
//	/*
//	 * 删除权限记录
//	 */
//	@Test
//	public void deleteRightRecordByCodeAndObjectRightId(){
//		CommonDto commonDto = new CommonDto();
//		commonDto.setParamString1("space:/IQSpace/resources/shishi/classAlbum.html");
//		commonDto.setParamString2("08227CAF96F14622BA3482BF43A6D441");
//		
//		AccessTokenParamDto accessTokenParamDto = new AccessTokenParamDto();
//		accessTokenParamDto.setAccessToken("EEB0B45F7DC64E83A85343422DA4379E");
//		accessTokenParamDto.setParamObj(commonDto);
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(accessTokenParamDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqRightInfoFacade.deleteRightRecordByCodeAndObjectRightId(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
//	/*
//	 * 更新一个具体权限
//	 */
//	@Test
//	public void updateOneRight(){
//		UpdateOneRightDto updateOneRightDto = new UpdateOneRightDto();
//		updateOneRightDto.setCode("space:/iqspace/resources/shishi/classalbum.html");
//		updateOneRightDto.setObjectRightId("08227CAF96F14622BA3482BF43A6D441");
//		updateOneRightDto.setRightKey(2);
//		updateOneRightDto.setRightValue(false);
//		
//		AccessTokenParamDto accessTokenParamDto = new AccessTokenParamDto();
//		accessTokenParamDto.setAccessToken("EEF17C906FBA4D1BB6E38DBCFD3E5227");
//		accessTokenParamDto.setParamObj(updateOneRightDto);
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(accessTokenParamDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqRightInfoFacade.updateOneRight(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
//	/*
//	 * 根据权限编码、授权对象类型、授权对象id获取权限实体
//	 */
//	@Test
//	public void selectByCodeAndRightTypeAndRightObjectId(){
//		CommonDto commonDto = new CommonDto();
//		commonDto.setParamString1("space:/iqspace/resources/shishi/classalbum.html");
//		commonDto.setParamString2("08227CAF96F14622BA3482BF43A6D441");
//		commonDto.setParamInteger1(1);
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(commonDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqRightInfoFacade.selectByCodeAndRightTypeAndRightObjectId(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
//	/*
//	 * 根据权限编码、授权对象id、指定权限值判断是否拥有该权限
//	 */
//	@Test
//	public void getHasTheRightByCodeRightObjectId(){
//		CommonDto commonDto = new CommonDto();
//		commonDto.setParamString1("space:/iqspace/resources/shishi/classalbum.html");
//		commonDto.setParamString2("08227CAF96F14622BA3482BF43A6D441");
//		commonDto.setParamInteger1(10);
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(commonDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqRightInfoFacade.getHasTheRightByCodeRightObjectId(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
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
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
