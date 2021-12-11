create table tag
(
    id   bigint unsigned auto_increment,
    name varchar(45) not null,
    constraint id_UNIQUE
        unique (id),
    constraint name_UNIQUE
        unique (name)
);

alter table tag
    add primary key (id);