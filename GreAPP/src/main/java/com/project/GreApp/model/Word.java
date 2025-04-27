package com.project.GreApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Word {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique = true)
	private String word;
	private String definition;
	private String example;
	@Column(columnDefinition = "TEXT")
	private String wordForms;  

	public String getWordForms() {
	    return wordForms;
	}

	public void setWordForms(String wordForms) {
	    this.wordForms = wordForms;
	}

	// Update the constructor if needed
	public Word(Integer id, String word, String definition, String example, String wordForms) {
	    super();
	    this.id = id;
	    this.word = word;
	    this.definition = definition;
	    this.example = example;
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
