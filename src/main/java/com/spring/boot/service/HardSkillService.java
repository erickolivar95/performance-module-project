package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.PerformancePostDTO;
import com.spring.boot.dto.PerformanceUpdateDTO;
import com.spring.boot.entity.Evaluation;
import com.spring.boot.entity.HardSkill;

public interface HardSkillService {

	public HardSkill addHardSkill(PerformancePostDTO body, List<Evaluation> evaluationList);
	
	public HardSkill updateHardSkill(PerformanceUpdateDTO body, int hardSkillId, List<Evaluation> evaluationList);
	
}
