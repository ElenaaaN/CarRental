create database CarRental;
use CarRental;


CREATE TABLE User (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Nume VARCHAR(100),
    Prenume VARCHAR(100),
    email VARCHAR(100),
    numar INT,
    parola VARCHAR(100)
);



create table Car(
Id int auto_increment primary KEY,
brand VARCHAR(100),
model VARCHAR(100),
culoare VARCHAR(100),
an INT,
pret INT, 
disponibilitate BOOl
);
INSERT INTO Car (brand, model, culoare, an, pret, disponibilitate)
VALUES
    ('Toyota', 'Corolla', 'Red', 2020, 30, TRUE),
    ('Ford', 'Focus', 'Blue', 2018, 25, TRUE),
    ('BMW', 'X5', 'Black', 2021, 50, TRUE);


select * from user;
select * from car;