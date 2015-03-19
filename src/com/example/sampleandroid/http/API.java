package com.example.sampleandroid.http;

import java.util.HashMap;

import android.os.Message;

import com.example.sampleandroid.mode.BaseEntity;
import com.google.gson.reflect.TypeToken;

public class API {
	private static final String TAG = "API";
	/**
	 * 请求成功
	 */
	public static final int REQUEST_SUCCESS = 1;
	/**
	 * 请求失败
	 */
	public static final int REQUEST_FAIL = 2;
	/**
	 * 请求开始
	 */
	public static final int REQUEST_BEGIN = 3;
	/**
	 * 没有网络
	 */
	public static final int REQUEST_NO_NETWORK = 4;

	private static final String SERVER_HOST = "http://182.92.98.79:8081/m.api?";

	/**
	 * 版本检测
	 */
	private static final String CHECKVERSION_CODE = "00001";

	/**
	 * 注册
	 */
	private static final String REG = "20001";

	/**
	 * 激活
	 */
	private static final String ACTIVE_USER = "20002";

	/**
	 * 登录
	 */
	private static final String LOGIN = "20003";

	/**
	 * 修改密码
	 */
	private static final String CHANGPWD = "20004";

	/**
	 * 用户反馈
	 */
	private static final String FEEDBACK = "20005";

	/**
	 * 用户找回密码
	 */
	private static final String FORGOT = "20007";

	/**
	 * 修改用户名
	 */
	private static final String USERINFO_CHANG = "20008";

	/**
	 * 上传头像
	 */
	private static final String USERHEAD_CHANGE = "20009";

	/**
	 * 用户详情
	 */
	private static final String USER_DETAIL = "20010";

