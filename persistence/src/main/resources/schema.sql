CREATE TABLE IF NOT EXISTS book(
    uid SERIAL primary key,
    isbn varchar(100) not null unique,
    author varchar(100) not null,
    title varchar(100) not null,
    "language" varchar(100) not null
);
CREATE TABLE IF NOT EXISTS users(
    behavior varchar(100),
    id SERIAL primary key,
    mail varchar(100) unique,
    telephone varchar(100)
);
CREATE TABLE IF NOT EXISTS location(
    id SERIAL primary key,
    zipcode varchar(100) NOT NULL,
    locality varchar(100) NOT NULL,
    province varchar(100) NOT NULL,
    country varchar(100) NOT NULL,
    address varchar(100)
);
CREATE TABLE IF NOT EXISTS photos(
    id SERIAL primary key,
    photo bytea NOT NULL
);
CREATE TABLE IF NOT EXISTS AssetInstance(
    id SERIAL primary key,
    assetId INT references book(uid) ON DELETE CASCADE,
    owner INT references users(id) ON DELETE CASCADE,
    locationId INT references location(id) ON DELETE SET NULL,
    physicalCondition varchar(100),
    photoId INT references photos(id) ON DELETE SET NULL,
    status varchar(100)
);
