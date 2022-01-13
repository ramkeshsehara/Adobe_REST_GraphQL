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


Place Order:
OrderService ==> getOrders()
OrderController

Browser: http://localhost:8080/api/orders

POSTMAN:
POST http://localhost:8080/api/orders
body:

{
	"customer" : {
		"email": "a@adobe.com"
	},
	"items": [
			{
				"quantity" : 2,
				"product" : {
					"id": 1
				}
			},
			{
				"quantity" : 1,
				"product" : {
					"id": 4
				}
			}
	]	
}

======================================================================

AOP ==> Aspect Oriented Programming

Need ==> eliminate Cross-Cutting Concerns which leads to code tangling and code scattering

Aspect
	 A concern which is not a part of main logic but can be used along with main logic;
	  leads to code tangling and code scattering

	  ==> Logging
	  ==> Security
	  ==> Transaction
	  ==> Profile

  public void transferFunds(...) {
  	takeStartTime()
  	log.info("transafer funcds called");
  	if(security.isValid()) {
  		log.info("valid user doing ...")
  		tx.begin()
  		credit()
  		log.info("credited")
  		debit();
  		log.info("debited")
  		tx.commit();

  	}
  	log.info("transafer funcds complted");
  	takeEndTime();
  }

Join point
A point where aspect can be weaved
==> any method or exception

Pointcut
==> selected JoinPoint
==> regex sort of expression

Advice
		==> Before
		==> After
		==> AfterReturing
		==> Around
		==> AfterThrowing
==> No Dynamic Pointcut

m1() {
	...
	ret = m2();
	...
	..
}

m2() {
	computate here and return
}

@AfterReturning(...)


ProxyObject

================

execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern)
          throws-pattern?)

====================


		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.7.0</version>
		</dependency>
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.7.0</version>
		</dependency>


Resolved [org.springframework.web.bind.MethodArgumentNotValidException: Validation failed for argument [0] in public org.springframework.http.ResponseEntity<com.adobe.entity.Product>

 com.adobe.api.ProductController.addProduct(com.adobe.entity.Product) with 2 errors: 

 [Field error in object 'product' on field 'name': rejected value []; codes [NotBlank.product.name,NotBlank.name,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [product.name,name]; arguments []; default message [name]]; default message [Name is required]] [Field error in object 'product' on field 'price': rejected value [-8900.0]; codes [Min.product.price,Min.price,Min.double,Min]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [product.price,price]; arguments []; default message [price],0]; default message [Price -8900.0 should be more than 0]] ]


=======================================

unit testing

<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
</dependency>

==> JUnit as Unit Testing Framework / TestNG
==> Mockito ==> Mocking library [ EasyMock / JMock]
==> Hamcrest ==> assertion librarires
==> json-path


@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	DispatcherServletTest will be loaded
	and ProductController is loaded into Spring container


	@MockBean
	private OrderService service;

	creates a Mock OrderService;

	----

	@Autowired
	private MockMvc mockMvc;

	is used to perform CRUD operations in testing environment

	============================================================

	Document RESTful

	RAML and OpenAPI/Swagger

	RAML is YAML based
	.yml
	path
		uri:
			..
				get:
					//
					arg

Swagger is API based way of documnetation




@Api
@ApiOperation
...

for customization

=============================================

Association Mapping

OneToMAny is LAZY by default
ManyToOne is EAGER by default

fetch = FetchType.EAGER | FetchType.LAZY

A
	B
		C
			D
			E
			F
		X
			Y


A, B, Y

A, B, C , E, F

A, X, Y


===> Entity Graph as solution

EntityGraph

@NamedEntityGraph(name = "companyWithDepartmentsGraph",
                attributeNodes = {@NamedAttributeNode("departments")})





 @NamedEntityGraph(name = "companyWithDepartmentsAndEmployeesGraph",
                attributeNodes = {
                		@NamedAttributeNode(value = "departments", subgraph = "departmentsWithEmployees")},
                subgraphs = @NamedSubgraph(
                        name = "departmentsWithEmployees",
                        attributeNodes = @NamedAttributeNode("employees"))),



hints.put("javax.persistence.fetchgraph", entityGraph);
When using fetchgraph all relationships are considered to be lazy regardless of annotation



hints.put("javax.persistence.loadgraph", entityGraph);
uses annotation + EAGER fetch nodes



====

