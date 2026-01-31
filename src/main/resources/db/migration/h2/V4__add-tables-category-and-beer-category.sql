    drop table if exists category;

    drop table if exists beer_category;
    
create table category (
    id char(36) not null,
    version integer,
    description varchar(255),
    created_date timestamp, -- no datetime(6) for H2
    last_modified_date timestamp, -- no datetime(6) for H2
    primary key (id)
);


create table beer_category (
    beer_id char(36) not null,
    category_id char(36) not null,
    primary key (beer_id, category_id),
    foreign key (beer_id) references beer(id),
    foreign key (category_id) references category(id)
);
