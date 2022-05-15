//DROP SCHEMA wikitabia;
CREATE SCHEMA wikitabia;
SET SCHEMA wikitabia;

CREATE TABLE users(
    id UUID DEFAULT RANDOM_UUID(),
    first_name VARCHAR,
    last_name VARCHAR,
    login VARCHAR NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE tags(
    id UUID DEFAULT RANDOM_UUID(),
    name VARCHAR UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE resources(
    id UUID DEFAULT RANDOM_UUID(),
    name VARCHAR NOT NULL,
    url VARCHAR NOT NULL UNIQUE,
    created_at TIMESTAMP,
    creator_id UUID,
    FOREIGN KEY (creator_id) REFERENCES users (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

CREATE TABLE tagsResources(
    tag_id UUID,
    resource_id UUID,
    FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE,
    FOREIGN KEY (resource_id) REFERENCES resources (id) ON DELETE CASCADE
);
