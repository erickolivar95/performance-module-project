package com.spring.boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.entity.Evaluation;

@Repository
public interface EvaluationRepository extends PagingAndSortingRepository<Evaluation, Integer>, JpaSpecificationExecutor<Evaluation> {

	@Query("select u from Evaluation u where u.managerId=?1")
	List<Evaluation> getEvaluationsByManagerId(int managerId);
	
	@Query("select u from Evaluation u where u.employeeId=?1")
	List<Evaluation> getEvaluationsByEmployeeId(int employeeId);
	
	@Query("select u from Evaluation u where u.managerId=?1 and u.employeeId=?2")
	List<Evaluation> getEvaluationsByManagerAndEmployeeId(int managerId, int employeeId);
	
	
}
