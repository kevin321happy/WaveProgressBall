package com.fips.huashun.common;

import static com.fips.huashun.common.IPConstans.IP1;
import static com.fips.huashun.common.IPConstans.PHP;

import android.graphics.Typeface;

/**
 * 功能：一些常量 Created by Administrator on 2016/8/16.
 *
 * @author 张柳 创建时间：2016年8月16日14:02:59
 */
public class Constants {

  //正式
  private String IP = "http://admin.52qmct.com";

  /* * ---*******************************新服务器IP《正式》************************************---*/
  // public static final String URL = "http://http://admin.52qmct.com/:8080/hsWebInterface";
  public static final String URL = "http://admin.52qmct.com:8080/hsWebInterface";
  public static final String IMG_URL = "";
  public static final String PHP_URL = "http://admin.52qmct.com/Api/vote";
  public static final String IMG = "";
  //public static final String H5_URL = "http://172.16.3.14:8080/H5/app/template/";
  public static final String H5_URL = "http://admin.52qmct.com/H5/app/template/";
/* * ---*******************************新服务器IP《正式》************************************---*/
<<<<<<< HEAD

=======
>>>>>>> f8c163e9f9b16c6f8465981156b159495b4df8c8
  /**
   * 测试服务器
   */
//  public static final String URL = "http://172.16.3.14:8080/hsWebInterface";
//  public static final String IMG_URL = "";
//  public static final String PHP_URL = "http://172.16.3.14/Api/vote";
//  public static final String IMG = "";
//  public static final String H5_URL = "http://172.16.3.14:8080/H5/app/template/";//H5
  /**
   * 本地服务器接口
   */
  //域名地址
  public static final String DOMAIN_NAME_URL = "http://v1.52qmct.com/";
  //OSS图片上传的目录
  public static final String OSS_PIC_URL = "uploadima/2017/";
  //活动列表
  public static final String LOCAL_ACTIVITYINFOLIST =
      IP1 + "EnActivityApp/activityinfoList";
  //活动详情
  public static final String LOCAL_GET_DETAIL = IP1 + "EnActivityApp/get_detail";
  //活动报名
  public static final String LOCAL_SIGN_UP = IP1 + "EnActivityApp/sign_up";
  //已报名人员
  public static final String LOCAL_SIGN_UP_LIST = IP1 + "EnActivityApp/sign_up_list";
  //活動簽到信息
  public static final String LOCAL_SIGNINFO = IP1 + "EnActivityApp/signinfo";
  //签到
  public static final String LOCAL_SIGN = IP1 + "/EnActivityApp/sign";
  //发布留言
  public static final String LOCAL_ADD_WORDS = IP1 + "Spacewords/add_words";
  //获取留言墙/反馈室信息
  public static final String LOCAL_GET_WORDS = IP1 + "Spacewords/get_words";
  //留言墙反馈室点赞
  public static final String LOCAL_GOODS = IP1 + "Spacewords/goods";
  //互动空间活动公告
  public static final String LOCAL_GET_PROCLAMATION =
      IP1 + "Spacewords/get_proclamation";
  //反馈评分
  public static final String LOCAL_MARK = IP1 + "Spacewords/mark";
  //管理员评分
  public static final String LOCAL_LECTORMARK = IP1 + "Spacewords/lectormark";
  //删除留言/反馈
  public static final String LOCAL_DEL_WORDS = IP1 + "Spacewords/del_words";
  //详细留言列表
  public static final String LOCAL_GET_REPLY = IP1 + "Spacewords/get_reply";
  //添加回复
  public static final String LOCAL_ADD_REPLY = IP1 + "Spacewords/add_reply";
  //删除回复
  public static final String LOCAL_DEL_REPLY = IP1 + "Spacewords/del_reply";
  //在线课程
  public static final String LOCAL_ONLINE_COURSE_LIST =
      IP1 + "EnActivityApp/online_course_list";
  //PK榜
  public static final String LOCAL_RANKLIST =
      IP1 + "/PkList/rankList";
  //Pk榜(点赞)
  public static final String LOCAL_SETPRISE = IP1 + "/PkList/setPrise";
  //设置用户设备ID
  public static final String LOCAL_SET_REGISTRATIONID =
      IP1 + "Api/set_registrationid";
  //清除设备Id
  public static final String LOCAL_RE_REGISTRATIONID =
      IP1 + "Api/re_registrationid";
  //调研列表
  public static final String LOCAL_SURVEY_LIST =
      IP1 + "EnActivityApp/survey_list";
  /**
   * 正式服接口
   */
    /*企业H5*/
  // public static final String H5_URL = Constants.h5_URL + "/H5/app/template/";
  /** 我自己的服务器IP */
  // public static final String URL ="http://172.16.90.35:8080/hsWebInterface";

