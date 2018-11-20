package com.example.employeedata.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.employeedata.dto.EmployeeData;
import com.example.employeedata.service.ValidationService;
import com.opencsv.CSVReader;

@RestController
public class EmployeeDataController {

	private static final  String ERROR_FILE = "errorFile.txt";
	
	@Autowired
	private ValidationService validationService;
	
	private static Set<EmployeeData> employeeDataSet;
	
	
	static {
		employeeDataSet =  new HashSet<EmployeeData>(); ;
	}
	
	@PostMapping("employees")
	@CrossOrigin(origins="*")
	public ResponseEntity<Set<EmployeeData>> uploadEmployeeData(@RequestParam("file") MultipartFile file) throws IOException {
		
		File errorFile = new File(ERROR_FILE);
		FileWriter writer = new FileWriter(errorFile);
		try {
			String[] row = null;
				
				CSVReader csvReader =  new CSVReader(new InputStreamReader(file.getInputStream(), 
						"UTF-8"));
				List<String[]> content = csvReader.readAll();
				
				if(content.size()>=1)
					content.remove(0);
			
				int i =0;
				for (Object object : content) {
					
					row = (String[]) object;
					if(row.length>=5) {
						EmployeeData empData = new EmployeeData();
						empData.setId(String.valueOf(UUID.randomUUID()));
						empData.setName(row[0]);
						empData.setDepartment(row[1]);
						empData.setDesignation(row[2]);
						empData.setSalary(row[3]);
						empData.setJoiningDate(row[4]);
			
						if(!validationService.isvalidRecord(empData)) {
							writer.write(row[0]+","+row[1]+","+row[2]+","+row[3]+","+row[4]);
							writer.write("\n");
							System.out.println("writing to file");
						}else {
							employeeDataSet.add(empData);
						}
						
					}
					i++;
				}
	
				csvReader.close();
				writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return new ResponseEntity<Set<EmployeeData>>(employeeDataSet, HttpStatus.OK);
	}
	
	@GetMapping("employees/error")
	@CrossOrigin(origins="*")
	public void downloadErrorReport(HttpServletResponse response) throws IOException {
		
		File file = new File(ERROR_FILE);
		response.setContentType("application/octet-stream");
		response.setContentLength((int) file.length());
		response.setContentType("application/text");
		response.setHeader("Content-Disposition", "attachment; filename="+ERROR_FILE+"");
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}
	
	
	@GetMapping("employees")
	@CrossOrigin(origins="*")
	public ResponseEntity<Set<EmployeeData>> getAllEmployeeData(){
		return new ResponseEntity<Set<EmployeeData>>(employeeDataSet,HttpStatus.OK);
	}
	
	@PutMapping("employee/{id}")
	@CrossOrigin(origins="*")
	public ResponseEntity<Set<EmployeeData>> updateEmployeeData(@PathParam("id") String id,
			@RequestBody EmployeeData employeeData){
		
		for(EmployeeData empData : employeeDataSet) {
			if(id.equals(empData.getId())) {
				empData.setName(employeeData.getName());
				empData.setDepartment(employeeData.getDepartment());
				empData.setSalary(employeeData.getSalary());
				empData.setJoiningDate(employeeData.getJoiningDate());
				empData.setDesignation(employeeData.getDesignation());
			}
		}
		return new ResponseEntity<Set<EmployeeData>>(employeeDataSet,HttpStatus.OK);
	}
}
