package com.adobe.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adobe.entity.Book;

public interface BookDao extends JpaRepository<Book, Integer> {

}
