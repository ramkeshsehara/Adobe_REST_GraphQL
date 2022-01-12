package com.adobe.cfg;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

@Configuration
public class CustomConfig {
	
	@Bean
	public GraphQLScalarType dateScalar() {
		return GraphQLScalarType.newScalar()
				.name("Date")
				.description("Custom Date Scalar type")
				.coercing(new Coercing<Date, String>() {
					
					@Override
					public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
						return dataFetcherResult.toString();
					}

					// publishedDate: $pubDate
					@Override
					public Date parseValue(Object input) throws CoercingParseValueException {
						try {
							return DateFormat.getInstance().parse((String)input);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						return null;
					}
					
					// publishedDate: "12-JAN-2022"
					@Override
					public Date parseLiteral(Object input) throws CoercingParseLiteralException {
						try {
							return DateFormat.getInstance().parse((String)input);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						return null;
					}
					
				}).build();
	}
}
