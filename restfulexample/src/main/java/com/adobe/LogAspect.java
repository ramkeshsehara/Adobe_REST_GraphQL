package com.adobe;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class LogAspect {
	Logger logger = LoggerFactory.getLogger(LogAspect.class);
	//https://docs.spring.io/spring-framework/docs/2.0.x/reference/aop.html
	@Before("execution(* com.adobe.service.*.*(..))")
	public void dologBefore(JoinPoint jp) {
		logger.info("called : {}", jp.getSignature());
		Object[] args = jp.getArgs();
		for(Object obj : args) {
			logger.info("argument {}", obj);
		}
	}
	
	@After("execution(* com.adobe.service.*.*(..))")
	public void dologAfter(JoinPoint jp) {
		logger.info("***********************");
	}
}
