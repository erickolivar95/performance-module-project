package com.spring.boot.service;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dto.PerformancePostDTO;
import com.spring.boot.dto.PerformanceUpdateDTO;
import com.spring.boot.entity.Evaluation;
import com.spring.boot.entity.SoftSkill;
import com.spring.boot.error.IncorrectBodyException;
import com.spring.boot.error.ItemNotFoundException;
import com.spring.boot.repository.SoftSkillRepository;

@Service(value="softSkillService")
public class SoftSkillServiceImpl implements SoftSkillService {
	
	@Autowired
	SoftSkillRepository softSkillRepository;
	
	
	private Date currentDate;
	
	@PostConstruct
	public void addConfig() throws ParseException {
		
		
		java.util.Date date = new java.util.Date();
		
		@SuppressWarnings("deprecation")
		int day = date.getDate();
		String dayFinal = String.valueOf(day);
		
		@SuppressWarnings("deprecation")
		int month = date.getMonth()+1;
		String monthFinal = String.valueOf(month);
		
		@SuppressWarnings("deprecation")
		int year = date.getYear()+1900;
		
		if(day<10) {
			dayFinal = "0" + dayFinal;
		}
		if(month<10) {
			monthFinal = "0" + monthFinal;
		}
		
		String currentDateString = year + "-" + monthFinal + "-" + dayFinal;
		
		currentDate = Date.valueOf(currentDateString);
		
	}
	
	
	public SoftSkill addSoftSkill(PerformancePostDTO body, List<Evaluation> evaluationList) {
	
		if(evaluationList.isEmpty()) {
			throw new ItemNotFoundException("Manager or employee id not found");
		}
		
		SoftSkill softSkill = new SoftSkill(body.getName(), body.getDescription());
		
		if(evaluationList.size() == 1) {
			if(currentDate.after(evaluationList.get(0).getEndDate())) {
				evaluationList.get(0).addSoftSkill(softSkill);
			}
			else {
				throw new IncorrectBodyException("The period of the evaluation is not finished yet");
			}
		}
		else {
			if(body.getEvaluationId() != null) {
				boolean isPresent=false;
				for(Evaluation ev : evaluationList) {
					if(body.getEvaluationId().equals(ev.getId())) {
						if(currentDate.before(ev.getEndDate())) {
							throw new IncorrectBodyException("The period of the evaluation is not finished yet");
						}
						isPresent = true;
						ev.addSoftSkill(softSkill);
						break;
					}
				}
				
				if(!isPresent) {
					throw new IncorrectBodyException("The evaluation id given is not correct");
				}
				
			}
			else {
				throw new IncorrectBodyException("When the manager id and the employee id given is more than once in the evaluation table, then you must specify the evaluation id ");
			}
		}
		
		softSkillRepository.save(softSkill);
		
		return softSkill;
	}
	
	
	
	public SoftSkill updateSoftSkill(PerformanceUpdateDTO body, int softSkillId, List<Evaluation> evaluationList) {
		
		if(evaluationList.isEmpty()) {
			throw new ItemNotFoundException("Manager or employee id not found");
		}
		
		SoftSkill softSkill = null;
		
		boolean isValidId = false;
		
		for(Evaluation ev : evaluationList) {
			for(SoftSkill soft : ev.getSoftSkillList()) {
				if(soft.getId() == softSkillId) {
					isValidId = true;
					softSkill = soft;
					break;
				}	
			}
			
			if(isValidId) break;
		}
		
		
		if(isValidId) {
			softSkill.setName(body.getName());
			softSkill.setDescription(body.getDescription());
		}
		else {
			throw new ItemNotFoundException("Hard skill id not found");
		}
		
		
		softSkillRepository.save(softSkill);
		
		return softSkill;
	}
	
	
}
