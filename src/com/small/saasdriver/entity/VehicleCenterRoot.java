package com.small.saasdriver.entity;

public class VehicleCenterRoot {
	private int ErrCode;

	private String ErrMsg;

	private VehicleCenterData Data;

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
	public void setData(VehicleCenterData Data){
	this.Data = Data;
	}
	public VehicleCenterData getData(){
	return this.Data;
	}
}
