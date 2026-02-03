drop table if exists beer_order_shipment;

create table beer_order_shipment (
    id char(36) not null,
    beer_order_id char(36) not null,
    tracking_number varchar(50),
    version integer,
    created_date timestamp, -- no datetime(6) for H2
    last_modified_date timestamp, -- no datetime(6) for H2
    primary key (id),
    foreign key (beer_order_id) references beer_order(id)
);

alter table beer_order
    add column beer_order_shipment_id char(36);
alter table beer_order
    add constraint fk_beer_order_shipment foreign key (beer_order_shipment_id) references beer_order_shipment(id);