CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS authority(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    first_name VARCHAR,
    last_name VARCHAR,
    login VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    telegram_login VARCHAR UNIQUE
);

CREATE TABLE IF NOT EXISTS users_authorities(
    user_id UUID,
    authority_id UUID,
    PRIMARY KEY (user_id, authority_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (authority_id) REFERENCES authority (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tags(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR UNIQUE,
    rating_count BIGINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS resources(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR NOT NULL,
    url VARCHAR NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT now(),
    creator_id UUID,
    rating_count BIGINT DEFAULT 0,
    FOREIGN KEY (creator_id) REFERENCES users (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS tags_resources(
    tag_id UUID,
    resource_id UUID,
    FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE,
    FOREIGN KEY (resource_id) REFERENCES resources (id) ON DELETE CASCADE
);