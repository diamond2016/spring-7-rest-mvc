    drop table if exists beer;

    drop table if exists customer;

create table beer (
    id char(36) not null,
    version integer,
    beer_name varchar(50) not null,
    beer_style varchar(50) not null, -- no ENUM for H2
    upc varchar(255) not null,
    price decimal(10,2) not null,
    quantity_on_hand integer not null,
    created_date timestamp, -- no datetime(6) for H2
    update_date timestamp, -- no datetime(6) for H2
    primary key (id)
);

create table customer (
    id char(36) not null,
    version integer,
    name varchar(50) not null,
    created_date timestamp, -- no datetime(6) for H2
    update_date timestamp, -- no datetime(6) for H2
    primary key (id)
);