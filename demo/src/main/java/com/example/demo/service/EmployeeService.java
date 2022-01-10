package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.EmployeeDao;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeDao employeeDao;
	
	public void doTask() {
		employeeDao.addEmployee();
	}
}
