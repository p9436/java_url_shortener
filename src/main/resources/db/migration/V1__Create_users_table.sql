CREATE TABLE users (
    id         serial primary key,
    name       varchar(255) not null,
    email      varchar(255) not null unique,
    created_at timestamp not null
);

CREATE INDEX idx_email ON users (email);

INSERT INTO users (name, email, created_at) VALUES ('Alice', 'alice@example.com', '1990-02-03');
INSERT INTO users (name, email, created_at) VALUES ('Bob', 'bob@example.com', '1963-10-17');
