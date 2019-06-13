DROP TABLE MEMBER;
CREATE TABLE MEMBER( 
    USER_ID VARCHAR2(10 CHAR) PRIMARY KEY, 
    PASSWORD VARCHAR2(256 CHAR), 
    NAME VARCHAR2(20 CHAR), SALT VARCHAR2(16), 
    USER_LEVEL VARCHAR2(20 CHAR), 
    EMAIL VARCHAR2(40 CHAR), 
    PHONE VARCHAR2(20 CHAR), 
    ADDRESS VARCHAR2(200 CHAR), 
    REG_DATE DATE DEFAULT sysdate, 
    UPDATE_DATE DATE DEFAULT sysdate 
);

drop table genre;
create table genre(
    id number primary key not null,
    name varchar(20) not null
);
drop sequence genre_seq;
create SEQUENCE genre_seq
increment by 1
start with 1;

drop table movie;
create table movie(
    id number primary key not null,
    GENRE_ID number references genre(id),
    title varchar2(50) not null,
    audience number default 0,
    poster_url varchar2(140),
    description varchar2(800)
);

drop sequence movie_seq;
create sequence movie_seq
increment by 1
start with 1;

drop table score;
create table score(
    id number primary key not null,
    movie_id number references movie(id) on delete cascade,
    content varchar2(40),
    score number
);

drop sequence score_seq;
create sequence score_seq
increment by 1
start with 1;

