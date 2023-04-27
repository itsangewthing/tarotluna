drop schema if exists tarotdb;

create schema tarotdb;

use tarotdb;

create table user(
    user_id int not null auto_increment,
    username varchar(20) not null,
    email varchar(128) not null,
    password varchar(286) not null,
    newsletter boolean not null,
    primary key(user_id)
);


create table card(
    card_id int not null auto_increment,
    name_short varchar(16)not null,
    name varchar(64) not null,
    value varchar(16) not null,
    value_int int not null,
    type varchar(64) not null,
    meaning_up varchar(64) not null, 
    meaning_reverse varchar(64) not null,
    description varchar(128) not null,
    types ENUM ('Major', 'Minor'DEFAULT'null')not null, 
    'Cardlist' JSON, 
    primary key(card_id),
    constraint fk_card_id
        foreign key(card_id) 
        references name(card_id)
);

CREATE TABLE Cardlist (
     nhits int not null auto_increment,
    'card_id' int NOT NULL auto_increment,
         primary key(nhits),
        constraint fk_cards
        foreign key(cards)
        references card(nhits)

)


