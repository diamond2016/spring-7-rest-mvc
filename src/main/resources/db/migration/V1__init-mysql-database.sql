    drop table if exists beer;

    drop table if exists customer;

    create table beer (
        price decimal(10,2) not null,
        quantity_on_hand integer not null,
        version integer,
        created_date datetime(6),
        update_date datetime(6),
        id char(36) not null,
        beer_name varchar(50) not null,
        upc varchar(255) not null,
        beer_style enum ('ALE','GOSE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT') not null,
        primary key (id)
    ) engine=InnoDB;

    create table customer (
        version integer,
        created_date datetime(6),
        update_date datetime(6),
        id char(36) not null,
        name varchar(50) not null,
        primary key (id)
    ) engine=InnoDB;
