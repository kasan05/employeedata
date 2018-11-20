package com.example.employeedata.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.employeedata.dto.Designation;
import com.example.employeedata.dto.EmployeeData;

@Service
public class ValidationService {

	
	public boolean isvalidRecord(EmployeeData employeeData) {
		List<String> designationList = new ArrayList<String>();
		designationList.add(Designation.DEVELOPER.getName());
		designationList.add(Designation.CEO.getName());
		designationList.add(Designation.VP.getName());
		designationList.add(Designation.MANAGER.getName());
		designationList.add(Designation.SENIOR_DEVELOPER.getName());
		designationList.add(Designation.TEAM_LEAD.getName());
		
		if(!employeeData.getName().matches("^[a-zA-Z]*")) {
			return false;
		}else if(!employeeData.getDepartment().matches("^[a-zA-Z*_]*")) {
			return false;
		}else if(!designationList.contains(employeeData.getDesignation())) {
			return false;
		}else if(!employeeData.getSalary().matches("^[0-9]*")) {
			return false;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dateFormat.parse(employeeData.getJoiningDate());
		} catch (ParseException e) {
			return false;
		}
		
		return true;
		
	}
	
	
	
}
