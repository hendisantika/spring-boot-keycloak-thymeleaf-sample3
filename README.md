# spring-boot-keycloak-thymeleaf-sample3

[![Java CI with Maven](https://github.com/hendisantika/spring-boot-keycloak-thymeleaf-sample3/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/spring-boot-keycloak-thymeleaf-sample3/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.x-6DB33F?logo=springboot)
![Keycloak](https://img.shields.io/badge/Keycloak-26-4D4D4D?logo=keycloak)

A sample Spring Boot web application with Thymeleaf views, secured with **Keycloak** via OAuth2 / OpenID Connect login.

## Tech Stack

| Component | Version |
|---|---|
| Java (JDK) | 25 |
| Spring Boot | 4.1.x (Spring Security 7) |
| Thymeleaf + `thymeleaf-extras-springsecurity6` | managed by Spring Boot |
| Keycloak | 26.0.6 (Docker) |
| PostgreSQL | 17 (Docker, optional) |
| Maven (wrapper) | 3.10.0 |

## Prerequisites

- JDK 25
- Docker & Docker Compose
- (Optional) Maven — the project ships with the Maven Wrapper (`./mvnw`)

## Running Locally

1. **Start the app.** Thanks to `spring-boot-docker-compose`, running the app automatically starts the services in
   [`docker-compose.yml`](docker-compose.yml) (Keycloak on port `9080`, PostgreSQL on port `5433`):

   ```shell
   ./mvnw spring-boot:run
   ```

   Or start the containers manually first:

   ```shell
   docker compose up -d
   ```

2. **Configure Keycloak** at <http://localhost:9080> (admin console login: `admin` / `admin`):
    - Create (or import) a realm named `Greylian`.
      A realm can be auto-imported by placing an export at `keycloak/realm-export.json`
      (an example export of a `PowerRanger` realm is available in [`PowerRanger.json`](PowerRanger.json)).
    - Create a confidential OpenID Connect client `spring-boot-thymeleaf-keycloak`
      with valid redirect URI `http://localhost:8080/login/oauth2/code/keycloak`.
    - Create a user to log in with.

3. **Update the client secret** in [`src/main/resources/application.yml`](src/main/resources/application.yml)
   if it differs from your Keycloak client:

   ```yaml
   spring:
     security:
       oauth2:
         client:
           provider:
             Greylian:
               issuer-uri: http://localhost:9080/realms/Greylian
           registration:
             keycloak:
               provider: Greylian
               client-id: spring-boot-thymeleaf-keycloak
               client-secret: <your-client-secret>
               scope: openid
   ```

4. Open <http://localhost:8080>. `/` and `/login` are public; every other page requires a Keycloak login.
   Visit `/logout` to sign out.

## Build & Test

```shell
./mvnw clean verify
```

The tests do **not** need a running Keycloak — the context test registers a static `ClientRegistrationRepository`
instead of performing OIDC discovery.

## CI & Dependency Updates

- **GitHub Actions** ([`maven.yml`](.github/workflows/maven.yml)): builds and tests with Temurin JDK 25 on every push
  and pull request to `main`, then submits the Maven dependency graph to GitHub (push only).
- **Dependabot** ([`dependabot.yml`](.github/dependabot.yml)):
    - Maven — daily, Spring/Thymeleaf and other minor/patch updates grouped
    - GitHub Actions — weekly, grouped
    - Docker Compose images (Keycloak, PostgreSQL) — weekly

## Author

**Hendi Santika**

- Email: hendisantika@gmail.com
- Telegram: [@hendisantika34](https://t.me/hendisantika34)
- Link: [s.id/hendisantika](https://s.id/hendisantika)
