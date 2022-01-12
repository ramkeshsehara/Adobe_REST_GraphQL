package com.adobe.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adobe.dao.PublisherDao;
import com.adobe.entity.Book;
import com.adobe.entity.Publisher;

import graphql.kickstart.tools.GraphQLResolver;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BookFieldQueryResolver implements GraphQLResolver<Book> {
	@Autowired
	private PublisherDao publisherDao;
//	private Double getRating(Book book) {
//		return Math.random() * 10;
//	}
	
	public Publisher getPublisher(Book book) {
		log.info("Book field resolver for {} ", book.getId());
		return publisherDao.findById(book.getPublisherId()).get();
	}
}
