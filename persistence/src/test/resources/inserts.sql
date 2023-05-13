INSERT INTO users(behavior,mail,name,telephone, password) VALUES ('BORROWER','EMAIL', 'NAME', 'TELEPHONE', 'PASSWORD_NOT_ENCODED');
INSERT INTO book(ISBN, AUTHOR, TITLE, lang)VALUES ('ISBN','AUTHOR','TITLE','LANGUAGE');
INSERT INTO location(zipcode, locality, province, country, address) VALUES ('ZIPCODE','LOCALITY','PROVINCE','COUNTRY','ADDRESS');
INSERT INTO photos(photo) VALUES (null);
INSERT INTO assetinstance(assetid, owner, locationid, physicalcondition, photoid, status) VALUES (1,1,1,'PHYSICAL_CONDITION',1,'STATUS');
INSERT INTO resetpasswordinfo(token,userid,expiration) VALUES ('TOKEN',1, TIMESTAMP '2010-07-16 18:19:00-8:00');