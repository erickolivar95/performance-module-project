package com.spring.boot.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import com.spring.boot.dto.EvaluationAllDTO;
import com.spring.boot.dto.EvaluationCommitmentsDTO;
import com.spring.boot.dto.EvaluationHardSkillsDTO;
import com.spring.boot.dto.EvaluationPostDTO;
import com.spring.boot.dto.EvaluationSoftSkillsDTO;
import com.spring.boot.entity.Evaluation;

public interface EvaluationService {

	public List<Evaluation> findAll();
	
	public List<Evaluation> getEvaluationsByManagerAndEmployeeId(int managerId, int employeeId);
	
	
	
	
	public Evaluation addEvaluation(EvaluationPostDTO body) throws ParseException;
	
	public List<EvaluationAllDTO> getAllEvaluationAllEmployees(Integer managerId, Integer employeeId, LocalDate fromDate, LocalDate toDate, String sort, String sortType);
	
	
	public List<EvaluationCommitmentsDTO> getCommitmentEvaluationsAllEmployees(Integer managerId, Integer employeeId, LocalDate fromDate, LocalDate toDate, String sort, String sortType);
	
	
	public List<EvaluationHardSkillsDTO> getHardSkillEvaluationsAllEmployees(Integer managerId, Integer employeeId, LocalDate fromDate, LocalDate toDate, String sort, String sortType);
	
	
	public List<EvaluationSoftSkillsDTO> getSoftSkillEvaluationsAllEmployees(Integer managerId, Integer employeeId, LocalDate fromDate, LocalDate toDate, String sort, String sortType);
	
	
}
