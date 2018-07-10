package com.saas.chat;

public interface Constants {

	// 聊天
	public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
	String isFriend = "isFriend";
	String LoginState = "LoginState";
	String UserInfo = "UserInfo";
	String URL = "URL";
	String Title = "Title";
	String ID = "id";
	String NAME = "NAME";
	String AccessToken = "AccessToken";
	String PWD = "PWD";
	String ContactMsg = "ContactMsg";
	String VersionInfo = "VersionInfo";
	String SeeMe = "SeeMe";
	String LokeMe = "LokeMe";
	String IsMsg = "IsMsg";
	String IsVideo = "IsVideo";
	String IsZhen = "IsZhen";
	String User_ID = "User_ID";
	String GROUP_ID = "GROUP_ID";
	String TYPE = "TYPE";
	// JSON status
	String Info = "info";
	String Value = "data";
	String Result = "status";
	String DB_NAME = "WeChat.db";
	String NET_ERROR = "网络错误，请稍后再试！";
	String BaiduPullKey = "Uvw5AMP15i9v1cUoS5aY7GR1";

	// 发送验证码 codeType 1注册 2修改密码
	String SendCodeURL = "";
	// 用户注册
	String RegistURL = "http://192.168.1.103:8080/webeasy/_samples/app/json/register.json";
	// 用户登录
	String Login_URL = "http://192.168.1.103:8080/webeasy/_samples/app/json/login.json";
	// 更新用户信息
	String UpdateInfoURL = "http://192.168.1.103:8080/webeasy/_samples/app/json/updateUserinfo.json";
	// 获取用户信息
	String getUserInfoURL_All = "http://192.168.1.103:8080/webeasy/_samples/app/json/getUserAllList.json";
	String getUserInfoURL_id_pho = "http://192.168.1.103:8080/webeasy/_samples/app/json/getUserList.json";
	String getUserInfoURL_pho = "http://192.168.1.103:8080/webeasy/_samples/app/json/getUserList.json";
	// 检查版本
	// 更新用户信息
	// 我的群组
	String getGroupListURL = "http://192.168.1.103:8080/webeasy/_samples/app/json/getGrouAllList.json";
	// 反馈
	// 获取群组信息
	// 查询所有好友033
	String getContactFriendURL = "http://192.168.1.103:8080/webeasy/_samples/app/json/getContactList.json";
	// 加入群组
	// 退出群组
	// 新建群组
	String newGroupURL = "http://192.168.1.103:8080/webeasy/_samples/app/json/addGroup.json";
	//加载个人信息
	String loadUserInfo = "http://192.168.1.103:8080/webeasy/_samples/app/json/userInfo.json";

	public static final String JSON_KEY_NICK = "nick";
	public static final String JSON_KEY_HXID = "hxid";
	public static final String JSON_KEY_FXID = "fxid";
	public static final String JSON_KEY_SEX = "sex";
	public static final String JSON_KEY_AVATAR = "avatar";
	public static final String JSON_KEY_CITY = "city";
	public static final String JSON_KEY_PROVINCE = "province";
	public static final String JSON_KEY_TEL = "tel";
	public static final String JSON_KEY_SIGN = "sign";
}
