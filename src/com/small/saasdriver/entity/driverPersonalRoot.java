package com.small.saasdriver.entity;

public class driverPersonalRoot {
	private int ErrCode;

	private String ErrMsg;

	private driverPersonalData Data;

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
	public void setData(driverPersonalData Data){
	this.Data = Data;
	}
	public driverPersonalData getData(){
	return this.Data;
	}
}
