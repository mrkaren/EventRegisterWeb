create table if not exists event
(
    id         int auto_increment
    primary key,
    name       varchar(255)                   not null,
    place      varchar(255)                   null,
    is_online  tinyint(1) default 0           not null,
    event_type enum ('FILM', 'SPORT', 'GAME') not null,
    price      double                         null,
    event_date datetime                       null
    );

create table if not exists user
(
    id          int auto_increment
    primary key,
    name        varchar(255)                           not null,
    surname     varchar(255)                           not null,
    email       varchar(255)                           not null,
    profile_pic varchar(255)                           null,
    password    varchar(255)           default 'admin' not null,
    user_role   enum ('ADMIN', 'USER') default 'USER'  null
    );

create table if not exists user_event
(
    user_id  int not null,
    event_id int not null,
    primary key (user_id, event_id),
    constraint event_id
    foreign key (event_id) references event (id),
    constraint user_id
    foreign key (user_id) references user (id)
    );

