package com.adobe.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.adobe.dao.OrderDao;
import com.adobe.dao.ProductDao;
import com.adobe.entity.Item;
import com.adobe.entity.Order;
import com.adobe.entity.Product;

@Service
public class OrderService {
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private OrderDao orderDao;
	
	@Transactional
	public Order placeOrder(Order o) {
		List<Item> items = o.getItems();
		double total = 0.0;
		for(Item item : items) {
			Product p = productDao.getById(item.getProduct().getId());
			if(p.getQuantity() < item.getQuantity()) {
				throw new RuntimeException("Product not in stock");
			}
			p.setQuantity(p.getQuantity() - item.getQuantity()); // Dirty Checking ==> update SQL
			item.setAmount(p.getPrice() * item.getQuantity());
			total += item.getAmount();
		}
		o.setTotal(total);
		return orderDao.save(o);// takes care of persisting items also // cascade
	}
	
	public List<Order> getOrders() {
		return orderDao.findAll();
	}
	
	
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
	
	public List<Product> getProductsByRange(double low, double high) {
		return productDao.getByRange(low, high);
	}
	
	@Transactional(TxType.REQUIRED)
	public void updateProduct(int id, double price) {
		productDao.updateProduct(id, price);
	}
}
