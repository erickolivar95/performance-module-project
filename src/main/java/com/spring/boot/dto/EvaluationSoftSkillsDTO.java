package com.spring.boot.dto;

import java.sql.Date;

public class EvaluationSoftSkillsDTO {

	private int evaluationId;
	
	private int managerId;
	
	private int employeeId;
	
	private String comment;
	
	private int monthsPeriod;
	
	private Date startDate;
	
	private Date endDate;
	
	private PerformanceSoftSkill performanceSoftSkill;
	
	
	public EvaluationSoftSkillsDTO() {
		performanceSoftSkill = new PerformanceSoftSkill();
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


	public int getMonthsPeriod() {
		return monthsPeriod;
	}


	public void setMonthsPeriod(int monthsPeriod) {
		this.monthsPeriod = monthsPeriod;
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


	public PerformanceSoftSkill getPerformanceSoftSkill() {
		return performanceSoftSkill;
	}


	public void setPerformanceSoftSkill(PerformanceSoftSkill performanceSoftSkill) {
		this.performanceSoftSkill = performanceSoftSkill;
	}


	
	
	
	
	
}
