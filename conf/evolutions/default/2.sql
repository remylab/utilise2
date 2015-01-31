# --- !Ups

create table subscriber (
  id                        bigint not null,
  email                     varchar(255),
  date_sub                  bigint,
  test                      varchar(255),
  constraint uq_subscriber_email unique (email),
  constraint pk_subscriber primary key (id))
;

create sequence subscriber_seq;

# --- !Downs
drop table if exists subscriber cascade;

drop sequence if exists subscriber_seq;

