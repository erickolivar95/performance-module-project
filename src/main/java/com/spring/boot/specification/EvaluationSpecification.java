package com.spring.boot.specification;

import java.sql.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;


import com.spring.boot.entity.Evaluation;
import com.spring.boot.entity.Evaluation_;

public class EvaluationSpecification implements Specification<Evaluation> {

private static final long serialVersionUID = 1L;
	
	private SearchCriteria criteria;
	
	public EvaluationSpecification(SearchCriteria criteria) {
		this.criteria = criteria;
	}
	
	@Override
	public Predicate toPredicate(Root<Evaluation> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if(criteria.getOperation().equals("=")) {
			return builder.equal(root.get(criteria.getKey()), Integer.valueOf(criteria.getValue().toString()) );
		}
		else if(criteria.getOperation().equals(">=")) {
			if(criteria.getKey().equals(Evaluation_.START_DATE)) {
				return builder.greaterThanOrEqualTo(root.get(Evaluation_.START_DATE), builder.literal(criteria.getValue()).as(Date.class) );
			}
		}
		else if(criteria.getOperation().equals("<=")) {
			if(criteria.getKey().equals(Evaluation_.END_DATE)) {
				return builder.lessThanOrEqualTo(root.<Date>get(Evaluation_.END_DATE), builder.literal(criteria.getValue()).as(Date.class) );
			}
		}
		
		return null;
	}
	
}
