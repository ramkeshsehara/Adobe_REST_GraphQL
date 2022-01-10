package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.EmployeeDao;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private MessagingService service;
	
	public void doTask() {
		service.sendMessage("employee added!!!");
		employeeDao.addEmployee();
	}
}

