package com.project.GreQuizService.model;

public class WordWrapper {
	private Integer id;
	private String word;
	public WordWrapper(Integer id, String word) {
		super();
		this.id = id;
		this.word = word;
	}
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
	
	
}
