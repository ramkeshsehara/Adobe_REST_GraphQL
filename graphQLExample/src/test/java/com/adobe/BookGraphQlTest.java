package com.adobe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.adobe.dao.BookDao;
import com.adobe.entity.Book;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
public class BookGraphQlTest {
	@Autowired
	private GraphQLTestTemplate template;
	
	@MockBean
	private BookDao bookDao;
	
	@Test
	public void getBookById() throws Exception {
		String expected  = "{\"data\":{\"bookById\":{\"id\":2,\"title\":\"Some Book\"}}}";
		
		Book book = new Book();
		book.setId(2);
		book.setTitle("Some Book");
		
		Optional<Book> opt = Optional.of(book);
		// mockito
		when(bookDao.findById(2)).thenReturn(opt);
		
		GraphQLResponse response = template.postForResource("get-book.graphqls");
		System.out.println(response.getRawResponse().getBody());
		assertEquals(response.isOk(), true);
		assertEquals(expected, response.getRawResponse().getBody());
	}
}
