CREATE TABLE urls (
    id          serial primary key,
    origin_url  text not null,
    url_hash    varchar(8) not null unique,
    user_id     serial,
    created_at  timestamp not null
);

CREATE INDEX idx_user_id ON urls (user_id);

CREATE INDEX idx_url_hash ON urls (url_hash);
