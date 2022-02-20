package com.spring.boot.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.boot.entity.HardSkill;

@Repository
public interface HardSkillRepository extends PagingAndSortingRepository<HardSkill, Integer>, JpaSpecificationExecutor<HardSkill> {

	@Modifying
	@Transactional
	@Query("UPDATE HardSkill u SET u.name=?1, u.description=?2 where u.id=?3")
	void updateHardSkill(String name, String description, int hardSkillId);
	
}
