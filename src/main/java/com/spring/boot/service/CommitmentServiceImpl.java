package com.spring.boot.service;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dto.PerformancePostDTO;
import com.spring.boot.dto.PerformanceUpdateDTO;
import com.spring.boot.entity.Commitment;
import com.spring.boot.entity.Evaluation;
import com.spring.boot.error.IncorrectBodyException;
import com.spring.boot.error.ItemNotFoundException;
import com.spring.boot.repository.CommitmentRepository;

@Service(value="commitmentService")
public class CommitmentServiceImpl implements CommitmentService {
	
	@Autowired
	CommitmentRepository commitmentRepository;
	
	
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
	
	

	public Commitment addCommitment(PerformancePostDTO body, List<Evaluation> evaluationList) {
		
		if(evaluationList.isEmpty()) {
			throw new ItemNotFoundException("Manager or employee id not found");
		}
		
		Commitment commitment = new Commitment(body.getName(), body.getDescription());
		
		if(evaluationList.size() == 1) {
			if(currentDate.after(evaluationList.get(0).getEndDate())) {
				evaluationList.get(0).addCommitment(commitment);
			}
			else {
				throw new IncorrectBodyException("The period of the evaluation is not finished yet");
			}
		}
		else {
			if(body.getEvaluationId() != null) {
				boolean isPresent=false;
				for(Evaluation ev : evaluationList) {
					if(body.getEvaluationId().equals(ev.getId())) {
						if(currentDate.before(ev.getEndDate())) {
							throw new IncorrectBodyException("The period of the evaluation is not finished yet");
						}
						isPresent = true;
						ev.addCommitment(commitment);
						break;
					}
				}
				
				if(!isPresent) {
					throw new IncorrectBodyException("The evaluation id given is not correct");
				}
				
			}
			else {
				throw new IncorrectBodyException("When the manager id and the employee id given is more than once in the evaluation table, then you must specify the evaluation id ");
			}
		}
		
		commitmentRepository.save(commitment);
		
		return commitment;
	}
	
	
	public Commitment updateCommitment(PerformanceUpdateDTO body, int commitmentId, List<Evaluation> evaluationList) {
		
		if(evaluationList.isEmpty()) {
			throw new ItemNotFoundException("Manager or employee id not found");
		}
		
		Commitment commitment = null;
		
		boolean isValidId = false;
		
		for(Evaluation ev : evaluationList) {
			for(Commitment commit : ev.getCommitmentList()) {
				if(commit.getId() == commitmentId) {
					isValidId = true;
					commitment = commit;
					break;
				}	
			}
			
			if(isValidId) break;
		}
		
		
		if(isValidId) {
			commitment.setName(body.getName());
			commitment.setDescription(body.getDescription());
		}
		else {
			throw new ItemNotFoundException("Hard skill id not found");
		}
		
		
		commitmentRepository.save(commitment);
		
		return commitment;
	}
	
	
	
}
