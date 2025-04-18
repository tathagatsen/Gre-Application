package com.project.GreApp.model;

public class QuestionWrapper {
	private Integer id;
	private String word;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public QuestionWrapper(Integer id, String word) {
		super();
		this.id = id;
		this.word = word;
	}
	public QuestionWrapper() {
	}
}