companyWithDepartmentsGraph

    select
        company0_.id as id1_2_0_,
        company0_.name as name2_2_0_,
        department1_.company_id as company_3_3_1_,
        department1_.id as id1_3_1_,
        department1_.id as id1_3_2_,
        department1_.company_id as company_3_3_2_,
        department1_.name as name2_3_2_ 
    from
        company company0_ 
    left outer join
        department department1_ 
            on company0_.id=department1_.company_id 
    where
        company0_.id=?


===
companyWithDepartmentsAndEmployeesGraph

select
        company0_.id as id1_2_0_,
        company0_.name as name2_2_0_,
        department1_.company_id as company_3_3_1_,
        department1_.id as id1_3_1_,
        department1_.id as id1_3_2_,
        department1_.company_id as company_3_3_2_,
        department1_.name as name2_3_2_,
        employees2_.department_id as departme5_4_3_,
        employees2_.id as id1_4_3_,
        employees2_.id as id1_4_4_,
        employees2_.address_id as address_4_4_4_,
        employees2_.department_id as departme5_4_4_,
        employees2_.name as name2_4_4_,
        employees2_.surname as surname3_4_4_ 
    from
        company company0_ 
    left outer join
        department department1_ 
            on company0_.id=department1_.company_id 
    left outer join
        employee employees2_ 
            on department1_.id=employees2_.department_id 
    where
        company0_.id=?

 ===================


SOAP Vs REST Vs GraphQL [ 2012 ==> 2015]

https://jsonplaceholder.typicode.com/users

============

schema.graphqls ==> resources folder

here we define Type ==> scalar, Object, union, interface, 

Query is a special Type ==> Root type where endpoints are declared

Single URI

POST http://localhost:8080/graphql

request payload contains "query"

response comes in the form of json

spring.graphql.path=/graphql


spring.graphql.graphiql.enabled=true
spring.graphql.graphiql.path=/graphiql



===================

https://www.graphql-java.com/

https://www.graphql-java-kickstart.com/spring-boot/

=====================

lombok

@Data
@Slfj
public class Book {

}

Download Lombok Jar File
https://mvnrepository.com/artifact/org.projectlombok/lombok

java -jar lombok-1.18.22.jar

==================================================

SOAP vs REST vs GraphQL

/products
/orders ==> 100 orders (1)
/customers/id ==> n hits

/graphql POST



spring.graphql.path=/graphql


spring.graphql.graphiql.enabled=true
spring.graphql.graphiql.path=/graphiql

===================

graphql-java ==> DataDetchers, DataLoaders, RuntimeWiring, Schema handlers
graphql-kickstart ==> Resolvers

=========================


schema {
	query: Query
	mutation: Mutation
	subscription: Subscription
}

Query, Mutation and Subscription are special types


type Product {
	fields ==> scalar, Object, enum, interfac, null
}

ID, String, Float, Boolean, Int

extended-scalars
https://github.com/graphql-java/graphql-java-extended-scalars

<dependency>
  <groupId>com.graphql-java</groupId>
  <artifactId>graphql-java-extended-scalars</artifactId>
  <version>17.0</version>
</dependency>

==============

	
	graphql-java-kickstart:

		<dependency>
			<groupId>com.graphql-java-kickstart</groupId>
			<artifactId>graphql-spring-boot-starter</artifactId>
			<version>11.1.0</version>
		</dependency>


  GraphiQL ==> client like POSTMAN to Query GraphQL

		<dependency>
			<groupId>com.graphql-java-kickstart</groupId>
			<artifactId>graphiql-spring-boot-starter</artifactId>
			<version>11.1.0</version>
		</dependency>


voyager ==> schema explorer ==> Like Swagger

		<dependency>
			<groupId>com.graphql-java-kickstart</groupId>
			<artifactId>voyager-spring-boot-starter</artifactId>
			<version>11.1.0</version>
		</dependency>

===========


"resources" folder
schema.graphqls

type Query {
	helloWorld:String!
}

==> we need Resolvers
==> GraphQLQueryResolver

@Component
public class HelloWorldQueryResolver implements GraphQLQueryResolver {
	//public String getHelloWorld() {
	public String helloWorld() {
		return "Hello World GraphQL!!!";
	}
}


===


http://localhost:8080/graphiql

Query -=->
query {
  helloWorld
}

output ===>

{
  "data": {
    "helloWorld": "Hello World GraphQL!!!"
  }
}


