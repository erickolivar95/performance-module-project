package com.spring.boot.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.sql.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.spring.boot.dto.EvaluationAllDTO;
import com.spring.boot.dto.EvaluationCommitmentsDTO;
import com.spring.boot.dto.EvaluationHardSkillsDTO;
import com.spring.boot.dto.EvaluationPostDTO;
import com.spring.boot.dto.EvaluationSoftSkillsDTO;
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

@Service(value="evaluationServiceManager")
public class EvaluationServiceImpl implements EvaluationService {

	@Autowired
	EvaluationRepository evaluationRepository;
	
	
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
	
	
	public List<Evaluation> findAll() {
		return (List<Evaluation>) evaluationRepository.findAll();
	}
	
	public List<Evaluation> getEvaluationsByManagerAndEmployeeId(int managerId, int employeeId) {
		return evaluationRepository.getEvaluationsByManagerAndEmployeeId(managerId, employeeId);
	}
	
	
	
	
	public Evaluation addEvaluation(EvaluationPostDTO body) throws ParseException {
		Date finalDate=null; 
		Evaluation evap = new Evaluation();
		
		boolean isPresent = false; //only for testing purposes
		
		
		List<Evaluation> evaluationList = evaluationRepository.getEvaluationsByManagerId(body.getManagerId());
		
		for(Evaluation ev : evaluationList) {
			
			if(body.getEmployeeId().equals(ev.getEmployeeId())) {
				isPresent = true;
				if(currentDate.before(ev.getEndDate())) {
					throw new IncorrectBodyException("The evaluation of the employee has not finished yet");
				}

			}
			
		}
		
		if(evaluationList.isEmpty()) {
			List<Evaluation> evaluationByEmployee = evaluationRepository.getEvaluationsByEmployeeId(body.getEmployeeId());
			if(!evaluationByEmployee.isEmpty()) {
				throw new IncorrectBodyException("The id of the employee given already belongs to another manager");
			}
		}
		
		
		evap.setManagerId(body.getManagerId());
		evap.setComment(body.getComment());
		evap.setEmployeeId(body.getEmployeeId());
		evap.setMonthsPeriod(body.getMonthsPeriod());
		
		if(body.getConditionTest()) { //this is is only for testing purposes, in the final version this if will be removed, and the content in the if block (not the else block) will be left and the content in the else block will be removed
			finalDate = getFinalDate(body.getMonthsPeriod());
			evap.setStartDate(currentDate);
			evap.setEndDate(finalDate);
		}
		else {
			Date currentDateTest = null;
			Date nextDateTest = null;
			
			if(isPresent) {
				currentDateTest = Date.valueOf("2022-01-01");
				nextDateTest = Date.valueOf("2022-01-31");
			}
			else {
				currentDateTest = Date.valueOf("2021-01-01");
				nextDateTest = Date.valueOf("2021-12-31");
			}
			
			evap.setStartDate(currentDateTest);
			evap.setEndDate(nextDateTest);
		}
		

		evaluationRepository.save(evap);
		
		return evap;
	}
	
	
	
	
	
	
	public List<EvaluationAllDTO> getAllEvaluationAllEmployees(Integer managerId, Integer employeeId, LocalDate fromDate, LocalDate toDate, String sort, String sortType) {
		
		Specification<Evaluation> evaluationSpecification = Specification.where(null);
		
		Specification<Evaluation> managerIdSpecification = Specification.where(new EvaluationSpecification(new SearchCriteria(Evaluation_.MANAGER_ID, "=", managerId)));
		evaluationSpecification = evaluationSpecification.or(managerIdSpecification);
		
		if(employeeId != null) {
			evaluationSpecification = evaluationSpecification.and(new EvaluationSpecification(new SearchCriteria(Evaluation_.EMPLOYEE_ID, "=", employeeId)));
		}
		
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
			else if(sort.toLowerCase().equals("employeeid")) sort = "employeeId";
			else if(sort.toLowerCase().equals("comment")) sort = "comment";
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
		
		
		EvaluationAllDTO evaluationAll = null;
		List<EvaluationAllDTO> evaluationAllList = new ArrayList<>();
		
		for(Evaluation ev : evaluationList) {
			evaluationAll = getInfoEvaluationAll(ev);
			evaluationAllList.add(evaluationAll);
		}
		
		
		if(evaluationAllList.isEmpty()) {
			throw new ItemNotFoundException("Manager or employee id or date not found");
		}
		
		return evaluationAllList;
		
	}

	
	
	
	public List<EvaluationCommitmentsDTO> getCommitmentEvaluationsAllEmployees(Integer managerId, Integer employeeId, LocalDate fromDate, LocalDate toDate, String sort, String sortType) {
		
		Specification<Evaluation> evaluationSpecification = Specification.where(null);
		
		Specification<Evaluation> managerIdSpecification = Specification.where(new EvaluationSpecification(new SearchCriteria(Evaluation_.MANAGER_ID, "=", managerId)));
		evaluationSpecification = evaluationSpecification.or(managerIdSpecification);
		
		if(employeeId != null) {
			evaluationSpecification = evaluationSpecification.and(new EvaluationSpecification(new SearchCriteria(Evaluation_.EMPLOYEE_ID, "=", employeeId)));
		}
		
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
			else if(sort.toLowerCase().equals("employeeid")) sort = "employeeId";
			else if(sort.toLowerCase().equals("comment")) sort = "comment";
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
		
		
		EvaluationCommitmentsDTO evaluationCommitments = null;
		List<EvaluationCommitmentsDTO> evaluationCommitmentsList = new ArrayList<>();
		
		for(Evaluation ev : evaluationList) {
			evaluationCommitments = getInfoEvaluationCommitments(ev);
			evaluationCommitmentsList.add(evaluationCommitments);
		}
		
		if(evaluationCommitmentsList.isEmpty()) {
			throw new ItemNotFoundException("Manager or employee id or date not found");
		}
		
		
		return evaluationCommitmentsList;
	
	}
	
	

	
	
