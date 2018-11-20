package com.example.employeedata.dto;

public enum Designation {

	
	DEVELOPER("Developer"),
	SENIOR_DEVELOPER("Senior Developer"),
	MANAGER("Manager"),
	TEAM_LEAD("Team Lead"),
	VP("VP"),
	CEO("");
	
	
	private String name;

	Designation(String name){
		this.name=  name;
	}
	
	public String getName() {
		return this.name;
	}
}
