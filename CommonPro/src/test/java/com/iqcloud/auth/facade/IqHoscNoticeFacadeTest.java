//package com.iqcloud.auth.facade;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//
//import com.alibaba.fastjson.JSON;
//import com.iqcloud.auth.dto.IQCloudInfoDto;
//import com.iqcloud.auth.entity.IqHoscNotice;
//
//
//@ContextConfiguration(locations = "classpath:root-context.xml")
//public class IqHoscNoticeFacadeTest extends AbstractJUnit4SpringContextTests{
//	@Autowired
//	private IIqHoscNoticeFacade iIqHoscNoticeFacade;
//
//	/*
//	 * 插入一个组织机构
//	 */
//	@Test
//	public void insertOneOrg(){
//		System.out.println("插入一个公告...");
//		IqHoscNotice notice = new IqHoscNotice();
//		notice.setContent("gggg");
//		notice.setCreateDate(new Date());
//		notice.setPublishDate(new Date());
//		notice.setOrgId("xsafdsa");
//		notice.setUserId("1");
//		notice.setState(1);
//		
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(notice);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqHoscNoticeFacade.addNotice(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
//}
//
