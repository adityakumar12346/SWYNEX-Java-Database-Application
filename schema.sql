CREATE DATABASE IF NOT EXISTS book_inventory_db;
USE book_inventory_db;

CREATE TABLE IF NOT EXISTS books (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    title    VARCHAR(200) NOT NULL,
    author   VARCHAR(100) NOT NULL,
    isbn     VARCHAR(13)  NOT NULL UNIQUE,
    price    DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    quantity INT NOT NULL CHECK (quantity >= 0)
);

-- Optional sample data
INSERT IGNORE INTO books (title, author, isbn, price, quantity) VALUES
('Clean Code', 'Robert C. Martin', '9780132350884', 35.99, 12),
('Effective Java', 'Joshua Bloch', '9780134685991', 42.50, 3),
('Head First Java', 'Kathy Sierra', '9781491910771', 38.00, 7);
