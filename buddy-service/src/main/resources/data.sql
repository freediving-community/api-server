INSERT INTO diving_pool (diving_pool_id, diving_pool_name, address, description, simple_address, operating_hours,
                         price_info, website_url, recommended_level, depth, contact_number, regular_closure, is_visible,
                         display_order)
VALUES ('DEEPSTATION', '딥스테이션', '경기 용인시 처인구 포곡읍 성산로 523 딥스테이션', '국내 최대 수심 36m, 바오밥나무와 동굴에서 펀다이빙/사진 찍기도 좋은 다이빙풀',
        '경기 용인시 처인구 포곡읍 성산로 523', '08:00 - 23:00 (3시간 간격)', '평일: 44,000원\r\n휴일: 66,000원', 'https://deepstation.kr/',
        '2', '36m', NULL, NULL, false, 0),
       ('PARADIVE', '파라다이브', '경기 시흥시 거북섬중앙로1 보니타가, 파라다이브(1동 3층)', '시흥에 위치한 최대 수심 35미터의 신생 다이빙풀, 3레벨 버디끼리 24m 입수 가능한 곳',
        '경기 시흥시 거북섬중앙로1 보니타가', '08:00 - 23:00 (3시간 또는 6시간 간격)',
        '일반 이용: 4.5만원 (평일)\r\n          7.9만원 (휴일)\r\n휴일 이용: 6.7만원', 'https://www.paradive.co.kr/', '2', '35m', NULL,
        NULL, false, 0),
       ('K26', 'K-26', '경기 가평 청평면 고성리 59-1', '서울 동부에서 접근이 좋고, 가성비가 좋은 가평 26m 수심의 다이빙풀', '경기 가평 청평면 고성리 59-1',
        '09:00 - 22:00 (3시간 간격)', '평일: 33,000원\r\n휴일: 55,000원', 'https://k-26.com/', '2', '26m', NULL, NULL, false, 0),
       ('THEME_SCUBA_POOL', '테마 잠수풀(TSN)', '경기 오산시 수청동 607-1', '2레벨 버디와 11m 수심을 즐길 수 있고, 창밖 기찻길 인생샷 찍기 좋은곳',
        '경기 오산시 수청동 607-1', '09:00 - 23:00 (4시간 자유 이용)', '평일: 33,000원\r\n휴일: 50,000원', 'https://www.t-sports.kr/', '2',
        '11m', NULL, NULL, false, 0),
       ('OLYMPIC_DIVING_POOL', '올림픽수영장 다이빙풀', '서울시 송파구 올림픽로 424 올림픽공원 내 올림픽수영장', '서울에서 접근성이 좋고, DYN 연습하기 좋은 풀장',
        '서울시 송파구 올림픽로 424', '토요일: 12:00 - 17:00\r\n일요일: 10:00 - 13:00\r\n       14:00 - 17:00',
        '평일: 15,000원\r\n휴일: 20,000원', 'https://4240735.modoo.at/?link=345es9an', '1', '5m', NULL, '매월 2째 주 일요일', false,
        0),
       ('SONGDO_SPORTS_PARK_POOL', '송도 스포츠파크 잠수풀', '인천광역시 연수구 인천신항대로 892번길 40',
        '예쁜 수중 벽화와 함께 5,000원에 즐길 수 있는 초가성비 송도시 운영 풀장', '인천광역시 연수구 인천신항대로 892번길 40',
        '화요일 - 토요일: 18:00 - 21:00\r\n일요일: 09:00 - 12:00 / 13:00 - 16:00', '일일 이용료: 5,000원',
        'https://ssp.eco-i.or.kr/reservation/dive/diving.asp?mNo=MC030000000', '1', '5m', NULL, '매주 월요일', false, 0),
       ('SEONGNAM_AQUALINE_POOL', '성남 아쿠아라인 다목적풀', '경기도 성남시 중원구 제일로 60', '수도권 접근성이 좋고, 바닥에 설치된 거울로 자세 교정이나 강습에 좋은 풀장',
        '경기도 성남시 중원구 제일로 60', '09:00 - 22:00', '평일: 15,000원\r\n휴일: 20,000원', 'https://aqualine.modoo.at/', '1', '5m',
        NULL, '매월2,4주 일요일', false, 0),
       ('WORLDCUP_SCUBA_POOL', '월드컵 스킨스쿠버 다이빙풀', '경기도 수원시 월드컵로 310 스포츠아일랜드',
        '33m x 25m의 넓은 풀장에 드는 햇볕, 바닥에 설치된 훌라후프링에서 돌고래 놀이를 즐길 수 있는 펀다이빙 강추 풀장', '경기도 수원시 월드컵로 310',
        '11월 - 2월: 09:00 - 21:00\r\n3월 - 10월: 09:00 - 22:00', '평일/휴일: 15,000원', 'http://m.suwonpool.com/page/page3',
        '2', '5m', NULL, '매월2주 수요일', false, 0),
       ('DAEBU_WELFARE_POOL', '대부동 복지체육센터', '경기도 안산시 단원구 영전로 126', '시에서 운영하는 안산시 최고 가성비 풀장', '경기도 안산시 단원구 영전로 126',
        '1부: 09:00 - 12:00\r\n2부: 14:00 - 17:00\r\n3부: 17:30 - 20:30', '평일/휴일: 7,500원',
        'https://www.ansanuc.net/homenew/10502/20005/bbsDetail.do?currentPageNo=1&searchCondition=&searchKeyword=&searchKey=&searchKey1=&searchKey3=&bbsIdx=11882',
        '2', '5.3m', '032-880-9768', '매주 월요일/공휴일', false, 0),
       ('CINEBLUE', '시네블루', '경기 파주시 탄현면 새오리로 427번길 120', '파주에 위치해 서울에서 가까운 6.3m의 수중 촬영장 겸 다이빙풀장',
        '경기 파주시 탄현면 새오리로 427번길 120', '1부: 09:00 - 12:00\r\n2부: 13:30 - 17:00', '평일: 35,000원\r\n휴일: 40,000원',
        'http://www.cineblue.co.kr', '1', '6.3m', '010-3386-1709', '전화/문자 문의 필수', false, 0),
       ('INADIV', '인어다이브', '경기도 용인시 처인구 포곡읍 둔전로47번길 21', '3m 깊이의 ', '경기도 용인시 처인구 포곡읍 둔전로47번길 21',
        '평일: 09:30 - 21:30까지 3시간 단위\r\n주말: 09:00 - 21:00까지 3시간 단위', '20,000원', 'https://inadiv.modoo.at/', '1', '3m',
        '010-8902-5717', '', false, 0);



