package com.example.employeedata.dto;

public class EmployeeData {

	private String id;
	private String name;
	private String department;
	private String designation;
	private String salary;
	private String joiningDate;

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id =  id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean ab =  obj instanceof EmployeeData && 
				this.name.equals(((EmployeeData)obj).getName())
				&& this.department.equals(((EmployeeData)obj).getDepartment())
				&& this.designation.equals(((EmployeeData)obj).getDesignation())
				&& this.salary.equals(((EmployeeData)obj).getSalary());
		System.out.println("condition " + ab);
		return ab;
				
	}
	
	@Override
	public int hashCode() {
		return (this.name+this.salary+this.designation+this.department).hashCode();
	}

}
