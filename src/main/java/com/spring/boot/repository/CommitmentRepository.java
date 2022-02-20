package com.spring.boot.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.boot.entity.Commitment;

@Repository
public interface CommitmentRepository extends PagingAndSortingRepository<Commitment, Integer>, JpaSpecificationExecutor<Commitment> {

	@Modifying
	@Transactional
	@Query("UPDATE Commitment u SET u.name=?1, u.description=?2 where u.id=?3")
	void updateCommitment(String name, String description, int softSkillId);
	
}
