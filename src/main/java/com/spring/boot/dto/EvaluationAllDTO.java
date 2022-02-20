package com.spring.boot.dto;

import java.sql.Date;

public class EvaluationAllDTO {

	private int evaluationId;
	
	private int managerId;
	
	private int employeeId;
	
	private String comment;
	
	private int monthsPeriod;
	
	private Date startDate;
	
	private Date endDate;
	
	private PerformanceAll performanceAll;
	
	
	public EvaluationAllDTO() {
		performanceAll = new PerformanceAll();
	}

	
	

	public int getMonthsPeriod() {
		return monthsPeriod;
	}

	public void setMonthsPeriod(int monthsPeriod) {
		this.monthsPeriod = monthsPeriod;
	}


	public int getEvaluationId() {
		return evaluationId;
	}


	public void setEvaluationId(int evaluationId) {
		this.evaluationId = evaluationId;
	}


	public int getManagerId() {
		return managerId;
	}


	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public int getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public PerformanceAll getPerformanceAll() {
		return performanceAll;
	}


	public void setPerformanceAll(PerformanceAll performanceAll) {
		this.performanceAll = performanceAll;
	}
	
	
	
	
}
