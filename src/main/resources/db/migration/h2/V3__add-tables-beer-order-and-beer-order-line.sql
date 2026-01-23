    drop table if exists beer_order;

    drop table if exists beer_order_line;

create table beer_order (
    id char(36) not null,
    version integer,
    customer_ref varchar(255),
    customer_id char(36) not null,
    created_date timestamp, -- no datetime(6) for H2
    last_modified_date timestamp, -- no datetime(6) for H2
    primary key (id),
    foreign key (customer_id) references customer(id)
);



create table beer_order_line (
    id char(36) not null,
    version integer,
    beer_id char(36) not null,
    order_quantity integer,
    quantity_allocated integer,
    beer_order_id char(36) not null,
    created_date timestamp, -- no datetime(6) for H2
    last_modified_date timestamp, -- no datetime(6) for H2
    primary key (id),
    foreign key (beer_order_id) references beer_order(id)
);