  /**
   * 登录URL
   */
//  public static final String LOGIN_URL = URL + "/login";
  /**
   * 注册时获取验证码URL
   */
//  public static final String REGISTER_VERIFICATIONCODE_URL = URL + "/notobtainvalidate";
  /**
   * 注册URL
   */
//  public static final String REGISTER_URL = URL + "/regist";
  /**
   * 忘记密码时获取验证码URL
   */
//  public static final String FORGOTPWD_VERIFICATIONCODE_URL = URL + "/yesbtainvalidate";
  /**
   * 忘记密码URL
   */
//  public static final String FORGOTPWD_URL = URL + "/forgotPassword";
  /**
   * 修改密码URL
   */
  public static final String UPDATEPWD_URL = URL + "/modifyPassword";
  /**
   * 首页
   */
//  public static final String HOME_URL = URL + "/showmyStudyCentre";
  /**
   * 所有讲师
   */
  public static final String LECTURER_ALL_URL = URL + "/AllTeacherPage";
  /**
   * 所有讲师 搜索
   */
  public static final String LECTURER_ALL_SEARCH_URL = URL + "/serachByCondition";
  /**
   * 讲师详情
   */
  public static final String LECTURER_INFO_URL = URL + "/serachTeacherinfo";
  /**
   * 所有课程
   */
  public static final String COURSE_ALL_URL = URL + "/checkall";
  /**
   * 购买课程
   */
  public static final String BUY_0COURSE_URL = URL + "/enterCourseBuy";
  /**
   * 活动专区
   */
  public static final String ACTIVITY_ALL_URL = URL + "/queryAllActivity";
  /**
   * 活动详情
   */
  public static final String ACTIVITY_INFO_URL = URL + "/queryActivityInfoByid";
  /**
   * 活动详情 用户当前魔豆查询
   */
  public static final String SEARCH_BEANPOINT_URL = URL + "/applyActivity";
  /**
   * 活动详情报名
   */
  public static final String ACTIVITY_JOIN_URL = URL + "/addActivitysign";
  /**
   * 是否有企业
   */
  public static final String CHECK_ENTERPRISE_URL = URL + "/checkenterpriseByid";
  /**
   * 根据企业名称模糊搜索企业
   */
  public static final String SEARCH_ENTERPRISE_URL = URL + "/queryentinfoBylikename";
  /**
   * 根据企业名称验证企业是否存在
   */
  public static final String VERIFICATION_ENTERPRISE_URL = URL + "/checkenterpriseByname";
  /**
   * 根据企业名称精确搜索企业
   */
  public static final String SEARCH_ENTERPRISE_ONE_URL = URL + "/queryentinfoBylikename";
  /**
   * 职工进行企业申请验证
   */
  public static final String EMPLOYEE_ENTERPRISE_ONE_URL = URL + "/submitApplytoEnterprise";
//  /**
//   * 企业中  更多企业课程
//   */
//  public static final String ENTERPRISE_MORE_COURSE = URL + "/checkMoreEntcourse";

