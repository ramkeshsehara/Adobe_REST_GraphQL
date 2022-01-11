package com.adobe.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.entity.Product;
import com.adobe.service.OrderService;

@RestController
@RequestMapping("api/products")
public class ProductController {
	@Autowired
	private OrderService service;
	// http://localhost:8080/api/products GET
	// http://localhost:8080/api/products?low=100&high=5000 GET
	@GetMapping()
	public @ResponseBody List<Product> getProducts(@RequestParam(name="low", defaultValue = "0.0") double low, 
			@RequestParam(name="high", defaultValue = "0.0") double high) {
		if(low == 0.0 && high == 0.0) {
			return service.getProducts();
		} else {
			return service.getProductsByRange(low, high);
		}
	}
	
	// http://localhost:8080/api/products/5   GET
	@GetMapping("/{pid}")
	public @ResponseBody Product getProduct(@PathVariable("pid") int id) {
		return service.getProductById(id);
	}
	
	// http://localhost:8080/api/products 
	// POST payload of Product is sent
	@PostMapping()
	public ResponseEntity<Product> addProduct(@RequestBody @Valid Product p) {
		Product prd =  service.addProduct(p);
		return new ResponseEntity<Product>(prd, HttpStatus.CREATED);
	}
	
}
