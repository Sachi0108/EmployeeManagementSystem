package com.klef.fsad.sdp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {
	
	@JsonProperty("identifier")
	private String identifier; // email or username
	
	@JsonProperty("password")
	private String password;
	
	public LoginRequest() {}
	
	public LoginRequest(String identifier, String password) {
		this.identifier = identifier;
		this.password = password;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "LoginRequest [identifier=" + identifier + ", password=" + password + "]";
	} 
}
