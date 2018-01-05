package com.iqcloud.common.consts;

/*
 * redis常量
 */
public class ConstantRedis {

	/*
	 * session根节点路径
	 * 
	 * key：IQCloud:Session:cookieValue:accessToken:userId value: UserBaseInfoDto
	 */
	public static final String Session_Root = "IQCloud:Session";

	/*
	 * 权限根节点路径
	 * 
	 * key： IQCloud:IqRightInfo:权限编码(code):ObjectRightId value: IqRightInfo
	 */
	public static final String RightInfo_Root = "IQCloud:RightInfo";

	/*
	 * 基本角色页面网址表 key: IQCloud:BasicRoleUrlRightInfo:权限编码(code:网址):ObjectRightId
	 */
	public static final String BasicRoleUrlRightInfo_Root = "IQCloud:BasicRoleUrlRightInfo";

	/*
	 * 目录树 根节点路径
	 * 
	 * key: IQCloud:ResCatalog:版本id(catId):catId value： 节点json值
	 */
	public static final String ResCata_Root = "IQCloud:ResCatalog";

	/*
	 * 数字班牌根节点路径
	 * 
	 * key: IQCloud:ClassBrand... value: ...
	 */
	public static final String ClassBrand_Root = "IQCloud:ClassBrand";

	/*
	 * 微信企业号根节点
	 */
	public static final String Weixin_Enterprise_Root = "IQCloud:WeixinEnterprise";

	/*
	 * 编辑试卷时，试卷题目信息缓存根节点
	 */
	public static final String Edit_Paper_QuestionInfo_Root = "IQCloud:EditPaperQuestionInfo";

	/*
	 * oppenoffice端口号信息
	 */
	public static final String OppenOffice_Port = "IQCloud:OppenOfficePort";

	/*
	 * 版本树ID
	 */
	public static final String ResCata_Ids = "IQCloud:ResCataIds";

	/*
	 * 公有资源凭证
	 */
	public static final String Visit_Publish_Resource_Voucher = "IQCloud:VisitPublishResourceVoucher";

	/*
	 * 版本及版本下级目录对应资源总数
	 */
	public static final String Version_Goods_Count = "IQCloud:VersionGoodsCount";

	/*
	 * 学段、课程关联的目录树ID
	 */
	public static final String GC_ResCata_Ids = "IQCloud:GCResCataIds";

	/*
	 * 资源统计报表
	 */
	public static final String Res_Goods_Report = "IQCloud:ResGoodsReport";

	/*
	 * 试卷统计信息
	 */
	public static final String Class_Paper_Static = "IQCloud:ClassPaperStatic";

	/*
	 * 多语言根节点
	 */
	public static final String Language_Root = "IQCloud:Language";

	/*
	 * 权限功能语言节点
	 */
	public static final String RightInfoFunctionCode = "/IQCloudManageCenter/resources/V7.0.0/html/framework/integratedManage/rightList.action";

	/*
	 * 资源类型语言节点
	 */
	public static final String ResourcesGoodTypeCode = "IQCloudResources-GoodsType";

	/*
	 * 公共常用语言配置
	 */
	public static final String CommonLanguageCommon = "/IQCommonLanguage-Common";

	/*
	 * 云盘系统自定义文件
	 */
	public static final String DiskFileSystemDefinedInfoCode = "IQCloudDisk-SystemDefinedInfo";

	/*
	 * 除了学生外的管理员和老师信息（以map的形式存储用户ID用户名）
	 */
	public static final String admin_teacher_user_Info = "IQCloud:AdminTeacherUserInfo";

	/*
	 * 存储某个用户正在检索的记录
	 */
	public static final String search_user_recode = "IQCloud:SearchUserRecode";

	/*
	 * 访问首页信息
	 */
	public static final String HomePageInfo_Root = "IQCloud:HomePageInfo";

	/*
	 * 考试相关人员
	 */
	public static final String Exam_Member = "IQCloud:ExamMember";

	/*
	 * 答题卡内容
	 */
	public static final String Answer_Sheet = "IQCloud:AnswerSheet";

	/*
	 * 分布式锁根节点
	 */
	public static final String Lock_Root = "IQCloud:Lock";

	/*
	 * 扫描记录Id
	 */
	public static final String Scan_Ids = "IQCloud:ScanIds";
	
	/*
	 * 开始阅卷教师Id
	 */
	public static final String Marking_Teacher_Id = "IQCloud:MarkingTeacherId";
}
