# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table blog_post (
  id                        bigint not null,
  member_id                 bigint not null,
  title                     varchar(255),
  body                      varchar(255),
  date_pub                  integer,
  is_online                 boolean,
  constraint pk_blog_post primary key (id))
;

create table member (
  id                        bigint not null,
  username                  varchar(255),
  password                  varchar(255),
  constraint uq_member_username unique (username),
  constraint pk_member primary key (id))
;

create sequence blog_post_seq;

create sequence member_seq;

alter table blog_post add constraint fk_blog_post_member_1 foreign key (member_id) references member (id);
create index ix_blog_post_member_1 on blog_post (member_id);



# --- !Downs

drop table if exists blog_post cascade;

drop table if exists member cascade;

drop sequence if exists blog_post_seq;

drop sequence if exists member_seq;

