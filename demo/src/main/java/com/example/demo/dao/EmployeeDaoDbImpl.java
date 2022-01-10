package com.example.demo.dao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name="dao", havingValue = "db")
public class EmployeeDaoDbImpl implements EmployeeDao {

	@Override
	public void addEmployee() {
		System.out.println("SQL code..");
	}

}
