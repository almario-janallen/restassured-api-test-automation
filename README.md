# RestAssured API Test Automation Suite

A scalable, maintainable API test automation framework built with **RestAssured**, **TestNG**, and **Allure Reports**, targeting the [ReqRes.in](https://reqres.in) public REST API.

---

## Prerequisites

Before running the project locally, make sure you have the following installed:

- Java 17+
- Maven 3.x
- Git
- Python 3.x (for serving Allure reports locally)

---

## Project Structure

```
src/
├── test/
│   ├── java/
│   │   ├── base/         # BaseTest class with RestAssured setup
│   │   ├── api/          # Request builders per module
│   │   ├── models/       # POJO classes for serialization/deserialization
│   │   ├── tests/        # Test classes organized by module
│   │   └── utils/        # ConfigReader, CsvDataReader, Constants, helpers
│   └── resources/
│       ├── schemas/      # JSON Schema files for response validation
│       ├── testdata/     # CSV files for data-driven tests
│       ├── config.properties   # Non-sensitive configuration (committed)
│       └── local.properties    # Sensitive values e.g. API key (NOT committed)
├── testng.xml            # Test suite definition and grouping
└── pom.xml               # Maven dependencies and plugin configuration
```

---

## Configuration

The framework uses a layered configuration system managed by `ConfigReader`:

| File | Purpose | Committed? |
|---|---|---|
| `config.properties` | Base URL, timeouts, non-sensitive values | Yes |
| `local.properties` | API key and other secrets | No (.gitignored) |
| `{env}.properties` | Environment-specific overrides (e.g. `qa.properties`) | Optional |

### Setting Up Locally

1. Create `src/test/resources/local.properties`
2. Add your API key:
```properties
REQRES_API_KEY=your_actual_key_here
```
3. Confirm `local.properties` is listed in `.gitignore`

> In CI, the API key is injected as a GitHub Actions secret and read via environment variable automatically.

---

## How to Run Tests

### Run the full test suite
```bash
mvn test
```

### Run smoke tests only
```bash
mvn test -Dgroups=smoke
```

### Run regression suite
```bash
mvn test -Dgroups=regression
```

### Run against a specific environment
```bash
mvn test -Denv=staging
```

---

## Allure Reports

### Generate the report
```bash
mvn allure:report
```

### Serve the report locally (recommended)
```bash
mvn allure:serve
```

This opens the report automatically in your browser.

### Viewing a downloaded report from CI

If you downloaded the Allure artifact from GitHub Actions, open a terminal, navigate to the unzipped folder, and run:

```bash
python -m http.server 9090
```

Then open `http://localhost:9090` in your browser. Press `Ctrl + C` to stop the server when done.

> Port 8080 may be in use — use 9090 or any other available port.

---

## Test Coverage

| Category | Endpoints Covered |
|---|---|
| Users | GET /users, GET /users/{id}, POST /users, PUT /users/{id}, PATCH /users/{id}, DELETE /users/{id} |
| Auth | POST /login, POST /register |
| Resources | GET /unknown, GET /unknown/{id} |

### Test Types

- **Positive Tests** — valid inputs, expected status codes and response bodies
- **Negative Tests** — invalid inputs, missing fields, unregistered emails
- **Schema Validation Tests** — response structure validated against JSON schemas
- **Data-Driven Tests** — key scenarios run across multiple input sets via CSV files

---

## Adding New Tests

1. Create a new test class under `src/test/java/tests/` following the existing naming convention (e.g. `UsersPositiveTest`, `AuthNegativeTest`)
2. Extend `BaseTest` to inherit RestAssured setup and response spec
3. Annotate each test method with the appropriate TestNG groups:
```java
@Test(groups = {"smoke", "regression"})
```
4. Use `ConfigReader.get("key")` for any config values — never hardcode URLs or credentials
5. If adding a new endpoint, add the path constant to `utils/Constants`
6. If schema validation is needed, add the JSON schema file to `src/test/resources/schemas/`

---

## CI/CD Pipeline

The GitHub Actions pipeline is defined in `.github/workflows/test.yml`.

**Triggers:**
- Every push to `main`
- Every pull request targeting `main`
- Manually via the Actions tab (`workflow_dispatch`)

**What it does:**
1. Checks out the code
2. Sets up Java 17
3. Runs the smoke test suite (`mvn test -Dgroups=smoke`)
4. Generates the Allure report
5. Uploads the report as a downloadable artifact (retained for 7 days)

**Finding the Allure report after a run:**
1. Go to the **Actions** tab in GitHub
2. Click the relevant workflow run
3. Scroll to the **Artifacts** section
4. Download **allure-report** and serve it locally using the steps above

---

## Framework Architecture Decisions

**RestAssured + TestNG** — RestAssured provides a fluent, readable DSL for HTTP assertions that integrates naturally with TestNG's grouping, parameterization, and parallel execution capabilities.

**Layered architecture** — Separating `base`, `api`, `models`, `utils`, and `tests` keeps concerns isolated. Test classes stay readable and free of setup boilerplate, while the API layer handles request construction independently.

**ConfigReader with local/CI split** — `config.properties` holds non-sensitive values and is committed to the repo. `local.properties` holds secrets and is gitignored. In CI, secrets are injected via GitHub Actions secrets and resolved through `System.getenv()` fallback in `ConfigReader`. This avoids ever committing credentials while keeping the framework functional in both environments.

**CSV for data-driven tests** — CSV files are lightweight, human-readable, and require no additional tooling compared to Excel, making them easier to maintain and diff in version control.

---

## Success Criteria

- 95%+ of defined test cases passing in CI/CD on every build
- 100% endpoint coverage across all ReqRes.in API routes
- Allure reports generated automatically with every pipeline run
- Average test suite execution time under 3 minutes
- Framework onboarding time for new team members under 30 minutes