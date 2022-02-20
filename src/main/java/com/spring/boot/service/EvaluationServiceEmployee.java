package com.spring.boot.service;

import java.time.LocalDate;
import java.util.List;

import com.spring.boot.dto.EvaluationEmployeeAllDTO;
import com.spring.boot.dto.EvaluationEmployeeCommitmentsDTO;
import com.spring.boot.dto.EvaluationEmployeeHardSkillsDTO;
import com.spring.boot.dto.EvaluationEmployeeSoftSkillsDTO;

public interface EvaluationServiceEmployee {

	public List<EvaluationEmployeeAllDTO> getAllEvaluationsForEmployee(int employeeId, LocalDate fromDate, LocalDate toDate, String sort, String sortType);
	
	public List<EvaluationEmployeeCommitmentsDTO> getCommitmentEvaluationsForEmployee(int employeeId, LocalDate fromDate, LocalDate toDate, String sort, String sortType);
	
	public List<EvaluationEmployeeHardSkillsDTO> getHardSkillEvaluationsForEmployee(int employeeId, LocalDate fromDate, LocalDate toDate, String sort, String sortType);
	
	public List<EvaluationEmployeeSoftSkillsDTO> getSoftSkillEvaluationsForEmployee(int employeeId, LocalDate fromDate, LocalDate toDate, String sort, String sortType);
	
}
