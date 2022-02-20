package com.spring.boot.dto;

import java.sql.Date;

public class EvaluationEmployeeCommitmentsDTO {

	private int evaluationId;
	
	private int employeeId;
	
	private int monthsPeriod;
	
	private Date startDate;
	
	private Date endDate;
	
	private PerformanceCommitment performanceCommitment;
	
	
	public EvaluationEmployeeCommitmentsDTO() {
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
