/*----------------------------------------------- CREATE TABLE -----------------------------------------------*/
create table follow
(
    id      bigint auto_increment
        primary key,
    from_id bigint null,
    to_id   bigint null,
    constraint following_unique
        unique (from_id, to_id)
);

create table job
(
    job_id          bigint       not null
        primary key,
    company_name    varchar(255) not null,
    expiration_date datetime(6)  not null,
    title           varchar(255) not null,
    url             varchar(255) not null
);

create table member
(
    id              bigint auto_increment
        primary key,
    created_at      datetime(6)   null,
    is_deleted      bit           not null,
    updated_at      datetime(6)   null,
    email           varchar(255)  not null,
    follower_count  int default 0 null,
    following_count int default 0 null,
    introduction    varchar(255)  null,
    nickname        varchar(255)  not null,
    password        varchar(255)  not null,
    role            varchar(255)  null
);

create table project
(
    project_id   bigint auto_increment
        primary key,
    created_at   datetime(6)   null,
    is_deleted   bit           not null,
    updated_at   datetime(6)   null,
    content      longtext      null,
    description  varchar(255)  null,
    like_count   int default 0 null,
    project_name varchar(255)  not null,
    thumb_nail   varchar(255)  null,
    member_id    bigint        not null,
    constraint FKf02mrsqr7qo2g4pi5oetixtf1
        foreign key (member_id) references member (id)
);

create table comment
(
    comment_id bigint auto_increment
        primary key,
    created_at datetime(6)  null,
    is_deleted bit          not null,
    updated_at datetime(6)  null,
    content    varchar(255) not null,
    member_id  bigint       not null,
    parent_id  bigint       null,
    project_id bigint       not null,
    constraint FKb5kenf6fjka6ck0snroeb5tmh
        foreign key (project_id) references project (project_id),
    constraint FKde3rfu96lep00br5ov0mdieyt
        foreign key (parent_id) references comment (comment_id),
    constraint FKmrrrpi513ssu63i2783jyiv9m
        foreign key (member_id) references member (id)
);

create table likes
(
    like_id    bigint auto_increment
        primary key,
    created_at datetime(6) null,
    is_deleted bit         not null,
    updated_at datetime(6) null,
    member_id  bigint      not null,
    project_id bigint      not null,
    constraint FK6gupou17or1xfkb1mkasgwqys
        foreign key (project_id) references project (project_id),
    constraint FKa4vkf1skcfu5r6o5gfb5jf295
        foreign key (member_id) references member (id)
);

create table tag
(
    id   bigint auto_increment
        primary key,
    tech varchar(255) not null
);

create table project_tag
(
    project_tag_id bigint auto_increment
        primary key,
    project_id     bigint null,
    tag_id         bigint null,
    constraint FK519h89u5tkrcmyquqgr5lh3y2
        foreign key (tag_id) references tag (id),
    constraint FKk3ccabfs72wkx2008pn7tij9b
        foreign key (project_id) references project (project_id)
);

/* ----------------------------------------------- INSERT PROJECT_TAG ----------------------------------------------- */
insert into tag (id, tech)
values (1, 'JavaScript');
insert into tag (id, tech)
values (2, 'Java');
insert into tag (id, tech)
values (3, 'TypeScript');
insert into tag (id, tech)
values (4, 'React');
insert into tag (id, tech)
values (5, 'Nextjs');
insert into tag (id, tech)
values (6, 'Spring');
insert into tag (id, tech)
values (7, 'GraphQL');
insert into tag (id, tech)
values (8, 'Redis');


/* ----------------------------------------------- INSERT MEMBER -----------------------------------------------
# Member ID:        string[PK값] ex)string1
# Member Password:  string[PK값] ex)string1
 */

insert into member (id, created_at, is_deleted, updated_at, email, follower_count, following_count, introduction, nickname, password, role)
values
    (1,'2023-09-26 22:57:00.979022',false,'2023-09-26 22:57:00.979022','string1@gmail.com', 0, 0,'string1','string1','$2a$10$bas9UT/DZbPyaQpjV17nnO/s.lLJqg4PPUoFxV51/oAi7tuhF1UyW','ROLE_USER');
