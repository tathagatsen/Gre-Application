package com.project.GreUserService.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Word {
	private Integer id;
	
	private String word;
	private String definition;
	private String example;
	private String wordForms;  
	private String category;
	private Integer userId;
	
	public String getCategory() {
		return category;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getWordForms() {
	    return wordForms;
	}

	public void setWordForms(String wordForms) {
	    this.wordForms = wordForms;
	}

	public Word() {
	    super();
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
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public String getExample() {
		return example;
	}
	public void setExample(String example) {
		this.example = example;
	}
}
