INSERT INTO board (board_type, board_name, description, sort_order, enabled, created_at, modified_at, created_by, modified_by)
VALUES ('GENERAL', '자유게시판', '자유롭게 작성하세요', 1, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1111, 1111);

INSERT INTO board (board_type, board_name, description, sort_order, enabled, created_at, modified_at, created_by, modified_by)
VALUES ('QNA', '질의응답', '질문과 답변을 작성', 2, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1111, 1111);

INSERT INTO board (board_type, board_name, description, sort_order, enabled, created_at, modified_at, created_by, modified_by)
VALUES ('TIP', 'tip공유', 'tip과 노하우를 공유합니다', 3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1111, 1111);

INSERT INTO board (board_type, board_name, description, sort_order, enabled, created_at, modified_at, created_by, modified_by)
VALUES ('BUDDY_QNA', '모집장 한마디', '버디모집 소개글', 4, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1111, 1111);

INSERT INTO article (comment_count, enable_comment, like_count, view_count, created_at, created_by, deleted_at, modified_at, modified_by, title, board_type, content)
VALUES (15, TRUE, 20, 300, CURRENT_TIMESTAMP, 1111, NULL, NULL, NULL, '테스트 제목', 'GENERAL', '게시글 본문내용 1');

INSERT INTO comment (visible, article_id, created_at, created_by, content)
VALUES (TRUE, 1, CURRENT_TIMESTAMP, 1111, '게시글 작성자의 첫 번째 댓글입니다.');

INSERT INTO comment (visible, article_id, created_at, created_by, content)
VALUES (TRUE, 1, CURRENT_TIMESTAMP + INTERVAL '1 second', 2222, '사용자 2222의 첫 번째 댓글입니다.');

INSERT INTO comment (visible, article_id, created_at, created_by, content)
VALUES (FALSE, 1, CURRENT_TIMESTAMP + INTERVAL '2 seconds', 3333, '사용자 3333 숨겨진 댓글입니다.');

INSERT INTO comment (visible, article_id, created_at, created_by, content, parent_id)
VALUES (FALSE, 1, CURRENT_TIMESTAMP + INTERVAL '3 seconds', 1111, '3333의 숨겨진 댓글에 대한 게시글 작성자 답글입니다.', 3);

INSERT INTO comment (visible, article_id, created_at, created_by, content)
VALUES (TRUE, 1, CURRENT_TIMESTAMP + INTERVAL '4 seconds', 3333, '일반 댓글입니다.');

INSERT INTO comment (visible, article_id, created_at, created_by, content, parent_id)
VALUES (TRUE, 1, CURRENT_TIMESTAMP + INTERVAL '5 seconds', 1111, '댓글에 대한 첫 번째 답글입니다.', 5);

INSERT INTO comment (visible, article_id, created_at, created_by, content, parent_id)
VALUES (TRUE, 1, CURRENT_TIMESTAMP + INTERVAL '6 seconds', 3333, '댓글에 대한 두 번째 답글입니다.', 5);

INSERT INTO comment (visible, article_id, created_at, created_by, content, parent_id)
VALUES (TRUE, 1, CURRENT_TIMESTAMP + INTERVAL '7 seconds', 2222, '댓글에 대한 세 번째 답글입니다.', 5);

INSERT INTO comment (visible, article_id, created_at, created_by, content, parent_id)
VALUES (TRUE, 1, CURRENT_TIMESTAMP + INTERVAL '8 seconds', 1111, '댓글에 대한 네 번째 답글입니다.', 5);

INSERT INTO comment (visible, article_id, created_at, created_by, content, parent_id)
VALUES (TRUE, 1, CURRENT_TIMESTAMP + INTERVAL '9 seconds', 3333, '댓글에 대한 다섯 번째 답글입니다.', 5);

INSERT INTO comment (visible, article_id, created_at, created_by, content)
VALUES (TRUE, 1, CURRENT_TIMESTAMP + INTERVAL '5 seconds', 1111, '게시글 작성자의 두 번째 댓글입니다.');

INSERT INTO comment (visible, article_id, created_at, created_by, content)
VALUES (TRUE, 1, CURRENT_TIMESTAMP + INTERVAL '6 seconds', 2222, '사용자 2222의 두 번째 댓글입니다.');

INSERT INTO comment (visible, article_id, created_at, created_by, content)
VALUES (TRUE, 1, CURRENT_TIMESTAMP + INTERVAL '7 seconds', 3333, '다른 사용자의 댓글입니다.');

INSERT INTO comment (visible, article_id, created_at, created_by, content, parent_id)
VALUES (TRUE, 1, CURRENT_TIMESTAMP + INTERVAL '10 seconds', 1111, '다른 사용자 댓글에 대한 답글입니다.', 13);

INSERT INTO comment (visible, article_id, created_at, created_by, content)
VALUES (FALSE, 1, CURRENT_TIMESTAMP + INTERVAL '2 seconds', 3333, '또 다른 숨겨진 댓글입니다.');
