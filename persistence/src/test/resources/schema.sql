
DROP TABLE IF EXISTS gift_certificate;
create table gift_certificate
(
    id             bigint unsigned auto_increment,
    name           varchar(45)    not null,
    description    varchar(255)   null,
    price          decimal(10, 2) null,
    duration       int            null,
    createDate     datetime       not null,
    lastUpdateDate datetime       not null,
    constraint id_gift_certificate_UNIQUE
        unique (id)
);

alter table gift_certificate
    add primary key (id);

DROP TABLE IF EXISTS tag;

create table tag
(
    id   bigint unsigned auto_increment,
    name varchar(45) not null,
    constraint tag_id_UNIQUE
        unique (id),
    constraint tag_name_UNIQUE
        unique (name)
);

alter table tag
    add primary key (id);


DROP TABLE IF EXISTS gift_certificate_tag_include;
create table gift_certificate_tag_include
(
    giftCertificate bigint unsigned not null,
    tag             bigint unsigned not null,
    primary key (giftCertificate, tag),
    constraint gift_certificate_fk
        foreign key (giftCertificate) references gift_certificate (id)
            on update cascade on delete cascade,
    constraint tag_kf
        foreign key (tag) references tag (id)
            on update cascade on delete cascade
);

create index tag_kf_idx
    on gift_certificate_tag_include (tag);