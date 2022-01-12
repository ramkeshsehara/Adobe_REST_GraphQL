package com.adobe.resolvers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adobe.dao.BookDao;
import com.adobe.entity.Book;

import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
public class BookQueryResolver implements GraphQLQueryResolver {
	
	@Autowired
	private BookDao bookDao;
	 
	public List<Book> getBooks() {
		return bookDao.findAll();
	}
	
	public Book getBookById(int id) {
		Optional<Book> opt =  bookDao.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		} else {
			throw new ResourceNotFoundException("Book with id "+ id + " doesn't exist!!!");
		}
	}
}
