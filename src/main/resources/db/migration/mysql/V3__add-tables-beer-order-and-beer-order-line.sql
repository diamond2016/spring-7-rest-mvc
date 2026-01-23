    drop table if exists beer_order;

    drop table if exists beer_order_line;

create table beer_order (
    id char(36) not null,
    version integer,
    customer_ref varchar(255),
    customer_id char(36) not null,
    created_date datetime(6), 
    last_modified_date datetime(6),
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
    created_date datetime(6), 
    last_modified_date datetime(6), 
    primary key (id),
    foreign key (beer_order_id) references beer_order(id)
);
