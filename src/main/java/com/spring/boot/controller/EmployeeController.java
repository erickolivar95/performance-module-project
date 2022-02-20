package com.spring.boot.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.dto.EvaluationEmployeeAllDTO;
import com.spring.boot.dto.EvaluationEmployeeCommitmentsDTO;
import com.spring.boot.dto.EvaluationEmployeeHardSkillsDTO;
import com.spring.boot.dto.EvaluationEmployeeSoftSkillsDTO;
import com.spring.boot.error.ErrorDTO;
import com.spring.boot.service.EvaluationServiceEmployee;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	EvaluationServiceEmployee evaluationServiceEmployee;
	

	@GetMapping("/{employeeId}/evaluations/performances/all")
	@Operation(summary="Get all the performance of an employee", responses = {
			@ApiResponse(description="Performance information shown successfully", responseCode="200",
					content=@Content(mediaType="application/json", schema=@Schema(implementation=EvaluationEmployeeAllDTO.class))),
			@ApiResponse(description="This type of employee can not access here", responseCode="403", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Employee id not found", responseCode="404", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class)))},
			parameters = {
					@Parameter(name="employeeId", required=true, description = "The id of the employee", example="1"),
					@Parameter(name="fromDate", required=false, description = "Search the initial date of an evaluation (in format yyyy-MM-dd)"),
					@Parameter(name="toDate", required=false, description = "Search the final date of an evaluation (in format yyyy-MM-dd)"),
					@Parameter(name="sort", required=false, description = "Sort by evaluationId, monthsPeriod, startDate or endDate"),
					@Parameter(name="sortType", required=false, description = "Type of sort, either asc (or ascending) or desc (or descending)")})
	public ResponseEntity<Object> getAllEvaluationsEmployee(@PathVariable(name="employeeId", required=true) int employeeId, @RequestParam(name="fromDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate fromDate, @RequestParam(name="toDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate toDate, @RequestParam(name="sort", required=false) String sort, @RequestParam(name="sortType", required=false) String sortType) {

		
		List<EvaluationEmployeeAllDTO> evaluation = evaluationServiceEmployee.getAllEvaluationsForEmployee(employeeId, fromDate, toDate, sort, sortType);
		
		return new ResponseEntity<Object>(evaluation, HttpStatus.OK);
		
	}
	
	@GetMapping("/{employeeId}/evaluations/performances/commitments")
	@Operation(summary="Get the commitments of an employee", responses = {
			@ApiResponse(description="Commitment information shown successfully", responseCode="200",
					content=@Content(mediaType="application/json", schema=@Schema(implementation=EvaluationEmployeeCommitmentsDTO.class))),
			@ApiResponse(description="This type of employee can not access here", responseCode="403", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Employee id not found", responseCode="404", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class)))},
			parameters = {
					@Parameter(name="employeeId", required=true, description = "The id of the employee", example="1"),
					@Parameter(name="fromDate", required=false, description = "Search the initial date of an evaluation (in format yyyy-MM-dd)"),
					@Parameter(name="toDate", required=false, description = "Search the final date of an evaluation (in format yyyy-MM-dd)"),
					@Parameter(name="sort", required=false, description = "Sort by evaluationId, monthsPeriod, startDate or endDate"),
					@Parameter(name="sortType", required=false, description = "Type of sort, either asc (or ascending) or desc (or descending)")})
	public ResponseEntity<Object> getCommitmentEvaluationsEmployee(@PathVariable(name="employeeId", required=true) int employeeId, @RequestParam(name="fromDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate fromDate, @RequestParam(name="toDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate toDate, @RequestParam(name="sort", required=false) String sort, @RequestParam(name="sortType", required=false) String sortType) {
	
		
		List<EvaluationEmployeeCommitmentsDTO> evaluation = evaluationServiceEmployee.getCommitmentEvaluationsForEmployee(employeeId, fromDate, toDate, sort, sortType);
		
		return new ResponseEntity<Object>(evaluation, HttpStatus.OK);
		
	}
	
	@GetMapping("/{employeeId}/evaluations/performances/hardSkills")
	@Operation(summary="Get the hard skills of an employee", responses = {
			@ApiResponse(description="Hard skill information shown successfully", responseCode="200",
					content=@Content(mediaType="application/json", schema=@Schema(implementation=EvaluationEmployeeHardSkillsDTO.class))),
			@ApiResponse(description="This type of employee can not access here", responseCode="403", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Employee id not found", responseCode="404", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class)))},
			parameters = {
					@Parameter(name="employeeId", required=true, description = "The id of the employee", example="1"),
					@Parameter(name="fromDate", required=false, description = "Search the initial date of an evaluation (in format yyyy-MM-dd)"),
					@Parameter(name="toDate", required=false, description = "Search the final date of an evaluation (in format yyyy-MM-dd)"),
					@Parameter(name="sort", required=false, description = "Sort by evaluationId, monthsPeriod, startDate or endDate"),
					@Parameter(name="sortType", required=false, description = "Type of sort, either asc (or ascending) or desc (or descending)")})
	public ResponseEntity<Object> getHardSkillEvaluationsEmployee(@PathVariable(name="employeeId", required=true) int employeeId, @RequestParam(name="fromDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate fromDate, @RequestParam(name="toDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate toDate, @RequestParam(name="sort", required=false) String sort, @RequestParam(name="sortType", required=false) String sortType) {
		
		
		List<EvaluationEmployeeHardSkillsDTO> evaluation = evaluationServiceEmployee.getHardSkillEvaluationsForEmployee(employeeId, fromDate, toDate, sort, sortType);
		
		return new ResponseEntity<Object>(evaluation, HttpStatus.OK);
		
	}
	
	@GetMapping("/{employeeId}/evaluations/performances/softSkills")
	@Operation(summary="Get the soft skills of an employee", responses = {
			@ApiResponse(description="Soft skill information shown successfully", responseCode="200",
					content=@Content(mediaType="application/json", schema=@Schema(implementation=EvaluationEmployeeSoftSkillsDTO.class))),
			@ApiResponse(description="This type of employee can not access here", responseCode="403", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class))),
			@ApiResponse(description="Employee id not found", responseCode="404", content=@Content(mediaType="application/json", schema=@Schema(implementation=ErrorDTO.class)))},
			parameters = {
					@Parameter(name="employeeId", required=true, description = "The id of the employee", example="1"),
					@Parameter(name="fromDate", required=false, description = "Search the initial date of an evaluation (in format yyyy-MM-dd)"),
					@Parameter(name="toDate", required=false, description = "Search the final date of an evaluation (in format yyyy-MM-dd)"),
					@Parameter(name="sort", required=false, description = "Sort by evaluationId, monthsPeriod, startDate or endDate"),
					@Parameter(name="sortType", required=false, description = "Type of sort, either asc (or ascending) or desc (or descending)")})
	public ResponseEntity<Object> getSoftSkillEvaluationsEmployee(@PathVariable(name="employeeId", required=true) int employeeId, @RequestParam(name="fromDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate fromDate, @RequestParam(name="toDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate toDate, @RequestParam(name="sort", required=false) String sort, @RequestParam(name="sortType", required=false) String sortType) {
		
		
		List<EvaluationEmployeeSoftSkillsDTO> evaluation = evaluationServiceEmployee.getSoftSkillEvaluationsForEmployee(employeeId, fromDate, toDate, sort, sortType);
		
		return new ResponseEntity<Object>(evaluation, HttpStatus.OK);
		
	}
	
}
