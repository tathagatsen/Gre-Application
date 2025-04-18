package com.project.GreQuizService.model;

public class QuizDto {
	private String quizName;
	private Integer numQ;
	
	public QuizDto(String quizName, Integer numQ) {
		super();
		this.quizName = quizName;
		
		this.numQ = numQ;
	}
	
	public QuizDto() {
	}
	
	
	public Integer getNumQ() {
		return numQ;
	}
	public void setNumQ(Integer numQ) {
		this.numQ = numQ;
	}
	
	
	public String getQuizName() {
		return quizName;
	}
	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}
	
	
	
}
