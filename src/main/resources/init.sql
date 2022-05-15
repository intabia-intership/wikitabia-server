BEGIN TRANSACTION;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users
(
    id             UUID PRIMARY KEY     DEFAULT uuid_generate_v4(),
    username       VARCHAR     NOT NULL UNIQUE,
    first_name     VARCHAR,
    last_name      VARCHAR,
    enabled        BOOLEAN     NOT NULL DEFAULT true,
    telegram_login VARCHAR UNIQUE,
    password       VARCHAR     NOT NULL,
    authority      VARCHAR(50) NOT NULL
);

CREATE TABLE tags
(
    id           UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name         VARCHAR UNIQUE,
    rating_count BIGINT           DEFAULT 0
);

CREATE TABLE resources
(
    id           UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name         VARCHAR NOT NULL,
    url          VARCHAR NOT NULL UNIQUE,
    created_at   TIMESTAMP        DEFAULT now(),
    creator_id   UUID    REFERENCES users (id) ON DELETE SET NULL,
    rating_count BIGINT           DEFAULT 0
);

CREATE TABLE tags_resources
(
    tag_id      UUID REFERENCES tags (id) ON DELETE CASCADE,
    resource_id UUID REFERENCES resources (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX ix_auth_username ON users (username, authority);

INSERT INTO users(username, first_name, last_name, telegram_login, password, authority)
VALUES ('1', '1', '1', '1', '$2a$12$aS07FvR6Xde93q8GoQMom.HYoy9chyoMkaXDtMFQwyKPzrMnCzI2S', 'ADMIN'),
       ('2', '2', '2', '2', '$2a$12$qyPLHbSqz1IybwrFVnRpcOYLEJPp9CDNa5SJEEmTZTF53vT7EKvQa', 'USER');

COMMIT TRANSACTION;