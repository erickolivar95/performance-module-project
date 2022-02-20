package com.spring.boot.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.dto.EvaluationAllDTO;
import com.spring.boot.dto.EvaluationCommitmentsDTO;
import com.spring.boot.dto.EvaluationHardSkillsDTO;
import com.spring.boot.dto.EvaluationPostDTO;
import com.spring.boot.dto.EvaluationSoftSkillsDTO;
import com.spring.boot.dto.PerformancePostDTO;
import com.spring.boot.dto.PerformanceUpdateDTO;
import com.spring.boot.entity.Evaluation;
import com.spring.boot.entity.Commitment;
import com.spring.boot.entity.HardSkill;
import com.spring.boot.entity.SoftSkill;
import com.spring.boot.error.ErrorDTO;
import com.spring.boot.error.IncorrectBodyException;
import com.spring.boot.service.CommitmentService;
import com.spring.boot.service.EvaluationService;
import com.spring.boot.service.HardSkillService;
import com.spring.boot.service.SoftSkillService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/managers")
public class ManagerController {
	
	
	@Autowired
	EvaluationService evaluationServiceManager;
	
	@Autowired
	HardSkillService hardSkillService;
	
	@Autowired
	SoftSkillService softSkillService;
	
	@Autowired
	CommitmentService commitmentService;
	
	
	
	
	@PostMapping("/evaluations")
	@Operation(summary="Add a registry of evaluation of a manager to an employee", responses = {
			@ApiResponse(description="Evaluation added successfully", responseCode="201",
					content=@Content(mediaType="application/json", schema=@Schema(implementation=Evaluation.class))),
			@ApiResponse(description="This type of employee can not access here", responseCode="403", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Incorrect request", responseCode="400", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class)))})
	public ResponseEntity<Object> addEvaluation(@RequestBody @Valid EvaluationPostDTO body, BindingResult bindingResult) throws ParseException {
		
		if(bindingResult.hasErrors()) {
			throw new IncorrectBodyException("Incorrect request");
		}
		
		Evaluation evap = evaluationServiceManager.addEvaluation(body);
		
		return new ResponseEntity<Object>(evap, HttpStatus.CREATED);
		
	}
	
	
	
	@PostMapping("/evaluations/hardSkills")
	@Operation(summary="Add a hard skill", responses = {
			@ApiResponse(description="Hard skill added successfully", responseCode="201",
					content=@Content(mediaType="application/json", schema=@Schema(implementation=HardSkill.class))),
			@ApiResponse(description="This type of employee can not access here", responseCode="403", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Incorrect request", responseCode="400", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Manager or employee id not found", responseCode="404", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class)))})
	public ResponseEntity<Object> addHardSkill(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The parameter evaluationId is only required when both the manager and employee id is more than once in the evaluation table, otherwise it is optional") @RequestBody @Valid PerformancePostDTO body, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			throw new IncorrectBodyException("Incorrect request");
		}
		
		List<Evaluation> evaluationList = evaluationServiceManager.getEvaluationsByManagerAndEmployeeId(body.getManagerId(), body.getEmployeeId());
		
		HardSkill hardSkill = hardSkillService.addHardSkill(body, evaluationList);
		
		return new ResponseEntity<Object>(hardSkill, HttpStatus.CREATED);
	}
	
	
	
