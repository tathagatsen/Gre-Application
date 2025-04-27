package com.project.GreQuizService.model;

public class QuestionWrapper {
	private Integer quizId;
	private Integer queId;
	private String word;
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
	public QuestionWrapper(Integer quizId, Integer queId, String word) {
		super();
		this.quizId = quizId;
		this.queId = queId;
		this.word = word;
	}
	
}
