CREATE TABLE IF NOT EXISTS book(
    uid SERIAL primary key,
    isbn varchar(100) not null unique,
    author varchar(100) not null,
    title varchar(100) not null,
    language varchar(100) not null,
    photo bytea
    );
CREATE TABLE IF NOT EXISTS users(
    behavior varchar(100),
    id SERIAL primary key,
    mail varchar(100) unique,
    telephone varchar(100)
);
CREATE TABLE IF NOT EXISTS AssetInstance(
    id SERIAL primary key,
    assetId INT references book(uid) ON DELETE CASCADE,
    owner INT references users(id) ON DELETE CASCADE,
    location varchar(100),
    physicalCondition varchar(100),
    status varchar(100)
);