insert into member (id, created_at, is_deleted, updated_at, email, follower_count, following_count, introduction, nickname, password, role)
values
    (2,'2023-09-26 22:57:00.979022',false,'2023-09-26 22:57:00.979022','string2@gmail.com', 0, 0,'string2','string2','$2a$10$bas9UT/DZbPyaQpjV17nnO/s.lLJqg4PPUoFxV51/oAi7tuhF1UyW','ROLE_USER');
insert into member (id, created_at, is_deleted, updated_at, email, follower_count, following_count, introduction, nickname, password, role)
values
    (3,'2023-09-26 22:57:00.979022',false,'2023-09-26 22:57:00.979022','string3@gmail.com', 0, 0,'string3','string2','$2a$10$bas9UT/DZbPyaQpjV17nnO/s.lLJqg4PPUoFxV51/oAi7tuhF1UyW','ROLE_USER');
insert into member (id, created_at, is_deleted, updated_at, email, follower_count, following_count, introduction, nickname, password, role)
values
    (4,'2023-09-26 22:57:00.979022',false,'2023-09-26 22:57:00.979022','string4@gmail.com', 0, 0,'string4','string2','$2a$10$bas9UT/DZbPyaQpjV17nnO/s.lLJqg4PPUoFxV51/oAi7tuhF1UyW','ROLE_USER');


/* ----------------------------------------------- INSERT PROJECT ----------------------------------------------- */
insert into project (project_id, created_at, is_deleted, updated_at, content, description, like_count, project_name,
                     thumb_nail, member_id)
values (1, '2023-09-26 22:57:00.979022', false, '2023-09-26 22:57:00.979022', 'string1', 'string1', 0, 'string1',
        'string1', 1);
insert into project (project_id, created_at, is_deleted, updated_at, content, description, like_count, project_name,
                     thumb_nail, member_id)
values (2, '2023-09-26 22:57:00.979022', false, '2023-09-26 22:57:00.979022', 'string2', 'string2', 0, 'string2',
        'string2', 2);

insert into project (project_id, created_at, is_deleted, updated_at, content, description, like_count, project_name,
                     thumb_nail, member_id)
values (3, '2023-09-26 22:57:00.979022', false, '2023-09-26 22:57:00.979022', 'string3', 'string3', 0, 'string3',
        'string3', 1);
insert into project (project_id, created_at, is_deleted, updated_at, content, description, like_count, project_name,
                     thumb_nail, member_id)
values (4, '2023-09-26 22:57:00.979022', false, '2023-09-26 22:57:00.979022', 'string4', 'string4', 0, 'string4',
        'string4', 2);

insert into project (project_id, created_at, is_deleted, updated_at, content, description, like_count, project_name,
                     thumb_nail, member_id)
values (5, '2023-09-26 22:57:00.979022', false, '2023-09-26 22:57:00.979022', 'string5', 'string5', 0, 'string5',
        'string5', 3);
insert into project (project_id, created_at, is_deleted, updated_at, content, description, like_count, project_name,
                     thumb_nail, member_id)
values (6, '2023-09-26 22:57:00.979022', false, '2023-09-26 22:57:00.979022', 'string6', 'string6', 0, 'string6',
        'string6', 3);

/* ----------------------------------------------- INSERT PROJECT_TAG ----------------------------------------------- */
insert into project_tag (project_tag_id, project_id, tag_id)
values (1, 1, 1);
insert into project_tag (project_tag_id, project_id, tag_id)
values (2, 1, 2);
insert into project_tag (project_tag_id, project_id, tag_id)
values (3, 2, 3);
insert into project_tag (project_tag_id, project_id, tag_id)
values (4, 2, 4);
insert into project_tag (project_tag_id, project_id, tag_id)
values (5, 3, 5);
insert into project_tag (project_tag_id, project_id, tag_id)
values (6, 3, 6);