INSERT INTO public.buddy_event_concept (concept_id, concept_name, concept_desc, enabled, display_order, created_date,
                                        updated_date)
VALUES ('FUN', '펀다이빙', '가볍게 즐기면서 하고 싶어요', true, 0, '2024-04-23 12:45:03.510781', '2024-04-23 12:45:03.510781');
INSERT INTO public.buddy_event_concept (concept_id, concept_name, concept_desc, enabled, display_order, created_date,
                                        updated_date)
VALUES ('PRACTICE', '연습', '덕다이빙 / DTY / CNF / CYN 등 기술을 연습하고 싶어요', true, 1, '2024-04-23 12:45:03.510781',
        '2024-04-23 12:45:03.510781');
INSERT INTO public.buddy_event_concept (concept_id, concept_name, concept_desc, enabled, display_order, created_date,
                                        updated_date)
VALUES ('PHOTO', '사진촬영', '멋있는 사진을 남기고 싶어요', true, 2, '2024-04-23 12:45:03.510781', '2024-04-23 12:45:03.510781');
INSERT INTO public.buddy_event_concept (concept_id, concept_name, concept_desc, enabled, display_order, created_date,
                                        updated_date)
VALUES ('TRAINING', '강습', '전문적으로 배우고 싶어요', true, 3, '2024-04-23 12:45:03.510781', '2024-04-23 12:45:03.510781');
INSERT INTO public.buddy_event_concept (concept_id, concept_name, concept_desc, enabled, display_order, created_date,
                                        updated_date)
