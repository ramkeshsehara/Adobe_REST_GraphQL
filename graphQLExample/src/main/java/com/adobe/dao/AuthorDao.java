package com.adobe.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adobe.entity.Author;

public interface AuthorDao extends JpaRepository<Author, Integer>{

}