http://localhost:8080/voyager

=============


curl --location --request POST 'http://localhost:8080/graphql' \
--header 'Content-Type: application/json' \
--data-raw '{"query":"query {\r\n  helloWorld\r\n}","variables":{}}'

=======

type Query {
	helloWorld:String!
	greeting(firstName:String!, lastName:String): String!
}

@Component
public class HelloWorldQueryResolver implements GraphQLQueryResolver {
	//public String getHelloWorld() {
	public String helloWorld() {
		return "Hello World GraphQL!!!";
	}
	
	public String greeting(String firstName, String lastName) {
		return String.format("Hello %s %s ", firstName, lastName);
	}
}


http://localhost:8080/graphiql

query {
  greeting(firstName: "Banu", lastName : "Prakash")
}

================================

application.properties

spring.jpa.hibernate.ddl-auto=none

schema.graphqls

type Query {
	helloWorld:String!
	greeting(firstName:String!, lastName:String): String!
	# return collection of books	
	books:[Book]
}

type Book {
	id:Int,
	title:String!,
	totalPages:Int,
	rating:Float,
	isbn:String
}



@Entity
@Table(name="books")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Book {
	@Id
	@Column(name="book_id")
	private int id;
	
	private String title;
	@Column(name="total_pages")
	private Integer totalPages;
	
	private double rating;
	private String isbn;
}


public interface BookDao extends JpaRepository<Book, Integer> {

}



@Component
public class BookQueryResolver implements GraphQLQueryResolver {
	
	@Autowired
	private BookDao bookDao;
	 
	public List<Book> getBooks() {
		return bookDao.findAll();
	}
}



==================

query {
  books {
    id
    title
    rating
    isbn
  }
}

====

query {
  books {
    id
    ...BookInfo
  }
}

fragment BookInfo on Book {
  title
  totalPages
  isbn
}

=========================
type Query {
	helloWorld:String!
	greeting(firstName:String!, lastName:String): String!
	# return collection of books	
	books:[Book]
	bookById(id:Int):Book
}

client: 
query {
  bookById(id:2){
    id
    title
  }
}

@Component
public class BookQueryResolver implements GraphQLQueryResolver {
	
	@Autowired
	private BookDao bookDao;
	 
	public List<Book> getBooks() {
		return bookDao.findAll();
	}
	
	public Book getBookById(int id) {
		return bookDao.findById(id).get();
	}
}

===================================================

GraphQL unit Testing

<dependency>
			<groupId>com.graphql-java-kickstart</groupId>
			<artifactId>graphql-spring-boot-starter-test</artifactId>
			<version>11.1.0</version>
		</dependency>

	we get GraphQLTestTemplate.java
	Helper class to test GraphQL queries and mutations.

=================

org.opentest4j.AssertionFailedError: expected: <{"data": {"bookById": {"id": 2,"title": "Some Book"}}}> but was: <{"data":{"bookById":{"id":2,"title":"Some Book"}}}>
 
==================
GraphQLResolver
GraphQLQueryResolver ==> resolve how each field of a type is fethed

query {
  bookById(id:2){
    id
    title
    publisher {
      name
    }
  }
}




{
  "errors": [
    {
      "message": "Internal Server Error(s) while executing query",
      "locations": []
    }
  ],
  "data": {
    "bookById": {
      "id": 2,
      "title": "Facing the Intelligence Explosion",
      "publisher": {
        "name": null
      }
    }
  }
}

========

Book.java
extra mapping to FK
ManyToOne

Publisher
PublisehDao

BookfieldQueryResolver

=============


Publihser.java
BookDao.java
PublisherDao.java
PublisherQueryReolver
PublisherFieldResolver


query {
   publishers {
    name
    books {
      title
      rating
    }
  }
}

=========================================

 Anonymous Operation:

 query {
   bookById(id: 5) {
    title 
    publisher {
      name
      books {
        title
      }
    }
  }
}


==> DataFetchingEnvironment ==> getOperation()

Named Operation:

query GET_BOOKS_BY_ID {
   bookById(id: 5) {
    title 
    publisher {
      name
      books {
        title
      }
    }
  }
}



====

query GET_BOOKS_BY_ID($bookid: Int) {
   bookById(id: $bookid) {
    title 
    publisher {
      name
      books {
        title
      }
    }
  }
}


Query Variables:

{
  "bookid": 5
}


