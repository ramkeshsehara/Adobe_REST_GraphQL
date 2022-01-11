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




spring.jpa.hibernate.ddl-auto=update

==> update ==> create table if not exist; if exists use it; if required alter

==> validate ==> check if database tables match your entity; if true use it else throw exception

==> create ==> drop and create tables for every exection ==> good for testing

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true


spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect



C:\Users\banup>docker exec -it local-mysql bash
root@a89ad477d23e:/# mysql -u "root" -p
Enter password:
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 18
Server version: 8.0.26 MySQL Community Server - GPL

Copyright (c) 2000, 2021, Oracle and/or its affiliates.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> use ADOBE_JAVA_2022
Reading table information for completion of table and column names
You can turn off this feature to get a quicker startup with -A

Database changed
mysql> show tables
    -> ;
+---------------------------+
| Tables_in_ADOBE_JAVA_2022 |
+---------------------------+
| products                  |
+---------------------------+
1 row in set (0.00 sec)

mysql> select * from products;
+----+----------------+--------+------+
| id | name           | price  | qty  |
+----+----------------+--------+------+
|  1 | iPhone 13      |  98000 |  100 |
|  2 | Sony Bravia    | 128000 |  100 |
|  3 | Samsung Refri  |  67000 |  100 |
|  4 | Logitech Mouse |    800 |  100 |
+----+----------------+--------+------+
4 rows in set (0.00 sec)

mysql>


===

JpaRepository Custom Queries

public interface ProductDao extends JpaRepository<Product, Integer>{
	List<Product> findByPrice(double price); // select * from products where price = ?
	List<Product> findByPriceAndQuantity(double price, int quantity); // select * from products where price = ? and qty = ?
	List<Product> findByPriceOrQuantity(double price, int quantity); // select * from products where price = ? or qty = ?
}


JPQL ==> Java Persistence Query Language 
uses class and variables

SQL => uses table names and column names

	@Query("from Product where price >= :l and price <= :h")
	List<Product> getByRange(@Param("l") double low, @Param("h") double high);


	@Query(value = "select * from products where price >= :l and price <= :h", nativeQuery = true)
	List<Product> getByRange(@Param("l") double low, @Param("h") double high);

	JDBC:
	executeQuery() ==> select

	exceuteUpdate() == > INSERT, DELETE and UPDATE

========================

Spring provides Declartive transaction support
	
	Programatic transaction:

	@Override
	public void addProduct(Product p) throws DaoException {
		String SQL = "INSERT INTO products (id, name, price, quantity) VALUES (0, ?, ?, ?)";
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement(SQL);
			ps.setString(1, p.getName());
			ps.setDouble(2, p.getPrice());
			ps.setInt(3, p.getQuantity());
			ps.executeUpdate(); // INSERT, DELETE, UPDATE
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw new DaoException("unable to add product " + e.getErrorCode() , e);
		} finally {
			DBUtil.closeConnection(con);
		}
	}



	@Transactional
	public void updateProduct(int id, double price) {
		productDao.updateProduct(id, price);
	}

	if any exception comes out of this method ==> rollback else commit

==========================



@Transactional
public void m1() {
	code
	m2();
	m3();
	code
}

@Transactional(TxType.REQUIRES_NEW)
public void m2() {
	...
}

@Transactional(TxType.REQUIRED)
public void m3() {
	...
}


m1(); 
 ==> Transaction is started [ TxA is started]
 ==> when m2() is called [ TxA is suspended] and [ TxB is started]
 	if any exception occurs in m2() only TxB is rolledback, no effect on TxA
 ==> when m2() complments ==> TxA resumes
 ==> when m3() is called TxA is passed to m3()
   => if m3() has exceptions TxA rollsback, code in m1 nad m3() are rolled back

==>
 MakeMyTrip
 	hotel booking () REQUIRES_NEW
 	flight booking() REQUIRES_NEW
 	return ticket() REQUIRES_NEW

Banking
	credit() REQUIRED
	debit() REQUIRED

===============================

JPA ==> Mapping Associations 



@Entity
@Table(name="orders")
public class Order {
 	 ...
	
	@OneToMany
	@JoinColumn(name="order_fk")
	private List<Item> items = new ArrayList<Item>();


Order ord has 4 items
To Save:
save(ord);
save(item1);
save(item2);
save(item3);
save(item4);

To delete:

delete(ord);
delete(item1);
delete(item2);
delete(item3);
delete(item4);
///

===

With Cascade

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="order_fk")
	private List<Item> items = new ArrayList<Item>();



Order ord has 4 items
To Save:
save(ord); ==> saves items also
 

To delete:
delete(ord); ==> delete items of order

No need for ItemDao

===

orderDao.findById(3);
 gets order but not items

itemDao.findItemOfOrder(3);

===

private Customer customer;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="order_fk")
	private List<Item> items = new ArrayList<Item>();

orderDao.findById(3);
 gets order and its  items also

==

