package com.spring.boot.dto;

import java.util.List;

import com.spring.boot.entity.SoftSkill;

public class PerformanceSoftSkill {
	
	private List<SoftSkill> softSkillList;
	
	

	public List<SoftSkill> getSoftSkillList() {
		return softSkillList;
	}

	public void setSoftSkillList(List<SoftSkill> softSkillList) {
		this.softSkillList = softSkillList;
	}	
	
	
}
