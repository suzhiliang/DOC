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
//import com.iqcloud.auth.entity.IqWeixinOrgWeixinAccount;
//
//@ContextConfiguration(locations = "classpath:root-context.xml")
//public class IqWeixinOrgWeixinAccountTest extends AbstractJUnit4SpringContextTests{
//	@Autowired
//	private IIqWeixinOrgWeixinAccountFacade iIqWeixinOrgWeixinAccountFacade;
//	
//	@Test
//	public void test(){
//		System.out.println("test...");
//	}
//	
//	/*
//	 * 通过微信企业号获取微信组织绑定实体
//	 */
//	@Test
//	public void selectByWeixinCorpId(){
//		IdDto idDto = new IdDto();
//		idDto.setId("wx123456789");
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(idDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqWeixinOrgWeixinAccountFacade.selectByWeixinCorpId(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
//	/*
//	 * 通过组织机构id获取微信组织绑定实体
//	 */
//	@Test
//	public void selectByOrgId(){
//		IdDto idDto = new IdDto();
//		idDto.setId("05058FA6C7A3403C8BAF01653DA53FA9");
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(idDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqWeixinOrgWeixinAccountFacade.selectByOrgId(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
//	/*
//	 * 插入组织机构与微信企业号绑定记录
//	 */
//	@Test
//	public void insertOrgIdWeixinCorpId(){
//		IqWeixinOrgWeixinAccount iqWeixinOrgWeixinAccount = new IqWeixinOrgWeixinAccount();
//		iqWeixinOrgWeixinAccount.setNote("测试说明!");
//		iqWeixinOrgWeixinAccount.setOrgId("04535BA040654E819A46E8EDE036D566");
//		iqWeixinOrgWeixinAccount.setWeixinCorpid("wx987654321");
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(iqWeixinOrgWeixinAccount);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqWeixinOrgWeixinAccountFacade.insertSelective(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//		
//	}
//}
