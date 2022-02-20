package com.spring.boot.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.spring.boot.dto.EvaluationEmployeeAllDTO;
import com.spring.boot.dto.EvaluationEmployeeCommitmentsDTO;
import com.spring.boot.dto.EvaluationEmployeeHardSkillsDTO;
import com.spring.boot.dto.EvaluationEmployeeSoftSkillsDTO;
import com.spring.boot.entity.Commitment;
import com.spring.boot.entity.Evaluation;
import com.spring.boot.entity.Evaluation_;
import com.spring.boot.entity.HardSkill;
import com.spring.boot.entity.SoftSkill;
import com.spring.boot.error.IncorrectBodyException;
import com.spring.boot.error.ItemNotFoundException;
import com.spring.boot.repository.EvaluationRepository;
import com.spring.boot.specification.EvaluationSpecification;
import com.spring.boot.specification.SearchCriteria;

@Service(value="evaluationServiceEmployee")
public class EvaluationServiceEmployeeImpl implements EvaluationServiceEmployee {
	
	@Autowired
	EvaluationRepository evaluationRepository;
	
	
	public List<EvaluationEmployeeAllDTO> getAllEvaluationsForEmployee(int employeeId, LocalDate fromDate, LocalDate toDate, String sort, String sortType) {
		
		Specification<Evaluation> evaluationSpecification = Specification.where(null);
		
		Specification<Evaluation> employeeIdSpecification = Specification.where(new EvaluationSpecification(new SearchCriteria(Evaluation_.EMPLOYEE_ID, "=", employeeId)));
		evaluationSpecification = evaluationSpecification.or(employeeIdSpecification);
		
		if(fromDate != null) {
			evaluationSpecification = evaluationSpecification.and(new EvaluationSpecification(new SearchCriteria(Evaluation_.START_DATE, ">=", Date.valueOf(fromDate))));
		}
		
		if(toDate != null) {
			evaluationSpecification = evaluationSpecification.and(new EvaluationSpecification(new SearchCriteria(Evaluation_.END_DATE, "<=", Date.valueOf(toDate))));
		}
		
		
		if(fromDate != null && toDate != null && fromDate.isAfter(toDate) ) {
			throw new IncorrectBodyException("the date fromDate must be before the date toDate");
		}
		
		if(sort != null && sortType != null && !sortType.toLowerCase().equals("asc") && !sortType.toLowerCase().equals("ascending") && !sortType.toLowerCase().equals("desc") && !sortType.toLowerCase().equals("descending")) {
			throw new IncorrectBodyException("Invalid Sort type, it can only be asc, ascending, desc or descending");
		}
		
		List<Evaluation> evaluationList = null;
		
		if(sort != null) {
			if(sort.toLowerCase().equals("evaluationid")) sort = "id";
			else if(sort.toLowerCase().equals("monthsperiod")) sort = "monthsPeriod";
			else if(sort.toLowerCase().equals("startdate")) sort = "startDate";
			else if(sort.toLowerCase().equals("enddate")) sort = "endDate";
			else throw new ItemNotFoundException("Sort item not found");
			
			if(sortType == null || sortType.toLowerCase().equals("asc") || sortType.toLowerCase().equals("ascending")) {

				evaluationList = evaluationRepository.findAll(evaluationSpecification, Sort.by(Sort.Direction.ASC, sort));
			}
			else {

				evaluationList = evaluationRepository.findAll(evaluationSpecification, Sort.by(Sort.Direction.DESC, sort));
			}
		}
		else {
			
			evaluationList = evaluationRepository.findAll(evaluationSpecification);
		}
		
		
		EvaluationEmployeeAllDTO evaluation = null;
		List<EvaluationEmployeeAllDTO> evaluationAllList = new ArrayList<>();
		
		for(Evaluation ev : evaluationList) {
			evaluation = new EvaluationEmployeeAllDTO();
			evaluation.setEvaluationId(ev.getId());
			evaluation.setEmployeeId(ev.getEmployeeId());
			evaluation.setMonthsPeriod(ev.getMonthsPeriod());
			evaluation.setStartDate(ev.getStartDate());
			evaluation.setEndDate(ev.getEndDate());
			
			SoftSkill[] softSkillArray = ev.getSoftSkillList().toArray(new SoftSkill[ev.getSoftSkillList().size()]);
			Arrays.sort(softSkillArray);
			ArrayList<SoftSkill> softSkillList = new ArrayList<>(Arrays.asList(softSkillArray));
			
			HardSkill[] hardSkillArray = ev.getHardSkillList().toArray(new HardSkill[ev.getHardSkillList().size()]);
			Arrays.sort(hardSkillArray);
			ArrayList<HardSkill> hardSkillList = new ArrayList<>(Arrays.asList(hardSkillArray));
			
			Commitment[] commitmentArray = ev.getCommitmentList().toArray(new Commitment[ev.getCommitmentList().size()]);
			Arrays.sort(commitmentArray);
			ArrayList<Commitment> commitmentList = new ArrayList<>(Arrays.asList(commitmentArray));
			
			evaluation.getPerformanceAll().setCommitmentList(commitmentList);
			evaluation.getPerformanceAll().setHardSkillList(hardSkillList);
			evaluation.getPerformanceAll().setSoftSkillList(softSkillList);
			
			evaluationAllList.add(evaluation);
		}
		
		
		if(evaluationAllList.isEmpty()) {
			throw new ItemNotFoundException("employee id or date not found");
		}
		
		return evaluationAllList;
	
	}
	
	
	public List<EvaluationEmployeeCommitmentsDTO> getCommitmentEvaluationsForEmployee(int employeeId, LocalDate fromDate, LocalDate toDate, String sort, String sortType) {
		
		Specification<Evaluation> evaluationSpecification = Specification.where(null);
		
		Specification<Evaluation> employeeIdSpecification = Specification.where(new EvaluationSpecification(new SearchCriteria(Evaluation_.EMPLOYEE_ID, "=", employeeId)));
		evaluationSpecification = evaluationSpecification.or(employeeIdSpecification);
		
		if(fromDate != null) {
			evaluationSpecification = evaluationSpecification.and(new EvaluationSpecification(new SearchCriteria(Evaluation_.START_DATE, ">=", Date.valueOf(fromDate))));
		}
		
		if(toDate != null) {
			evaluationSpecification = evaluationSpecification.and(new EvaluationSpecification(new SearchCriteria(Evaluation_.END_DATE, "<=", Date.valueOf(toDate))));
		}
		
		
		if(fromDate != null && toDate != null && fromDate.isAfter(toDate) ) {
			throw new IncorrectBodyException("the date fromDate must be before the date toDate");
		}
		
		if(sort != null && sortType != null && !sortType.toLowerCase().equals("asc") && !sortType.toLowerCase().equals("ascending") && !sortType.toLowerCase().equals("desc") && !sortType.toLowerCase().equals("descending")) {
			throw new IncorrectBodyException("Invalid Sort type, it can only be asc, ascending, desc or descending");
		}
		
		List<Evaluation> evaluationList = null;
		
		if(sort != null) {
			if(sort.toLowerCase().equals("evaluationid")) sort = "id";
			else if(sort.toLowerCase().equals("monthsperiod")) sort = "monthsPeriod";
			else if(sort.toLowerCase().equals("startdate")) sort = "startDate";
			else if(sort.toLowerCase().equals("enddate")) sort = "endDate";
			else throw new ItemNotFoundException("Sort item not found");
			
			if(sortType == null || sortType.toLowerCase().equals("asc") || sortType.toLowerCase().equals("ascending")) {

				evaluationList = evaluationRepository.findAll(evaluationSpecification, Sort.by(Sort.Direction.ASC, sort));
			}
			else {

				evaluationList = evaluationRepository.findAll(evaluationSpecification, Sort.by(Sort.Direction.DESC, sort));
			}
		}
		else {
			
			evaluationList = evaluationRepository.findAll(evaluationSpecification);
		}
		
		
		EvaluationEmployeeCommitmentsDTO evaluation = null;
		List<EvaluationEmployeeCommitmentsDTO> evaluationAllList = new ArrayList<>();
		
		for(Evaluation ev : evaluationList) {
			evaluation = new EvaluationEmployeeCommitmentsDTO();
			evaluation.setEvaluationId(ev.getId());
			evaluation.setEmployeeId(ev.getEmployeeId());
			evaluation.setMonthsPeriod(ev.getMonthsPeriod());
			evaluation.setStartDate(ev.getStartDate());
			evaluation.setEndDate(ev.getEndDate());
			
			Commitment[] commitmentArray = ev.getCommitmentList().toArray(new Commitment[ev.getCommitmentList().size()]);
			Arrays.sort(commitmentArray);
			ArrayList<Commitment> commitmentList = new ArrayList<>(Arrays.asList(commitmentArray));
			
			evaluation.getPerformanceCommitment().setCommitmentList(commitmentList);
			
			evaluationAllList.add(evaluation);
		}
		
		
		if(evaluationAllList.isEmpty()) {
			throw new ItemNotFoundException("employee id or date not found");
		}
		
		return evaluationAllList;
	
	}
	
	
	public List<EvaluationEmployeeHardSkillsDTO> getHardSkillEvaluationsForEmployee(int employeeId, LocalDate fromDate, LocalDate toDate, String sort, String sortType) {
		
		Specification<Evaluation> evaluationSpecification = Specification.where(null);
		
		Specification<Evaluation> employeeIdSpecification = Specification.where(new EvaluationSpecification(new SearchCriteria(Evaluation_.EMPLOYEE_ID, "=", employeeId)));
		evaluationSpecification = evaluationSpecification.or(employeeIdSpecification);
		
		if(fromDate != null) {
			evaluationSpecification = evaluationSpecification.and(new EvaluationSpecification(new SearchCriteria(Evaluation_.START_DATE, ">=", Date.valueOf(fromDate))));
		}
		
		if(toDate != null) {
			evaluationSpecification = evaluationSpecification.and(new EvaluationSpecification(new SearchCriteria(Evaluation_.END_DATE, "<=", Date.valueOf(toDate))));
		}
		
		
		if(fromDate != null && toDate != null && fromDate.isAfter(toDate) ) {
			throw new IncorrectBodyException("the date fromDate must be before the date toDate");
		}
		
		if(sort != null && sortType != null && !sortType.toLowerCase().equals("asc") && !sortType.toLowerCase().equals("ascending") && !sortType.toLowerCase().equals("desc") && !sortType.toLowerCase().equals("descending")) {
			throw new IncorrectBodyException("Invalid Sort type, it can only be asc, ascending, desc or descending");
		}
		
		List<Evaluation> evaluationList = null;
		
		if(sort != null) {
			if(sort.toLowerCase().equals("evaluationid")) sort = "id";
			else if(sort.toLowerCase().equals("monthsperiod")) sort = "monthsPeriod";
			else if(sort.toLowerCase().equals("startdate")) sort = "startDate";
			else if(sort.toLowerCase().equals("enddate")) sort = "endDate";
			else throw new ItemNotFoundException("Sort item not found");
			
			if(sortType == null || sortType.toLowerCase().equals("asc") || sortType.toLowerCase().equals("ascending")) {

				evaluationList = evaluationRepository.findAll(evaluationSpecification, Sort.by(Sort.Direction.ASC, sort));
			}
			else {

				evaluationList = evaluationRepository.findAll(evaluationSpecification, Sort.by(Sort.Direction.DESC, sort));
			}
		}
		else {
			
			evaluationList = evaluationRepository.findAll(evaluationSpecification);
		}
		
		
		EvaluationEmployeeHardSkillsDTO evaluation = null;
		List<EvaluationEmployeeHardSkillsDTO> evaluationAllList = new ArrayList<>();
		
		for(Evaluation ev : evaluationList) {
			evaluation = new EvaluationEmployeeHardSkillsDTO();
			evaluation.setEvaluationId(ev.getId());
			evaluation.setEmployeeId(ev.getEmployeeId());
			evaluation.setMonthsPeriod(ev.getMonthsPeriod());
			evaluation.setStartDate(ev.getStartDate());
			evaluation.setEndDate(ev.getEndDate());
			
			HardSkill[] hardSkillArray = ev.getHardSkillList().toArray(new HardSkill[ev.getHardSkillList().size()]);
			Arrays.sort(hardSkillArray);
			ArrayList<HardSkill> hardSkillList = new ArrayList<>(Arrays.asList(hardSkillArray));
			
			evaluation.getPerformanceHardSkill().setHardSkillList(hardSkillList);
			
			evaluationAllList.add(evaluation);
		}
		
		
		if(evaluationAllList.isEmpty()) {
			throw new ItemNotFoundException("employee id or date not found");
		}
		
		return evaluationAllList;
	
	}
	
	
	public List<EvaluationEmployeeSoftSkillsDTO> getSoftSkillEvaluationsForEmployee(int employeeId, LocalDate fromDate, LocalDate toDate, String sort, String sortType) {
		
		Specification<Evaluation> evaluationSpecification = Specification.where(null);
		
		Specification<Evaluation> employeeIdSpecification = Specification.where(new EvaluationSpecification(new SearchCriteria(Evaluation_.EMPLOYEE_ID, "=", employeeId)));
		evaluationSpecification = evaluationSpecification.or(employeeIdSpecification);
		
		if(fromDate != null) {
			evaluationSpecification = evaluationSpecification.and(new EvaluationSpecification(new SearchCriteria(Evaluation_.START_DATE, ">=", Date.valueOf(fromDate))));
		}
		
		if(toDate != null) {
			evaluationSpecification = evaluationSpecification.and(new EvaluationSpecification(new SearchCriteria(Evaluation_.END_DATE, "<=", Date.valueOf(toDate))));
		}
		
		
		if(fromDate != null && toDate != null && fromDate.isAfter(toDate) ) {
			throw new IncorrectBodyException("the date fromDate must be before the date toDate");
		}
		
		if(sort != null && sortType != null && !sortType.toLowerCase().equals("asc") && !sortType.toLowerCase().equals("ascending") && !sortType.toLowerCase().equals("desc") && !sortType.toLowerCase().equals("descending")) {
			throw new IncorrectBodyException("Invalid Sort type, it can only be asc, ascending, desc or descending");
		}
		
		List<Evaluation> evaluationList = null;
		
		if(sort != null) {
			if(sort.toLowerCase().equals("evaluationid")) sort = "id";
			else if(sort.toLowerCase().equals("monthsperiod")) sort = "monthsPeriod";
			else if(sort.toLowerCase().equals("startdate")) sort = "startDate";
			else if(sort.toLowerCase().equals("enddate")) sort = "endDate";
			else throw new ItemNotFoundException("Sort item not found");
			
			if(sortType == null || sortType.toLowerCase().equals("asc") || sortType.toLowerCase().equals("ascending")) {

				evaluationList = evaluationRepository.findAll(evaluationSpecification, Sort.by(Sort.Direction.ASC, sort));
			}
			else {
			
				evaluationList = evaluationRepository.findAll(evaluationSpecification, Sort.by(Sort.Direction.DESC, sort));
			}
		}
		else {
			
			evaluationList = evaluationRepository.findAll(evaluationSpecification);
		}
		
		
		EvaluationEmployeeSoftSkillsDTO evaluation = null;
		List<EvaluationEmployeeSoftSkillsDTO> evaluationAllList = new ArrayList<>();
		
		for(Evaluation ev : evaluationList) {
			evaluation = new EvaluationEmployeeSoftSkillsDTO();
			evaluation.setEvaluationId(ev.getId());
			evaluation.setEmployeeId(ev.getEmployeeId());
			evaluation.setMonthsPeriod(ev.getMonthsPeriod());
			evaluation.setStartDate(ev.getStartDate());
			evaluation.setEndDate(ev.getEndDate());
			
			SoftSkill[] softSkillArray = ev.getSoftSkillList().toArray(new SoftSkill[ev.getSoftSkillList().size()]);
			Arrays.sort(softSkillArray);
			ArrayList<SoftSkill> softSkillList = new ArrayList<>(Arrays.asList(softSkillArray));
			
			evaluation.getPerformanceSoftSkill().setSoftSkillList(softSkillList);
			
			evaluationAllList.add(evaluation);
		}
		
		
		if(evaluationAllList.isEmpty()) {
			throw new ItemNotFoundException("employee id or date not found");
		}
		
		return evaluationAllList;
	
	}
}
