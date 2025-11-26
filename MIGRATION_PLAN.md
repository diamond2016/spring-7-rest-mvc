# File-by-file Migration Plan

This document lists all main Java sources and the suggested action for the package-layout refactor. The approach is conservative: verify package declarations and imports, and only move files when necessary in small commits, regenerating MapStruct after moves.

Legend:
- Action: `verify` = check package/imports after refactor, `move` = move file to a new package, `skip` = no action.

Files and planned actions:

- `src/main/java/guru/springframework/spring7restmvc/bootstrap/BootstrapData.java` — Action: verify (bootstrap stays under `bootstrap`).
- `src/main/java/guru/springframework/spring7restmvc/mapper/CustomerMapper.java` — Action: verify (mapper package okay). Regenerate MapStruct after any mapper/package moves.
- `src/main/java/guru/springframework/spring7restmvc/mapper/BeerMapper.java` — Action: verify.
- `src/main/java/guru/springframework/spring7restmvc/repository/CustomerRepository.java` — Action: verify (repository package OK).
- `src/main/java/guru/springframework/spring7restmvc/repository/BeerRepository.java` — Action: verify.
- `src/main/java/guru/springframework/spring7restmvc/service/CustomerService.java` — Action: verify (service APIs stay here).
- `src/main/java/guru/springframework/spring7restmvc/service/BeerService.java` — Action: verify.
- `src/main/java/guru/springframework/spring7restmvc/Spring7RestMvcApplication.java` — Action: skip (application bootstrap).
- `src/main/java/guru/springframework/spring7restmvc/service/impl/CustomerServiceJPA.java` — Action: verify (implementation already under `service.impl`).
- `src/main/java/guru/springframework/spring7restmvc/service/impl/BeerServiceJPA.java` — Action: verify.
- `src/main/java/guru/springframework/spring7restmvc/service/impl/CustomerServiceImpl.java` — Action: verify.
- `src/main/java/guru/springframework/spring7restmvc/service/impl/BeerServiceImpl.java` — Action: verify.
- `src/main/java/guru/springframework/spring7restmvc/model/dto/CustomerDTO.java` — Action: verify (dto package OK).
- `src/main/java/guru/springframework/spring7restmvc/model/dto/BeerStyle.java` — Action: verify.
- `src/main/java/guru/springframework/spring7restmvc/model/dto/BeerDTO.java` — Action: verify.
- `src/main/java/guru/springframework/spring7restmvc/model/entity/Beer.java` — Action: verify (entity package OK).
- `src/main/java/guru/springframework/spring7restmvc/model/entity/Customer.java` — Action: verify.
- `src/main/java/guru/springframework/spring7restmvc/controller/NotFoundException.java` — Action: verify (controller helper).
- `src/main/java/guru/springframework/spring7restmvc/controller/CustomerController.java` — Action: verify.
- `src/main/java/guru/springframework/spring7restmvc/controller/CustomErrorController.java` — Action: verify.
- `src/main/java/guru/springframework/spring7restmvc/controller/BeerController.java` — Action: verify.

Notes / Next steps
1. The current layout already follows the intended structure (controllers, services, service.impl, repository, mapper, model.entity, model.dto). Therefore the plan is to "verify" package declarations and imports for each file and avoid wide moves unless a specific change is requested.
2. If we perform any file moves that change package names, run:

   - `./mvnw -DskipTests clean compile` — to regenerate MapStruct generated sources.
   - Then run `./mvnw test` for full verification.

3. When ready to prepare the PR we will:
   - Commit the migration plan and any small, tested refactor commits to branch `refactor/package-layout`.
   - Add `PR_DRAFT.md` describing changes and the verification steps (local-only). Do not push without your confirmation.

4. Suggested commit strategy:
   - Move one logical package/file at a time, run targeted tests (`./mvnw -Dtest=FooTest test`), regenerate MapStruct if mappers are affected, then run full tests and commit.

If you want me to actually move files now, tell me which files to move or confirm that you want the agent to apply the conservative plan (verify-only, regenerate, run tests, prepare PR draft).
