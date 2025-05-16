package com.project.GreQuizService.model;

public class UserQuestions {
	private Integer userId;
	private Integer quizId;
	private Integer queId;
	private String word;
	
	public UserQuestions() {
		super();
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getQuizId() {
		return quizId;
	}
	public void setQuizId(Integer quizId) {
		this.quizId = quizId;
	}
	public Integer getQueId() {
		return queId;
	}
	public void setQueId(Integer queId) {
		this.queId = queId;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
}