By Default:
@ManyToOne is EAGER fetching
@OneToMany is LAZY fetching


======


UI:
	{
		"customer": {
			"email": "a@adobe.com"
		},
		items: [
			{
				"product": {
					id: 3
				},
				quantity: 2
			},

			{
				"product": {
					id: 2
				},
				quantity: 1
			},
		]
	}


Dirty Checking in JPA
within a @Transactional if entity gets changed; it is sync with DB =-> update SQL is trigged

======================================

Customer

Vehicle
	regNo
	type
	..

Rental
	id
	cost
	Vehicle vehicle
	Customer customer

Rental has manyToone with Vechicle
Rental has manyToone with Customer


===================================


RestfulWeb Services

===========================================


Day 2:


@SpringBootApplication => @Configuration, @ComponentScan, @EanbleAutoConfiguration

@Component, @Service, @Repository, @Configuration, @Autowired, @Bean

@Primary, @Qualifier, @Profile, @ConditionalOnProperty, @ConditionalOnBean


JPA
@Entity, @Table, @Id, @Column, @GeneratedValue, @ManyToOne, @OneToMany, @JoinColumn

==

DataSource, EntityManagerFactory, EntityManager [ wrapper for DB connection] -=> to perform CRUD operation

extends JpaRepository<T,ID>

@Query
@Modifing


====================


Building RESTful Web Services

Traditional Web application development ==> Server side rendered pages are sent to client

Application <--> Application 
	SOAP ==> Simple Object Access Protocol ==> Web Services
	SOAP envelop has XML payload, headers, auth details
	DOMParser, SAXParser, ...

	REST ==> Representational State Transfer ==> Architecture Principle ==> HTTP Protocol

	Resource ==> file, database, printer, ...

	State of Resource is served to client in various formats => XML, CSV, JSON [JavaScript Object notation]
	Object
	{
		key: value
	}

	collection []

	HTTP headers:
		"accept" : "application/json" ==> to inform the server to send JSON data to client

		"content-type" : "text/xml" ==> client payload to server is on "XML" server

	Resources are accessed using plural nouns ==> URI

	http://localhost:8080/api/products

	http://localhost:8080/api/orders

	http://localhost:8080/api/customers


	Actions are performed using VERBS ==> HTTP methods ==> GET, POST, PUT, DELETE


	CREATE ==> POST
	READ ==> GET
	UPDATE ==> PUT / PATCH
	DELETe ==> DELETE

	1) 
	GET
	http://localhost:8080/api/products

	server sends all products


	2) PathParameter to get a single record or sub-resource

		GET
	http://localhost:8080/api/products/4

	get a product whose id is 4


	GET
	http://localhost:8080/api/customers/a@adobe.com/orders

	3) QueryPArameter ==> filter

	GET
	http://localhost:8080/api/products?low=50000&high=100000
	http://localhost:8080/api/products?page=2&size=20

	4) 
	POST
	http://localhost:8080/api/products
	payload contains new product data which has to be added to "products" resource

	5) 
	PUT
	http://localhost:8080/api/products/4

	payload contains  product data which has to be updates to "product whose id is 4" resource

	6) 

	DELETE
	http://localhost:8080/api/products/4
	delete product whose id is "4"

	GET and DELETE has no payload and are IDEMPOTENT ==> Safe methods

	=======================================================================

	POSTMAN

	===============

	@Controller and @RestController

		@Controller ==> returns Views

		@RestController ==> returns data

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	@GetMapping()
	m1() {}

	@PostMapping()
	m2() {}
}


<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

* Tomcat Embedded Server is included
* HttpMessageHandler for JSON using Jackson library is included
	Java <--> JSON
		1) jackson
		2) jettison
		3) gson
		4) moxy

* DispatcherServlet, HandlerMapping

===========

@ResponseBody List<Product>
List<Product> has to be converted to representation [ XML / JSON ] and send it in response body
uses "accept" header


@RequestBody Product p
request body which has representation has  to be conveted to Java	
uses "content-type" header


http://localhost:8080/api/products
http://localhost:8080/api/products/4
http://localhost:8080/api/products?low=80000&high=500000


POSTMAN

POST http://localhost:8080/api/products

Headers:
accept: application/json
content-type: application/json



Body:

 {
     "name":"Tata Sky",
     "price":8900.0,
     "quantity":100
}

=======================
Default scope of Bean is "singleton"

1) 
@Service
@Scope("prototype")
public class OrderService {

A and B gets different instances of OrderService

class A {
	@Autowired
	OrderService service;
}

class B {
	@Autowired
	OrderService service;
}

2) 

@Service
@Scope("request")
public class OrderService {


When client makes a request ORderService instance is added to "request"

request.setAttribute("orderService", new OrderService());

once response is commited this OrderService instance is destroyed

3)

@Service
@Scope("session")
public class OrderService {

one per user session

session.setAttribute("orderService", new OrderService());

session.invalidate();

=============================


Resume @ 11: 20