	public List<EvaluationHardSkillsDTO> getHardSkillEvaluationsAllEmployees(Integer managerId, Integer employeeId, LocalDate fromDate, LocalDate toDate, String sort, String sortType) {
		Specification<Evaluation> evaluationSpecification = Specification.where(null);
		
		Specification<Evaluation> managerIdSpecification = Specification.where(new EvaluationSpecification(new SearchCriteria(Evaluation_.MANAGER_ID, "=", managerId)));
		evaluationSpecification = evaluationSpecification.or(managerIdSpecification);
		
		if(employeeId != null) {
			evaluationSpecification = evaluationSpecification.and(new EvaluationSpecification(new SearchCriteria(Evaluation_.EMPLOYEE_ID, "=", employeeId)));
		}
		
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
			else if(sort.toLowerCase().equals("employeeid")) sort = "employeeId";
			else if(sort.toLowerCase().equals("comment")) sort = "comment";
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
		
		EvaluationHardSkillsDTO evaluationHardSkills = null;
		List<EvaluationHardSkillsDTO> evaluationHardSkillsList = new ArrayList<>();
		
		for(Evaluation ev : evaluationList) {
			evaluationHardSkills = getInfoEvaluationHardSkills(ev);	
			evaluationHardSkillsList.add(evaluationHardSkills);
		}
		
		if(evaluationHardSkillsList.isEmpty()) {
			throw new ItemNotFoundException("Manager or employee id or date not found");
		}
		
		
		return evaluationHardSkillsList;
	
	}
	
	
	
	public List<EvaluationSoftSkillsDTO> getSoftSkillEvaluationsAllEmployees(Integer managerId, Integer employeeId, LocalDate fromDate, LocalDate toDate, String sort, String sortType) {
		Specification<Evaluation> evaluationSpecification = Specification.where(null);
		
		Specification<Evaluation> managerIdSpecification = Specification.where(new EvaluationSpecification(new SearchCriteria(Evaluation_.MANAGER_ID, "=", managerId)));
		evaluationSpecification = evaluationSpecification.or(managerIdSpecification);
		
		if(employeeId != null) {
			evaluationSpecification = evaluationSpecification.and(new EvaluationSpecification(new SearchCriteria(Evaluation_.EMPLOYEE_ID, "=", employeeId)));
		}
		
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
			else if(sort.toLowerCase().equals("employeeid")) sort = "employeeId";
			else if(sort.toLowerCase().equals("comment")) sort = "comment";
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
		
		
		EvaluationSoftSkillsDTO evaluationSoftSkills = null;
		List<EvaluationSoftSkillsDTO> evaluationSoftSkillsList = new ArrayList<>();
		
		for(Evaluation ev : evaluationList) {
			evaluationSoftSkills = getInfoEvaluationSoftSkills(ev);	
			evaluationSoftSkillsList.add(evaluationSoftSkills);
		}
		
		if(evaluationSoftSkillsList.isEmpty()) {
			throw new ItemNotFoundException("Manager or employee id or date not found");
		}
		
		
		return evaluationSoftSkillsList;
	
	}
	
	
	
	
	private EvaluationAllDTO getInfoEvaluationAll(Evaluation ev) {
		EvaluationAllDTO evaluationAll = new EvaluationAllDTO();
		evaluationAll.setEvaluationId(ev.getId());
		evaluationAll.setManagerId(ev.getManagerId());
		evaluationAll.setEmployeeId(ev.getEmployeeId());
		evaluationAll.setComment(ev.getComment());
		evaluationAll.setMonthsPeriod(ev.getMonthsPeriod());
		evaluationAll.setStartDate(ev.getStartDate());
		evaluationAll.setEndDate(ev.getEndDate());
		
		SoftSkill[] softSkillArray = ev.getSoftSkillList().toArray(new SoftSkill[ev.getSoftSkillList().size()]);
		Arrays.sort(softSkillArray);
		ArrayList<SoftSkill> softSkillList = new ArrayList<>(Arrays.asList(softSkillArray));
		
		HardSkill[] hardSkillArray = ev.getHardSkillList().toArray(new HardSkill[ev.getHardSkillList().size()]);
		Arrays.sort(hardSkillArray);
		ArrayList<HardSkill> hardSkillList = new ArrayList<>(Arrays.asList(hardSkillArray));
		
		Commitment[] commitmentArray = ev.getCommitmentList().toArray(new Commitment[ev.getCommitmentList().size()]);
		Arrays.sort(commitmentArray);
		ArrayList<Commitment> commitmentList = new ArrayList<>(Arrays.asList(commitmentArray));
		
		evaluationAll.getPerformanceAll().setCommitmentList(commitmentList);
		evaluationAll.getPerformanceAll().setHardSkillList(hardSkillList);
		evaluationAll.getPerformanceAll().setSoftSkillList(softSkillList);
		return evaluationAll;
	}
	