VALUES ('MERMAID', '머메이드', '인어가 되어 보실래요?', true, 4, '2024-04-23 12:45:03.510781', '2024-04-23 12:45:03.510781');


-- 버디 이벤트 테스트 데이터

-- H2 데이터베이스용 SQL 쿼리 수정
INSERT INTO public.buddy_event (user_id, event_start_date, event_end_date, participant_count,
                                car_share_yn, status, kakao_room_code, freediving_level, comment,
                                updated_date, created_date, gender_type)
VALUES (1,
        DATEADD('HOUR', 34, CAST(CURRENT_DATE AS TIMESTAMP)),
        DATEADD('HOUR', 37, CAST(CURRENT_DATE AS TIMESTAMP)),
        3, true, 'RECRUITING', 'gQWkq2Uf', null,
        '이번 모임은 캐주얼하게 진행합니다.이번 모임은 캐주얼하게 진행합니다.이번 모임은 캐주얼하게 진행합니다.이번 모임은 캐주얼하게 진행합니다.이번 모임은 캐주얼하게 진행합니다.이번 모임은 캐주얼하게 진행합니다.이번 모임은 캐주얼하게 진행합니다.이번 모임은 캐주얼하게 진행합니다.이번 모임은 캐주얼하게 진행합니다.이번 모임은 캐주얼하게 진행합니다.',
        NOW(), NOW(), 'ALL');

INSERT INTO public.buddy_event (user_id, event_start_date, event_end_date, participant_count,
                                car_share_yn, status, kakao_room_code, freediving_level, comment,
                                updated_date, created_date, gender_type)
VALUES (2,
        DATEADD('HOUR', 39, CAST(CURRENT_DATE AS TIMESTAMP)),
        DATEADD('HOUR', 42, CAST(CURRENT_DATE AS TIMESTAMP)),
        2, false, 'RECRUITING', 'gQWkq2Uf', 2,
        '테스트!!이번 모임은 캐주얼하게 진행합니다.테스트!!이번 모임은 캐주얼하게 진행합니다.테스트!!이번 모임은 캐주얼하게 진행합니다.테스트!!이번 모임은 캐주얼하게 진행합니다.테스트!!이번 모임은 캐주얼하게 진행합니다.',
        NOW(), NOW(), 'ALL');

INSERT INTO public.buddy_event (user_id, event_start_date, event_end_date, participant_count,
                                car_share_yn, status, kakao_room_code, freediving_level, comment,
                                updated_date, created_date, gender_type)
VALUES (3,
        DATEADD('HOUR', 43, CAST(CURRENT_DATE AS TIMESTAMP)),
        DATEADD('HOUR', 46, CAST(CURRENT_DATE AS TIMESTAMP)),
        5, true, 'RECRUITING', 'gQWkq2Uf', null,
        '테테스트!!이번 모임은 캐주얼하게 진행합니다.테스트!!이번 모임은 캐주얼하게 진행합니다.테스트!!이번 모임은 캐주얼하게 진행합니다.트 2!!',
        NOW(), NOW(), 'ALL');

INSERT INTO public.buddy_event_concept_mapping (event_id, concept_id, created_date, updated_date)
VALUES (1, 'FUN', now(), now());
INSERT INTO public.buddy_event_concept_mapping (event_id, concept_id, created_date, updated_date)
VALUES (3, 'PHOTO', now(), now());
INSERT INTO public.buddy_event_concept_mapping (event_id, concept_id, created_date, updated_date)
VALUES (3, 'PRACTICE', now(), now());

