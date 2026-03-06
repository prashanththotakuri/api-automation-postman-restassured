# API Automation Framework

<div align="center">

[![Build Status](https://img.shields.io/github/actions/workflow/status/prashanththotakuri/api-automation-framework/build.yml?style=flat-square)](https://github.com/prashanththotakuri/api-automation-framework/actions)
[![Java Version](https://img.shields.io/badge/Java-17-blue?style=flat-square)](https://www.oracle.com/java/)
[![RestAssured](https://img.shields.io/badge/RestAssured-5.x-green?style=flat-square)](https://rest-assured.io/)
[![TestNG](https://img.shields.io/badge/TestNG-7.x-orange?style=flat-square)](https://testng.org/)
[![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)](LICENSE)

**Enterprise-grade REST API testing** with RestAssured, TestNG, and comprehensive validation patterns

[Features](#-key-features) • [Setup](#-quick-start) • [Examples](#-usage-examples) • [Best Practices](#-api-testing-best-practices) • [FAQ](#-troubleshooting)

</div>

-----

## 📋 Overview

A production-ready API automation framework designed for testing REST APIs at scale:

- **API-First Testing** - Test APIs independent of UI
- **Comprehensive Validation** - Status codes, response bodies, headers, performance
- **Multiple Auth Methods** - Basic, OAuth 2.0, JWT, API Keys
- **Data-Driven Testing** - Parameterized tests with CSV, JSON data sources
- **Contract Testing** - Verify API contracts with JSON Schema validation
- **Performance Testing** - Track response times, identify bottlenecks
- **Mock Server Integration** - Test against mock APIs with Mockito/WireMock
- **CI/CD Ready** - Integrated with GitHub Actions, Jenkins, GitLab CI
- **Detailed Reporting** - Extent Reports with request/response logs

-----

## 🎯 Key Features

|Feature                |Details                                         |
|-----------------------|------------------------------------------------|
|**RestAssured**        |BDD-style REST API testing framework            |
|**Multi-Auth**         |Basic, Bearer, OAuth2, API Keys, Digest         |
|**Request Building**   |Fluent API for building complex requests        |
|**Response Parsing**   |JSON/XML parsing with JSONPath & XPath          |
|**Assertions**         |Status codes, headers, body content, performance|
|**Data Validation**    |JSON Schema, custom validators                  |
|**Logging**            |Detailed request/response logging               |
|**Retry Logic**        |Automatic retry for flaky APIs                  |
|**Mock Servers**       |WireMock, Postman mock servers                  |
|**Performance Metrics**|Response time tracking, SLA validation          |
|**CI/CD Integration**  |GitHub Actions, Jenkins, GitLab CI              |

-----

## 🏗 Framework Architecture

```
api-automation-framework/
│
├── .github/
│   └── workflows/
│       └── build.yml                # GitHub Actions CI/CD
│
├── src/
│   ├── main/java/
│   │   ├── api/
│   │   │   ├── endpoints/
│   │   │   │   ├── UserEndpoint.java
│   │   │   │   ├── ProductEndpoint.java
│   │   │   │   └── OrderEndpoint.java
│   │   │   │
│   │   │   ├── client/
│   │   │   │   ├── ApiClient.java   # Base API client
│   │   │   │   └── RequestBuilder.java
│   │   │   │
│   │   │   └── response/
│   │   │       ├── ApiResponse.java
│   │   │       └── ResponseParser.java
│   │   │
│   │   ├── auth/
│   │   │   ├── AuthHandler.java
│   │   │   ├── BasicAuthHandler.java
│   │   │   ├── BearerTokenHandler.java
│   │   │   └── OAuth2Handler.java
│   │   │
│   │   ├── utils/
│   │   │   ├── JsonValidator.java
│   │   │   ├── SchemaValidator.java
│   │   │   ├── DataGenerator.java
│   │   │   ├── ConfigReader.java
│   │   │   └── ReportUtils.java
│   │   │
│   │   └── models/
│   │       ├── User.java
│   │       ├── Product.java
│   │       └── Order.java
│   │
│   └── test/java/
│       └── tests/
│           ├── UserApiTests.java
│           ├── ProductApiTests.java
│           ├── OrderApiTests.java
│           └── AuthTests.java
│
├── src/test/resources/
│   ├── config.properties
│   ├── testdata/
│   │   ├── users.json
│   │   ├── products.csv
│   │   └── orders.json
│   ├── schemas/
│   │   ├── user-schema.json
│   │   ├── product-schema.json
│   │   └── order-schema.json
│   └── log4j.properties
│
├── pom.xml
└── README.md
```

-----

## 🚀 Quick Start

### Prerequisites

- **Java 17+** ([Download](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.8+** ([Download](https://maven.apache.org/download.cgi))
- **Postman** (Optional, for endpoint reference)

### Installation

1. **Clone the repository**
   
   ```bash
   git clone https://github.com/prashanththotakuri/api-automation-framework.git
   cd api-automation-framework
   ```
1. **Install dependencies**
   
   ```bash
   mvn clean install
   ```
1. **Configure API endpoints**
- Update `src/test/resources/config.properties`:
   
   ```properties
   api.base.url=https://api.example.com
   api.version=v1
   auth.type=bearer
   auth.token=your_token_here
   ```
1. **Run tests**
   
   ```bash
   mvn test
   ```

-----

## 📊 Performance & Scale

|Metric                          |Value         |Details                 |
|--------------------------------|--------------|------------------------|
|**API Endpoints Tested**        |25+           |Across 4+ API modules   |
|**Test Cases**                  |100+          |Happy paths + edge cases|
|**Sequential Runtime**          |~2-3 minutes  |All APIs tested         |
|**Parallel Runtime (4 threads)**|~45-60 seconds|70%+ faster             |
|**Avg Response Validation**     |<100ms        |Per endpoint            |
|**Schema Validation**           |100% coverage |All responses           |

-----

## 💡 Usage Examples

### Example 1: Basic API Test

```java
@Test
public void testGetUserById() {
    int userId = 123;
    
    Response response = RestAssured
        .given()
            .header("Authorization", "Bearer " + token)
            .pathParam("id", userId)
        .when()
            .get("/api/v1/users/{id}")
        .then()
            .statusCode(200)
            .body("id", equalTo(userId))
            .body("email", notNullValue())
            .extract()
            .response();
    
    // Additional assertions
    Assert.assertEquals(response.getStatusCode(), 200);
}
```

### Example 2: POST Request with Body

```java
@Test
public void testCreateUser() {
    String requestBody = "{\n" +
        "  \"name\": \"John Doe\",\n" +
        "  \"email\": \"john@example.com\",\n" +
        "  \"role\": \"USER\"\n" +
        "}";
    
    Response response = RestAssured
        .given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + token)
            .body(requestBody)
        .when()
            .post("/api/v1/users")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("email", equalTo("john@example.com"))
            .extract()
            .response();
    
    int createdUserId = response.path("id");
    logger.info("Created user with ID: " + createdUserId);
}
```

### Example 3: JSON Schema Validation

```java
@Test
public void testGetUserWithSchemaValidation() {
    RestAssured
        .given()
            .header("Authorization", "Bearer " + token)
        .when()
            .get("/api/v1/users/123")
        .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"))
            .extract()
            .response();
}

// user-schema.json
{
    "$schema": "http://json-schema.org/draft-07/schema#",
    "type": "object",
    "properties": {
        "id": { "type": "integer" },
        "name": { "type": "string" },
        "email": { "type": "string", "format": "email" },
        "role": { "type": "string", "enum": ["USER", "ADMIN"] }
    },
    "required": ["id", "name", "email"],
    "additionalProperties": false
}
```

### Example 4: Data-Driven API Tests

```java
@Test(dataProvider = "userData")
public void testCreateUserWithDifferentRoles(String name, String email, String role) {
    String requestBody = String.format(
        "{ \"name\": \"%s\", \"email\": \"%s\", \"role\": \"%s\" }",
        name, email, role
    );
    
    RestAssured
        .given()
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("/api/v1/users")
        .then()
            .statusCode(201)
            .body("role", equalTo(role));
}

@DataProvider
public Object[][] userData() {
    return new Object[][] {
        {"John Doe", "john@example.com", "USER"},
        {"Jane Admin", "jane@example.com", "ADMIN"},
        {"Bob User", "bob@example.com", "USER"}
    };
}
```

### Example 5: Authentication Handling

```java
public class AuthHandler {
    
    public static String getBearerToken(String clientId, String clientSecret) {
        return RestAssured
            .given()
                .auth().basic(clientId, clientSecret)
                .contentType("application/x-www-form-urlencoded")
                .param("grant_type", "client_credentials")
            .when()
                .post("/oauth/token")
            .then()
                .statusCode(200)
                .extract()
                .path("access_token");
    }
    
    public static String getJWTToken(String username, String password) {
        return RestAssured
            .given()
                .contentType("application/json")
                .body("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}")
            .when()
                .post("/api/v1/auth/login")
            .then()
                .statusCode(200)
                .extract()
                .path("token");
    }
}
```

### Example 6: Response Validation with Conditions

```java
@Test
public void testGetProductWithConditionalValidation() {
    Response response = RestAssured
        .given()
            .queryParam("category", "electronics")
        .when()
            .get("/api/v1/products")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("price", everyItem(greaterThan(0.0f)))
            .body("inStock", everyItem(either(is(true)).or(is(false))))
            .extract()
            .response();
    
    List<Integer> prices = response.jsonPath().getList("price");
    int avgPrice = prices.stream().mapToInt(Integer::intValue).sum() / prices.size();
    logger.info("Average price: " + avgPrice);
}
```

-----

## 🔒 Authentication Examples

### Basic Authentication

```java
RestAssured
    .given()
        .auth().basic("username", "password")
    .when()
        .get("/api/users")
    .then()
        .statusCode(200);
```

### Bearer Token

```java
RestAssured
    .given()
        .header("Authorization", "Bearer " + token)
    .when()
        .get("/api/users")
    .then()
        .statusCode(200);
```

### OAuth 2.0

```java
RestAssured
    .given()
        .auth().oauth2(token)
    .when()
        .get("/api/users")
    .then()
        .statusCode(200);
```

### API Key

```java
RestAssured
    .given()
        .queryParam("api_key", apiKey)
    .when()
        .get("/api/users")
    .then()
        .statusCode(200);
```

-----

## 📊 API Testing Best Practices

### 1. **Validate Status Codes**

```java
// ✅ GOOD
.then().statusCode(200)
.then().statusCode(201)
.then().statusCode(404)

// ❌ BAD
.then().statusCode(anyOf(200, 201, 202))
```

### 2. **Use JSONPath for Assertions**

```java
// ✅ GOOD
.body("user.name", equalTo("John"))
.body("items.size()", greaterThan(0))
.body("find{it.id==5}.name", equalTo("Product"))

// ❌ BAD
.body(is(expectedBody))
```

### 3. **Separate Concerns**

```java
// ✅ GOOD
- Test API contract separately
- Test business logic separately
- Test error scenarios separately

// ❌ BAD
- Testing everything in one test
```

### 4. **Use Test Data Builders**

```java
// ✅ GOOD
User user = UserBuilder.aUser()
    .withName("John")
    .withEmail("john@example.com")
    .withRole("ADMIN")
    .build();

// ❌ BAD
String json = "{\"name\":\"John\", ...}";
```

-----

## 🔄 CI/CD Integration

### GitHub Actions

```yaml
name: API Tests
on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: '17'
      - run: mvn clean test
      - uses: actions/upload-artifact@v2
        if: failure()
        with:
          name: test-reports
          path: target/surefire-reports/
```

-----

## ❓ Troubleshooting

### Q: How do I handle dynamic response data?

**A:** Use JSONPath to extract and validate dynamic data:

```java
String userId = response.jsonPath().getString("user.id");
List<String> names = response.jsonPath().getList("users.name");
```

### Q: How do I test file uploads?

**A:** Use multipart form data:

```java
RestAssured
    .given()
        .multiPart("file", new File("path/to/file.pdf"))
        .multiPart("description", "My file")
    .when()
        .post("/api/upload")
    .then()
        .statusCode(200);
```

### Q: How do I mock external API calls?

**A:** Use WireMock:

```java
WireMockServer wireMock = new WireMockServer(8080);
wireMock.stubFor(get(urlEqualTo("/api/users/1"))
    .willReturn(aResponse()
        .withStatus(200)
        .withBody("{\"id\": 1, \"name\": \"John\"}")));
wireMock.start();
```

-----

## 📚 Resources

- [RestAssured Docs](https://rest-assured.io/)
- [JSON Schema Validation](https://json-schema.org/)
- [JSONPath Guide](https://github.com/json-path/JsonPath)
- [HTTP Status Codes](https://httpwg.org/specs/rfc7231.html#status.codes)

-----

## 🤝 Contributing

Contributions welcome! See <CONTRIBUTING.md>.

-----

## 📝 License

MIT License - see <LICENSE>

-----

## 👤 Author

**Prashanth Thotakuri**  
Senior QA Automation Engineer  
[GitHub](https://github.com/prashanththotakuri) • [LinkedIn](https://www.linkedin.com/in/prashanth-prashanth-57b691130)

-----

<div align="center">

**⭐ If this framework helped, please star the repository!**

[⬆ Back to Top](#api-automation-framework)

</div>