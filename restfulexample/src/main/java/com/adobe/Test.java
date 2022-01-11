package com.adobe;

import com.adobe.entity.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	public static void main(String[] args) throws Exception {
		Customer c = new Customer("me@gmail.com", "A");
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(System.out, c);
	}

}
