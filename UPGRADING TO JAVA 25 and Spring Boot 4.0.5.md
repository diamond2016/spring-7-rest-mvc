UPGRADING TO JAVA 25 and Spring Boot 4.0.5

Passing to this scenario the base import definition in pom.xml needed for tests is org.spring.framework.boot and spring-boot-starter-test.

Parant masu have org.spring.framework.boot /spring-boot-starter-parent / version 4.0.5

**All dependencies (spring-boot-starter-webmvc-test, spring-boot-starter-data-jpa-test, spring-boot-test-autoconfigure) are properly included in pom.xml**

New BootstrapDataTest (use flyway)
./mvnw -Dspring.profiles.active=localh2 -Dtest=BootstrapDataTest test
./mvnw -Dspring.profiles.active=localh2 test (all tests)