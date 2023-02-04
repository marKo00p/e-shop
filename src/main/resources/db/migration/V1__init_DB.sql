--USER
DROP SEQUENCE IF EXISTS user_seq;
create sequence user_seq start with 1 increment by 1;

DROP TABLE IF EXISTS users CASCADE;
 create table users
 (
     id bigint not null,
     archive boolean not null,
     email varchar(255),
     name varchar(255),
     password varchar(255),
     role varchar(255),
     bucket_id bigint,
     locked boolean,
     enable boolean,
     primary key (id)
 );

--BUCKET
DROP SEQUENCE IF EXISTS buckets_seq;
create sequence buckets_seq start with 1 increment by 1;

DROP TABLE IF EXISTS buckets CASCADE;
create table buckets
(
    id bigint not null,
    user_id bigint,
    primary key (id)
);

--LINK BETWEEN BUCKET AND USER
alter table if exists buckets
    add constraint buckets_fk_user
        foreign key (user_id) references users;

alter table if exists users
    add constraint users_fk_buket
        foreign key (bucket_id) references buckets;

--CATEGORY
DROP SEQUENCE IF EXISTS category_seq;
create sequence category_seq start with 1 increment by 1;

DROP TABLE IF EXISTS categories CASCADE;
create table categories
(
    id bigint not null,
    title varchar(255),
    primary key (id)
);

--PRODUCT
DROP SEQUENCE IF EXISTS product_seq;
create sequence product_seq start with 1 increment by 1;

DROP TABLE IF EXISTS products CASCADE;
create table products
(
    id bigint not null,
    price numeric(38,2),
    title varchar(255),
    primary key (id)
);

--CATEGORY AND PRODUCT
DROP TABLE IF EXISTS products_categories CASCADE;
create table products_categories
(
    product_id bigint not null,
    category_id bigint not null
);

alter table if exists products_categories
    add constraint products_categories_fk_category
        foreign key (category_id) references categories;
alter table if exists products_categories
    add constraint products_categories_fk_product
        foreign key (product_id) references products;

--PRODUCT IN BUCKET
DROP TABLE IF EXISTS buckets_products CASCADE;
create table buckets_products
(
    bucket_id bigint not null,
    product_id bigint not null
);

alter table if exists buckets_products
    add constraint buckets_products_fk_product
        foreign key (product_id) references products;

alter table if exists buckets_products
    add constraint buckets_products_fk_bucket
        foreign key (bucket_id) references buckets;

--ORDER
DROP SEQUENCE IF EXISTS order_seq;
create sequence order_seq start with 1 increment by 1;

DROP TABLE IF EXISTS orders CASCADE;
create table orders
(
    id bigint not null,
    address varchar(255),
    created timestamp(6),
    status varchar(255),
    sum numeric(38,2),
    updated timestamp(6),
    user_id bigint, primary key (id)
);

alter table if exists orders
    add constraint orders_fk_user
        foreign key (user_id) references users;

--ORDER DETAILS
DROP SEQUENCE IF EXISTS order_details_seq;
create sequence order_details_seq start with 1 increment by 1;

create table orders_details
(
    id bigint not null,
    amount numeric(38,2),
    price numeric(38,2),
    order_id bigint,
    product_id bigint,
    details_id bigint not null,
    primary key (id)
);

alter table if exists orders_details
    add constraint orders_details_uk unique (details_id);

alter table if exists orders_details
    add constraint orders_details_fk_order
        foreign key (order_id) references orders;

alter table if exists orders_details
    add constraint orders_details_fk_product
        foreign key (product_id) references products;

alter table if exists orders_details
    add constraint orders_details_fk_order_details
    foreign key (details_id) references orders_details;

