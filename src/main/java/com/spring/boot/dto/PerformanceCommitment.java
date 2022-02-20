package com.spring.boot.dto;

import java.util.List;

import com.spring.boot.entity.Commitment;

public class PerformanceCommitment {
	
	private List<Commitment> commitmentList;
	
	

	public List<Commitment> getCommitmentList() {
		return commitmentList;
	}

	public void setCommitmentList(List<Commitment> commitmentList) {
		this.commitmentList = commitmentList;
	}
	
	
	
}
