package com.spring.boot.entity;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="evaluation")
public class Evaluation {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="manager_id")
	private int managerId;
	
	@Column(name="employee_id")
	private int employeeId;
	
	@Column(name="comment")
	private String comment;
	
	@Column(name="months_period")
	private int monthsPeriod;
	
	@Column(name="start_date")
	private Date startDate; 
	
	@Column(name="end_date")
	private Date endDate; 
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="evaluation_id")
	private List<SoftSkill> softSkillList;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="evaluation_id")
	private List<HardSkill> hardSkillList;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="evaluation_id")
	private List<Commitment> commitmentList;
	
	
	
	public Evaluation() {
		
	}
	
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	
	public List<SoftSkill> getSoftSkillList() {
		return softSkillList;
	}


	public void setSoftSkillList(List<SoftSkill> softSkillList) {
		this.softSkillList = softSkillList;
	}


	public List<HardSkill> getHardSkillList() {
		return hardSkillList;
	}


	public void setHardSkillList(List<HardSkill> hardSkillList) {
		this.hardSkillList = hardSkillList;
	}


	public List<Commitment> getCommitmentList() {
		return commitmentList;
	}


	public void setCommitmentList(List<Commitment> commitmentList) {
		this.commitmentList = commitmentList;
	}


	
	public void addSoftSkill(SoftSkill softSkill) {
		if(softSkillList == null) {
			softSkillList = new ArrayList<>();
		}
		
		softSkillList.add(softSkill);
	}
	
	public void addHardSkill(HardSkill hardSkill) {
		if(hardSkillList == null) {
			hardSkillList = new ArrayList<>();
		}
		
		hardSkillList.add(hardSkill);
	}
	
	public void addCommitment(Commitment commitment) {
		if(commitmentList == null) {
			commitmentList = new ArrayList<>();
		}
		
		commitmentList.add(commitment);
	}
	
	
}
