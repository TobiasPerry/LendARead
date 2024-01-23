INSERT INTO users(id,behavior,mail,name,telephone, password,LOCALE) VALUES (0,'BORROWER','EMAIL', 'NAME', 'TELEPHONE', 'PASSWORD_NOT_ENCODED','ES');

INSERT INTO book(uid,ISBN, AUTHOR, TITLE, lang)VALUES (0,'ISBN','AUTHOR','TITLE','spa');

INSERT INTO location(id,zipcode, locality, province, country, address,active,name,owner) VALUES (0,'ZIPCODE','LOCALITY','PROVINCE','COUNTRY','Address',true,'NAME',0);

INSERT INTO photos(id,photo) VALUES (0,null);

INSERT INTO assetinstance(id,assetid, owner, locationid, physicalcondition, photoid, status,ISRESERVABLE,MAXLENDINGDAYS) VALUES (0,0,0,0,'ASNEW',0,'PUBLIC',true,7);

INSERT INTO resetpasswordinfo(id,token,userid,expiration) VALUES (0,'TOKEN',1, TIMESTAMP '2010-07-16 18:19:00-8:00');

INSERT INTO lendings(id,assetinstanceid, borrowerid, lenddate, devolutiondate, active) VALUES (0,0,0, TIMESTAMP '2010-07-16 18:19:00-8:00', TIMESTAMP '2010-07-16 18:19:00-8:00','ACTIVE');
INSERT INTO lendings(id,assetinstanceid, borrowerid, lenddate, devolutiondate, active) VALUES (1,0,0, TIMESTAMP '2017-07-16 18:19:00-8:00', TIMESTAMP '2018-07-16 18:19:00-8:00','ACTIVE');