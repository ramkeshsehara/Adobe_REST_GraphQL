package com.adobe.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adobe.dao.ProductDao;
import com.adobe.entity.Product;

@Service
public class OrderService {
	@Autowired
	private ProductDao productDao;
	
	public Product addProduct(Product p) {
		return productDao.save(p);
	}
	
	public List<Product> getProducts() {
		return productDao.findAll();
	}
	
	public Product getProductById(int id) {
		Optional<Product> opt = productDao.findById(id);
		if(opt.isPresent() ) {
			return opt.get();
		} else {
			throw new EntityNotFoundException("product with id " + id + " doesn't exist!!!");
		}
	}
}
