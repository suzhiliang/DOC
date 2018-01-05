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
//import com.iqcloud.auth.dto.module.AddOrEditOneUserDto;
//import com.iqcloud.auth.dto.module.CommonDto;
//import com.iqcloud.auth.dto.module.IdDto;
//import com.iqcloud.auth.dto.module.LoginDto;
//import com.iqcloud.auth.dto.module.IdsAndAtrriParamDto;
//import com.iqcloud.auth.dto.module.PagingDto;
//import com.iqcloud.auth.entity.IqOrganization;
//import com.iqcloud.auth.entity.IqRole;
//import com.iqcloud.auth.entity.IqSubject;
//import com.iqcloud.auth.entity.IqUsers;
//
//@ContextConfiguration(locations = "classpath:root-context.xml")
//public class IqUsersFacadeTest extends AbstractJUnit4SpringContextTests{
//	@Autowired
//	private IIqUsersFacade iIqUsersFacade;
//	
////	@Test
////	public void test(){
////		System.out.println("测试......");
////	}
////	
////	@Test
////	public void insertSelective(){
////		IqUsers iqUsers = new IqUsers();
////		//iqUsers.setId(CommonFun.getOneUUID());
////		iqUsers.setUserName("测试登录名3");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(iqUsers)));
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.insertSelective(iqCloudInfoDto);
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
////	}
////	
////	@Test
////	public void selectByPrimaryKey(){
////		String id = "08227CAF96F14622BA3482BF43A6D441";
////		IdDto idDto = new IdDto();
////		idDto.setId(id);
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(idDto)));
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.selectByPrimaryKey(iqCloudInfoDto);
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
////		
////	}
////	
////	/*
////	 * 更新用户信息
////	 */
////	@Test
////	public void updateByPrimaryKeySelective(){
////		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
////		accessTokenEntity.setAccessToken("0BAC6A49DD4E4ED3AB97116E42EC6B76");
////		
////		IqUsers iqUsers = new IqUsers();
////		iqUsers.setId("A59092BD685944FBAAC528D34B5F17F4");
////		iqUsers.setQq("12122511");
////		iqUsers.setEmail("111@qq.com");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonHead(accessTokenEntity);
////		iqCloudInfoDto.setJsonBody(iqUsers);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.updateByPrimaryKeySelective(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
////	}
////	
////	/*
////	 * 登录
////	 */
////	@Test
////	public void login(){
////		LoginDto loginDto = new LoginDto();
////		loginDto.setUserName("A0U1w001");			// 用户账号
//////		loginDto.setUserName("441778166@qq.com");	// 邮箱
//////		loginDto.setUserName("15259165615");		// 手机
////		loginDto.setUserPwd("3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(loginDto)));
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.login(iqCloudInfoDto);
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
////	}
////	
////	/*
////	 * 登出
////	 */
////	@Test
////	public void logout(){
////		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
////		accessTokenEntity.setAccessToken("1B63886211504F37B81C243DA343F730");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenEntity)));
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.logout(iqCloudInfoDto);
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
////	}
////	
////	/*
////	 * 获取用户实体
////	 */
////	@Test
////	public void selectUserEntityByAccessToken(){
////		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
////		accessTokenEntity.setAccessToken("DE7D626D4A55431BAA6280B5BF79579F");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenEntity)));
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.selectUserEntityByAccessToken(iqCloudInfoDto);
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
////	}
////	
////	/*
////	 * 获取用户状态
////	 */
////	@Test
////	public void selectUserStatus(){
////		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
////		accessTokenEntity.setAccessToken("DE7D626D4A55431BAA6280B5BF79579F");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenEntity)));
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.selectUserStatus(iqCloudInfoDto);
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
////	}
////	
////	/*
////	 * 获取用户头像
////	 */
////	@Test
////	public void selectUserHeadImage(){
////		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
////		accessTokenEntity.setAccessToken("DE7D626D4A55431BAA6280B5BF79579F");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenEntity)));
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.selectUserHeadImage(iqCloudInfoDto);
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
////	}
////	
////	/*
////	 * 获取用户注册时间
////	 */
////	@Test
////	public void selectUserRegDateTime(){
////		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
////		accessTokenEntity.setAccessToken("DE7D626D4A55431BAA6280B5BF79579F");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenEntity)));
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.selectUserRegDateTime(iqCloudInfoDto);
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
////	}
////	
////	/*
////	 * 获取学号、座位号
////	 */
////	@Test
////	public void selectUserStudentNumberAndSeatNumber(){
////		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
////		accessTokenEntity.setAccessToken("DE7D626D4A55431BAA6280B5BF79579F");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenEntity)));
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.selectUserStudentNumberAndSeatNumber(iqCloudInfoDto);
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
////	}
////	
////	/*
////	 * 获取用户个人说明
////	 */
////	@Test
////	public void selectUserInfo(){
////		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
////		accessTokenEntity.setAccessToken("DE7D626D4A55431BAA6280B5BF79579F");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenEntity)));
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.selectUserInfo(iqCloudInfoDto);
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
////	}
////	
////	/*
////	 * 获取用户登录名
////	 */
////	@Test
////	public void selectUserName(){
////		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
////		accessTokenEntity.setAccessToken("DE7D626D4A55431BAA6280B5BF79579F");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenEntity)));
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.selectUserName(iqCloudInfoDto);
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
////	}
////	
////	/*
////	 * 获取用户属性名
////	 */
////	@Test
////	public void selectByUserIdsAndFields(){
////		System.out.println("selectByUserIdsAndFields");
////		List<String> userIds = new ArrayList<String>();
////		userIds.add("040053D12ABF4EDCB2E98B51759B1DAC");
////		userIds.add("050732C4E18F4D1F9463E1E442B90EE1");
////		
////		List<String> fields = new ArrayList<String>();
////		fields.add("user_name");
////		fields.add("real_name");
////		fields.add("seat_number");
////		
////		IdsAndAtrriParamDto userAtrriParamDto = new IdsAndAtrriParamDto();
////		userAtrriParamDto.setFields(fields);
////		userAtrriParamDto.setIds(userIds);
////		userAtrriParamDto.setSortField("seat_number");
////		userAtrriParamDto.setSortType(0);
////		
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(userAtrriParamDto)));
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.selectByUserIdsAndFields(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
////	}
////	
////	/*
////	 * 通过参数获取用户分页信息
////	 */
////	@Test
////	public void selectPagingUserInfoByParam(){
////		System.out.println("selectPagingUserInfoByParam");
////		
////		PagingDto pagingDto = new PagingDto();
////		pagingDto.setPageItemCount(2);
////		pagingDto.setPageNum(1);
////		pagingDto.setParamString1("张");
////		
////		// 角色id
////		List<String> rolesList = new ArrayList<String>();
////		rolesList.add("FB96CB6C02F848128A3BA10C3C041A31");	// 老师
//////		rolesList.add("565A7759190F420697DAA3B8B60D08A7");	// 学生
////		rolesList.add("97C3EB267169498297A07364E77B4600");	// 家长
////		pagingDto.setStringParamArray1(rolesList);
////		
////		// 角色类型
////		List<Integer> rolesTypeList = new ArrayList<Integer>();
//////		rolesTypeList.add(3);		// 老师
////		rolesTypeList.add(4);		// 学生
//////		rolesTypeList.add(5);		// 家长
////		pagingDto.setIntegerParamArray1(rolesTypeList);
////		
////		// 组织
////		List<String> orgsList = new ArrayList<String>();
////		orgsList.add("6196BA2B2CA34F25A008B101517CB6B3");	// 初二(1)班
//////		orgsList.add("75E0FC3FF72241129824DE1C211E6641");	// 初二(2)班
////		pagingDto.setStringParamArray2(orgsList);
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(pagingDto)));
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.selectPagingUserInfoByParam(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
////		
////	}
////	
////	/*
////	 * 更新用户状态
////	 */
////	@Test
////	public void updateUserStatus(){
////		System.out.println("updateUserStatus");
////		
////		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
////		accessTokenEntity.setAccessToken("0BAC6A49DD4E4ED3AB97116E42EC6B76");
////		
////		List<String> userIds = new ArrayList<String>();
////		userIds.add("A59092BD685944FBAAC528D34B5F17F4");
////		userIds.add("054C5AFEE4484B5F8DBDFF4B3EA35EA1");
////		
////		CommonDto commonDto = new CommonDto();
////		commonDto.setStringParamArray1(userIds);
////		commonDto.setParamInteger1(2);
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonHead(accessTokenEntity);
////		iqCloudInfoDto.setJsonBody(commonDto);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.updateUserStatus(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
////	}
////	
////	/*
////	 * 初始化密码
////	 */
////	@Test
////	public void updateInitUserPassword(){
////		System.out.println("updateInitUserPassword");
////		
////		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
////		accessTokenEntity.setAccessToken("27BE8C4E0A644C4EA6F9BDBECB098CAB");
////	
////		List<String> userIds = new ArrayList<String>();
////		userIds.add("A59092BD685944FBAAC528D34B5F17F4");
////		userIds.add("054C5AFEE4484B5F8DBDFF4B3EA35EA1");
////		
////		CommonDto commonDto = new CommonDto();
////		commonDto.setStringParamArray1(userIds);
////		commonDto.setParamString1("e10adc3949ba59abbe56e057f20f883e");
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonHead(accessTokenEntity);
////		iqCloudInfoDto.setJsonBody(commonDto);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.updateInitUserPassword(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
////	}
////	
////	/*
////	 * 添加一个用户
////	 */
////	@Test
////	public void insertOneUser(){
////		System.out.println("insertOneUser");
////		
////		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
////		accessTokenEntity.setAccessToken("3E4E7CD496E54D9188B7F83FB0CCE983");
////		
////		AddOrEditOneUserDto addOrEditOneUserDto = new AddOrEditOneUserDto();
////		
////		// 用户实体
////		IqUsers iqUsers = new IqUsers();
////		iqUsers.setRealName("张三");
////		iqUsers.setUserName("A0U1w10050");
////		iqUsers.setPassword("3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d");
////		iqUsers.setRoleId("565A7759190F420697DAA3B8B60D08A7");   // 学生
////		addOrEditOneUserDto.setIqUsers(iqUsers);
////		
////		// 基本角色 --- 老师
////		IqRole iqRole = new IqRole();
////		iqRole.setId("FB96CB6C02F848128A3BA10C3C041A31");
////		addOrEditOneUserDto.setIqBaseRole(iqRole);
////		
////		// 相关角色
////		List<IqRole> iqRoles = new ArrayList<IqRole>();
////		// 机构管理员
////		IqRole iqRole2 = new IqRole();
////		iqRole2.setId("4E87865D592B405CA8CAA26B7EE0B45B");
////		iqRoles.add(iqRole2);
////		// 班主任
////		IqRole iqRole3 = new IqRole();
////		iqRole3.setId("FEE45397B37A42249E5760E0AE7D12C7");
////		iqRoles.add(iqRole3);
////		addOrEditOneUserDto.setIqRoles(iqRoles);
////		
////		// 组织实体
////		List<IqOrganization> iqOrganizations = new ArrayList<IqOrganization>();
////		IqOrganization iqOrganization1 = new IqOrganization();
////		// 初二年(1)班
////		iqOrganization1.setId("6196BA2B2CA34F25A008B101517CB6B3");
////		iqOrganizations.add(iqOrganization1);
////		IqOrganization iqOrganization2 = new IqOrganization();
////		// 初二年(2)班
////		iqOrganization2.setId("75E0FC3FF72241129824DE1C211E6641");
////		iqOrganizations.add(iqOrganization2);
////		addOrEditOneUserDto.setIqOrganizations(iqOrganizations);
////		
////		// 学科关联
////		List<IqSubject> iqSubjects = new ArrayList<IqSubject>();
////		// 语文
////		IqSubject iqSubject1 = new IqSubject();
////		iqSubject1.setId("0FF5CC503C1611E5BC3A001D0998CE9B");
////		iqSubjects.add(iqSubject1);
////		// 数学
////		IqSubject iqSubject2 = new IqSubject();
////		iqSubject2.setId("1E87421E3C1611E5BC3A001D0998CE9B");
////		iqSubjects.add(iqSubject2);
////		
////		addOrEditOneUserDto.setIqSubjects(iqSubjects);
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonHead(accessTokenEntity);
////		iqCloudInfoDto.setJsonBody(addOrEditOneUserDto);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.insertOneUser(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
////	}
////	
////	/*
////	 * 根据特权码产生新用户id列表
////	 */
////	@Test
////	public void getNewUserNameByAccessToken(){
////		System.out.println("getNewUserNameByAccessToken");
////		
////		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
////		accessTokenEntity.setAccessToken("3E4E7CD496E54D9188B7F83FB0CCE983");
////		
////		CommonDto commonDto = new CommonDto();
////		commonDto.setParamBoolean1(true);
////		commonDto.setParamInteger1(10);
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonHead(accessTokenEntity);
////		iqCloudInfoDto.setJsonBody(commonDto);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.getNewUserNameByAccessToken(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
////	}
//	
////	/*
////	 *根据用户名获取用户信息
////	 */
////	@Test
////	public void selectUserEntityBy(){
////		//IqUserBinding iqUserBinding = new IqUserBinding();
////		IdDto idDto = new IdDto();
////		idDto.setId("A0U1w098");
////		//iqUserBinding.setId("111");
////		//iqUserBinding.setUserId("DAAD5ACD32CC4D1E9FC7DC35A139D571");
////		//iqUserBinding.setEmail("865534357");
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(idDto)));
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto2 = iIqUsersFacade.selectUserEntityByUserName(iqCloudInfoDto);
////
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
////		
////	}
//	
//	/*
//	 * 更新用户信息
//	 */
//	@Test
//	public void updateByPrimaryKeySelective(){
//		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
//		accessTokenEntity.setAccessToken("0BAC6A49DD4E4ED3AB97116E42EC6B76");
//		
//		IqUsers iqUsers = new IqUsers();
//		iqUsers.setId("A59092BD685944FBAAC528D34B5F17F4");
//		iqUsers.setQq("12122511");
//		iqUsers.setEmail("111@qq.com");
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonHead(accessTokenEntity);
//		iqCloudInfoDto.setJsonBody(iqUsers);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.updateByPrimaryKeySelective(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//	}
//	
//	/*
//	 * 登录
//	 */
//	@Test
//	public void login(){
//		LoginDto loginDto = new LoginDto();
//		loginDto.setUserName("A0U1w001");			// 用户账号
////		loginDto.setUserName("441778166@qq.com");	// 邮箱
////		loginDto.setUserName("15259165615");		// 手机
//		loginDto.setUserPwd("3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d");
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(loginDto)));
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.login(iqCloudInfoDto);
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//	}
//	
//	/*
//	 * 登出
//	 */
//	@Test
//	public void logout(){
//		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
//		accessTokenEntity.setAccessToken("1B63886211504F37B81C243DA343F730");
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenEntity)));
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.logout(iqCloudInfoDto);
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//	}
//	
//	/*
//	 * 获取用户实体
//	 */
//	@Test
//	public void selectUserEntityByAccessToken(){
//		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
//		accessTokenEntity.setAccessToken("DE7D626D4A55431BAA6280B5BF79579F");
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenEntity)));
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.selectUserEntityByAccessToken(iqCloudInfoDto);
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//	}
//	
//	/*
//	 * 根据用户名获取用户信息 
//	 */
//	@Test
//	public void selectUserEntityByUserName(){
//		IdDto idDto = new IdDto();
//		idDto.setId("A0U1w028");
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(idDto);
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto2 = iIqUsersFacade.selectUserEntityByUserName(iqCloudInfoDto);
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto2));
//	}
//	
//	/*
//	 * 获取用户状态
//	 */
//	@Test
//	public void selectUserStatus(){
//		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
//		accessTokenEntity.setAccessToken("DE7D626D4A55431BAA6280B5BF79579F");
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenEntity)));
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.selectUserStatus(iqCloudInfoDto);
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//	}
//	
//	/*
//	 * 获取用户头像
//	 */
//	@Test
//	public void selectUserHeadImage(){
//		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
//		accessTokenEntity.setAccessToken("DE7D626D4A55431BAA6280B5BF79579F");
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenEntity)));
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.selectUserHeadImage(iqCloudInfoDto);
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//	}
//	
//	/*
//	 * 获取用户注册时间
//	 */
//	@Test
//	public void selectUserRegDateTime(){
//		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
//		accessTokenEntity.setAccessToken("DE7D626D4A55431BAA6280B5BF79579F");
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenEntity)));
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.selectUserRegDateTime(iqCloudInfoDto);
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//	}
//	
//	/*
//	 * 获取学号、座位号
//	 */
//	@Test
//	public void selectUserStudentNumberAndSeatNumber(){
//		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
//		accessTokenEntity.setAccessToken("DE7D626D4A55431BAA6280B5BF79579F");
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenEntity)));
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.selectUserStudentNumberAndSeatNumber(iqCloudInfoDto);
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//	}
//	
//	/*
//	 * 获取用户个人说明
//	 */
//	@Test
//	public void selectUserInfo(){
//		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
//		accessTokenEntity.setAccessToken("DE7D626D4A55431BAA6280B5BF79579F");
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenEntity)));
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.selectUserInfo(iqCloudInfoDto);
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//	}
//	
//	/*
//	 * 获取用户登录名
//	 */
//	@Test
//	public void selectUserName(){
//		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
//		accessTokenEntity.setAccessToken("DE7D626D4A55431BAA6280B5BF79579F");
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(accessTokenEntity)));
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.selectUserName(iqCloudInfoDto);
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//	}
//	
//	/*
//	 * 获取用户属性名
//	 */
//	@Test
//	public void selectByUserIdsAndFields(){
//		System.out.println("selectByUserIdsAndFields");
//		List<String> userIds = new ArrayList<String>();
//		userIds.add("040053D12ABF4EDCB2E98B51759B1DAC");
//		userIds.add("050732C4E18F4D1F9463E1E442B90EE1");
//		
//		List<String> fields = new ArrayList<String>();
//		fields.add("user_name");
//		fields.add("real_name");
//		fields.add("seat_number");
//		
//		IdsAndAtrriParamDto userAtrriParamDto = new IdsAndAtrriParamDto();
//		userAtrriParamDto.setFields(fields);
//		userAtrriParamDto.setIds(userIds);
//		userAtrriParamDto.setSortField("seat_number");
//		userAtrriParamDto.setSortType(0);
//		
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(userAtrriParamDto)));
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.selectByUserIdsAndFields(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//	}
//	
//	/*
//	 * 通过参数获取用户分页信息
//	 */
//	@Test
//	public void selectPagingUserInfoByParam(){
//		System.out.println("selectPagingUserInfoByParam");
//		
//		PagingDto pagingDto = new PagingDto();
//		pagingDto.setPageItemCount(2);
//		pagingDto.setPageNum(1);
//		pagingDto.setParamString1("张");
//		
//		// 角色id
//		List<String> rolesList = new ArrayList<String>();
//		rolesList.add("FB96CB6C02F848128A3BA10C3C041A31");	// 老师
////		rolesList.add("565A7759190F420697DAA3B8B60D08A7");	// 学生
//		rolesList.add("97C3EB267169498297A07364E77B4600");	// 家长
//		pagingDto.setStringParamArray1(rolesList);
//		
//		// 角色类型
//		List<Integer> rolesTypeList = new ArrayList<Integer>();
////		rolesTypeList.add(3);		// 老师
//		rolesTypeList.add(4);		// 学生
////		rolesTypeList.add(5);		// 家长
//		pagingDto.setIntegerParamArray1(rolesTypeList);
//		
//		// 组织
//		List<String> orgsList = new ArrayList<String>();
//		orgsList.add("6196BA2B2CA34F25A008B101517CB6B3");	// 初二(1)班
////		orgsList.add("75E0FC3FF72241129824DE1C211E6641");	// 初二(2)班
//		pagingDto.setStringParamArray2(orgsList);
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonBody(JSONObject.parseObject(JSON.toJSONString(pagingDto)));
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.selectPagingUserInfoByParam(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//		
//	}
//	
////	/*
////	 * 更新用户状态
////	 */
////	@Test
////	public void updateUserStatus(){
////		System.out.println("updateUserStatus");
////		
////		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
////		accessTokenEntity.setAccessToken("0BAC6A49DD4E4ED3AB97116E42EC6B76");
////		
////		List<String> userIds = new ArrayList<String>();
////		userIds.add("A59092BD685944FBAAC528D34B5F17F4");
////		userIds.add("054C5AFEE4484B5F8DBDFF4B3EA35EA1");
////		
////		CommonDto commonDto = new CommonDto();
////		commonDto.setStringParamArray1(userIds);
////		commonDto.setParamInteger1(2);
////		
////		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
////		iqCloudInfoDto.setJsonHead(accessTokenEntity);
////		iqCloudInfoDto.setJsonBody(commonDto);
////		
////		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
////		
////		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.updateUserStatus(iqCloudInfoDto);
////		
////		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
////	}
//	
//	/*
//	 * 初始化密码
//	 */
//	@Test
//	public void updateInitUserPassword(){
//		System.out.println("updateInitUserPassword");
//		
//		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
//		accessTokenEntity.setAccessToken("27BE8C4E0A644C4EA6F9BDBECB098CAB");
//	
//		List<String> userIds = new ArrayList<String>();
//		userIds.add("A59092BD685944FBAAC528D34B5F17F4");
//		userIds.add("054C5AFEE4484B5F8DBDFF4B3EA35EA1");
//		
//		CommonDto commonDto = new CommonDto();
//		commonDto.setStringParamArray1(userIds);
//		commonDto.setParamString1("e10adc3949ba59abbe56e057f20f883e");
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonHead(accessTokenEntity);
//		iqCloudInfoDto.setJsonBody(commonDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.updateInitUserPassword(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//	}
//	
//	/*
//	 * 添加一个用户
//	 */
//	@Test
//	public void insertOrUpdateOneUser(){
//		System.out.println("insertOrUpdateOneUser");
//		/*
//		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
//		accessTokenEntity.setAccessToken("DB07954F6D93409BB6481C8F7FAF53F2");
//		
//		AddOrEditOneUserDto addOrEditOneUserDto = new AddOrEditOneUserDto();
//		addOrEditOneUserDto.setIsAddUser(true);
//		
//		// 用户实体
//		IqUsers iqUsers = new IqUsers();
//		iqUsers.setRealName("张三");
//		iqUsers.setUserName("A0U1w10050");
//		iqUsers.setPassword("3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d");
//		iqUsers.setRoleId("565A7759190F420697DAA3B8B60D08A7");   // 学生
//		iqUsers.setAuthStatus(1);
//		iqUsers.setUserStatus(1);
//		addOrEditOneUserDto.setIqUsers(iqUsers);
//		
//		// 基本角色 --- 老师
//		IqRole iqRole = new IqRole();
//		iqRole.setId("FB96CB6C02F848128A3BA10C3C041A31");
//		addOrEditOneUserDto.setIqBaseRole(iqRole);
//		
//		// 相关角色
//		List<IqRole> iqRoles = new ArrayList<IqRole>();
//		// 机构管理员
//		IqRole iqRole2 = new IqRole();
//		iqRole2.setId("4E87865D592B405CA8CAA26B7EE0B45B");
//		iqRoles.add(iqRole2);
//		// 班主任
//		IqRole iqRole3 = new IqRole();
//		iqRole3.setId("FEE45397B37A42249E5760E0AE7D12C7");
//		iqRoles.add(iqRole3);
//		addOrEditOneUserDto.setIqRoles(iqRoles);
//		
//		// 组织实体
//		List<IqOrganization> iqOrganizations = new ArrayList<IqOrganization>();
//		IqOrganization iqOrganization1 = new IqOrganization();
//		// 初二年(1)班
//		iqOrganization1.setId("6196BA2B2CA34F25A008B101517CB6B3");
//		iqOrganizations.add(iqOrganization1);
//		IqOrganization iqOrganization2 = new IqOrganization();
//		// 初二年(2)班
//		iqOrganization2.setId("75E0FC3FF72241129824DE1C211E6641");
//		iqOrganizations.add(iqOrganization2);
//		addOrEditOneUserDto.setIqOrganizations(iqOrganizations);
//		
//		// 学科关联
//		List<IqSubject> iqSubjects = new ArrayList<IqSubject>();
//		// 语文
//		IqSubject iqSubject1 = new IqSubject();
//		iqSubject1.setId("0FF5CC503C1611E5BC3A001D0998CE9B");
//		iqSubjects.add(iqSubject1);
//		// 数学
//		IqSubject iqSubject2 = new IqSubject();
//		iqSubject2.setId("1E87421E3C1611E5BC3A001D0998CE9B");
//		iqSubjects.add(iqSubject2);
//		
//		addOrEditOneUserDto.setIqSubjects(iqSubjects);
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonHead(accessTokenEntity);
//		iqCloudInfoDto.setJsonBody(addOrEditOneUserDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.insertOrUpdateOneUser(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//		*/
//		
//		
//		// 编辑
//		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
//		accessTokenEntity.setAccessToken("DB07954F6D93409BB6481C8F7FAF53F2");
//		
//		AddOrEditOneUserDto addOrEditOneUserDto = new AddOrEditOneUserDto();
//		addOrEditOneUserDto.setIsAddUser(false);
//		
//		// 用户实体
//		IqUsers iqUsers = new IqUsers();
//		iqUsers.setId("E7B6479FC63E46F99870C329B56838F7");
//		iqUsers.setRealName("李四");
//		iqUsers.setRoleId("565A7759190F420697DAA3B8B60D08A7");
//		addOrEditOneUserDto.setIqUsers(iqUsers);
//		
//		// 基本角色 --- 学生
//		IqRole iqRole = new IqRole();
//		iqRole.setId("565A7759190F420697DAA3B8B60D08A7");
//		addOrEditOneUserDto.setIqBaseRole(iqRole);
//		
//		// 相关角色
//		List<IqRole> iqRoles = new ArrayList<IqRole>();
//		// 班长
//		IqRole iqRole2 = new IqRole();
//		iqRole2.setId("49F2FA2A9686425789E06F2A84B814F3");
//		iqRoles.add(iqRole2);
//		
//		// 组织实体
//		List<IqOrganization> iqOrganizations = new ArrayList<IqOrganization>();
//		IqOrganization iqOrganization1 = new IqOrganization();
//		// 初一年(1)班
//		iqOrganization1.setId("E329E652807F43668B69D92E210D799D");
//		iqOrganizations.add(iqOrganization1);
//		addOrEditOneUserDto.setIqOrganizations(iqOrganizations);
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonHead(accessTokenEntity);
//		iqCloudInfoDto.setJsonBody(addOrEditOneUserDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.insertOrUpdateOneUser(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//	}
//	
//	/*
//	 * 根据特权码产生新用户id列表
//	 */
//	@Test
//	public void getNewUserNameByAccessToken(){
//		System.out.println("getNewUserNameByAccessToken");
//		
//		AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
//		accessTokenEntity.setAccessToken("3E4E7CD496E54D9188B7F83FB0CCE983");
//		
//		CommonDto commonDto = new CommonDto();
//		commonDto.setParamBoolean1(true);
//		commonDto.setParamInteger1(10);
//		
//		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
//		iqCloudInfoDto.setJsonHead(accessTokenEntity);
//		iqCloudInfoDto.setJsonBody(commonDto);
//		
//		System.out.println("request---> " + JSON.toJSONString(iqCloudInfoDto));
//		
//		IQCloudInfoDto iqCloudInfoDto1 = iIqUsersFacade.getNewUserNameByAccessToken(iqCloudInfoDto);
//		
//		System.out.println("response---> " + JSON.toJSONString(iqCloudInfoDto1));
//	}
//	
//	
//}


