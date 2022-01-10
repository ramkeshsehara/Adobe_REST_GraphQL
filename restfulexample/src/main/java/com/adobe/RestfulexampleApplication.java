package com.adobe;

import java.util.List;

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
//		addProducts();
//		getProducts();
		
//		getProductsByRange();
		
		updateProduct();
	}

	private void updateProduct() {
		service.updateProduct(4, 899.00);
	}

	private void getProductsByRange() {
		List<Product> products = service.getProductsByRange(50_000, 1_00_000);
		for(Product p : products) {
			System.out.println(p.getName() + ", " + p.getPrice());
		}
	}

	private void getProducts() {
		List<Product> products = service.getProducts();
		for(Product p : products) {
			System.out.println(p.getName() + ", " + p.getPrice());
		}
	}

	private void addProducts() {
		service.addProduct(new Product(0, "iPhone 13", 98000.00, 100));
		service.addProduct(new Product(0, "Sony Bravia", 128000.00, 100));
		service.addProduct(new Product(0, "Samsung Refri", 67000.00, 100));
		service.addProduct(new Product(0, "Logitech Mouse", 800.00, 100));
	}

}
