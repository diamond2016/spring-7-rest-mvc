# Spring Framework 7: Beginner to Guru
## Spring 7 Rest MVC

In this repo I wil follow with my updates, the repo of the course: Spring Framework 7: Beginner to Guru.

The original repository of the course is here: [Spring Framework 7 - Beginner to Guru](https://github.com/springframeworkguru/spring-7-rest-mvc). Forked at branch: `75-db-create-scripts`.

This is the link to the course: [Udemy course](https://www.udemy.com/share/1080GW3@Kt5Zs-OWJSp2b2ZHRUpH1uPg-WrZPmFawYMVDRlhg-N9-eS1aDN0wpl9iPkCEExT/).


This is the architecture up to now:

### RESTful Services in Spring Boot Framework

* Use project Lombok and Mockito for tests
* Created with Spring initializer java 21 maven & dependencies Spring Boot Web
* Jpa and Mapstruct for entities management
* H2 or MYSQL database (currently MYSQL)
* I use VsCode
* Added support for containerization with Docker
* Tests with Mockito

### Main activities 
- 20.11.2025 Introduced MySql and a new profile to switch between H2 or MYSQL
- 22.11.2025 Due to the new release of Spring Boot 4 and Spring framework 7 I'll create a new project and leave the previous in this repo: [spring6-rest-mvc](https://github.com/diamond2016/spring6-rest-mvc).
- 23.11.2025 Start with migrations (delta-db) using FlyWay.
- 24.11.2025 introduced Testcontainers (use Docker in tests with MySql)
- 27.11.2025 Make refactor for a new archtecture see doc. Release 01.01.00
- 12.12.2025 OpenCsv for CSV import/export and flyway migration scripts for both h2/mysql databases. 