	private EvaluationCommitmentsDTO getInfoEvaluationCommitments(Evaluation ev) {
		EvaluationCommitmentsDTO evaluationCommitments = new EvaluationCommitmentsDTO();
		evaluationCommitments.setEvaluationId(ev.getId());
		evaluationCommitments.setManagerId(ev.getManagerId());
		evaluationCommitments.setEmployeeId(ev.getEmployeeId());
		evaluationCommitments.setComment(ev.getComment());
		evaluationCommitments.setMonthsPeriod(ev.getMonthsPeriod());
		evaluationCommitments.setStartDate(ev.getStartDate());
		evaluationCommitments.setEndDate(ev.getEndDate());
		
		Commitment[] commitmentArray = ev.getCommitmentList().toArray(new Commitment[ev.getCommitmentList().size()]);
		Arrays.sort(commitmentArray);
		ArrayList<Commitment> commitmentList = new ArrayList<>(Arrays.asList(commitmentArray));
		
		evaluationCommitments.getPerformanceCommitment().setCommitmentList(commitmentList);
		return evaluationCommitments;
	}
	
	private EvaluationHardSkillsDTO getInfoEvaluationHardSkills(Evaluation ev) {
		EvaluationHardSkillsDTO evaluationHardSkills = new EvaluationHardSkillsDTO();
		evaluationHardSkills.setEvaluationId(ev.getId());
		evaluationHardSkills.setManagerId(ev.getManagerId());
		evaluationHardSkills.setEmployeeId(ev.getEmployeeId());
		evaluationHardSkills.setComment(ev.getComment());
		evaluationHardSkills.setMonthsPeriod(ev.getMonthsPeriod());
		evaluationHardSkills.setStartDate(ev.getStartDate());
		evaluationHardSkills.setEndDate(ev.getEndDate());
		
		HardSkill[] hardSkillArray = ev.getHardSkillList().toArray(new HardSkill[ev.getHardSkillList().size()]);
		Arrays.sort(hardSkillArray);
		ArrayList<HardSkill> hardSkillList = new ArrayList<>(Arrays.asList(hardSkillArray));
		
		evaluationHardSkills.getPerformanceHardSkill().setHardSkillList(hardSkillList);
		return evaluationHardSkills;
	}
	
	private EvaluationSoftSkillsDTO getInfoEvaluationSoftSkills(Evaluation ev) {
		EvaluationSoftSkillsDTO evaluationSoftSkills = new EvaluationSoftSkillsDTO();
		evaluationSoftSkills.setEvaluationId(ev.getId());
		evaluationSoftSkills.setManagerId(ev.getManagerId());
		evaluationSoftSkills.setEmployeeId(ev.getEmployeeId());
		evaluationSoftSkills.setComment(ev.getComment());
		evaluationSoftSkills.setMonthsPeriod(ev.getMonthsPeriod());
		evaluationSoftSkills.setStartDate(ev.getStartDate());
		evaluationSoftSkills.setEndDate(ev.getEndDate());
		
		SoftSkill[] softSkillArray = ev.getSoftSkillList().toArray(new SoftSkill[ev.getSoftSkillList().size()]);
		Arrays.sort(softSkillArray);
		ArrayList<SoftSkill> softSkillList = new ArrayList<>(Arrays.asList(softSkillArray));
		
		evaluationSoftSkills.getPerformanceSoftSkill().setSoftSkillList(softSkillList);
		return evaluationSoftSkills;
	}
	
	
	private Date getFinalDate(int months) throws ParseException { 

		java.util.Date date = new java.util.Date();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 0);
		calendar.add(Calendar.MONTH, months);
		
		java.util.Date nextdate = calendar.getTime();
		
		@SuppressWarnings("deprecation")
		int day1 = nextdate.getDate();
		String dayFinal1 = String.valueOf(day1);
		
		@SuppressWarnings("deprecation")
		int month1 = nextdate.getMonth()+1;
		String monthFinal1 = String.valueOf(month1);
		
		@SuppressWarnings("deprecation")
		int year1 = nextdate.getYear()+1900;
		
		if(day1<10) {
			dayFinal1 = "0" + dayFinal1;
		}
		
		if(month1<10) {
			monthFinal1 = "0" + monthFinal1;
		}
		
		String nextDateString = year1 + "-" + monthFinal1 + "-" + dayFinal1;
		
		return Date.valueOf(nextDateString);
				
	}
	
}