	@PostMapping("/evaluations/softSkills")
	@Operation(summary="Add a soft skill", responses = {
			@ApiResponse(description="Soft skill added successfully", responseCode="201",
					content=@Content(mediaType="application/json", schema=@Schema(implementation=SoftSkill.class))),
			@ApiResponse(description="This type of employee can not access here", responseCode="403", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Incorrect request", responseCode="400", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Manager or employee id not found", responseCode="404", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class)))})
	public ResponseEntity<Object> addSoftSkill(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The parameter evaluationId is only required when both the manager and employee id is more than once in the evaluation table, otherwise it is optional") @RequestBody @Valid PerformancePostDTO body, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			throw new IncorrectBodyException("Incorrect request");
		}
		
		List<Evaluation> evaluationList = evaluationServiceManager.getEvaluationsByManagerAndEmployeeId(body.getManagerId(), body.getEmployeeId());
		
		SoftSkill softSkill = softSkillService.addSoftSkill(body, evaluationList);
		
		return new ResponseEntity<Object>(softSkill, HttpStatus.CREATED);
	}
	
	
	@PostMapping("/evaluations/commitments")
	@Operation(summary="Add a commitment", responses = {
			@ApiResponse(description="Commitment added successfully", responseCode="201",
					content=@Content(mediaType="application/json", schema=@Schema(implementation=Commitment.class))),
			@ApiResponse(description="This type of employee can not access here", responseCode="403", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Incorrect request", responseCode="400", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Manager or employee id not found", responseCode="404", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class)))})
	public ResponseEntity<Object> addCommitment(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The parameter evaluationId is only required when both the manager and employee id is more than once in the evaluation table, otherwise it is optional") @RequestBody @Valid PerformancePostDTO body, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			throw new IncorrectBodyException("Incorrect request");
		}
		
		List<Evaluation> evaluationList = evaluationServiceManager.getEvaluationsByManagerAndEmployeeId(body.getManagerId(), body.getEmployeeId());
		
		Commitment commitment = commitmentService.addCommitment(body, evaluationList);
		
		return new ResponseEntity<Object>(commitment, HttpStatus.CREATED);
	}
	
	
	
	@PutMapping("/evaluations/hardSkills/{hardSkillId}")
	@Operation(summary="Update a hard skill for an employee", responses = {
			@ApiResponse(description="Hard skill updated successfully", responseCode="201",
					content=@Content(mediaType="application/json", schema=@Schema(implementation=HardSkill.class))),
			@ApiResponse(description="This type of employee can not access here", responseCode="403", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Hard skill or manager or employee id not found", responseCode="404", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Incorrect request", responseCode="400", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class)))},
			parameters = {
					@Parameter(name="hardSkillId", required=true, description = "The id of the hard skill", example="1")})
	public ResponseEntity<Object> updateHardSkill(@PathVariable(name="hardSkillId", required=true) int hardSkillId, @RequestBody @Valid PerformanceUpdateDTO body, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			throw new IncorrectBodyException("Incorrect request");
		}
		
		List<Evaluation> evaluationList = evaluationServiceManager.getEvaluationsByManagerAndEmployeeId(body.getManagerId(), body.getEmployeeId());
		
		HardSkill hardSkill = hardSkillService.updateHardSkill(body, hardSkillId, evaluationList);
		
		return new ResponseEntity<Object>(hardSkill, HttpStatus.CREATED);
	}
	
	
	
	@PutMapping("/evaluations/softSkills/{softSkillId}")
	@Operation(summary="Update a soft skill for an employee", responses = {
			@ApiResponse(description="Soft skill updated successfully", responseCode="201",
					content=@Content(mediaType="application/json", schema=@Schema(implementation=SoftSkill.class))),
			@ApiResponse(description="This type of employee can not access here", responseCode="403", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Soft skill or manager or employee id not found", responseCode="404", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Incorrect request", responseCode="400", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class)))},
			parameters = {
					@Parameter(name="softSkillId", required=true, description = "The id of the soft skill", example="1")})
	public ResponseEntity<Object> updateSoftSkill(@PathVariable(name="softSkillId", required=true) int softSkillId, @RequestBody @Valid PerformanceUpdateDTO body, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			throw new IncorrectBodyException("Incorrect request");
		}
		
		List<Evaluation> evaluationList = evaluationServiceManager.getEvaluationsByManagerAndEmployeeId(body.getManagerId(), body.getEmployeeId());
		
		SoftSkill softSkill = softSkillService.updateSoftSkill(body, softSkillId, evaluationList);
		
		return new ResponseEntity<Object>(softSkill, HttpStatus.CREATED);
	}
	
	
	
	@PutMapping("/evaluations/commitments/{commitmentId}")
	@Operation(summary="Update a commitment for an employee", responses = {
			@ApiResponse(description="Commitment updated successfully", responseCode="201",
					content=@Content(mediaType="application/json", schema=@Schema(implementation=Commitment.class))),
			@ApiResponse(description="This type of employee can not access here", responseCode="403", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Commitment or manager or employee id not found", responseCode="404", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Incorrect request", responseCode="400", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class)))},
			parameters = {
					@Parameter(name="commitmentId", required=true, description = "The id of the commitment", example="1")})
	public ResponseEntity<Object> updateCommitment(@PathVariable(name="commitmentId", required=true) int commitmentId, @RequestBody @Valid PerformanceUpdateDTO body, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			throw new IncorrectBodyException("Incorrect request");
		}
		
		List<Evaluation> evaluationList = evaluationServiceManager.getEvaluationsByManagerAndEmployeeId(body.getManagerId(), body.getEmployeeId());
		
		Commitment commitment = commitmentService.updateCommitment(body, commitmentId, evaluationList);
		
		return new ResponseEntity<Object>(commitment, HttpStatus.CREATED);
	}
	
	
	
	@GetMapping("/evaluations/manager/{managerId}/performances/all")
	@Operation(summary="Get all the performance of all the employees of a manager", responses = {
			@ApiResponse(description="Performance information shown successfully", responseCode="200",
					content=@Content(mediaType="application/json", schema=@Schema(implementation=EvaluationAllDTO.class))),
			@ApiResponse(description="This type of employee can not access here", responseCode="403", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Incorrect request", responseCode="400", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Manager or employee id or date not found", responseCode="404", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class)))},
			parameters = {
					@Parameter(name="managerId", required=true, description = "The id of the manager", example="1"),
					@Parameter(name="employeeId", required=false, description = "Search the id of an employee"),
					@Parameter(name="fromDate", required=false, description = "Search the initial date of an evaluation (in format yyyy-MM-dd)"),
					@Parameter(name="toDate", required=false, description = "Search the final date of an evaluation (in format yyyy-MM-dd)"),
					@Parameter(name="sort", required=false, description = "Sort by evaluationId, employeeId, comment, monthsPeriod, startDate or endDate"),
					@Parameter(name="sortType", required=false, description = "Type of sort, either asc (or ascending) or desc (or descending)")})
	public ResponseEntity<Object> getAllEvaluationsAllEmployees(@PathVariable(name="managerId", required=true) Integer managerId, @RequestParam(name="employeeId", required=false) Integer employeeId, @RequestParam(name="fromDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate fromDate, @RequestParam(name="toDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate toDate, @RequestParam(name="sort", required=false) String sort, @RequestParam(name="sortType", required=false) String sortType) {
		
		
		return new ResponseEntity<Object>(evaluationServiceManager.getAllEvaluationAllEmployees(managerId, employeeId, fromDate, toDate, sort, sortType), HttpStatus.OK);
		
	}
	
	
	
	@GetMapping("/evaluations/manager/{managerId}/performances/commitments")
	@Operation(summary="Get the commitments of all the employees of a manager", responses = {
			@ApiResponse(description="Commitment information shown successfully", responseCode="200",
					content=@Content(mediaType="application/json", schema=@Schema(implementation=EvaluationCommitmentsDTO.class))),
			@ApiResponse(description="This type of employee can not access here", responseCode="403", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Incorrect request", responseCode="400", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Manager or employee id or date not found", responseCode="404", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class)))},
			parameters = {
					@Parameter(name="managerId", required=true, description = "The id of the manager", example="1"),
					@Parameter(name="employeeId", required=false, description = "Search the id of an employee"),
					@Parameter(name="fromDate", required=false, description = "Search the initial date of an evaluation (in format yyyy-MM-dd)"),
					@Parameter(name="toDate", required=false, description = "Search the final date of an evaluation (in format yyyy-MM-dd)"),
					@Parameter(name="sort", required=false, description = "Sort by evaluationId, employeeId, comment, monthsPeriod, startDate or endDate"),
					@Parameter(name="sortType", required=false, description = "Type of sort, either asc (or ascending) or desc (or descending)")})
	public ResponseEntity<Object> getCommitmentsEvaluationsAllEmployees(@PathVariable(name="managerId", required=true) Integer managerId, @RequestParam(name="employeeId", required=false) Integer employeeId, @RequestParam(name="fromDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate fromDate, @RequestParam(name="toDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate toDate, @RequestParam(name="sort", required=false) String sort, @RequestParam(name="sortType", required=false) String sortType) {
		
				
		return new ResponseEntity<Object>(evaluationServiceManager.getCommitmentEvaluationsAllEmployees(managerId, employeeId, fromDate, toDate, sort, sortType), HttpStatus.OK);
		
	}
	

	
	@GetMapping("/evaluations/manager/{managerId}/performances/hardSkills")
	@Operation(summary="Get the hard skills of all the employees of a manager", responses = {
			@ApiResponse(description="Hard skill information shown successfully", responseCode="200",
					content=@Content(mediaType="application/json", schema=@Schema(implementation=EvaluationHardSkillsDTO.class))),
			@ApiResponse(description="This type of employee can not access here", responseCode="403", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Incorrect request", responseCode="400", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Manager or employee id or date not found", responseCode="404", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class)))},
			parameters = {
					@Parameter(name="managerId", required=true, description = "The id of the manager", example="1"),
					@Parameter(name="employeeId", required=false, description = "Search the id of an employee"),
					@Parameter(name="fromDate", required=false, description = "Search the initial date of an evaluation (in format yyyy-MM-dd)"),
					@Parameter(name="toDate", required=false, description = "Search the final date of an evaluation (in format yyyy-MM-dd)"),
					@Parameter(name="sort", required=false, description = "Sort by evaluationId, employeeId, comment, monthsPeriod, startDate or endDate"),
					@Parameter(name="sortType", required=false, description = "Type of sort, either asc (or ascending) or desc (or descending)")})
	public ResponseEntity<Object> gethardSkillsEvaluationsAllEmployees(@PathVariable(name="managerId", required=true) Integer managerId, @RequestParam(name="employeeId", required=false) Integer employeeId, @RequestParam(name="fromDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate fromDate, @RequestParam(name="toDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate toDate, @RequestParam(name="sort", required=false) String sort, @RequestParam(name="sortType", required=false) String sortType) {
		
				
		return new ResponseEntity<Object>(evaluationServiceManager.getHardSkillEvaluationsAllEmployees(managerId, employeeId, fromDate, toDate, sort, sortType), HttpStatus.OK);
		
		
	}
	

	
	
	@GetMapping("/evaluations/manager/{managerId}/performances/softSkills")
	@Operation(summary="Get the soft skills of all the employees of a manager", responses = {
			@ApiResponse(description="Soft skill information shown successfully", responseCode="200",
					content=@Content(mediaType="application/json", schema=@Schema(implementation=EvaluationSoftSkillsDTO.class))),
			@ApiResponse(description="This type of employee can not access here", responseCode="403", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Incorrect request", responseCode="400", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Manager or employee id or date not found", responseCode="404", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class)))},
			parameters = {
					@Parameter(name="managerId", required=true, description = "The id of the manager", example="1"),
					@Parameter(name="employeeId", required=false, description = "Search the id of an employee"),
					@Parameter(name="fromDate", required=false, description = "Search the initial date of an evaluation (in format yyyy-MM-dd)"),
					@Parameter(name="toDate", required=false, description = "Search the final date of an evaluation (in format yyyy-MM-dd)"),
					@Parameter(name="sort", required=false, description = "Sort by evaluationId, employeeId, comment, monthsPeriod, startDate or endDate"),
					@Parameter(name="sortType", required=false, description = "Type of sort, either asc (or ascending) or desc (or descending)")})
	public ResponseEntity<Object> getsoftSkillsEvaluationsAllEmployees(@PathVariable(name="managerId", required=true) Integer managerId, @RequestParam(name="employeeId", required=false) Integer employeeId, @RequestParam(name="fromDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate fromDate, @RequestParam(name="toDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate toDate, @RequestParam(name="sort", required=false) String sort, @RequestParam(name="sortType", required=false) String sortType) {
		
				
		return new ResponseEntity<Object>(evaluationServiceManager.getSoftSkillEvaluationsAllEmployees(managerId, employeeId, fromDate, toDate, sort, sortType), HttpStatus.OK);
		
		
	}
	
	
	
	
}
