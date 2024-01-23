CREATE TABLE ARTICLE_HASHTAG (
    ARTICLE_ID BIGINT NOT NULL,
    HASHTAG_ID BIGINT NOT NULL
)
;
INSERT INTO BOARD ( board_Type, board_Name, description, sort_Order ,enabled, created_At ,modified_at,  created_By , modified_by)
VALUES ( 'GENERAL', '자유게시판', '자유롭게 작성하세요', 1 , TRUE,  CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 22091008 , 22091008)
;
INSERT INTO BOARD ( board_Type, board_Name, description, sort_Order ,enabled, created_At ,modified_at,  created_By , modified_by)
VALUES ( 'QNA', '질의응답', '질문과 답변을 작성', 2 , TRUE,  CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 22091008 , 22091008)
;
INSERT INTO BOARD ( board_Type, board_Name, description, sort_Order ,enabled, created_At ,modified_at,  created_By , modified_by)
VALUES ( 'TIP', 'Tip공유', 'Tip과 노하우를 공유합니다', 3 , TRUE,  CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 22091008 , 22091008)
;
INSERT INTO BOARD ( board_Type, board_Name, description, sort_Order ,enabled, created_At ,modified_at,  created_By , modified_by)
VALUES ( 'BUDDY_INTRODUCE', '모집장 한마디', '버디모집 소개글', 4 , FALSE,  CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 22091008 , 22091008)
;
