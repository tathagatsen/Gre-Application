package com.project.GreQuizService.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Quiz {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String quizName;
	
	@ElementCollection
	private List<Integer> queIds;
	private Integer userId;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public List<Integer> getQueIds() {
		return queIds;
	}

	public void setQueIds(List<Integer> queIds) {
		this.queIds = queIds;
	}

	public Quiz(Integer id, String quizName, List<Integer> queIds) {
		super();
		this.id = id;
		this.quizName = quizName;
		this.queIds = queIds;
	}

	public Quiz() {
		super();
	}
	
}
