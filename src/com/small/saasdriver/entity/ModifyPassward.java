package com.small.saasdriver.entity;

public class ModifyPassward {
	private int ErrCode;

	private String ErrMsg;

	private String Data;

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
	public void setData(String Data){
	this.Data = Data;
	}
	public String getData(){
	return this.Data;
	}
}
