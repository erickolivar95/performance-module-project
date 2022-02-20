package com.spring.boot.dto;

import java.util.List;

import com.spring.boot.entity.Commitment;
import com.spring.boot.entity.HardSkill;
import com.spring.boot.entity.SoftSkill;

public class PerformanceAll {

	private List<HardSkill> hardSkillList;
	
	private List<SoftSkill> softSkillList;
	
	private List<Commitment> commitmentList;
	
	

	public List<HardSkill> getHardSkillList() {
		return hardSkillList;
	}

	public void setHardSkillList(List<HardSkill> hardSkillList) {
		this.hardSkillList = hardSkillList;
	}

	public List<SoftSkill> getSoftSkillList() {
		return softSkillList;
	}

	public void setSoftSkillList(List<SoftSkill> softSkillList) {
		this.softSkillList = softSkillList;
	}

	public List<Commitment> getCommitmentList() {
		return commitmentList;
	}

	public void setCommitmentList(List<Commitment> commitmentList) {
		this.commitmentList = commitmentList;
	}
	
	
	
}