  /**
   * 企业中 PK榜个人 学习
   */
  public static final String PERSONALSTUDYKLIST_URL = URL + "/personalStudyPkList";
  /**
   * 企业中 PK榜个人 积分
   */
  public static final String PERSONALCODEPKLIST_URL = URL + "/personalCodePkList";
  /**
   * 企业中 PK榜个人 参与项目数
   */
  public static final String PERSONALJOINACTSPKLIST_URL = URL + "/personalJoinActsPkList";
  /**
   * 企业中 PK榜个人 通过数考试
   */
  public static final String PERSONALPASSEXAMPKLIST_URL = URL + "/personalPassExamPkList";
  /**
   * 企业中 PK榜个人 考试时间
   */
  public static final String PERSONALEXAMTIMEPKLIST_URL = URL + "/personalExamTimePkList";
  /**
   * 企业中 PK榜部门 学习
   */
//  public static final String PARTMENTSTUDYPKLIST_URL = URL + "/partmentStudyPkList";
  /**
   * 企业中 PK榜部门 积分
   */
  public static final String PARTMENTCODEPKLIST_URL = URL + "/partmentCodePkList";
  /**
   * 企业中 PK榜部门 参与项目数
   */
  public static final String PARTMENTJOINACTSPKLIST_URL = URL + "/partmentJoinActsPkList";
  /**
   * 企业中 PK榜部门 考试通过数
   */
  public static final String PARTMENTPASSEXAMPKLIST_URL = URL + "/partmentPassExamPkList";
  /**
   * 企业中 PK榜部门 考试时间
   */
  public static final String PARTMENTEXAMTIMEPKLIST_URL = URL + "/partmentExamTimePkList";
  /**
   * 企业中 PK榜企业 学习
   */
//  public static final String ENSTUDYPKLIST_URL = URL + "/enStudyPkList";
  /**
   * 企业中 PK榜企业 积分
   */
  public static final String ENTERPRICECODELIST_URL = URL + "/enterpriceCodeList";
  /**
   * 企业中 PK榜企业 参与项目数
   */
  public static final String ENJOINACTSPKLIST_URL = URL + "/enJoinActsPkList";
  /**
   * 企业中 PK榜企业 考试通过数
   */
  public static final String ENPASSEXAMPKLIST_URL = URL + "/enPassExamPkList";
  /**
   * 企业中 PK榜企业 考试时间
   */
  public static final String ENEXAMTIMEPKLIST_URL = URL + "/enExamTimePkList";
  /**
   * 企业中 企业公告
   */
  public static final String ENTERPRISE_NOTICE_URL = URL + "/checkentnotice";
  /**
   * 企业中 企业天地
   */
  public static final String ENTERPRISE_ACTIVITY_URL = URL + "/showentProject";
  /**
   * 企业中 企业活动详情
   */
  public static final String ENTERPRISE_ACTIVITYINFO_URL = URL + "/showentProjectinfo";
  /**
   * 企业中 企业活动详情-通知
   */
  public static final String ENTERPRISE_ACTIVITY_NOTICE_URL = URL + "/showActivityinfoByaid";
  /**
   * 企业中 企业活动详情-签到
   */
  public static final String ENTERPRISE_ACTIVITY_SIGN_URL = URL + "/activityReg";
  /**
   * 企业中 企业活动详情-课程
   */
  public static final String ENTERPRISE_ACTIVITY_COURSE_URL = URL + "/showentAvtivityCourse";
  /**
   * 企业中 任务-投票列表
   */
  public static final String GET_PROJECTVOTELIST_URL = URL + "/getProjectVoteList";
  /**
   * 企业中 任务-投票详情（未投票）
   */
  public static final String GET_PROJECTVOTEDETAIL_URL = URL + "/getProjectVotedetail";
  /**
   * 企业中 任务-开始投票
   */
  public static final String SUBMIT_PROJECTVOTE_URL = URL + "/submitProjectVote";
  /**
   * 企业中 任务-投票详情（投票结果）
   */
  public static final String GET_PROJECTVOTERESULT_URL = URL + "/getProjectVoteResult";
  /**
   * 企业活动PK
   */
  public static final String ENTERPRISE_ACTPK_URL = URL + "/enterpriseActPkList";
  /**
   * 我的书房
   */
//  public static final String STUDY_URL = URL + "/checkBookhomeInfo";
  /**
   * 搜索历史
   */
  public static final String HISTORY_URL = URL + "/serachHistory";
  /**
   * 裘马
   */
//  public static final String SHOWSELFINFO_URL = URL + "/ShowSelfInfo";
  /**
   * 头像上传
   */
  public static final String HEAD_URL = URL + "/uploadUserHead";
  /**
   * 更新用户信息
   */
  public static final String UPDATE_USERINFO_URL = URL + "/updateSelfInfo";
  /**
   * 裘马 中我的消息（系统消息）
   */
  public static final String MYSYSTEMMSG_URL = URL + "/mySystemMsg";
  /**
   * 裘马 中我的消息（课程消息）
   */
  public static final String MYCOURSEMSG_URL = URL + "/myCourseMsg";
  /**
   * 裘马 中我的消息（活动消息）
   */
  public static final String MYACTIVITYMSG_URL = URL + "/myActivityMsg";
  /**
   * 裘马 中我的消息设置为已读
   */
  public static final String HADCHECKMSG_URL = URL + "/hadcheckmsg";
  /**
   * 裘马 中我的课程（已完成课程）
   */
  public static final String SHOWMYHADCOURSE_URL = URL + "/showMyhadCourse";
  /**
   * 裘马 中我的课程（课程目录）  /Checkcoursection
   */
  /**
   * 裘马 中我的课程（未完成课程）
   */
  public static final String SHOWMYNOCOURSE_URL = URL + "/showMynoCourse";
  /**
   * 裘马 中我的魔豆
   */
  public static final String SHOWMYBEANS_URL = URL + "/showMybeans";
  /**
   * 裘马 中我的积分（详情）
   */
  public static final String INTEGRALINFO_URL = URL + "/userIntegralInfo";
  /**
   * 裘马 中我的积分任务
   */
  public static final String INTEGRAL_TASK_URL = URL + "/gettaskList";
//    public static final String H5_URL = Constants.IMG_URL+"/H5/app/template/";
  /**
   * 发表课程评论
   */
  public static final String PUBLISH_COURSE_EVALUATE_URL = URL + "/addCourserateByuser";
  /**
   * 魔豆充值
   */
  public static final String ADDBEANS_URL = URL + "/addBeans";
  /**
   * 我的积分任务-签到
   */
  public static final String SIGN_GETINTEGRAL_URL = URL + "/showUserSign";
  /**
   * 意见反馈 图片上传
   */
  public static final String FEEDBACK_IMG_URL = URL + "/userResultFeedback";
  /**
   * 意见反馈 反馈内容提交
   */
  public static final String FEEDBACK_CONTENT_URL = URL + "/userResultFeedbackContent";
  /**
   * 我的魔豆明细
   */
  public static final String MY_BEANS_DETAIL_URL = URL + "/showUserbeansInfo";
  /**
   * 我的勋章
   */
  public static final String MY_MEDA_URL = URL + "/showMymedalinfo";
  /**
   * 添加学习记录
   * 改造接口
   */
  public static final String ADD_STUDYINFO_URL = IP1 + "/addstudyinfo";
  /**
   * 考试任务-通过的考试记录
   */
  public static final String TASK_EXAM_PASS_URL = URL + "/showMyhadpassCourse";
  /**
   * 考试任务-需要通过的考试列表
   */
  public static final String TASK_EXAM_NEED_URL = URL + "/showMynopassCourse";
  //字体
  public static Typeface TF_HKHB_FONT = null;
  public static Typeface TC_HKWW_FONT = null;
  public static Typeface TF_HANYI_FONT = null;
<<<<<<< HEAD
=======

>>>>>>> f8c163e9f9b16c6f8465981156b159495b4df8c8
  /** 接口改造 **/
  /**
   * 企业中 我的课程
   */
  public static final String ENTERPRISE_MUSTMYENTERPRISE_URL =
      IPConstans.IP1 + "Course/queryEnterpriseMyCourse";

