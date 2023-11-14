-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence client_SEQ start with 1 increment by 1;
create sequence address_SEQ start with 1 increment by 1;
create sequence phone_SEQ start with 1 increment by 1;

create table address
(
    id     bigint not null primary key,
    street varchar(100)
);

create table client
(
    id   bigint not null primary key,
    name varchar(50),
    address_id bigint,
    FOREIGN KEY (address_id) REFERENCES address(id)
);

create table phone
(
    id     bigint not null primary key,
    number varchar(50),
    id_client bigint
);
