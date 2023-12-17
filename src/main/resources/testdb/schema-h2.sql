create table if not exists company
(
    id               bigint       not null auto_increment,
    name             varchar(255) not null,
    region           varchar(255) not null,
    series           varchar(255) not null,
    created_at       timestamp    not null,
    last_modified_at timestamp    not null,
    primary key (id)
);

create table if not exists document
(

    id               bigint       not null auto_increment,
    job_id           bigint,
    content          varchar(255) not null,
    created_at       timestamp    not null,
    last_modified_at timestamp    not null,
    primary key (id)
);

create table if not exists job
(
    id               bigint       not null auto_increment,
    company_id       bigint,
    position         varchar(255) not null,
    created_at       timestamp    not null,
    last_modified_at timestamp    not null,
    primary key (id)
);

create table if not exists retrospect
(
    id               bigint       not null auto_increment,
    content          varchar(255) not null,
    good_point       varchar(255) not null,
    bad_point        varchar(255) not null,
    score            smallint,
    summary          varchar(255) not null,
    created_at       timestamp    not null,
    last_modified_at timestamp    not null,
    primary key (id)
);

create table if not exists stage
(
    id               bigint       not null auto_increment,
    job_id           bigint,
    retrospect_id    bigint,
    name             varchar(255) not null,
    state            varchar(255) not null,
    created_at       timestamp    not null,
    last_modified_at timestamp    not null,
    primary key (id)
);

alter table document
    add foreign key (job_id) references job (id);

alter table job
    add foreign key (company_id) references company (id);

alter table stage
    add foreign key (job_id) references job (id);

alter table stage
    add foreign key (retrospect_id) references retrospect (id);
