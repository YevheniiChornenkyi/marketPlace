DROP DATABASE market;
CREATE DATABASE market;

USE market;

CREATE TABLE roles(
    id VARCHAR(36) NOT NULL,
    role_name VARCHAR (40) NOT NULL,
    PRIMARY KEY ( id )
);

INSERT INTO roles (id, role_name) VALUES ("1", "ADMIN");
INSERT INTO roles (id, role_name) VALUES ("2", "CUSTOMER");

CREATE TABLE users(
    id VARCHAR(36) NOT NULL,
    name VARCHAR(40) NOT NULL,
    surname VARCHAR(40) NOT NULL,
    psw VARCHAR(40) NOT NULL,
    role_id VARCHAR(36) NOT NULL,
    email VARCHAR(40) NOT NULL,
    PRIMARY KEY (id),
        FOREIGN KEY ( role_id )
            REFERENCES roles(id)
            ON DELETE CASCADE
);

INSERT INTO users (id, name, surname, psw, role_id, email) VALUES ("1", "Yevheniy" , "Chornenkiy", "login1", "1", "evgeny.chornenky@gmail.com" );