Test suite strategy and usage

Overview

This project uses a hybrid testing strategy:

- Fast unit / slice tests: run by Maven Surefire. These are generally H2-backed and run quickly.
- MySQL-backed integration tests: use Testcontainers to run a real MySQL instance in Docker. These are tagged with `@Tag("mysql")` and executed by the Maven Failsafe lifecycle (integration-test/verify).

Goals

- Keep fast tests fast and default for quick development cycles.
- Run MySQL-backed integration tests in an isolated Docker container (Testcontainers) when you need full MySQL compatibility and Flyway migrations validation.
- Make commands explicit so you can run H2-only or MySQL tests as needed.

Prerequisites

- Docker installed and running locally (Testcontainers requires Docker).
- Java 21 and Maven wrapper (`./mvnw`) available.

Key files & conventions

- `pom.xml`:
  - Surefire configured to exclude tests tagged `mysql`.
  - Failsafe configured to include tests tagged `mysql`.
  - Testcontainers BOM is imported for version management.
  - Two profiles are present: `mysql` (for integration runs) and `h2` (for fast H2-only runs).

- Tests:
  - MySQL-backed tests are tagged with `@Tag("mysql")` and extend the shared base `guru.springframework.spring7restmvc.test.AbstractMySqlIntegrationTest` which starts a static Testcontainers `MySQLContainer` and injects `spring.datasource.*` properties.
  - H2-backed tests remain untagged (fast) and are executed by Surefire.

Commands / Scenarios

1) Quick feedback — run fast unit/H2 tests only

```bash
# run default fast tests (H2)
./mvnw -P h2 test
```

2) Run a specific test (Testcontainers will start container if the test requires it)

```bash
# run a single test class (if it's mysql-tagged, Testcontainers starts the container)
./mvnw -Dtest=BeerRepositoryTest test
```

3) Run all integration (MySQL) tests

```bash
# run failsafe lifecycle to execute mysql-tagged tests
./mvnw -P mysql verify
# or (verify runs failsafe which executes mysql-tagged tests)
./mvnw verify
```

4) Run everything (fast + integration)

```bash
# runs surefire and failsafe; mysql tests will be executed by failsafe
./mvnw clean verify
```

Notes about performance

- MySQL tests are slower due to container start and Flyway migrations. Static containers reduce repeated startups when running multiple tests in the same JVM.
- Keep the H2-suite as the default developer flow. Run MySQL tests on-demand or in CI.

CI considerations

- CI runners must support Docker for Testcontainers. On GitHub Actions use `jobs.<job>.runs-on: ubuntu-latest` and ensure Docker is available (default on hosted runners).
- Add a separate CI job to run `./mvnw -P mysql verify` (integration step). Keep quick unit tests in a separate job for fast feedback.

Housekeeping / Cleanup

- `spring-boot-docker-compose` dependency was removed to avoid duplicate approaches (we use Testcontainers).
- Keep `flyway-core` and MySQL driver; `flyway-mysql` may remain if you depend on vendor-specific support — remove if unnecessary.

Next steps you may want to run

- Convert remaining MySQL-specific tests to extend the shared Testcontainers base and tag them `mysql` (you said you'll do this incrementally).
- Add a CI job example (I can provide a GitHub Actions workflow file if you want).

Troubleshooting

- If Testcontainers can't connect to Docker, check your Docker socket permissions and that Docker is running.
- If an image pull fails due to rate limits, consider authenticating docker (`docker login`) or using a mirrored registry.

If you want, I can now:
- Convert all remaining tests that currently use `@ActiveProfiles("localmysql")` to extend the Testcontainers base and tag them `mysql`.
- Add a GitHub Actions workflow example that runs `./mvnw -P mysql verify` in CI.

