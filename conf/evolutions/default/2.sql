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

alter table blog_post add column date_newsletter integer default 0;


# --- !Downs
drop table if exists subscriber cascade;

drop sequence if exists subscriber_seq;

alter table blog_post drop date_newsletter;

