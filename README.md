# Strife API

Backend REST API for **Strife**, an encyclopedia of armed forces — vehicles, weapons, and equipment used by countries throughout history.

Built with **Spring Boot 3.4.4** and **PostgreSQL**.

---

## Tech Stack

- **Java 21**
- **Spring Boot 3.4.4**
- **Spring Data JPA** — database access
- **PostgreSQL** — main database
- **Flyway** — database migrations
- **Lombok** — boilerplate reduction

---

## Project Structure

```
src/main/java/com/strife/strife_api/
├── entity/          # JPA entities (Domain, Country, Family, Model, ModelOperator)
├── repository/      # Spring Data repositories
├── dto/             # API response shapes
├── service/         # Business logic
├── controller/      # REST endpoints
└── config/          # CORS, Jackson configuration

src/main/resources/
├── application.properties         # Local config (not committed)
├── application.properties.template  # Config template (safe to commit)
└── db/migration/
    └── V1__init_schema.sql        # Initial database schema
```

---

## Data Model

```
DOMAIN → FAMILY ← COUNTRY
              ↓
            MODEL
              ↓
       MODEL_OPERATOR → COUNTRY
```

- **Domain** — the five categories: Aviation, Naval, Ground Forces, Infantry, Ordnance
- **Country** — nation of origin, also used as operator
- **Family** — a family of vehicles or weapons (e.g. Saint-Chamond)
- **Model** — a specific variant, with a freeform `specs` JSONB column for statistics
- **ModelOperator** — countries that operated a model, with historical notes

---

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/domains` | List all domains |
| GET | `/api/domains/{domain}/countries` | Countries with content in a domain |
| GET | `/api/domains/{domain}/countries/{country}/families` | Families by domain and country |
| GET | `/api/families/{family}/models` | Models in a family |
| GET | `/api/models/{model}` | Full model detail (specs, article, operators) |

---

## Getting Started

### Prerequisites

- Java 21
- Maven 3.9+
- PostgreSQL 14+

### Database setup

```sql
CREATE USER strife WITH PASSWORD 'your_password';
CREATE DATABASE strife OWNER strife;
```

### Configuration

Copy the template and fill in your values:

```bash
cp src/main/resources/application.properties.template src/main/resources/application.properties
```

### Run

```bash
mvn spring-boot:run
```

Flyway will automatically apply all migrations on startup. The API will be available at `http://localhost:8080`.

---

## License

Work in progress. All rights reserved.
