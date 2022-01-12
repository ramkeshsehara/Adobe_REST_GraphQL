package com.adobe;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(GraphQlExampleApplication.class)
public class TestApplication {

}
