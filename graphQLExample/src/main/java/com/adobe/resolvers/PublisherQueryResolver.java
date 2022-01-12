package com.adobe.resolvers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adobe.dao.PublisherDao;
import com.adobe.entity.Publisher;

import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
public class PublisherQueryResolver implements GraphQLQueryResolver {
	@Autowired
	private PublisherDao publisherDao;
	
	public List<Publisher> publishers() {
		return publisherDao.findAll();
	}
}
