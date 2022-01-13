package com.adobe.mutation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adobe.dao.AuthorDao;
import com.adobe.entity.Author;
import com.adobe.subscription.AuthorPublisher;

import graphql.kickstart.tools.GraphQLMutationResolver;

@Component
public class AuthorMutationResolver implements GraphQLMutationResolver {
	@Autowired
	private AuthorDao authorDao;
	
	@Autowired
	private AuthorPublisher publisher;
	
	public Integer createAuthor(Author author) {
		publisher.publish(author);
		return authorDao.save(author).getId();
	}
}
