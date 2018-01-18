INSERT INTO Pizza(NAME , description, price)
VALUES('Margarita', 'lecker', 3.29);

INSERT INTO Pizza(NAME , description, price)
VALUES('Durum', 'lecker', 5.29);

INSERT INTO Pizza(NAME , description, price)
VALUES('Shaurma', 'lecker', 6.31);


INSERT INTO Address(STREET,HOUSENUMBER,TOWN,ZIP_CODE)
VALUES ('Innere Schneeberger', '23', 'Zwickau', '08056');

INSERT INTO Address(STREET,HOUSENUMBER,TOWN,ZIP_CODE)
VALUES ('Makarenko Strasse', '48', 'Zwickau', '09562');



INSERT INTO Customer(FIRST_NAME, LAST_NAME, LOGIN_NAME, password_hash, role)
VALUES ( 'Dmytro', 'Savchuk', 'Dimas', '$2a$10$ebyC4Z5WtCXXc.HGDc1Yoe6CLFzcntFmfse6/pTj7CeDY5I05w16C', 'ADMIN');

INSERT INTO Customer(FIRST_NAME, LAST_NAME, LOGIN_NAME, password_hash, role)
VALUES ( 'Roberto', 'Karlos', 'karlos7', '$2a$10$ebyC4Z5WtCXXc.HGDc1Yoe6CLFzcntFmfse6/pTj7CeDY5I05w16C', 'USER');


INSERT INTO  CUSTOMER_ADDRESSES(CUSTOMER_ID, ADDRESSES_ID) VALUES('1', '1');
INSERT INTO  CUSTOMER_ADDRESSES(CUSTOMER_ID, ADDRESSES_ID) VALUES('2', '2');