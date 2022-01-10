# Adobe_REST_GraphQL
Spring Boot: Building RESTful and GraphQL services


Banuprakash C

Full Stack Architect,

Co-founder Lucida Technologies Pvt Ltd.,

Corporate Trainer,

Email: banuprakashc@yahoo.co.in

https://www.linkedin.com/in/banu-prakash-50416019/

https://github.com/BanuPrakash/Adobe_REST_GraphQL

https://notepad.ltd/egrnhgxh

====

Softwares Required:
1) Java 8

2) Eclipse IDE for Enterprise Java Developers: 
	https://www.eclipse.org/downloads/packages/release/2020-03/m1/eclipse-ide-enterprise-java-developers

3) MySQL  [ Prefer on Docker]

Install Docker Desktop

Docker steps:

a) docker pull mysql

b) docker run --name local-mysql –p 3306:3306 -e MYSQL_ROOT_PASSWORD=Welcome123 -d mysql

container name given here is "local-mysql"

For Mac:
docker run -p 3306:3306 -d --name local-mysql -e MYSQL_ROOT_PASSWORD=Welcome123 mysql


c) CONNECT TO A MYSQL RUNNING CONTAINER:

$ docker exec -it local-mysql /bin/bash

d) Run MySQL client:

bash terminal> mysql -u "root" -p

mysql> exit

============================


Spring Framework
==> For building Enterprise application
==> Lightweight container [ layer on top of JVM ]
==> Core features of Spring supports life cycle management of beans and dependency injection


Bean ==> any object managed by Spring container is a bean

MyClass obj = new MyClass(); // for spring this is not a bean

DI ==> For Loosely coupled application

Spring uses Metadata for managing lifecycle and dependency injection
1) XML
2) Annotation


interface EmployeeDao {
	void addEmployee(Employee e);
}

public class EmployeeDaoMySqlImpl implements EmployeeDao {
	public void addEmployee(Employee e) {
		.. SQL
	}
}

public class EmployeeDaoMongoDbImpl implements EmployeeDao {
	public void addEmployee(Employee e) {
		.. Mongo instruction
	}
}

public class MyService {
	private EmployeeDao employeeDao;

	public void setEmployeeDao(EmployeeDao dao) {
		this.employeeDao = dao;
	}

	doTask() {
		..
		employeeDao.addEmployee(e);
	}
}

beans.xml
	<bean id="employeeDaoMySQL" class="pkg.EmployeeDaoMySqlImpl" />
	<bean id="employeeDaoMongo" class="pkg.EmployeeDaoMongoDbImpl" />

 	<bean id="service" class="pkg.MyService">
 		<property name="employeeDao" ref= "employeeDaoMySQL" />
 	</bean>


ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");

OR

ApplicationContext ctx = new FileSystemXmlApplicationContext("/users/temp/config/beans.xml");

===

Metadata in the form of Annotation

1) @Component
2) @Repository
3) @Service
4) @Configuration
5) @Controller
6) @RestController


Wiring is done using @Autowired or @Inject [ Java EE specification ] annoation


@Component
public class Util {

}

interface EmployeeDao {
	void addEmployee(Employee e);
}

@Repository
public class EmployeeDaoMySqlImpl implements EmployeeDao {
	public void addEmployee(Employee e) {
		.. SQL
	}
}


@Service
public class MyService {
	@Autowired
	private EmployeeDao employeeDao;

	doTask() {
		..
		employeeDao.addEmployee(e);
	}
}


@Autowired ==> DI using type

==> ByteCode Instrumentation libraries ==> CGLib, ByteBuddy, JavaAssist

 

 =======================

 Spring Boot a library which simplifies using Spring Framework [5.x]

 ==> Simplifies using Spring framework
 ==> Highly opiniated ==> out of the box it configures lot of things
 	a) If we are using web applciation devlopment ==> configures Embedded Tomcat server
 	But we can configure "jetty" , "netty" , ...

 	b) If we are using RDBMS
 		configures database connection pool using HikariCP library
 	c) If using JPA
 		uses Hibernate as JPA vendor
==> Easily dockerizable

Dockerfile
FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]



===========================

eclipse

Eclipse Market Place

Search for STS

Spring Tools 4.3.9 

================================================


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}


SpringApplication.run() ==> creates Spring container ==> ApplicationContext


@SpringBootApplication

1) @ComponentScan
	scans for above mentioned 6 annotations from "com.example.demo" and sub-packages
	and creates instances of these classes


2) @EnableAutoConfiguration
	based on depenceny included it instantes and configures
	a) tomcat embedded contaniner
	b) HibernateJPAVendor
	c) Connection Pool

3) @Configuration
	this class can act like configuration class


===

Devlopment mode I need MongoDB and production mode I need SQL ==> choose beans based on profile

@Repository
@Profile("prod")
public class EmployeeDaoMongoImpl implements EmployeeDao {

	@Override
	public void addEmployee() {
		System.out.println("mongodb code..");
	}

}

@Repository
@Profile("dev")
public class EmployeeDaoDbImpl implements EmployeeDao {
	@Override
	public void addEmployee() {
		System.out.println("SQL code..");
	}
}

1) Program arguments:
--spring.profiles.active=dev

Run as ==> Run ==> arguments ==> Program arguments
--spring.profiles.active=dev

2) application.properties
spring.profiles.active=dev

3) System properties

CommandLine ==> System properties ==> application.properties







