package com.project.GreQuizService.model;

public class QuizDto {
	private Integer userId;
	private String quizName;
	private Integer numQ;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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
