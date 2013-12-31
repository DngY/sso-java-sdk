package com.viathink.cio.beans;

public class Scopes {

	
	public static final String USER_PROFILE_BASIC = "profile_basic";
	
	
	public static final Scopes USER_PROFILE_BASIC_SCOPE = new Scopes("UserProfileBasic",Scopes.USER_PROFILE_BASIC,"用户基本信息");
	
	
	private String name;
	private String value;
	private String description;
	
	private Scopes(String name, String value, String description) {
		this.name = name;
		this.value = value;
		this.description = description;
		
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
}