	// ** 版本检测
	public static void checkVersion(Message msg) {

		java.lang.reflect.Type type = new TypeToken<BaseEntity>() {
		}.getType();
		HashMap<String, String> maps = new HashMap<String, String>();
		maps.put("_mt", CHECKVERSION_CODE);
//		excuteHttpGet(msg, "checkVersion", BaseEntity.class, type, maps);
	}
	/*	

	*//**
	 * 注册获取验证码
	 * 
	 * <pre>
	 * 应用参数	可否为空	参数名称	说明
	 * phone	否	手机号码	
	 * province	是	gprs省	可以为空
	 * city	是	gprs城市	可以为空
	 * area	是	gprs区	可以为空
	 * addr	是	Gprs信息	可以为空
	 * 
	 * </pre>
	 * 
	 */
	/*
	 * public static void registerGetCode(Message msg, String phone, String
	 * province, String city, String area, String addr) {
	 * 
	 * java.lang.reflect.Type type = new TypeToken<BaseEntity>() { }.getType();
	 * HashMap<String, String> hashmap = new HashMap<String, String>();
	 * hashmap.put("_mt", REG); hashmap.put("phone", phone);
	 * hashmap.put("province", province); hashmap.put("city", city);
	 * hashmap.put("area", area); hashmap.put("addr", addr);
	 * 
	 * excuteHttpGet(msg, "reg", BaseEntity.class, type, hashmap); }
	 * 
	 * public static void getVerificationCode(Message msg, String phone) {
	 * 
	 * java.lang.reflect.Type type = new TypeToken<BaseEntity>() { }.getType();
	 * HashMap<String, String> hashmap = new HashMap<String, String>();
	 * hashmap.put("_mt", FORGOT); hashmap.put("phone", phone);
	 * 
	 * excuteHttpGet(msg, "getVerificationCode", BaseEntity.class, type,
	 * hashmap); }
	 * 
	 * public static void login(Message msg, String phone, String pass) {
	 * 
	 * java.lang.reflect.Type type = new TypeToken<BaseEntity<UserWithToken>>()
	 * { }.getType(); HashMap<String, String> maps = new HashMap<String,
	 * String>(); maps.put("_mt", LOGIN); maps.put("phone", phone);
	 * maps.put("password", CommonUtils.Md5(pass)); excuteHttpGet(msg, "login",
	 * BaseEntity.class, type, maps); }
	 *//**
	 * 激活
	 * 
	 * @param code
	 * @param phone
	 * @param handler
	 */
	/*
	 * public static void activeUser(String code, String phone, String password,
	 * Message msg) {
	 * 
	 * java.lang.reflect.Type type = new TypeToken<BaseEntity<UserWithToken>>()
	 * { }.getType(); HashMap<String, String> maps = new HashMap<String,
	 * String>(); maps.put("_mt", ACTIVE_USER); maps.put("phone", phone);
	 * maps.put("password", CommonUtils.Md5(password)); maps.put("activeCode",
	 * code); excuteHttpGet(msg, "activeUser", UserWithToken.class, type, maps);
	 * }
	 *//**
	 * 修改密码
	 */
	/*
	 * public static void changePwd(Message msg, String phone, String oldpwd,
	 * String newpwd) { java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity>() { }.getType(); HashMap<String, String> maps = new
	 * HashMap<String, String>(); maps.put("_mt", CHANGPWD); maps.put("phone",
	 * phone); maps.put("password1", CommonUtils.Md5(oldpwd));
	 * maps.put("password2", CommonUtils.Md5(newpwd)); excuteHttpGet(msg,
	 * "changePwd", BaseEntity.class, type, maps); }
	 *//**
	 * 用户反馈
	 */
	/*
	 * public static <T> void userFeedBack(final Message msg, final String
	 * feedback) { java.lang.reflect.Type type = new TypeToken<BaseEntity>() {
	 * }.getType(); HashMap<String, String> maps = new HashMap<String,
	 * String>(); maps.put("_mt", FEEDBACK);
	 * 
	 * HashMap<String, String> postData = new HashMap<String, String>();
	 * postData.put("feedBack", Uri.encode(feedback)); excuteHttpPost(msg,
	 * "userFeedBack", BaseEntity.class, type, maps, postData); }
	 *//**
	 * 条件搜索
	 * 
	 * @param msg
	 * @param city
	 * @param content
	 */
	/*
	 * public static void search(Message msg, String content, String page) {
	 * java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<ProductJsonParser>>() { }.getType(); HashMap<String,
	 * String> maps = new HashMap<String, String>(); maps.put("_mt", SEARCH);
	 * HashMap<String, String> postData = new HashMap<String, String>();
	 * postData.put("q", content); postData.put("page", page);
	 * excuteHttpPost(msg, "search", BaseEntity.class, type, maps, postData); }
	 *//**
	 * 修改用户信息
	 */
	/*
	 * public static void changeUserInfo(Message msg, User userInfo) { if
	 * (userInfo == null) { L.v(TAG, "changeUserInfo", "new_userInfo is null");
	 * return; } java.lang.reflect.Type type = new TypeToken<BaseEntity>() {
	 * }.getType(); HashMap<String, String> maps = new HashMap<String,
	 * String>(); maps.put("_mt", USERINFO_CHANG); HashMap<String, String>
	 * postData = new HashMap<String, String>(); if
	 * (!TextUtils.isEmpty(userInfo.nick)) { maps.put("nick", userInfo.nick); }
	 * if (!TextUtils.isEmpty(userInfo.gender)) { postData.put("gender",
	 * userInfo.gender); // if (userInfo.gender.equals("男")) { //
	 * postData.put("gender", "1"); // } else if (userInfo.gender.equals("女")) {
	 * // postData.put("gender", "2"); // } } if
	 * (!TextUtils.isEmpty(userInfo.address)) { postData.put("address",
	 * userInfo.address); } excuteHttpPost(msg, "changeUserInfo",
	 * BaseEntity.class, type, maps, postData); // excuteHttpGet(msg,
	 * "changeUserInfo", BaseEntity.class, type, maps);
	 * 
	 * }
	 *//**
	 * 修改用户头像
	 * 
	 * @param fileFormat
	 *            后缀
	 */
	/*
	 * public static void changeUserHead(Message msg, String base64Pic, String
	 * fileFormat) { java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<String>>() { }.getType(); HashMap<String, String>
	 * maps = new HashMap<String, String>(); maps.put("_mt", USERHEAD_CHANGE);
	 * HashMap<String, String> postData = new HashMap<String, String>();
	 * postData.put("subfix", fileFormat); postData.put("base64Pic", base64Pic);
	 * excuteHttpPost(msg, "changeUserHead", BaseEntity.class, type, maps,
	 * postData); }
	 * 
	 * public static void getUserInfo(Message messgae) { java.lang.reflect.Type
	 * type = new TypeToken<BaseEntity<User>>() { }.getType();
	 * 
	 * HashMap<String, String> maps = new HashMap<String, String>();
	 * maps.put("_mt", USER_DETAIL); excuteHttpGet(messgae, "getUserInfo",
	 * BaseEntity.class, type, maps); }
	 * 
	 * public static void getBrowsingHistory(Message messgae) {
	 * java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<ProductJsonParser>>() { }.getType();
	 * 
	 * HashMap<String, String> maps = new HashMap<String, String>();
	 * maps.put("_mt", BROWSING_HISTORY); excuteHttpGet(messgae,
	 * "getBrowsingHistory", BaseEntity.class, type, maps); }
	 *//**
	 * 获取推荐列表
	 * 
	 * @param msg
	 */
	/*
	 * public static void getRecommadList(Message msg, String page) {
	 * java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<ProductJsonParser>>() { }.getType(); HashMap<String,
	 * String> maps = new HashMap<String, String>(); maps.put("_mt",
	 * RECOMMANDLIST); maps.put("page", page); maps.put("pageSize", "30");
	 * excuteHttpGet(msg, "getRecommadList", BaseEntity.class, type, maps);
	 * 
	 * }
	 *//**
	 * 获取产品列表
	 * 
	 * @param msg
	 * @param condition
	 */
	/*
	 * public static void getProduct(Message msg, SearchConditionEntity
	 * condition) { java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<ProductJsonParser>>() { }.getType(); HashMap<String,
	 * String> maps = new HashMap<String, String>(); maps.put("_mt",
	 * GET_SIMU_PRODUCT); maps.put("page", condition.page + "");
	 * maps.put("pageSize", condition.pageSize + ""); maps.put("act",
	 * condition.act + ""); maps.put("structured", condition.structured + "");
	 * maps.put("investType", condition.investType + ""); maps.put("interval",
	 * condition.interval + ""); maps.put("rateOrder", condition.rateOrder +
	 * ""); excuteHttpGet(msg, "getProduct", BaseEntity.class, type, maps);
	 * 
	 * }
	 * 
	 * public static void getProductDetail(Message msg, String productId) {
	 * java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<ProductDetailEntity>>() { }.getType();
	 * HashMap<String, String> maps = new HashMap<String, String>();
	 * maps.put("_mt", DETAIL_SIMU); maps.put("productId", productId);
	 * excuteHttpGet(msg, "getProductDetail", BaseEntity.class, type, maps); }
	 *//**
	 * 获取曲线
	 * 
	 * @param msg
	 * @param productId
	 * @param ranking
	 */
	/*
	 * public static void getLineData(Message msg, String productId, String
	 * ranking) { java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<ArrayList<LineDataEntity>>>() { }.getType();
	 * HashMap<String, String> maps = new HashMap<String, String>();
	 * maps.put("_mt", GET_LINE); maps.put("productId", productId);
	 * maps.put("ranking", ranking); excuteHttpGet(msg, "getLineData",
	 * BaseEntity.class, type, maps); }
	 * 
	 * public static void collectionProduct(Message msg, String productId,
	 * String category, String option) { java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<OperateResultsEntity>>() { }.getType();
	 * HashMap<String, String> maps = new HashMap<String, String>();
	 * maps.put("_mt", COLLECTION); maps.put("productId", productId);
	 * maps.put("category", category); maps.put("option", option);
	 * excuteHttpGet(msg, "attentionProduct", BaseEntity.class, type, maps); }
	 * 
	 * public static void getMyCollection(Message msg, String page) {
	 * java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<ProductJsonParser>>() { }.getType(); HashMap<String,
	 * String> maps = new HashMap<String, String>(); maps.put("_mt",
	 * GET_MY_COLLECTIONS); maps.put("pageSize", Integer.MAX_VALUE + "");
	 * excuteHttpGet(msg, "getMyCollection", BaseEntity.class, type, maps); }
	 *//**
	 * 发现，机构，个人，通用
	 * 
	 * @param message
	 * @param q
	 *            查询条件
	 */
	/*
	 * public static void getFindList(Message message, String page, String
	 * bizType, String mt) { java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<ContentPaserEntity<ArrayList<FindItem>>>>() {
	 * }.getType(); HashMap<String, String> maps = new HashMap<String,
	 * String>(); maps.put("_mt", mt); maps.put("bizType", bizType);
	 * maps.put("page", page); maps.put("pageSize", Integer.MAX_VALUE + "");
	 * excuteHttpGet(message, "getFindList" + mt, BaseEntity.class, type, maps);
	 * }
	 *//**
	 * 获取find详情（ 个人 详情）
	 * 
	 * @param message
	 * @param id
	 */
	/*
	 * public static void getFindPersonalDetail(Message message, String id) {
	 * java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<FindDetailPaserEntity>>() { }.getType();
	 * HashMap<String, String> maps = new HashMap<String, String>();
	 * maps.put("_mt", GET_MAGAGER_DETAIL); maps.put("managerId", id);
	 * excuteHttpGet(message, "getFindPersonalDetail", BaseEntity.class, type,
	 * maps); }
	 *//**
	 * 获取find详情（ 机构 详情）
	 * 
	 * @param message
	 * @param id
	 */
	/*
	 * public static void getFindConsultantDetail(Message message, String id) {
	 * java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<FindDetailPaserEntity>>() { }.getType();
	 * HashMap<String, String> maps = new HashMap<String, String>();
	 * maps.put("_mt", GET_CONSULTANT_DETAIL); maps.put("consultantId", id);
	 * excuteHttpGet(message, "getFindConsultantDetail", BaseEntity.class, type,
	 * maps); }
	 * 
	 * public static void attention(Message msg, String objId, int objType, int
	 * follow) { java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<OperateResultsEntity>>() { }.getType();
	 * HashMap<String, String> maps = new HashMap<String, String>();
	 * maps.put("_mt", ATTENTION); maps.put("objId", objId); maps.put("objType",
	 * objType + ""); maps.put("option", follow + ""); excuteHttpGet(msg,
	 * "attention_consultant_manager", BaseEntity.class, type, maps); }
	 *//**
	 * 获取关注
	 * 
	 * @param handler
	 * @throws Exception
	 */
	/*
	 * public static void getMyAttention(Message msg) { java.lang.reflect.Type
	 * type = new
	 * TypeToken<BaseEntity<ContentPaserEntity<ArrayList<FindItem>>>>() {
	 * }.getType(); HashMap<String, String> maps = new HashMap<String,
	 * String>(); maps.put("_mt", GET_MY_ATTENTION); maps.put("pageSize",
	 * Integer.MAX_VALUE + ""); excuteHttpGet(msg, "getMyAttention",
	 * BaseEntity.class, type, maps); }
	 *//**
	 * 获取比较曲线 raking=2 最近1年
	 * 
	 * @param handler
	 * @throws Exception
	 */
	/*
	 * public static void getCompareProductInfo(Message msg, String productId) {
	 * java.lang.reflect.Type type = new TypeToken<CompanyDetail>() {
	 * }.getType(); HashMap<String, String> maps = new HashMap<String,
	 * String>(); maps.put("_mt", GET_COMPARED_CONTENT); maps.put("productId",
	 * productId); maps.put("ranking", "2"); excuteHttpGet(msg,
	 * "getCompareProductInfo", BaseEntity.class, type, maps); }
	 * 
	 * public static void getAskTags(Message msg) { java.lang.reflect.Type type
	 * = new TypeToken<BaseEntity<ArrayList<ItemOfTaglist>>>() { }.getType();
	 * HashMap<String, String> maps = new HashMap<String, String>();
	 * maps.put("_mt", GET_ASK_TAGs); excuteHttpPost(msg, "getAskTags",
	 * BaseEntity.class, type, maps, null); }
	 * 
	 * public static void getAskDetail(Message msg, String questionId) {
	 * java.lang.reflect.Type type = new TypeToken<BaseEntity<AskEntity>>() {
	 * }.getType(); HashMap<String, String> maps = new HashMap<String,
	 * String>(); maps.put("_mt", GET_ASK_DETAIL); HashMap<String, String>
	 * postData = new HashMap<String, String>(); postData.put("questionId",
	 * questionId); excuteHttpPost(msg, "getAskDetail", BaseEntity.class, type,
	 * maps, postData); }
	 * 
	 * public static void submitASK(Message msg, AskEntity entity) {
	 * java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<AskSuccessEntity>>() { }.getType(); HashMap<String,
	 * String> maps = new HashMap<String, String>(); maps.put("_mt",
	 * SUBMIT_ASK); HashMap<String, String> postData = new HashMap<String,
	 * String>(); postData.put("topic", entity.topic); postData.put("detail",
	 * entity.detail); String tagids = ""; if (null != entity.tagDOList) { for
	 * (int a = 0; a < entity.tagDOList.size(); a++) { tagids +=
	 * entity.tagDOList.get(a).id + ","; } if (tagids.endsWith(",")) { tagids =
	 * tagids.substring(0, tagids.length() - 1); } } postData.put("tagIds",
	 * tagids); excuteHttpPost(msg, "submitASK", BaseEntity.class, type, maps,
	 * postData); }
	 *//**
	 * 获取最新/最热ask
	 */
	/*
	 * public static void getASK(Message msg, String _mt, String page) {
	 * java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<ContentPaserEntity<ArrayList<AskEntity>>>>() {
	 * }.getType(); HashMap<String, String> maps = new HashMap<String,
	 * String>(); maps.put("_mt", _mt); HashMap<String, String> postData = new
	 * HashMap<String, String>(); postData.put("page", page);
	 * postData.put("pageSzie", "10");
	 * 
	 * excuteHttpPost(msg, "getASK", BaseEntity.class, type, maps, postData); }
	 *//**
	 * 获取最新/最热ask
	 */
	/*
	 * public static void submitReply(Message msg, String questionId, String
	 * replyContent, boolean annoy) { java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<Object>>() { }.getType(); HashMap<String, String>
	 * maps = new HashMap<String, String>(); maps.put("_mt", REPLY_ASK);
	 * HashMap<String, String> postData = new HashMap<String, String>();
	 * postData.put("questionId", questionId); postData.put("replyContent",
	 * replyContent); postData.put("annoy", annoy ? "0" : "1");
	 * excuteHttpPost(msg, "submitReply", BaseEntity.class, type, maps,
	 * postData); }
	 *//**
	 * 获取tag详情
	 */
	/*
	 * public static void getTagDetail(Message msg, String tagId) {
	 * java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<TagDetailEntity>>() { }.getType(); HashMap<String,
	 * String> maps = new HashMap<String, String>(); maps.put("_mt",
	 * GET_TAG_DETAIL); HashMap<String, String> postData = new HashMap<String,
	 * String>(); postData.put("tagId", tagId); excuteHttpPost(msg,
	 * "getTagDetail", BaseEntity.class, type, maps, postData); }
	 *//**
	 * 获取answer list
	 */
	/*
	 * public static void getAnswerList(Message msg, String questionId, String
	 * page) { java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<AnswerJsonParser>>() { }.getType(); HashMap<String,
	 * String> maps = new HashMap<String, String>(); maps.put("_mt",
	 * GET_ANSWER_LIST); HashMap<String, String> postData = new HashMap<String,
	 * String>(); postData.put("questionId", questionId); postData.put("page",
	 * page); postData.put("pageSzie", "1"); excuteHttpPost(msg,
	 * "getAnswerList", BaseEntity.class, type, maps, postData); }
	 *//**
	 * 获取answer 详情
	 */
	/*
	 * public static void getAnswerDetail(Message msg, String replyId) {
	 * java.lang.reflect.Type type = new TypeToken<BaseEntity<AnswerEntity>>() {
	 * }.getType(); HashMap<String, String> maps = new HashMap<String,
	 * String>(); maps.put("_mt", ANSWER); HashMap<String, String> postData =
	 * new HashMap<String, String>(); postData.put("replyId", replyId);
	 * 
	 * excuteHttpPost(msg, "getAnswerDetail", BaseEntity.class, type, maps,
	 * postData); }
	 *//**
	 * 获取tag相关的问题
	 */
	/*
	 * public static void getTagAnswerList(Message msg, String replyId, String
	 * _mt) { java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<ContentPaserEntity<ArrayList<AskEntity>>>>() {
	 * }.getType(); HashMap<String, String> maps = new HashMap<String,
	 * String>(); maps.put("_mt", _mt); maps.put("tagId", replyId);
	 * 
	 * excuteHttpGet(msg, "getTagAnswerList", BaseEntity.class, type, maps);
	 * 
	 * }
	 *//**
	 * 点赞
	 * 
	 * @param msg
	 * @param replyId
	 * @param option
	 */
	/*
	 * public static void praiseAnswer(Message msg, String replyId, String
	 * option) { java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<Integer>>() { }.getType(); HashMap<String, String>
	 * maps = new HashMap<String, String>(); maps.put("_mt", PRAISE);
	 * maps.put("replyId", replyId); maps.put("option", option);
	 * excuteHttpGet(msg, "praiseAnswer", BaseEntity.class, type, maps); }
	 *//**
	 * 追问
	 * 
	 * @param msg
	 * @param replyId
	 * @param option
	 */
	/*
	 * public static void followAnswer(Message msg, String replyId, String
	 * content, boolean privatechart) { java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<Boolean>>() { }.getType(); HashMap<String, String>
	 * maps = new HashMap<String, String>(); maps.put("_mt", FOLLOW);
	 * HashMap<String, String> postData = new HashMap<String, String>();
	 * postData.put("replyId", replyId); postData.put("replyContent", content);
	 * if (privatechart) { postData.put("privateChat", "1"); } else {
	 * postData.put("privateChat", "0"); }
	 * 
	 * excuteHttpPost(msg, "followAnswer", BaseEntity.class, type, maps,
	 * postData); }
	 *//**
	 * 追问
	 * 
	 * @param msg
	 * @param replyId
	 * @param option
	 */
	/*
	 * public static void getFollowAnswer(Message msg, String replyId, String
	 * content) { java.lang.reflect.Type type = new
	 * TypeToken<BaseEntity<ArrayList<AnswerEntity>>>() { }.getType();
	 * HashMap<String, String> maps = new HashMap<String, String>();
	 * maps.put("_mt", GET_FOLLOW); maps.put("replyId", replyId);
	 * excuteHttpGet(msg, "getFollowAnswer", BaseEntity.class, type, maps); }
	 *//**
	 * 
	 */
	/*

	*//**
	 * Get方法
	 * 
	 * @param handler
	 * @param url
	 * @param tag
	 * @param t
	 */
	/*
	 * private static <T> void excuteHttpGet(Message msg, final String tag, T t,
	 * java.lang.reflect.Type type, HashMap<String, String> param) { String url
	 * = SERVER_HOST; url += encodeParams(param) + getCommParams(); url =
	 * CommonUtils.ConfigureFormatURL(App.getContext(), url); excuteHttp(msg,
	 * tag, t, Method.GET, type, url, null); }
	 * 
	 * private static <T> void excuteHttpPost(Message msg, final String tag, T
	 * t, java.lang.reflect.Type type, HashMap<String, String> param,
	 * HashMap<String, String> postData) { String url = SERVER_HOST; url +=
	 * encodeParams(param) + getCommParams(); url =
	 * CommonUtils.ConfigureFormatURL(App.getContext(), url); excuteHttp(msg,
	 * tag, t, Method.POST, type, url, postData); }
	 *//**
	 * 
	 * @param msg
	 * @param url
	 * @param tag
	 * @param t
	 * @param params
	 * @param method
	 * @param type
	 */
	/*
	 * public static <T> void excuteHttp(final Message msg, final String tag, T
	 * t, int method, java.lang.reflect.Type type, String url, final
	 * HashMap<String, String> postData) {
	 * 
	 * L.v(TAG, tag, url); L.v(TAG, tag, null == postData ? "null" :
	 * postData.toString()); if (!CommonUtils.checkNetState(true)) { final
	 * Handler handler = msg.getTarget(); Message message = new Message();
	 * message.what = REQUEST_NO_NETWORK; message.arg1 = msg.arg1;
	 * message.setData(msg.getData()); handler.sendMessage(message); return; }
	 * 
	 * final Handler handler = msg.getTarget(); Message message = new Message();
	 * message.what = REQUEST_BEGIN; message.arg1 = msg.arg1;
	 * message.setData(msg.getData()); handler.sendMessage(message);
	 * JsonRequest<T> request = new JsonRequest<T>(type, method, url, handler,
	 * new Listener<T>() {
	 * 
	 * @Override public void onResponse(final T arg0) { L.v(TAG, tag,
	 * arg0.toString()); int intnum = REQUEST_FAIL; if (null != arg0 && (arg0
	 * instanceof BaseEntity)) { if (((BaseEntity) arg0).code.equals("0")) {
	 * intnum = REQUEST_SUCCESS; PreferencesUtils.putString("shost",
	 * ((BaseEntity) arg0).shost); } else { L.v(tag, "onResponse",
	 * "REQUEST_FAIL"); handler.post(new Runnable() {
	 * 
	 * @Override public void run() { CommonUtils .showToast(((BaseEntity)
	 * arg0).msg); } }); } } else {
	 * 
	 * } Message message = msg; message.obj = arg0; message.what = intnum;
	 * message.arg2 = tag.hashCode(); handler.sendMessage(message); } }, new
	 * ErrorListener() {
	 * 
	 * @Override public void onErrorResponse(final VolleyError arg0) { L.v(TAG,
	 * "onErrorResponse", arg0.getMessage()); Message message = msg; message.obj
	 * = arg0; message.what = REQUEST_FAIL; handler.sendMessage(message); } }) {
	 * 
	 * @Override protected Map<String, String> getParams() throws
	 * AuthFailureError { // TODO Auto-generated method stub return postData; }
	 * }; RequestManager.addRequest(request, tag); }
	 * 
	 * public static String encodeParams(HashMap<String, String> maps) {
	 * L.v(TAG, "encodeParams", maps.toString()); String url = ""; if (null !=
	 * maps && maps.size() > 0) { Iterator<String> iter =
	 * maps.keySet().iterator();
	 * 
	 * while (iter.hasNext()) { String key = iter.next(); String value =
	 * maps.get(key); if (!TextUtils.isEmpty(value) && !TextUtils.isEmpty(key))
	 * url += key + "=" + URLEncoder.encode(value) + "&"; } if
	 * (!TextUtils.isEmpty(url) && url.endsWith("&")) { url = url.substring(0,
	 * url.lastIndexOf("&")); } } return url;
	 * 
	 * }
	 * 
	 * private static String getCommParams() { String params = ""; //
	 * SimpleDateFormat dateformat = new SimpleDateFormat( //
	 * "yyyy-MM-dd HH:mm:ss"); // String params = "&_cp=www&_tt=" +
	 * dateformat.format(new Date()) // + "&fmt=json&id=1";
	 * 
	 * try { String token = DataManagers.getToken(); if
	 * (!TextUtils.isEmpty(token)) { params += "&_tk=" + token; } } catch
	 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * return params; }
	 * 
	 * private static HashMap getCommParamsWithMap() { String params = "";
	 * 
	 * HashMap<String, String> maps = new HashMap<String, String>(); //
	 * SimpleDateFormat dateformat = new SimpleDateFormat( //
	 * "yyyy-MM-dd HH:mm:ss"); // String params = "&_cp=www&_tt=" +
	 * dateformat.format(new Date()) // + "&fmt=json&id=1"; try { String token =
	 * DataManagers.getToken(); if (!TextUtils.isEmpty(token)) { maps.put("_tk",
	 * token); } } catch (Exception e) { e.printStackTrace(); } return maps; }
	 */
}
