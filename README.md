# Clean Arch API — Java + Spring Boot

API REST em **Java + Spring Boot**, com:

- CRUD de Tasks
- Autenticação com **JWT**
- Banco de dados **PostgreSQL**
- Organização em camadas (domain, application, infrastructure, interface)
- Documentação automática com **OpenAPI / Swagger**

## Tecnologias

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (jjwt)
- PostgreSQL
- Maven

## Arquitetura (visão geral)

- `domain`: entidades de domínio e repositórios (`User`, `Task`, etc.)
- `application`: regras de negócio / casos de uso (`AuthService`, `TaskService`, `JwtService`)
- `config`: cross-cutting (segurança, JWT, Swagger/OpenAPI)
- `interface`: controllers REST (camada de entrada)
- `infrastructure`: detalhes de infraestrutura (seed de dados, integrações)

## Endpoints principais

### Auth

`POST /api/auth/register`  
Body:

```json
{
  "email": "user@example.com",
  "password": "123456"
}
````

Resposta:

```json
{
  "token": "<JWT>"
}
```

`POST /api/auth/login`
Body igual ao register.

---

### Tasks (requer Header Authorization: Bearer <token>)

`GET /api/tasks` – lista tasks do usuário autenticado.

`POST /api/tasks`
Body:

```json
{
  "title": "Minha primeira task",
  "description": "Alguma descrição"
}
```

`PUT /api/tasks/{id}`

```json
{
  "title": "Task atualizada",
  "description": "Nova descrição",
  "done": true
}
```

`DELETE /api/tasks/{id}`

---

## Rodando localmente

1. Tenha **Java 17+** e **Maven** instalados.
2. Crie um banco PostgreSQL:

```sql
CREATE DATABASE cleanarch;
```

3. Ajuste `application.properties` com usuário/senha do seu Postgres.
4. Rode:

```bash
mvn spring-boot:run
```

5. Acesse:

* Swagger UI: `http://localhost:8080/swagger-ui.html`
* Healthcheck simples: `GET http://localhost:8080/health` (se quiser criar depois)

## Deploy no Render (resumo)

* Suba o projeto para um repositório no GitHub.
* Crie um serviço Web no Render:

  * Build: `mvn clean package`
  * Start: `java -jar target/clean-arch-api-0.0.1-SNAPSHOT.jar`
* Configure as variáveis de ambiente:

  * `SPRING_DATASOURCE_URL`
  * `SPRING_DATASOURCE_USERNAME`
  * `SPRING_DATASOURCE_PASSWORD`

---

> Projeto simples, mas mostra:
>
> * Java + Spring Boot
> * API REST real
> * Auth JWT
> * PostgreSQL
> * Separação em camadas
> * Documentação com Swagger


