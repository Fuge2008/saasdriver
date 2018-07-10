package com.small.saasdriver.entity;

public class simpleRoot {
	private int ErrCode;

	private String ErrMsg;

	private simpleData Data;

	public void setErrCode(int ErrCode){
	this.ErrCode = ErrCode;
	}
	public int getErrCode(){
	return this.ErrCode;
	}
	public void setErrMsg(String ErrMsg){
	this.ErrMsg = ErrMsg;
	}
	public String getErrMsg(){
	return this.ErrMsg;
	}
	public void setData(simpleData Data){
	this.Data = Data;
	}
	public simpleData getData(){
	return this.Data;
	}
}