INSERT INTO public.buddy_event_diving_pool_mapping (diving_pool_id, event_id, updated_date, created_date)
VALUES ('DEEPSTATION', 1, now(), now());
INSERT INTO public.buddy_event_diving_pool_mapping (diving_pool_id, event_id, updated_date, created_date)
VALUES ('THEME_SCUBA_POOL', 2, now(), now());
INSERT INTO public.buddy_event_diving_pool_mapping (diving_pool_id, event_id, updated_date, created_date)
VALUES ('SONGDO_SPORTS_PARK_POOL', 2, now(), now());
INSERT INTO public.buddy_event_diving_pool_mapping (diving_pool_id, event_id, updated_date, created_date)
VALUES ('PARADIVE', 3, now(), now());
INSERT INTO public.buddy_event_diving_pool_mapping (diving_pool_id, event_id, updated_date, created_date)
VALUES ('THEME_SCUBA_POOL', 3, now(), now());

INSERT INTO public.buddy_event_join_requests (user_id, event_id, status, created_date, updated_date)
VALUES (1, 1, 'OWNER', now(), now());
INSERT INTO public.buddy_event_join_requests (user_id, event_id, status, created_date, updated_date)
VALUES (2, 2, 'OWNER', now(), now());
INSERT INTO public.buddy_event_join_requests (user_id, event_id, status, created_date, updated_date)
VALUES (3, 3, 'OWNER', now(), now());
INSERT INTO public.buddy_event_join_requests (user_id, event_id, status, created_date, updated_date)
VALUES (1, 2, 'PARTICIPATING', now(), now());
INSERT INTO public.buddy_event_join_requests (user_id, event_id, status, created_date, updated_date)
VALUES (1, 3, 'REJECTED', now(), now());
INSERT INTO public.buddy_event_join_requests (user_id, event_id, status, created_date, updated_date)
VALUES (2, 1, 'PARTICIPATING', now(), now());
INSERT INTO public.buddy_event_join_requests (user_id, event_id, status, created_date, updated_date)
VALUES (2, 3, 'APPLIED', now(), now());
INSERT INTO public.buddy_event_join_requests (user_id, event_id, status, created_date, updated_date)
VALUES (3, 1, 'PARTICIPATING', now(), now());
INSERT INTO public.buddy_event_join_requests (user_id, event_id, status, created_date, updated_date)
VALUES (3, 2, 'APPLIED', now(), now());



INSERT INTO public.buddy_event_like_count (event_id, like_count, created_date)
VALUES (1, 1, now());
INSERT INTO public.buddy_event_like_count (event_id, like_count, created_date)
VALUES (2, 1, now());
INSERT INTO public.buddy_event_like_count (event_id, like_count, created_date)
VALUES (3, 1, now());


INSERT INTO public.buddy_event_like_mapping (user_id, event_id, is_deleted, created_date, updated_date)
VALUES (1, 1, false, '2024-04-18 17:39:57.769088', '2024-04-18 17:39:57.769088');
INSERT INTO public.buddy_event_like_mapping (user_id, event_id, is_deleted, created_date, updated_date)
VALUES (1, 2, false, '2024-04-18 17:39:57.769088', '2024-04-18 17:39:57.769088');
INSERT INTO public.buddy_event_like_mapping (user_id, event_id, is_deleted, created_date, updated_date)
VALUES (1, 3, false, '2024-04-18 17:39:57.769088', '2024-04-18 17:39:57.769088');
INSERT INTO public.buddy_event_like_mapping (user_id, event_id, is_deleted, created_date, updated_date)
VALUES (2, 2, false, '2024-04-18 17:39:57.769088', '2024-04-18 17:39:57.769088');
INSERT INTO public.buddy_event_like_mapping (user_id, event_id, is_deleted, created_date, updated_date)
VALUES (2, 3, false, '2024-04-18 17:39:57.769088', '2024-04-18 17:39:57.769088');
INSERT INTO public.buddy_event_like_mapping (user_id, event_id, is_deleted, created_date, updated_date)
VALUES (3, 3, false, '2024-04-18 17:39:57.769088', '2024-04-18 17:39:57.769088');

