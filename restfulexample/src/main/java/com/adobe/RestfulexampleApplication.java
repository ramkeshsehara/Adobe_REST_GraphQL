package com.adobe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adobe.entity.Product;
import com.adobe.service.OrderService;

@SpringBootApplication
public class RestfulexampleApplication implements CommandLineRunner {
	@Autowired
	private OrderService service;
	
	public static void main(String[] args) {
		SpringApplication.run(RestfulexampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		service.addProduct(new Product(0, "iPhone 13", 98000.00, 100));
		service.addProduct(new Product(0, "Sony Bravia", 128000.00, 100));
		service.addProduct(new Product(0, "Samsung Refri", 67000.00, 100));
		service.addProduct(new Product(0, "Logitech Mouse", 800.00, 100));
	}

}
