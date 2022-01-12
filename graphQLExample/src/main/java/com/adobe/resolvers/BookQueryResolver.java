package com.adobe.resolvers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adobe.dao.BookDao;
import com.adobe.entity.Book;

import graphql.execution.DataFetcherResult;
import graphql.kickstart.execution.error.GenericGraphQLError;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.Connection;
import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetchingEnvironment;

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
	
	
	public DataFetcherResult<Book> partialInfoBookById(int id) {
		return DataFetcherResult.<Book>newResult()
				.data(bookDao.findById(id).get())
				.error(new GenericGraphQLError("could not get publihser of book")).build();
	}
	
	public Connection<Book> booksByPage(int first, String after, DataFetchingEnvironment env) {
		return new SimpleListConnection<>(bookDao.findAll()).get(env);
	}
}
