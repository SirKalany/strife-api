CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE domain (
    id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    slug VARCHAR(50)  NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE country (
    id       UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    slug     VARCHAR(50)  NOT NULL UNIQUE,
    name     VARCHAR(100) NOT NULL,
    flag_url VARCHAR(255)
);

CREATE TABLE family (
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    domain_id   UUID         NOT NULL REFERENCES domain(id),
    country_id  UUID         NOT NULL REFERENCES country(id),
    slug        VARCHAR(100) NOT NULL UNIQUE,
    name        VARCHAR(150) NOT NULL,
    image_url   VARCHAR(255),
    description TEXT
);

CREATE TABLE model (
    id               UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    family_id        UUID         NOT NULL REFERENCES family(id),
    slug             VARCHAR(100) NOT NULL UNIQUE,
    name             VARCHAR(150) NOT NULL,
    year_introduced  INT,
    year_retired     INT,
    image_url        VARCHAR(255),
    article          TEXT,
    specs            JSONB
);

CREATE TABLE model_operator (
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    model_id   UUID NOT NULL REFERENCES model(id),
    country_id UUID NOT NULL REFERENCES country(id),
    notes      TEXT,
    UNIQUE (model_id, country_id)
);

INSERT INTO domain (slug, name) VALUES
    ('air',      'Aviation'),
    ('naval',    'Naval'),
    ('ground',   'Ground Forces'),
    ('infantry', 'Infantry'),
    ('ordnance', 'Ordnance');