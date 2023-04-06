CREATE TABLE IF NOT EXISTS book(
    uid SERIAL primary key,
    isbn varchar(100) not null unique,
    author varchar(100) not null,
    title varchar(100) not null,
    language varchar(100) not null,
    photo bytea
    );