====================
 
Query, GraphQLQueryResolver and GraphQLResolver [ field ]

type, fragment

===========================================

* Custom Scalar Type

scalar Date


type Book {
	id:Int,
	title:String!,
	totalPages:Int,
	rating:Float,
	isbn:String,
	publishedDate: Date,
	publisher:Publisher # Many To One
}

===

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

==

query GET_BOOKS_BY_ID($bookid: Int) {
   bookById(id: $bookid) {
    title 
    publishedDate
  }
}

============================================

* Directives
used with FIELD, ARGUMENT, INPUT

Built-in Directives
@skip
@include
@deprecated
@specifiedBy

===

directive @deprecated on FIELD_DEFINIITION


totalPages:Int @deprecated(reason:"prefer using pages"),

====



query GET_BOOKS_BY_ID($bookid: Int, $pubReq: Boolean!) {
   bookById(id: $bookid) {
    title 
    publishedDate
    totalPages
    publisher @include(if : $pubReq) {
      name
    }
  }
}

{
  "bookid": 5,
  "pubReq": false
}


===================


* Custom Directive

title:String! @uppercase,

SchemaDirectiveWiring



title:String @auth(role:"admin")


=======================================================
Query

Mutation



type Mutation {
	createAuthor(author:AuthorInput):Int
}

input AuthorInput {
	firstName:String!,
	lastName:String,
	middleName:String
}



public interface AuthorDao extends JpaRepository<Author, Integer>{

}

@Component
public class AuthorMutationResolver implements GraphQLMutationResolver {
	@Autowired
	private AuthorDao authorDao;
	
	public Integer createAuthor(Author author) {
		return authorDao.save(author).getId();
	}
}

mutation {
  createAuthor(author: {
    firstName : "Banu",
    lastName : "Prakash"
  })
}


==========================================


GraphQLError Handling

query {
  bookById(id:9990) {
    title
  }
}

{
  "errors": [
    {
      "message": "Internal Server Error(s) while executing query",
      "locations": []
    }
  ],
  "data": {
    "bookById": null
  }
}

=========================

public class ResourceNotFoundException extends RuntimeException implements GraphQLError {

==
	DataFetcherResult:

	{
  "errors": [
    {
      "message": "Exception while fetching data (/bookById) : Book with id 9990 doesn't exist!!!",
      "locations": [
        {
          "line": 2,
          "column": 3
        }
      ],
      "path": [
        "bookById"
      ],
      "extensions": {
        "classification": "DataFetchingException"
      }
    }
  ],
  "data": {
    "bookById": null
  }
}


===

public class SimpleGraphQLErrorHandler implements GraphQLErrorHandler {
	 public List<GraphQLError> processErrors(List<GraphQLError> errors) {
	 	// to avoid complete trace
	 }
}

====

Scalar type ==> Coercing
Directive ==> SchemaDirectiveWiring
Mutation
GraphQLError



DataFetcherResult:
	
public DataFetcherResult<Book> partialInfoBookById(int id) {
		return DataFetcherResult.<Book>newResult()
				.data(bookDao.findById(id).get())
				.error(new GenericGraphQLError("could not get publihser of book")).build();
}


query {
  partialInfoBookById(id:2) {
    title
  }
}


{
  "errors": [
    {
      "message": "could not get publihser of book",
      "locations": []
    }
  ],
  "data": {
    "partialInfoBookById": {
      "title": "FACING THE INTELLIGENCE EXPLOSION"
    }
  }
}

=====================

Pagination
offset way:


booksPaginate(page:Int, size:Int) :[Book]

List<Book> booksPaginate(int page, int size) {
	Pageable page = PageRequest.of(page, size);
	return bookDao.findAll(page).getContent();
}

===

Relay for pagination

cursor-based pagination



  hero {
    name
    friends(first:2) {
      totalCount
      edges {
        node {
          name
        }
        cursor
      }
      pageInfo {
        hasPreviousPage
        hasNextPage
      }
    }
  }
}



type Query {
	helloWorld:String!
	greeting(firstName:String!, lastName:String): String!
	# return collection of books	
	books:[Book]
	bookById(id:Int):Book
	publishers: [Publisher]
	partialInfoBookById(id:Int):Book
	booksByPage(first:Int, after:String): BookConnection
}

type BookConnection {
	edges: [BookEdge]
	pageInfo: PageInfo
}

