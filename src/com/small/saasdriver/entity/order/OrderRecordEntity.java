package com.small.saasdriver.entity.order;

import java.util.List;

/**
 * 封装历史记录信息
 *
 */
public class OrderRecordEntity {
	public int ErrCode;
	public String ErrMsg;
	public List<Data> Data;

	@Override
	public String toString() {
		return "OrderRecordEntity [ErrCode=" + ErrCode + ", ErrMsg=" + ErrMsg + ", Data=" + Data + "]";
	}

	// 历史记录主体信息
	public static class Data {

		public int UseVehicleSeatingCapacity;
		public double ActualCost;
		public double ActualDiscount;
		public int DriverType;
		public double Kilometres;
		public String StartTime;
		public String EndTime;
		public double OilCharge;
		public double ParkingCharge;
		public double TollCharge;
		public double OtherCharge;
		public String OtherChargeDescription;
		public String CustomerName;
		public String CustomerPhoneNum;
		public int UserType;
		public int OrderMode;
		public int OrderID;
		public int PersonNum;
		public String OccTime;
		public String UseVehicleDate;
		public String CompanyName;
		public String Origin;
		public double OriginLongitude;
		public double OriginLatitude;
		public String Destination;
		public double DestinationLongitude;
		public double DestinationLatitude;
		public String OrderState;
		public String OtherCommand;

		@Override
		public String toString() {
			return "Data [UseVehicleSeatingCapacity=" + UseVehicleSeatingCapacity + ", ActualCost=" + ActualCost
					+ ", ActualDiscount=" + ActualDiscount + ", DriverType=" + DriverType + ", Kilometres=" + Kilometres
					+ ", StartTime=" + StartTime + ", EndTime=" + EndTime + ", OilCharge=" + OilCharge
					+ ", ParkingCharge=" + ParkingCharge + ", TollCharge=" + TollCharge + ", OtherCharge=" + OtherCharge
					+ ", OtherChargeDescription=" + OtherChargeDescription + ", CustomerName=" + CustomerName
					+ ", CustomerPhoneNum=" + CustomerPhoneNum + ", UserType=" + UserType + ", OrderMode=" + OrderMode
					+ ", OrderID=" + OrderID + ", PersonNum=" + PersonNum + ", OccTime=" + OccTime + ", UseVehicleDate="
					+ UseVehicleDate + ", CompanyName=" + CompanyName + ", Origin=" + Origin + ", OriginLongitude="
					+ OriginLongitude + ", OriginLatitude=" + OriginLatitude + ", Destination=" + Destination
					+ ", DestinationLongitude=" + DestinationLongitude + ", DestinationLatitude=" + DestinationLatitude
					+ ", OrderState=" + OrderState + ", OtherCommand=" + OtherCommand + "]";
		}

	}
}
