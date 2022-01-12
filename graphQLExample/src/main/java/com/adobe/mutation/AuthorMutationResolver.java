package com.adobe.mutation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adobe.dao.AuthorDao;
import com.adobe.entity.Author;

import graphql.kickstart.tools.GraphQLMutationResolver;

@Component
public class AuthorMutationResolver implements GraphQLMutationResolver {
	@Autowired
	private AuthorDao authorDao;
	
	public Integer createAuthor(Author author) {
		return authorDao.save(author).getId();
	}
}
