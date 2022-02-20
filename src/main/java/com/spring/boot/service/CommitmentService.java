package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.PerformancePostDTO;
import com.spring.boot.dto.PerformanceUpdateDTO;
import com.spring.boot.entity.Commitment;
import com.spring.boot.entity.Evaluation;

public interface CommitmentService {

	public Commitment addCommitment(PerformancePostDTO body, List<Evaluation> evaluationList);
	
	public Commitment updateCommitment(PerformanceUpdateDTO body, int commitmentId, List<Evaluation> evaluationList);
	
}
