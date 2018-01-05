//package com.iqcloud.auth.facade;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//
//import com.alibaba.fastjson.JSON;
//import com.iqcloud.auth.dto.IQCloudInfoDto;
//import com.iqcloud.auth.dto.module.StringDto;
//
//@ContextConfiguration(locations = "classpath:root-context.xml")
//public class IqSemesterFacadeTest extends AbstractJUnit4SpringContextTests{
//	
//	@Autowired
//	private IIqSemesterFacade iqSemesterFacade;
//	   @Test
//	   public void selectIqSemesterByTimeSlot(){
//		   StringDto stringDto =new StringDto();
//		   stringDto.setStr("2016-01-01 12:00:00");
//		   IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		   iqCloudInfoDto.setJsonBody(stringDto);
//		   System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		   IQCloudInfoDto iqCloudInfoDto1 = iqSemesterFacade.selectIqSemesterByTimeSlot(iqCloudInfoDto);
//		   System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//	   }
//
//}
