package com.spring.boot.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="soft_skill")
public class SoftSkill implements Comparable<SoftSkill> {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;

	
	
	public SoftSkill(String name, String description) {
		this.name = name;
		this.description = description;
	}



	public SoftSkill() {
		
	}

	
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	
	
	@Override
	public int compareTo(SoftSkill s) {
		if(id < s.getId()) {
			return -1;
		}
		if(id > s.getId()) {
			return 1;
		}
		return 0;
	}
	
	
}
