package com.adobe.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adobe.entity.Product;

public interface ProductDao extends JpaRepository<Product, Integer>{

}
