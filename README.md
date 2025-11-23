# 🚀 **Clean Arch API – Java + Spring Boot**

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.3-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/PostgreSQL-15-336791?style=for-the-badge&logo=postgresql&logoColor=white" />
  <img src="https://img.shields.io/badge/JWT-Security-orange?style=for-the-badge&logo=jsonwebtokens&logoColor=white" />
  <img src="https://img.shields.io/badge/Status-Ativo-success?style=for-the-badge" />
</p>

---

# 📌 **Sobre o Projeto**

API REST completa com **Java + Spring Boot**, desenvolvida seguindo princípios de **Clean Architecture**, contendo:

✨ Autenticação JWT
✨ CRUD completo de Tasks
✨ Postgres como banco principal
✨ Documentação automática via Swagger
✨ Projeto limpo, padronizado e pronto para produção
✨ Ideal para portfólio e demonstração profissional

---

# 🧱 **Arquitetura do Projeto**

```
src/main/java/com/gabriel/cleanarch
│
├── config/              # Configurações globais (JWT, Security, Swagger)
├── domain/              # Entidades e Regras essenciais do domínio
│   ├── user/
│   └── task/
├── application/         # Casos de uso e regras de aplicação (Services)
│   ├── auth/
│   └── task/
├── interface/           # Entrada da aplicação (Controllers REST)
│   ├── auth/
│   └── task/
└── infrastructure/      # Recursos externos (Seed, integrações)
```

Arquitetura modular, escalável e fácil de manter.

---

# 🔐 **Funcionalidades**

### ✔️ Autenticação e Segurança

* Registro
* Login
* Geração e validação de JWT
* BCrypt Hash
* Stateless Security

### ✔️ CRUD de Tasks (protegido)

* Criar Task
* Listar Task do usuário autenticado
* Atualizar Task
* Remover Task

### ✔️ Geral

* Estrutura limpa e organizada
* Documentação automática via Swagger
* Banco PostgreSQL com JPA

---

# 🚦 **Endpoints Principais**

## 🔑 Auth

### **POST** `/api/auth/register`

```json
{
  "email": "user@example.com",
  "password": "123456"
}
```

### **POST** `/api/auth/login`

```json
{
  "email": "user@example.com",
  "password": "123456"
}
```

Resposta:

```json
{
  "token": "<jwt>"
}
```

---

## 📝 Tasks (*Require Authorization header*)

### **GET** `/api/tasks`

### **POST** `/api/tasks`

```json
{
  "title": "Minha Task",
  "description": "Descrição aqui"
}
```

### **PUT** `/api/tasks/{id}`

```json
{
  "title": "Atualizada",
  "description": "Nova descrição",
  "done": true
}
```

### **DELETE** `/api/tasks/{id}`

---

# 🧰 **Tecnologias Utilizadas**

| Área             | Tecnologias                |
| ---------------- | -------------------------- |
| **Backend**      | Java 17, Spring Boot 3     |
| **Segurança**    | Spring Security + JWT      |
| **Banco**        | PostgreSQL + JPA/Hibernate |
| **Documentação** | Swagger / Springdoc        |
| **Build**        | Maven                      |
| **Arquitetura**  | Clean Architecture         |

---

# ⚙️ **Como Rodar Localmente**

### 1️⃣ Requisitos

* Java **17+**
* Maven
* PostgreSQL

### 2️⃣ Configurar banco

```sql
CREATE DATABASE cleanarch;
```

### 3️⃣ Configurar `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/cleanarch
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
```

### 4️⃣ Rodar

```bash
mvn spring-boot:run
```

### 5️⃣ Acessos úteis

* Swagger: **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**
* API Root: **[http://localhost:8080/api](http://localhost:8080/api)**

---

# 🚀 **Deploy no Render**

### Build Command:

```
mvn clean package
```

### Start Command:

```
java -jar target/clean-arch-api-0.0.1-SNAPSHOT.jar
```

### Variáveis de ambiente:

```
DB_USER=
DB_PASS=
DB_URL=
JWT_SECRET=
```

---

# 🧑‍💻 Autor

**Gabriel Gonçalves**
Desenvolvedor Fullstack
📍 Goiás – Brasil
🔗 linkedin.com/in/gabriel-gonçalves-8586aa226

---

# ⭐ **Se gostou, deixa uma star no repo!**