type BookEdge {
	cursor: String
	node: Book
}

type PageInfo {
	hasPreviousPage: Boolean!
	hasNextPage: Boolean!
}


=====



cursor 
pageInfo
edges
	node ==> acutual element
	cursor

query {
  booksByPage(first:5) {
    edges {
      cursor
      node {
        title
        rating
        isbn
      }
    }
    pageInfo {
      hasPreviousPage
      hasNextPage
    }
  }
}


query {
  booksByPage(first:5, after: "c2ltcGxlLWN1cnNvcjg=") {
    edges {
      cursor
      node {
        title
        rating
        isbn
      }
    }
    pageInfo {
      hasPreviousPage
      hasNextPage
    }
  }
}

======


Type Definition Factory

@connection

booksByPage(first:Int, after:String): @connection(for:"Book")

no need for BookConnection, BookEdge and PageInfo types to be declared

===============

Union Example:

type Product {
	id: Int,
	name:String,
	price: Float
}

type Tv {
	id: Int,
	name:String,
	price: Float,
	screenType:String
}

type Mobile {
	id: Int,
	name:String,
	price: Float,
	connectivity:String
}

union ProductUnion = Product | Tv | Mobile

extend type Query {
	products: [ProductUnion]
}


===


	@Bean
	public SchemaParserDictionary getSchemaParserDictionary() {
		return new SchemaParserDictionary().add(Mobile.class).add(Tv.class).add(Product.class);
	}

===



@Component
public class ProductResolver implements GraphQLQueryResolver {
	public List<Product> getProducts() {
		Product[] products = new Product[5]; // Array of 5 Pointers
		products[0] = new Tv(133, "Sony Bravia", 135000.00, "LED"); // upcasting
		products[1] = new Mobile(453, "MotoG", 12999.00, "4G");
		products[2] = new Tv(634, "Onida Thunder", 3500.00, "CRT");
		products[3] = new Mobile(621, "iPhone XR", 99999.00, "4G");
		products[4] = new Mobile(844, "Oppo", 9999.00, "4G");
		return Arrays.asList(products);
	}
}





query {
   products {
    __typename
    
    ... on Mobile {
      id
      name
      connectivity
    }
    
    ... on Tv {
      name
      screenType
    }
  }
}


=======================

Day 4



Day 3 Recap:

* resources folder we need files with "graphql" or "graphqls" extension which contains GraphQL SDL
* schema has Query, Mutation and Subscription built-in types 

schema {
	query: Query,
	mutation: Mutation,
	subscription: Subscription
}

type Query {

}

type Mutation {

}

type Subscription {

}


* User types

type Book {

	scalar
	object
	enum
}

Built-in Scalar types ==> Int, ID, String, Boolean, Float
extended-scalars ==> PositiveInt, ...

Custom Scalar values

scalar Email

type Person {
	email: Email
}


Coercing implementation ==> parseLiteral, parseValue and serialize

---

type Query {
	books: [Book]
}
* GraphQLQueryResolver
* GraphQLResolver<Book> to get fields of Book 

type Mutation {
	createBook(...)
}

* GraphQLMutationResolver


---

* Directives

@include
@deprecated
@skip

Custom Directive
	SchemaDirective ==> Wiring
	UppercaseDirective
	@uppercase

----

GraphQLError

---

Union
Fragments

--------------------

Pagination
relay API Connection

Connection has pageInfo and Edge
Edge has cursor and node
node is a wrapper for type [ book ...]


---------------------------------------------------

DataFetcherResult ==> errors and data

To pass partial data

-------------------------------------------------

* Async Operations

Promise API


===============

Subscription

Publisher - Subscriber
Observable -- Observer model


<dependency>
			<groupId>io.reactivex.rxjava2</groupId>
			<artifactId>rxjava</artifactId>
			<version>2.1.0</version>
	</dependency>



type Subscription {
 authors:Author!
}

type Author {
	id: Int,
	firstName: String,
	lastName: String,
	middleName :String
}



@Component
public class AuthorSubscription implements GraphQLSubscriptionResolver {
	
	@Autowired
	private AuthorPublisher publisher;
	
	public Publisher<Author> authors() {
		return publisher.getPublisher();
	}
}




===

Client 1:
subscription {
  authors {
     firstName
  }
}

Client 2:
mutation {
  createAuthor(author : {
     firstName : "DG"
    lastName : "G"
  })
}

