CREATE TABLE IF NOT EXISTS book(
    uid SERIAL primary key,
    isbn varchar(100) not null unique,
    author varchar(100) not null,
    title text not null,
    lang varchar(100) not null
);
CREATE TABLE IF NOT EXISTS users(
    behavior varchar(100),
    id SERIAL primary key,
    mail varchar(100) unique,
    name varchar(100) not null,
    telephone varchar(100),
    password varchar(200)
);
CREATE TABLE IF NOT EXISTS location(
   id SERIAL PRIMARY KEY,
   name VARCHAR(100) NOT NULL,
   zipcode VARCHAR(100) NOT NULL,
   locality VARCHAR(100) NOT NULL,
   province VARCHAR(100) NOT NULL,
   country VARCHAR(100) NOT NULL,
   address VARCHAR(100) DEFAULT 'Address',
   owner INT REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS photos(
    id SERIAL primary key,
    photo bytea
);

CREATE TABLE IF NOT EXISTS AssetInstance(
    id SERIAL primary key,
    assetId INT references book(uid) ON DELETE CASCADE,
    owner INT references users(id) ON DELETE CASCADE,
    locationId INT references location(id) ON DELETE SET NULL,
    physicalCondition varchar(100),
    photoId INT references photos(id) ON DELETE SET NULL,
    status varchar(100),
    maxLendingDays INT
);
CREATE TABLE IF NOT EXISTS lendings(
    id SERIAL primary key,
    assetInstanceId INT references AssetInstance(id) ON DELETE CASCADE ,
    borrowerId INT references users(id)ON DELETE CASCADE ,
    lendDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    devolutionDate TIMESTAMP NOT NULL,
    active TEXT NOT NULL default 'ACTIVE'
);
CREATE TABLE IF NOT EXISTS resetpasswordinfo(
    id SERIAL primary key,
    token text UNIQUE NOT NULL,
    userId INT references users(id) ON DELETE CASCADE,
    expiration TIMESTAMP NOT  NULL
);

CREATE TABLE IF NOT EXISTS languages(
    id VARCHAR(3) PRIMARY KEY,
    name varchar(100)
);
