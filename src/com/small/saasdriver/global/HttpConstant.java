package com.small.saasdriver.global;

/**
 * 项目中使用的URL控制管理类
 * 
 * @author ZhangKui
 *
 */
public class HttpConstant {

	// 测试用服务器
	public final static String BASE_URL = "http://120.77.86.137:82";
	// 正式发布用服务器
	 //public final static String BASE_URL="http://192.168.1.254:130";

	/** ================== 订单部分用到的URL接口 ================== */

	/** 订单接收开关URL */
	// public final static String ORDER_SWITCH_URL =BASE_URL+
	// "/VehicleDriverWorking/UpdateWorkStatus";
	/** 第一期暂无 订单接收模式URL ：0-即时，1-预约，2-全部 */
	// public final static String ORDER_MODEL_URL =BASE_URL+ "";
	/** 向服务器获取最新订单URL：订单刷新 */
	public final static String ORDER_NEW_URL = BASE_URL + "/Order/ReceivedOrder";
	/** 订单确认URL */
	public final static String ORDER_ENSURE_URL = BASE_URL + "/Order/AgreedOrRefusedOrder";
	/** 订单开始确认URL */
	public final static String ORDER_START_URL = BASE_URL + "/Order/ConfirmOrderStart";
	/** 订单结束确认URL */
	public final static String ORDER_END_URL = BASE_URL + "/Order/ConfirmOrderEnd";
	/** 订单数据提交URL */
	public final static String ORDER_DATA_COMMIT_URL = BASE_URL + "/OrderVehicleTravel/UploadData";

	/** 加载订单信息 */
	// public final static String ORDER_DATA_LOAD =BASE_URL+
	// "/Order/ReceivedOrder/";
	/** 订单接收确认 */
	public final static String ORDER_START_CONFIRM = BASE_URL + "/Order/ConfirmationReceivedOrder/";
	/** 订单开始或订单结束 */
	public final static String ORDER_START_OR_END = BASE_URL + "/Order/ConfirmOrderStartOrEnd/";

	/** 未来订单或当前订单 */
	public final static String ORDER_NOW_OR_PLAN = BASE_URL + "/Order/GetOngoingOrderOrFuturePlanning/";

	/** 上传行程数据 */
	public final static String UPLOAD_ORDER_DATA = BASE_URL + "/Order/UploadData/";

	/** 获取历史订单 */
	public final static String ORDER_DATA_RECORD = BASE_URL + "/Order/GetTaskRecord/";

	/** 订单数据统计 */
	public final static String ORDER_DATA_STATISTICS = BASE_URL + "/Order/GetDataStatistics/";

	// 新闻
	public final static String NEWS = "http://v.juhe.cn/toutiao/index";

	/** ================== 车主中心的URL接口 ================== */
	/** 获取司机个人信息 */
	public final static String DRIVER_PRESONAL_INFROMATION = BASE_URL + "/Driver/GetDriverInfo";
	/** 获取司机个人信息（简单） */
	public final static String DRIVER_PRESONAL_INFROMATION2 = BASE_URL + "/Driver/GetSimpleBasicInfo";
	/** 获取车辆信息 */
	public final static String DRIVER_VEHICLE_CENTER = BASE_URL + "/Vehicle/GetCarInfoModel";
	/** 任务记录 */
	public final static String DRIVER_TASK_RECORD = BASE_URL + "/Driver/GetTaskRecord";
	/** 头像上传 */
	public final static String header_post = BASE_URL + "/DriverInfo/UploadProfilePicture/";
	/** ================== 登录注册的URL接口 ================== */
	/** 未登录 ——>改密验证码 */
	public final static String DRIVER_LOGIN_VERIFICATION = BASE_URL + "/DriverInfo/GetForgetCode/";
	/** 未登录 ——>修改密码 */
	public final static String DRIVER_UNCHANGE_PASSWORD = BASE_URL + "/DriverInfo/UpdatePassword/";
	/** 登录——> 修改密码 */
	public final static String DRIVER_CHANGE_PASSWORD = BASE_URL + "/DriverInfo/LoggedUpdatePwd/";
	/** 登录 */
	public final static String DRIVER_LOGIN = BASE_URL + "/DriverInfo/LoginResult/";

	// public static final String BASE_URL = "http://192.168.1.135:5000/";

}
