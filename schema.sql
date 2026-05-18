CREATE DATABASE IF NOT EXISTS mspace;

USE mspace;

CREATE TABLE IF NOT EXISTS subscribers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    status VARCHAR(10) NOT NULL
);

INSERT INTO subscribers (full_name, phone_number, status) VALUES ('Joshua','071234','active');

SELECT * FROM subscribers;

DELETE FROM subscribers where id=1;