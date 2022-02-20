package com.spring.boot.dto;

import java.sql.Date; 

public class EvaluationCommitmentsDTO {

	private int evaluationId;
	
	private int managerId;
	
	private int employeeId;
	
	private String comment;
	
	private int monthsPeriod;
	
	private Date startDate;
	
	private Date endDate;
	
	private PerformanceCommitment performanceCommitment;
	
	
	public EvaluationCommitmentsDTO() {
		performanceCommitment = new PerformanceCommitment();
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


	public PerformanceCommitment getPerformanceCommitment() {
		return performanceCommitment;
	}


	public void setPerformanceCommitment(PerformanceCommitment performanceCommitment) {
		this.performanceCommitment = performanceCommitment;
	}
	
	
	
	
}
