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
INSERT INTO roles (id, role_name) VALUES ("3", "NOT_ACTIVATE");

CREATE TABLE users(
    id VARCHAR(36) NOT NULL,
    name VARCHAR(40) NOT NULL,
    surname VARCHAR(40) NOT NULL,
    psw VARCHAR(40) NOT NULL,
    role_id VARCHAR(36) NOT NULL,
    email VARCHAR(40) NOT NULL,
    is_active BIT(1) DEFAULT 1 NOT NULL,
    PRIMARY KEY (id),
        FOREIGN KEY ( role_id )
            REFERENCES roles(id)
            ON DELETE CASCADE
);

INSERT INTO users (id, name, surname, psw, role_id, email) VALUES ("1", "Yevheniy" , "Chornenkiy", "login1", "1", "evgeny.chornenky@gmail.com" );
INSERT INTO users (id, name, surname, psw, role_id, email, is_active) VALUES ("2", "Boris" , "Britva", "login1", "2", "boris@gmail.com", 0);
INSERT INTO users (id, name, surname, psw, role_id, email, is_active) VALUES ("3", "Anton" , "Baton", "login1", "2", "anton@gmail.com", 1);

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

CREATE TABLE statuses (
    status VARCHAR(20) NOT NULL,

    PRIMARY KEY (status)
);

INSERT INTO statuses (status) VALUES ("UNCONFIRMED");
INSERT INTO statuses (status) VALUES ("PROCESSED");
INSERT INTO statuses (status) VALUES ("AWAITING_DELIVERY");
INSERT INTO statuses (status) VALUES ("AWAITING_PICKUP");
INSERT INTO statuses (status) VALUES ("CANCELED");

CREATE TABLE orders (
    order_id VARCHAR(40) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    status VARCHAR(20) NOT NULL,
    address VARCHAR(40) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    price VARCHAR(20) NOT NULL,

    PRIMARY KEY (order_id),
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE,
        FOREIGN KEY (status)
            REFERENCES statuses(status)
            ON DELETE CASCADE
);

INSERT INTO orders (order_id, user_id, status, address, phone_number, price) VALUES ("1", "3", "PROCESSED", "bread factory","88004004455", "1998");
INSERT INTO orders (order_id, user_id, status, address, phone_number, price) VALUES ("2", "3", "PROCESSED", "Feanor street 29","88004004455", "2666");

CREATE TABLE order_item (
    order_item_id VARCHAR(60) NOT NULL,
    parent_order_id VARCHAR(40) NOT NULL,
    goods_id INT NOT NULL,
    quantity INT NOT NULL,
    price VARCHAR(20) NOT NULL,
    total_price VARCHAR(20) NOT NULL,

    PRIMARY KEY (order_item_id),
        FOREIGN KEY (parent_order_id)
            REFERENCES orders(order_id)
            ON DELETE CASCADE,
        FOREIGN KEY (goods_id)
            REFERENCES goods(id)
            ON DELETE CASCADE
);

INSERT INTO order_item (order_item_id, parent_order_id, goods_id, quantity, price, total_price) VALUES ("11", "1", "1", "2", "999", "1998");
INSERT INTO order_item (order_item_id, parent_order_id, goods_id, quantity, price, total_price) VALUES ("21", "2", "1", "2", "333", "666");
INSERT INTO order_item (order_item_id, parent_order_id, goods_id, quantity, price, total_price) VALUES ("22", "2", "2", "2", "1000", "2000");

CREATE TABLE activation_cods (
    user_id VARCHAR(40) NOT NULL,
    activation_cod VARCHAR(40) NOT NULL,

    PRIMARY KEY (user_id),
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE
);
