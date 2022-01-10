package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.service.EmployeeService;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	@Autowired
	private EmployeeService service;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
//		ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
		
//		String[] names = ctx.getBeanDefinitionNames();
//		
//		for(String name : names) {
//			System.out.println(name);
//		}
		
//		EmployeeService service = ctx.getBean("employeeService", EmployeeService.class);
//		service.doTask();
		
	}

	@Override
	public void run(String... args) throws Exception {
		// executes after Spring container is created
		service.doTask();
	}

}
