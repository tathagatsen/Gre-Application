package com.project.GreUserService.model;

public class WordDto {
	private String word;
	private String definition;
	private String example;
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
	public WordDto() {
		super();
	}
	
}
