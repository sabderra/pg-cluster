CREATE SCHEMA IF NOT EXISTS test;

CREATE TABLE IF NOT EXISTS test.employees (
employee_id serial PRIMARY KEY,
account_id int not NULL,
name varchar(10)
);

CREATE TABLE IF NOT EXISTS test.orders (
id SERIAL PRIMARY KEY,
data text not NULL,
employee_id int not NULL
);