package com.small.saasdriver.entity;

public class LoginData {
	private int LoginID;

	private String DriverID;

	private String Identify;

	public void setLoginID(int LoginID){
	this.LoginID = LoginID;
	}
	public int getLoginID(){
	return this.LoginID;
	}
	public void setDriverID(String DriverID){
	this.DriverID = DriverID;
	}
	public String getDriverID(){
	return this.DriverID;
	}
	public void setIdentify(String Identify){
	this.Identify = Identify;
	}
	public String getIdentify(){
	return this.Identify;
	}
}
