drop table article_hashtag;
create table article_hashtag (
    id         bigint  generated by default as identity,
    article_id bigint not null,
    hashtag_id bigint not null,
    primary key (id)
)
;

insert into board ( board_type, board_name, description, sort_order ,enabled, created_at ,modified_at,  created_by , modified_by)
values ( 'general', '자유게시판', '자유롭게 작성하세요', 1 , true,  current_timestamp(), current_timestamp(), 22091008 , 22091008)
;
insert into board ( board_type, board_name, description, sort_order ,enabled, created_at ,modified_at,  created_by , modified_by)
values ( 'qna', '질의응답', '질문과 답변을 작성', 2 , true,  current_timestamp(), current_timestamp(), 22091008 , 22091008)
;
insert into board ( board_type, board_name, description, sort_order ,enabled, created_at ,modified_at,  created_by , modified_by)
values ( 'tip', 'tip공유', 'tip과 노하우를 공유합니다', 3 , true,  current_timestamp(), current_timestamp(), 22091008 , 22091008)
;
insert into board ( board_type, board_name, description, sort_order ,enabled, created_at ,modified_at,  created_by , modified_by)
values ( 'buddy_introduce', '모집장 한마디', '버디모집 소개글', 4 , false,  current_timestamp(), current_timestamp(), 22091008 , 22091008)
;

insert into HASHTAG values (current_timestamp(), 1, 0, 'test001');
insert into HASHTAG values (current_timestamp(), 2, 0, 'test002');