/*  테스트 사용자
 INSERT INTO public.users (created_date, updated_date, user_id, sex_type, nickname, oauth_type, user_status, email, phone_number, content, oauth_interlock, profile_img_url, provider_id) VALUES ('2024-05-05 13:42:08.431082', '2024-05-05 13:42:08.448992', 1, null, '행복다이버_00004', 'GOOGLE', 'ACTIVE', 'djw9126@gmail.com', null, null, null, 'https://lh3.googleusercontent.com/a/ACg8ocL-oH6fR7L309Vfi8NItCgvYdvCYjRcWVIuloFxviM__sVuEw=s96-c', '106610519602513940118');
 INSERT INTO public.user_license (confirm_tf, license_level, confirm_admin_id, created_date, license_id, updated_date, user_id, license_status, role, dive_type, license_img_url, org_name) VALUES (true, 2, 1, '2024-05-05 14:20:24.258353', 11, '2024-05-06 23:38:58.336691', 1, 'APPROVED', 'WAIT_LICENSE_APPROVAL', 'FREE_DIVE', 'https://d1pjflw6c3jt4r.cloudfront.net/images/license/6-3a6f31c5dd1040dd9fd3a1c85681f35b.png', null);

INSERT INTO public.users (created_date, updated_date, user_id, sex_type, nickname, oauth_type, user_status, email, phone_number, content, oauth_interlock, profile_img_url, provider_id) VALUES ('2024-05-05 13:42:08.431082', '2024-05-05 13:42:08.448992', 2, null, '잠수병다이버_00004', 'GOOGLE', 'ACTIVE', 'redjoon10@gmail.com', null, null, null, 'https://lh3.googleusercontent.com/a/ACg8ocL-oH6fR7L309Vfi8NItCgvYdvCYjRcWVIuloFxviM__sVuEw=s96-c', '106610519602513940118');
INSERT INTO public.user_license (confirm_tf, license_level, confirm_admin_id, created_date, license_id, updated_date, user_id, license_status, role, dive_type, license_img_url, org_name) VALUES (false, 2, 1, '2024-05-05 14:20:24.258353', 12, '2024-05-06 23:38:58.336691', 2, 'APPROVED', 'WAIT_LICENSE_APPROVAL', 'FREE_DIVE', 'https://d1pjflw6c3jt4r.cloudfront.net/images/license/6-3a6f31c5dd1040dd9fd3a1c85681f35b.png', null);

INSERT INTO public.users (created_date, updated_date, user_id, sex_type, nickname, oauth_type, user_status, email, phone_number, content, oauth_interlock, profile_img_url, provider_id) VALUES ('2024-05-05 13:42:08.431082', '2024-05-05 13:42:08.448992', 3, null, '펭귄다이버_00004', 'GOOGLE', 'ACTIVE', 'hmj0977@gmail.com', null, null, null, 'https://lh3.googleusercontent.com/a/ACg8ocL-oH6fR7L309Vfi8NItCgvYdvCYjRcWVIuloFxviM__sVuEw=s96-c', '106610519602513940118');
INSERT INTO public.user_license (confirm_tf, license_level, confirm_admin_id, created_date, license_id, updated_date, user_id, license_status, role, dive_type, license_img_url, org_name) VALUES (true, 2, 1, '2024-05-05 14:20:24.258353', 13, '2024-05-06 23:38:58.336691', 3, 'APPROVED', 'WAIT_LICENSE_APPROVAL', 'FREE_DIVE', 'https://d1pjflw6c3jt4r.cloudfront.net/images/license/6-3a6f31c5dd1040dd9fd3a1c85681f35b.png', null);
*/

INSERT INTO user_diving_pool
VALUES (1, 'DEEPSTATION'),
       (1, 'PARADIVE'),
       (2, 'PARADIVE');
INSERT INTO user_buddy_event_concept
VALUES (1, 'FUN'),
       (1, 'PHOTO'),
       (2, 'PHOTO')


