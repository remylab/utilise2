# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table member (
  id                        bigint not null,
  username                  varchar(255),
  password                  varchar(255),
  constraint uq_member_username unique (username),
  constraint pk_member primary key (id))
;

create sequence member_seq;




# --- !Downs

drop table if exists member cascade;

drop sequence if exists member_seq;

