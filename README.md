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


======

application.properties
dao=mongo

@ConditionalOnProperty(name="dao", havingValue = "db")
public class EmployeeDaoDbImpl implements EmployeeDao {

@ConditionalOnProperty(name="dao", havingValue = "mongo")
public class EmployeeDaoMongoImpl implements EmployeeDao {


---

@ConditionalOnMissingBean("employeeDaoMongoImpl")
public class EmployeeDaoDbImpl implements EmployeeDao {
}

------------

@Configuration


1) inject values into variables from properties file

2) Use factory methods to create objects and hand it over to Spring container

	2.1) 3rd party library provided classes needs to be used in Spring container
	Example: DatabaseConnection Pool

	@Configuration
	public class MyConfig {

		// factory methods
		@Bean
		public DataSource getDataSource() {
			ComboPooledDataSource cpds = new ComboPooledDataSource();
			cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver            
			cpds.setJdbcUrl( "jdbc:postgresql://localhost/testdb" );
			cpds.setUser("swaldman");                                  
			cpds.setPassword("test-password");                                  
				
			// the settings below are optional -- c3p0 can work with defaults
			cpds.setMinPoolSize(5);                                     
			cpds.setAcquireIncrement(5);
			cpds.setMaxPoolSize(20);
			return cpds;
		}
	}

	@Service
	class MyService {
		@Autowired
		@DataSource ds;
	}

	2.2) Spring always used default constructor to instantiate

	public class MessagingService {
	private String url;
	private int port;
	
	public MessagingService(String url, int port) {
		this.url = url;
		this.port = port;
	}
	
	public void sendMessage(String msg) {
		System.out.println("message " + msg + "  sent to " +url);
	}
}


	@Configuration
	public class MyConfig {
	
	@Value("${url}")
	private String url;
	
	@Value("${port}")
	private int port;
	
	@Bean
	public MessagingService msgService() {
		return new MessagingService(url, port);
	}
}

--

@Service
public class EmployeeService {
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private MessagingService service;
	
	public void doTask() {
		service.sendMessage("employee added!!!");
		employeeDao.addEmployee();
	}
}

==================================================

JPA ==> Java Persistence API

ORM ==> Object Relation Mapping ==> RDBMS

ODM ==> Object Data Mapping ==> MongoDB


Java < ==== > Relational database table

ORM is a library on top of JDBC to simplfiy dtabase operations [ CRUD operations]

Customer.java and Address.java in Java

public class Customer {
		id
		firstName
		Address address
}

public class Address {
	street
	city
}


RDBMS
1) 
single table
id | firstName | street | city

JDBC code to insert into.. 
select from ..

2)
customer_table

id | first_name
1 		X

address table
id | street   | city
1    M G Road  BLORE

3)
customer_table

id | first_name
1 		X

address table
aid | street   | city | customer_id
101    M G Road  BLORE  | 1




ORM

@Table(name="customers")
public class Customer {

	@Column(name="fname")
	private String firstName;

	private int age;
}


Customer c = new Customer();

save(c); // generate INSERT into customers(fname,age) values (...)

findAll(); // select fname, age from customers


ORM libraries
1) Hibernate
2) TopLink
2) EclipseLink
3) KODO
4) OpenJPA
5) JODO
...

JPA is a specification for ORMs

JPA ==> ORM ==> JDBC ==> RDBMS


==================================


JPA

		@Bean
		public DataSource getDataSource() {
			ComboPooledDataSource cpds = new ComboPooledDataSource();
			cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver            
			cpds.setJdbcUrl( "jdbc:postgresql://localhost/testdb" );
			cpds.setUser("swaldman");                                  
			cpds.setPassword("test-password");                                  
				
			// the settings below are optional -- c3p0 can work with defaults
			cpds.setMinPoolSize(5);                                     
			cpds.setAcquireIncrement(5);
			cpds.setMaxPoolSize(20);
			return cpds;
		}


		@Bean
		public EntityManagerFactory emf(DataSource ds) {
			LocalContainerEntityManagerFactoryBean emfBean = new ...
			emfBean.setJpaVendor(new HibernateJPAVendor());
			emfBean.setDataSource(ds);
			emfBean.setPackagesToScan("com.adobe.prj.entity"); // @Entity
			..
			return emf;
		}


===

@Repository
public class EmployeeDaoJpaImpl implments EmployeeDao {
	@PersistenceContext
	EntityManager em;

	addEmployee(e) {
		em.save(e);
	}

	...

}

========

Spring Data JPA simplifies using JPA

Spring Boot
 ==> configures HikariCP
 ==> configures EntityManagerFactory
 ==> no need for @Repository class

 public interface EmployeeDao extends JPARepository<Employee, Integer> {

 }

 out of the box all CRUD operations are available

 ==> internally it creates an instance of EmployeeDao which does CRUD operations with pre-defined methods


============================







