# PR Draft: package-layout refactor (local branch: `refactor/package-layout`)

Summary
-------
This PR prepares the codebase for a small, safe package-layout refactor. Changes so far (local-only):

- Fixed `CustomerServiceJPA.saveNewCustomer` to persist and return the saved entity (fixes NPE in CustomerControllerIT).
- Added test-package placeholders under `src/test/java` to mirror main package layout.
- Added `MIGRATION_PLAN.md` with a per-file verification plan and next steps.

What this PR contains
---------------------
- `MIGRATION_PLAN.md` — file-by-file plan (verification-first, minimal moves).

What remains (suggested)
------------------------
1. Move files only when needed and in small commits (one logical change per commit).
2. If mapper interfaces or packages move, run `./mvnw -DskipTests clean compile` to regenerate MapStruct implementations.
3. Run `./mvnw test` after each set of moves to validate behavior.

How to validate locally (you already ran these steps):
- `./mvnw -DskipTests clean compile`  # regenerate generated sources
- `./mvnw test`                       # full test suite

Branch & commit notes
----------------------
- All commits are on branch `refactor/package-layout` (local). I did not push or open a remote PR.

Next action (ask me to proceed):
- I can apply verified file moves now (one small move per commit) and repeat the regenerate → test cycle, or
- If you'd prefer, I can prepare the commit series and a suggested PR description ready to push.

When you confirm, I will either:
- Apply the conservative plan (verify-only changes + PR draft), or
- Start moving files one-by-one and commit each step, regenerating MapStruct and running tests after each move.
