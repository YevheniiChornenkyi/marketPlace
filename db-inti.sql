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
    is_active BIT(1) DEFAULT 0 NOT NULL,
    PRIMARY KEY (id),
        FOREIGN KEY ( role_id )
            REFERENCES roles(id)
            ON DELETE CASCADE
);

INSERT INTO users (id, name, surname, psw, role_id, email) VALUES ("1", "Yevheniy" , "Chornenkiy", "login1", "1", "evgeny.chornenky@gmail.com" );

CREATE TABLE categories (
     id INT NOT NULL AUTO_INCREMENT,
     category_name VARCHAR(40) NOT NULL,
     PRIMARY KEY ( id )
);

INSERT INTO categories (id, category_name) VALUES ("1", "Electronic tools");
INSERT INTO categories (id, category_name) VALUES ("2", "Battery tools");
INSERT INTO categories (id, category_name) VALUES ("3", "Ladders");
INSERT INTO categories (id, category_name) VALUES ("4", "Hand tools");
INSERT INTO categories (id, category_name) VALUES ("5", "Consumables");

CREATE TABLE manufacturers (
    id INT NOT NULL AUTO_INCREMENT,
    manufacturer_name VARCHAR(40) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO manufacturers (id, manufacturer_name) VALUES ("1", "Dnipro-M");
INSERT INTO manufacturers (id, manufacturer_name) VALUES ("2", "Samurai");
INSERT INTO manufacturers (id, manufacturer_name) VALUES ("3", "Zenit");
INSERT INTO manufacturers (id, manufacturer_name) VALUES ("4", "Bosh");
INSERT INTO manufacturers (id, manufacturer_name) VALUES ("5", "INTERTOOL");

CREATE TABLE goods (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(40) NOT NULL,
    model VARCHAR(40),
    price VARCHAR(32) NOT NULL,
    category INT NOT NULL,
    image_name VARCHAR(40),
    description VARCHAR(200),
    manufacturer INT NOT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
        FOREIGN KEY (category)
            REFERENCES categories(id)
            ON DELETE CASCADE,
        FOREIGN KEY (manufacturer)
            REFERENCES manufacturers(id)
            ON DELETE CASCADE
);

INSERT INTO goods (name, price, category, description, manufacturer) VALUES ("Britva", "1000", "1", "Britva", "1");
INSERT INTO goods (name, price, category, image_name, description, manufacturer) VALUES ("Axe", "100500", "2", "axe.png", "Axe", "2");

CREATE TABLE images (
    image_id VARCHAR(40) NOT NULL,

    PRIMARY KEY (image_id)
);

INSERT INTO images (image_id) VALUES ("axe.jpg");
