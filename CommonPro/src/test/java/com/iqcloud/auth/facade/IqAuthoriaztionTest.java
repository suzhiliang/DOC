//package com.iqcloud.auth.facade;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//
//import com.alibaba.fastjson.JSON;
//import com.iqcloud.auth.dto.IQCloudInfoDto;
//import com.iqcloud.auth.dto.module.AccessTokenEntity;
//import com.iqcloud.auth.dto.module.AccessTokenParamDto;
//import com.iqcloud.auth.dto.module.AuthUserInfoDto;
//import com.iqcloud.auth.dto.module.CommonDto;
//import com.iqcloud.auth.dto.module.IdDto;
//import com.iqcloud.auth.dto.module.PagingDto;
//import com.iqcloud.auth.entity.IqAuthChangeLog;
//import com.iqcloud.auth.entity.IqAuthMainframe;
//import com.iqcloud.auth.entity.IqAuthorization;
//import com.iqcloud.auth.entity.IqManagerAuth;
//import com.iqcloud.auth.entity.IqUsers;
//
//@ContextConfiguration(locations = "classpath:root-context.xml")
//public class IqAuthoriaztionTest extends AbstractJUnit4SpringContextTests {
//	@Autowired
//	private IIqAuthorizationFacade iIqAuthorizationFacade;
//	
//	@Test
//	public void test(){
//		System.out.println("测试......");
//		
//	}
//	/*
//	 * 增加一个激活码
//	 */
//	@Test
//	public void insertOneAuthorization(){
//		System.out.println("增加一个激活码");
//		
//		IqAuthorization iqAuthorization = new IqAuthorization();
//		iqAuthorization.setLimitClass(100);
//		iqAuthorization.setLimitTeacher(150);
//		iqAuthorization.setLimitStudent(2000);
//		iqAuthorization.setLinkman("测试模拟学校-02-联系人");
////		iqAuthorization.setOrganizationId("36AE316BCA324F30B8F64199C2AA203C");
//		
//		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
//		accessTokenEntity.setAccessToken("6ABDB21BBE40451CA4AEE6D94664FC11");
//
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonHead(accessTokenEntity);
//		iqCloudInfoDto.setJsonBody(iqAuthorization);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqAuthorizationFacade.insertOneAuthorization(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
//	/*
//	 * 批量增加一组激活码
//	 */
//	@Test
//	public void insertAuthorizations(){
//		System.out.println("批量增加一组激活码");
//		
//		CommonDto commonDto = new CommonDto();
//		commonDto.setParamInteger1(5);	// 产生五个激活码
//		
//		AccessTokenParamDto accessTokenParamDto = new AccessTokenParamDto();
//		accessTokenParamDto.setAccessToken("252FA997B6464A7D88DA0B734F1911C3");
//		accessTokenParamDto.setParamObj(commonDto);
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(accessTokenParamDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqAuthorizationFacade.insertAuthorizations(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
//	/*
//	 * 获取一个具体激活码实体
//	 */
//	@Test
//	public void selectOneAuthorizationEntity(){
//		System.out.println("获取一个具体激活码实体");
//		
//		IdDto idDto = new IdDto();
//		idDto.setId("E120DFEE9FB64677825120660C68A8CE");
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(idDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqAuthorizationFacade.selectOneAuthorizationEntity(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
//	/*
//	 * 获取所有激活码实体
//	 */
//	@Test
//	public void selectAuthorizationEntitys(){
//		System.out.println("获取所有激活码实体");
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqAuthorizationFacade.selectAuthorizationEntitys();
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
//	/*
//	 * 删除一个激活码
//	 */
//	@Test
//	public void deleteOneAuthorization(){
//		System.out.println("删除一个激活码");
//		
//		IdDto idDto = new IdDto();
//		idDto.setId("B789B3159C7B4132983B22947ADF008E");
//		
//		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
//		accessTokenEntity.setAccessToken("6ABDB21BBE40451CA4AEE6D94664FC11");
//
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonHead(accessTokenEntity);
//		iqCloudInfoDto.setJsonBody(idDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqAuthorizationFacade.deleteOneAuthorization(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
//	/*
//	 * 更新-修改激活码属性值
//	 */
//	@Test
//	public void updateAuthorization(){
//		System.out.println("更新-修改激活码属性值");
//		
//		IqAuthorization iqAuthorization = new IqAuthorization();
//		iqAuthorization.setId("798DB7A8F7F840429F19333730035753");
//		iqAuthorization.setLimitClass(50);
//		iqAuthorization.setLimitStudent(600);
//		iqAuthorization.setLimitTeacher(100);
//		
//		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
//		accessTokenEntity.setAccessToken("6ABDB21BBE40451CA4AEE6D94664FC11");
//
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonHead(accessTokenEntity);
//		iqCloudInfoDto.setJsonBody(iqAuthorization);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqAuthorizationFacade.updateAuthorization(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
//	/*
//	 * 添加、修改激活码修改记录
//	 */
//	@Test
//	public void insertUpdateRecord(){
//		System.out.println("添加、修改激活码修改记录");
//		
//		IqAuthChangeLog iqAuthChangeLog = new IqAuthChangeLog();
//		iqAuthChangeLog.setAuthId("35F6DB84003B405991394EE878EF91CF");
//		iqAuthChangeLog.setChangeContent("修改班级、老师、学生、家长的数量上限... C");
//		iqAuthChangeLog.setUserId("08227CAF96F14622BA3482BF43A6D441");
//		iqAuthChangeLog.setUserName("张三");
//		
//		AccessTokenParamDto accessTokenParamDto = new AccessTokenParamDto();
//		accessTokenParamDto.setAccessToken("252FA997B6464A7D88DA0B734F1911C3");
//		accessTokenParamDto.setParamObj(iqAuthChangeLog);
//
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(accessTokenParamDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqAuthorizationFacade.insertUpdateRecord(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
//	/*
//	 * 根据激活码主键id获取激活码修改记录
//	 */
//	@Test
//	public void selectUpdateRecordByAuthId(){
//		System.out.println("根据激活码主键id获取激活码修改记录");
//		
//		IdDto idDto = new IdDto();
//		idDto.setId("35F6DB84003B405991394EE878EF91CF");
//
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(idDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqAuthorizationFacade.selectUpdateRecordByAuthId(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
//	/*
//	 * 添加或者修改与激活码相关联的远程服务信息
//	 */
//	@Test
//	public void updateRemoteInfo(){
//		System.out.println("添加或者修改与激活码相关联的远程服务信息");
//		IqAuthMainframe iqAuthMainframe = new IqAuthMainframe();
//		
//		// 插入一条记录
////		iqAuthMainframe.setAuthId("55ACAFF353904AADBE5BE6C0BCB9A8DE");
////		iqAuthMainframe.setDomain("www.iqcedu.com");
////		iqAuthMainframe.setIp("58.22.30.37");
////		iqAuthMainframe.setPort(80);
////		iqAuthMainframe.setOrgId("05058FA6C7A3403C8BAF01653DA53FA9");
//		
//		// 更新一条记录  
//		iqAuthMainframe.setId("B601CD0B9CDF45F1962AA290E17D4C80");   // 更新记录，就要设置id，其他跟插入一样
//		iqAuthMainframe.setDomain("www.IQCloudedu.com");
//		
//		AccessTokenParamDto accessTokenParamDto = new AccessTokenParamDto();
//		accessTokenParamDto.setAccessToken("252FA997B6464A7D88DA0B734F1911C3");
//		accessTokenParamDto.setParamObj(iqAuthMainframe);
//
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(accessTokenParamDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqAuthorizationFacade.updateRemoteInfo(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
//	/*
//	 * 获取与激活码相关联的远程服务信息
//	 */
//	@Test
//	public void selectRemoteInfo(){
//		System.out.println("获取与激活码相关联的远程服务信息");
//		
//		IdDto idDto = new IdDto();
//		idDto.setId("55ACAFF353904AADBE5BE6C0BCB9A8DE");
//	
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(idDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqAuthorizationFacade.selectRemoteInfo(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
//	/*
//	 * 由激活码产生对应的组织管理员
//	 */
//	@Test
//	public void insertOrgAdmin(){
//		System.out.println("由激活码产生对应的组织管理员");
//		
//		AuthUserInfoDto authUserInfoDto = new AuthUserInfoDto();
//		IqUsers iqUsers = new IqUsers();
//		iqUsers.setUserName("A0U1w004");
//		iqUsers.setPassword("3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d");
//		authUserInfoDto.setIqUsers(iqUsers);
//		authUserInfoDto.setAuthId("35F6DB84003B405991394EE878EF91CF");
//
//		AccessTokenParamDto accessTokenParamDto = new AccessTokenParamDto();
//		accessTokenParamDto.setAccessToken("252FA997B6464A7D88DA0B734F1911C3");
//		accessTokenParamDto.setParamObj(authUserInfoDto);
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(accessTokenParamDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqAuthorizationFacade.insertOrgAdmin(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
//	/*
//	 * 获取分页信息
//	 */
//	@Test
//	public void getPagingDataInfo(){
//		PagingDto pagingDto = new PagingDto();
//		pagingDto.setPageItemCount(3);
//		pagingDto.setPageNum(2);
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(pagingDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqAuthorizationFacade.getPagingDataInfo(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//		
//	}
//	
//	/*
//	 * 修改激活码状态
//	 */
//	@Test
//	public void updateStatus(){
//		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
//		accessTokenEntity.setAccessToken("98EF4C1D09AE40B78A8CF91F6C41EFBC");
//		
//		CommonDto commonDto = new CommonDto();
//		commonDto.setParamInteger1(0);		// 激活码状态
//		commonDto.setParamString1("0C3F9186F7B44AF59CACE9E460ADC51D");		// 组织id
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonHead(accessTokenEntity);
//		iqCloudInfoDto.setJsonBody(commonDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqAuthorizationFacade.updateStatus(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
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
//
//
//
//
//
//
//
//
