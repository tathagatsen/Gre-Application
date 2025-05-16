	package com.project.GreUserService.model;

public class UserDto {
	
	private String userName;
	private String userEmailId;
	public UserDto() {
		super();
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmailId() {
		return userEmailId;
	}
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	
}
