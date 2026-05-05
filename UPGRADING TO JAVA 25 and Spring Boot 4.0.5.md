UPGRADING TO JAVA 25 and Spring Boot 4.0.5

Resolving problem in tests

Summary
Fixed the @WebMvcTest annotation issue in Spring Boot application after upgrading to Java 25 and Spring Boot 4.0.5.

Problem Identified
The test autoconfigure packages were reorganized in Spring Boot 4.0.5. The old packages like org.springframework.boot.test.autoconfigure.web.servlet no longer exist, causing compilation errors for @WebMvcTest, @DataJpaTest, and @AutoConfigureTestDatabase.

Solution Applied
Updated the import statements in all affected test files:

BeerControllerTest.java and CustomerControllerTest.java: Changed @WebMvcTest import from org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest to org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest

Repository test files (BeerRepositoryTest.java, CustomerRepositoryTest.java, CategoryRepositoryTest.java, BeerOrderRepositoryTest.java, BootstrapDataTest.java):

Changed @DataJpaTest import from org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest to org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
Changed @AutoConfigureTestDatabase import from org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase to org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase

Validation
Test compilation now succeeds without errors
Application starts successfully on port 8080 with Spring Boot 4.0.5 and Java 25

**All dependencies (spring-boot-starter-webmvc-test, spring-boot-starter-data-jpa-test, spring-boot-test-autoconfigure) are properly included in pom.xml**

NEXT STEP
go on with SpringSecurityConfig wich creates problems. Even testListBeers does not run. Ok solved with @EnableWebSecurity
now problem with "object mapper" probably to upgrade, see the target test testListBeers() in BeerControllerTest (No qualifying bean of type 'com.fasterxml.jackson.databind.ObjectMapper' available)