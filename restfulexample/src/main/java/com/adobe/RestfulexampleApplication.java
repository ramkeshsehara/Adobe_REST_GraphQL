package com.adobe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adobe.entity.Customer;
import com.adobe.entity.Item;
import com.adobe.entity.Order;
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
		
//		updateProduct();
		ordering();
	}

	private void ordering() {
		Product p1 = new Product();
		p1.setId(3);
		
		Product p2 = new Product();
		p2.setId(2);
		
		Item i1 = new Item();
		i1.setProduct(p1);
		i1.setQuantity(4);
		
		Item i2 = new Item();
		i2.setProduct(p2);
		i2.setQuantity(1);
		
		Order o = new Order();
		o.getItems().add(i1);
		o.getItems().add(i2);
		
		Customer c = new Customer();
		c.setEmail("b@adobe.com");
		o.setCustomer(c);
		
		service.placeOrder(o);
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
