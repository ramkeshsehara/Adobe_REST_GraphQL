package com.adobe.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adobe.entity.Order;

public interface OrderDao extends JpaRepository<Order, Integer> {

}
