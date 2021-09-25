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
    psw VARCHAR(48) NOT NULL,
    role_id VARCHAR(36) NOT NULL,
    email VARCHAR(40) NOT NULL,
    is_active BIT(1) DEFAULT 1 NOT NULL,
    PRIMARY KEY (id),
        FOREIGN KEY ( role_id )
            REFERENCES roles(id)
            ON DELETE CASCADE
);

INSERT INTO users (id, name, surname, psw, role_id, email) VALUES ("1", "Yevheniy" , "Chornenkiy", "16B1BE10A1231B71A11E711F1261641B81AE1BB1721F1124", "1", "evgeny.chornenky@gmail.com" );
INSERT INTO users (id, name, surname, psw, role_id, email, is_active) VALUES ("2", "Boris" , "Menethil", "16B1BE10A1231B71A11E711F1261641B81AE1BB1721F1124", "2", "boris@gmail.com", 0);
INSERT INTO users (id, name, surname, psw, role_id, email, is_active) VALUES ("3", "Dmitrii" , "Hellscream", "16B1BE10A1231B71A11E711F1261641B81AE1BB1721F1124", "2", "anton@gmail.com", 1);
INSERT INTO users (id, name, surname, psw, role_id, email, is_active) VALUES ("4", "Bogdan" , "Lightbringer", "16B1BE10A1231B71A11E711F1261641B81AE1BB1721F1124", "2", "anton@gmail.com", 1);
INSERT INTO users (id, name, surname, psw, role_id, email, is_active) VALUES ("5", "Vitaly" , "Fordring", "16B1BE10A1231B71A11E711F1261641B81AE1BB1721F1124", "2", "anton@gmail.com", 1);

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

INSERT INTO goods (name, price, model, category, image_name, description, manufacturer) VALUES ("Perforator", "2000", "RH-100Q", "1", "perforator.jpg", "Perforator straight Dnepr-M RH-100Q", "1");
INSERT INTO goods (name, price, model, category, image_name, description, manufacturer) VALUES ("Grinder", "1250", "GL-125S", "1", "grinder.jpg", "Angle grinder Dnipro-M GL-125S", "1");
INSERT INTO goods (name, price, model, category, image_name, description, manufacturer) VALUES ("Screwdriver", "2450", "SD-55A", "1", "screwdriver.png", "Cordless screwdriver Dnipro-M SD-55A", "1");
INSERT INTO goods (name, price, model, category, image_name, description, manufacturer) VALUES ("Cordless blower", "990", "DCB-200", "2", "cordless_blower.jpg", "Dnipro-M DCB-200 cordless blower (without battery and charger)", "1");
INSERT INTO goods (name, price, model, category, image_name, description, manufacturer) VALUES ("Cordless impact driver", "1200", "DTD-200", "2", "Cordless_impact_driver.jpg", "Cordless impact driver Dnipro-M DTD-200 (without battery and charger)", "1");
INSERT INTO goods (name, price, model, category, image_name, description, manufacturer) VALUES ("Dual cordless chain saw", "5500", "DCS-200BC", "2", "Dual_cordless_chain_saw.jpg", "Dnipro-M DCS-200BC Dual cordless chain saw (without battery and charger)", "1");
INSERT INTO goods (name, price, model, category, image_name, description, manufacturer) VALUES ("Stepladder", "1500", "LT-0056", "3", "intertool-lt-0056-original-wm.jpg", "Stepladder 6 steps, top step height 1280mm, 481х1138х1787mm, 150kg INTERTOOL LT-0056", "5");
INSERT INTO goods (name, price, model, category, image_name, description, manufacturer) VALUES ("Stepladder", "990", "LT-0037", "3", "Stepladder_3_steps.jpg", "Stepladder 3 steps 380 * 260mm height 1131mm, up to the top step 729mm INTERTOOL LT-0037", "5");
INSERT INTO goods (name, price, model, category, image_name, description, manufacturer) VALUES ("Ladder platform", "3500", "LT-0027", "3", "Ladder_platform.jpg", "Ladder platform (2x6) INTERTOOL LT-0027", "5");
INSERT INTO goods (name, price, model, category, image_name, description, manufacturer) VALUES ("Saw", "409", "W-330-LH", "4", "W-330-LH-500x500.jpg", "Saw with curved blade in a cover (L = 330 mm / 4 mm) (Samurai W-330-LH)", "2");
INSERT INTO goods (name, price, model, category, image_name, description, manufacturer) VALUES ("Saw", "500", "P-CH350-LH", "4", "P-CH350-LH-500x500.jpg", "Saw with curved blade and hanger, L = 350mm / 4mm / 1.2mm (Samurai P-CH350-LH)", "2");
INSERT INTO goods (name, price, model, category, image_name, description, manufacturer) VALUES ("Saw", "650", "GCM-270-MH", "4", "GCM-210-MH-500x500.jpg", "Saw with curved blade in a case (Samurai GCM-270-MH)", "2");
INSERT INTO goods (name, price, category, image_name, description, manufacturer) VALUES ("Metal drill bits", "290", "5", "2608577347.jpg", "Metal drill bits Bosch HSS PointTeQ 2-10 mm, Mini-X-Line 7 pcs.", "4");
INSERT INTO goods (name, price, category, image_name, description, manufacturer) VALUES ("Flap disc", "55", "5", "2608603657.jpg", "Flap disc Bosch Standard for Metal X431, 125х22.23 mm, 80", "4");
INSERT INTO goods (name, price, category, image_name, description, manufacturer) VALUES ("Plastic case", "55", "5", "2608522363.jpg", "Bosch Pick and Click Plastic Carrying Case, Size L", "4");

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
