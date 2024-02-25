CREATE TABLE IF NOT EXISTS customer (
    customer_ref BIGINT PRIMARY KEY,
    customer_name VARCHAR(255),
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    town VARCHAR(255),
    county VARCHAR(255),
    country VARCHAR(255),
    postcode VARCHAR(255)
);