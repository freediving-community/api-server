

insert into diving_pool (diving_pool_id,
                         diving_pool_name,
                         address,
                         description,
                         is_visible,
                         display_order,
                         updated_date,
                         created_date)
values ('DEEPSTATION', '딥스테이션', '경기 용인시 처인구 포곡읍 성산로 523 딥스테이션', '딥스테이션, 예약제 운영, 2레벨 제한, 평일 다양한 입장권 제공', TRUE, 0, now(), now()),
       ('PARADIVE', '파라다이브', '경기 시흥시 거북섬중앙로1 보니타가, 파라다이브(1동 3층)', '파라다이브, 수심 35미터', TRUE, 1, now(), now()),
       ('K26', 'K-26', '경기 가평 청평면 고성리 59-1', 'K-26 실전잠수풀, 예약제 운영', TRUE, 2, now(), now()),
       ('THEME_SCUBA_POOL', '테마 잠수풀(TSN)', '경기 오산시 수청동 607-1', '다이빙의 꿈을 실현, 2레벨 제한, 평일 종일권', TRUE, 3, now(), now()),
       ('OLYMPIC_DIVING_POOL', '올림픽수영장 다이빙풀', '서울시 송파구 올림픽로 424 올림픽공원 내 올림픽수영장', '매월 둘째 주 일요일 휴무', TRUE, 4, now(), now()),
       ('SONGDO_SPORTS_PARK_POOL', '송도 스포츠파크 잠수풀', '인천광역시 연수구 인천신항대로 892번길 40', '송도시 운영, 예약제, 매주 월요일 휴무', TRUE, 5, now(), now()),
       ('SEONGNAM_AQUALINE_POOL', '성남 아쿠아라인 다목적풀', '경기도 성남시 중원구 제일로 60', '매월 둘, 넷째 주 일요일 휴무', TRUE, 6, now(), now()),
       ('WORLDCUP_SCUBA_POOL', '월드컵 스킨스쿠버 다이빙풀', '경기도 수원시 월드컵로 310 스포츠아일랜드', '매월 둘째 주 수요일 휴무', TRUE, 7, now(), now()),
       ('DAEBU_WELFARE_POOL', '대부동 복지체육센터', '경기도 안산시 단원구 영전로 126', '안산시 운영, 예약제, 2레벨 제한, 매주 월요일/공휴일 휴무', TRUE, 8, now(), now());



insert into buddy_event_concept (concept_id,
                                 concept_name,
                                 enabled,
                                 display_order,
                                 updated_date,
                                 created_date)
values ('FUN', '펀다이빙', TRUE, 0, now(), now()),
       ('PRACTICE', '연습', TRUE, 1, now(), now()),
       ('PHOTO', '사진촬영', TRUE, 2, now(), now()),
       ('TRAINING', '강습', TRUE, 3, now(), now()),
       ('LEVEL_UP', '레벨업', TRUE, 3, now(), now());