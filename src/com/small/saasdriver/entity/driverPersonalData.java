package com.small.saasdriver.entity;

import java.util.List;

public class driverPersonalData {
	private String DriverPhotoPath;

	private String DriverName;

	private String Sex;

	private int Age;

	private int DrivingAge;

	private String DriverLevel;

	private String PhoneNum;

	private String Identification;

	private String Email;

	private String CompanyName;

	private int LevelID;

	private List<String> BusinessTypeList;

	private int IsExistDrunkDrivingRecord;

	public void setDriverPhotoPath(String DriverPhotoPath) {
		this.DriverPhotoPath = DriverPhotoPath;
	}

	public String getDriverPhotoPath() {
		return this.DriverPhotoPath;
	}

	public void setDriverName(String DriverName) {
		this.DriverName = DriverName;
	}

	public String getDriverName() {
		return this.DriverName;
	}

	public void setSex(String Sex) {
		this.Sex = Sex;
	}

	public String getSex() {
		return this.Sex;
	}

	public void setAge(int Age) {
		this.Age = Age;
	}

	public int getAge() {
		return this.Age;
	}

	public void setDrivingAge(int DrivingAge) {
		this.DrivingAge = DrivingAge;
	}

	public int getDrivingAge() {
		return this.DrivingAge;
	}

	public void setDriverLevel(String DriverLevel) {
		this.DriverLevel = DriverLevel;
	}

	public String getDriverLevel() {
		return this.DriverLevel;
	}

	public void setPhoneNum(String PhoneNum) {
		this.PhoneNum = PhoneNum;
	}

	public String getPhoneNum() {
		return this.PhoneNum;
	}

	public void setIdentification(String Identification) {
		this.Identification = Identification;
	}

	public String getIdentification() {
		return this.Identification;
	}

	public void setEmail(String Email) {
		this.Email = Email;
	}

	public String getEmail() {
		return this.Email;
	}

	public void setCompanyName(String CompanyName) {
		this.CompanyName = CompanyName;
	}

	public String getCompanyName() {
		return this.CompanyName;
	}

	public void setLevelID(int LevelID) {
		this.LevelID = LevelID;
	}

	public int getLevelID() {
		return this.LevelID;
	}

	public void setString(List<String> BusinessTypeList) {
		this.BusinessTypeList = BusinessTypeList;
	}

	public List<String> getString() {
		return this.BusinessTypeList;
	}

	public void setIsExistDrunkDrivingRecord(int IsExistDrunkDrivingRecord) {
		this.IsExistDrunkDrivingRecord = IsExistDrunkDrivingRecord;
	}

	public int getIsExistDrunkDrivingRecord() {
		return this.IsExistDrunkDrivingRecord;
	}

}
