package com.saas.chat.chat;

import java.util.List;

/**
 * 封装个人信息
 */
public class UserInfo {

	public String nickName;
	public String headImage;
	public String userID;
	public String sex;
	public String region;
	public String selfDescribe;

	@Override
	public String toString() {
		return "Data [nickName=" + nickName + ", headImage=" + headImage + ", userID=" + userID + ", sex=" + sex
				+ ", region=" + region + ", selfDescribe=" + selfDescribe + "]";

	}

}
