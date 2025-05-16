package com.project.GreUserService.model;

public class UserQuizResponse {
	private Integer userId;
	private Integer quizId;
	private Integer queId;
	private String definition;
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
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public UserQuizResponse() {
		super();
	}
	
}