  /**
   * 课程详情
   */
  public static final String CHECKCOURSEBASE = IPConstans.IP1 + "Course/Checkcoursebase";
  /**
   * 课程目录
   */
  public static final String CHECKCOURSECTION = IPConstans.IP1 + "Course/Checkcoursection";
  /**
   * 获取课程的所有评论get
   */
  public static final String QUERYALLCOMMENTSBYCOURSEID =
      IPConstans.IP1 + "Course/queryAllCommentsByCourseId";
  /**
   * 推荐课程
   */
  public static final String GETRECOMMENDCOURSE = IPConstans.IP1 + "Course/getRecommendCourse";
  /**
   * 学习时长
   */
  public static final String ADDSTUINFO = IPConstans.IP1 + "Course/addstuinfo";
  /**
   * 学习完成
   */
  public static String ADDSTUDYINFO = IPConstans.IP1 + "Course/addstudyinfo";
  /**
   * 首页书房
   */
  public static final String HOME_URL = IPConstans.IP1 + "StudyCenter/appIndex";
  /**
   * 企业界面
   */
  public static final String STUDY_URL = IPConstans.IP1 + "StudyCenter/checkBookhomeInfo";
  /**
   * 裘马界面
   */
  public static final String SHOWSELFINFO_URL = IPConstans.IP1 + "StudyCenter/showSelfInfo";
  /**
   * 企业更多课程
   */
  public static final String ENTERPRISE_MORE_COURSE = IP1 + "Course/queryMoreEntcourse";
  /**
   * 登录接口
   */
<<<<<<< HEAD
<<<<<<< HEAD
  public static final String LOGIN_URL =IP1+ "LoginCommon/login";
=======
  public static final String LOGIN_URL = IP1 + "LoginCommon/login";
>>>>>>> dev
  /**
   * 忘记密码获取验证码
   */
  public static final String FORGOTPWD_VERIFICATIONCODE_URL = IP1 + "LoginCommon/yesbtainvalidate";
  /**
   * 注册获取验证码
   */
  public static final String REGISTER_VERIFICATIONCODE_URL = IP1 + "LoginCommon/notobtainvalidate";
  /**
   * 注册的URl
   */
  public static final String REGISTER_URL = IP1 + "LoginCommon/regist";
  /**
   * 忘记密码重置
   */
<<<<<<< HEAD
  public static final String FORGOTPWD_URL = IP1+"LoginCommon/forgotPassword";
=======
  public static final String LOGIN_URL = "http://admin.52qmct.com/Api/LoginCommon/login";
  /**
   * 忘记密码获取验证码
   */
  public static final String FORGOTPWD_VERIFICATIONCODE_URL = "http://admin.52qmct.com/Api/LoginCommon/yesbtainvalidate";
  /**
   * 注册获取验证码
   */
  public static final String REGISTER_VERIFICATIONCODE_URL = "http://admin.52qmct.com/Api/LoginCommon/notobtainvalidate";
  /**
   * 注册的URl
   */
  public static final String REGISTER_URL = "http://admin.52qmct.com/Api/LoginCommon/regist";
  /**
   * 忘记密码重置
   */
  public static final String FORGOTPWD_URL = "http://admin.52qmct.com/Api/LoginCommon/forgotPassword";

>>>>>>> f8c163e9f9b16c6f8465981156b159495b4df8c8
=======
  public static final String FORGOTPWD_URL = IP1 + "LoginCommon/forgotPassword";

  /**
   * 企业中 PK榜部门 学习
   */
  public static final String PARTMENTSTUDYPKLIST_URL = IP1 + "Course/partmentStudyPkList";
  /**
   * 企业中 PK榜企业 学习
   */
  public static final String ENSTUDYPKLIST_URL = IP1 + "Course/enStudyPkList";
  /**
   * 企业组织架构
   */
  public static final String GETORGANIZATIONLIST_URL = PHP + "organ/getOrganizationList";
>>>>>>> dev
}
