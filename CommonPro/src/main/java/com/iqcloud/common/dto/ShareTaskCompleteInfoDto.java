package com.iqcloud.common.dto;

public class ShareTaskCompleteInfoDto {
	private Integer completeTime; // 任务完成用时
	private Integer completeNum; // 任务完成个数
	private Integer questionNum; // 学生提问个数
	private Integer integral; // 任务积分
	private Integer integralSort; // 积分班级排名
	private Integer taskNum; // 任务个数
	private Integer completeSort; // 任务提交及时率
	private String taskTitle; // 任务的名称
	private Integer isSuccess; // 是否成功完成任务，0为未成功，1为成功

	public Integer getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Integer completeTime) {
		this.completeTime = completeTime;
	}

	public Integer getCompleteNum() {
		return completeNum;
	}

	public void setCompleteNum(Integer completeNum) {
		this.completeNum = completeNum;
	}

	public Integer getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getIntegralSort() {
		return integralSort;
	}

	public void setIntegralSort(Integer integralSort) {
		this.integralSort = integralSort;
	}

	public Integer getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(Integer taskNum) {
		this.taskNum = taskNum;
	}

	public Integer getCompleteSort() {
		return completeSort;
	}

	public void setCompleteSort(Integer completeSort) {
		this.completeSort = completeSort;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public Integer getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}

}
