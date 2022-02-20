package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.PerformancePostDTO;
import com.spring.boot.dto.PerformanceUpdateDTO;
import com.spring.boot.entity.Evaluation;
import com.spring.boot.entity.SoftSkill;

public interface SoftSkillService {

	public SoftSkill addSoftSkill(PerformancePostDTO body, List<Evaluation> evaluationList);
	
	public SoftSkill updateSoftSkill(PerformanceUpdateDTO body, int softSkillId, List<Evaluation> evaluationList);
	
}
