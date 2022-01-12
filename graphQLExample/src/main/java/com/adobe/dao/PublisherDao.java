package com.adobe.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adobe.entity.Publisher;

public interface PublisherDao extends JpaRepository<Publisher, Integer> {

}
