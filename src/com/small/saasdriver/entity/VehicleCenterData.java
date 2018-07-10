package com.small.saasdriver.entity;

import java.util.List;

public class VehicleCenterData {
	private String VehiclePhotoPath;

	private String LicenseNum;

	private String Brand;

	private String GasDisplacement;

	private String Color;

	private String FuelCost;

	private int SeatingCapacity;

	private String FuelType;

	@Override
	public String toString() {
		return "VehicleCenterData [VehiclePhotoPath=" + VehiclePhotoPath
				+ ", LicenseNum=" + LicenseNum + ", Brand=" + Brand
				+ ", GasDisplacement=" + GasDisplacement + ", Color=" + Color
				+ ", FuelCost=" + FuelCost + ", SeatingCapacity="
				+ SeatingCapacity + ", FuelType=" + FuelType + ", VehicleType="
				+ VehicleType + ", Score=" + Score + ", Level=" + Level
				+ ", ServicedLife=" + ServicedLife + ", VehicleProperty="
				+ VehicleProperty + ", LeasingCompanyName="
				+ LeasingCompanyName + ", EnterpriseName=" + EnterpriseName
				+ ", ExtenrlCarLC=" + ExtenrlCarLC + ", BusinessTypeList="
				+ BusinessTypeList + "]";
	}
	private int VehicleType;

	private int Score;

	private int Level;

	private int ServicedLife;

	private int VehicleProperty;

	private String LeasingCompanyName;

	private String EnterpriseName;

	private String ExtenrlCarLC;

	private List<String> BusinessTypeList ;

	public void setVehiclePhotoPath(String VehiclePhotoPath){
	this.VehiclePhotoPath = VehiclePhotoPath;
	}
	public String getVehiclePhotoPath(){
	return this.VehiclePhotoPath;
	}
	public void setLicenseNum(String LicenseNum){
	this.LicenseNum = LicenseNum;
	}
	public String getLicenseNum(){
	return this.LicenseNum;
	}
	public void setBrand(String Brand){
	this.Brand = Brand;
	}
	public String getBrand(){
	return this.Brand;
	}
	public void setGasDisplacement(String GasDisplacement){
	this.GasDisplacement = GasDisplacement;
	}
	public String getGasDisplacement(){
	return this.GasDisplacement;
	}
	public void setColor(String Color){
	this.Color = Color;
	}
	public String getColor(){
	return this.Color;
	}
	public void setFuelCost(String FuelCost){
	this.FuelCost = FuelCost;
	}
	public String getFuelCost(){
	return this.FuelCost;
	}
	public void setSeatingCapacity(int SeatingCapacity){
	this.SeatingCapacity = SeatingCapacity;
	}
	public int getSeatingCapacity(){
	return this.SeatingCapacity;
	}
	public void setFuelType(String FuelType){
	this.FuelType = FuelType;
	}
	public String getFuelType(){
	return this.FuelType;
	}
	public void setVehicleType(int VehicleType){
	this.VehicleType = VehicleType;
	}
	public int getVehicleType(){
	return this.VehicleType;
	}
	public void setScore(int Score){
	this.Score = Score;
	}
	public int getScore(){
	return this.Score;
	}
	public void setLevel(int Level){
	this.Level = Level;
	}
	public int getLevel(){
	return this.Level;
	}
	public void setServicedLife(int ServicedLife){
	this.ServicedLife = ServicedLife;
	}
	public int getServicedLife(){
	return this.ServicedLife;
	}
	public void setVehicleProperty(int VehicleProperty){
	this.VehicleProperty = VehicleProperty;
	}
	public int getVehicleProperty(){
	return this.VehicleProperty;
	}
	public void setLeasingCompanyName(String LeasingCompanyName){
	this.LeasingCompanyName = LeasingCompanyName;
	}
	public String getLeasingCompanyName(){
	return this.LeasingCompanyName;
	}
	public void setEnterpriseName(String EnterpriseName){
	this.EnterpriseName = EnterpriseName;
	}
	public String getEnterpriseName(){
	return this.EnterpriseName;
	}
	public void setExtenrlCarLC(String ExtenrlCarLC){
	this.ExtenrlCarLC = ExtenrlCarLC;
	}
	public String getExtenrlCarLC(){
	return this.ExtenrlCarLC;
	}
	public void setString(List<String> BusinessTypeList){
	this.BusinessTypeList = BusinessTypeList;
	}
	public List<String> getString(){
	return this.BusinessTypeList;
	}
}
