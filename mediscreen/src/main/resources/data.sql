CREATE TABLE patient (
                id INT AUTO_INCREMENT NOT NULL,
                first_name VARCHAR(20) NOT NULL,
                last_name VARCHAR(20) NOT NULL,
                address VARCHAR(100) NOT NULL,
                city VARCHAR(20) NOT NULL,
                phone VARCHAR(20) NOT NULL,
                sex CHAR(1) NOT NULL,
                birthday DATE NOT NULL,
                PRIMARY KEY (id)
);