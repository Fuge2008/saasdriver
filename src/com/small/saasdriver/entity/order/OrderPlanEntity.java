package com.small.saasdriver.entity.order;

import java.util.List;



/**
 * 接收订单信息封装
 */
public class OrderPlanEntity {
	public int ErrCode;
	public String ErrMsg;
	//public SlideView slideView;
	public List<Data> Data;

	@Override
	public String toString() {
		return "OrderReceiveEntity [ErrCode=" + ErrCode + ", ErrMsg=" + ErrMsg + ", Data=" + Data + "]";
	}

	public static class Data {

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
		public String OtherCommand;
		public String OrderState;

		@Override
		public String toString() {
			return "Data [CustomerName=" + CustomerName + ", CustomerPhoneNum=" + CustomerPhoneNum + ", UserType="
					+ UserType + ", OrderMode=" + OrderMode + ", OrderID=" + OrderID + ", PersonNum=" + PersonNum
					+ ", OccTime=" + OccTime + ", UseVehicleDate=" + UseVehicleDate + ", CompanyName=" + CompanyName
					+ ", Origin=" + Origin + ", OriginLongitude=" + OriginLongitude + ", OriginLatitude="
					+ OriginLatitude + ", Destination=" + Destination + ", DestinationLongitude=" + DestinationLongitude
					+ ", DestinationLatitude=" + DestinationLatitude + ", OtherCommand=" + OtherCommand
					+ ", OrderState=" + OrderState + "]";
		}

	}
}
