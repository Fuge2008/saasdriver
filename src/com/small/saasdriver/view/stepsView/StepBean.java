package com.small.saasdriver.view.stepsView;

/**
 * 封装订单整个流程步骤
 * 
 * @author admin
 *
 */
public class StepBean {
	public static final int STEP_UNDO = -1;// 未完成 undo step
	public static final int STEP_CURRENT = 0;// 正在进行 current step
	public static final int STEP_COMPLETED = 1;// 已完成 completed step

	private String name;
	private int state;
	private String time;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public StepBean() {
		super();
	}

	public StepBean(String time, String name, int state) {
		super();
		this.time = time;
		this.name = name;
		this.state = state;
	}

}
