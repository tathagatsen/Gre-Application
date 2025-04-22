package com.project.GreQuestionService.model;

public class Response {
	private Integer id;
	private String definition;
	public Response(Integer id, String definition) {
		super();
		this.id = id;
		this.definition = definition;
	}
	
	public Response() {
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
}
