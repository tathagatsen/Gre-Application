package com.project.GreQuestionService.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Word {

	private Integer id;
	private String word;
	private String definition;
	private String example;
	public Word(Integer id, String word, String definition, String example) {
		super();
		this.id = id;
		this.word = word;
		this.definition = definition;
		this.example = example;
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
