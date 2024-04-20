INSERT INTO diving_pool (diving_pool_id, diving_pool_name, address, description, simple_address, operating_hours,
                         price_info, website_url, recommended_level, depth, is_visible, display_order, updated_date,
                         created_date)
VALUES ('DEEPSTATION', '딥스테이션', '경기 용인시 처인구 포곡읍 성산로 523 딥스테이션', '딥스테이션은 예약제로 운영되며, 2레벨 이상 이용 가능. 평일에는 다양한 입장권이 제공됩니다.',
        '경기 용인시 처인구 포곡읍 성산로 523', '08:00 - 23:00, 3시간 간격', '평일: 44,000만원, \n휴일: 66,000원', NULL, '초급 이상', '36m', TRUE, 0,
        now(), now()),
       ('PARADIVE', '파라다이브', '경기 시흥시 거북섬중앙로1 보니타가, 파라다이브(1동 3층)', '파라다이브는 최대 수심 35미터로, 다이빙 연습에 적합한 환경입니다.',
        '경기 시흥시 거북섬중앙로1 보니타가', '08:00 - 23:00, 3시간 또는 6시간 간격', '일반 이용: 4.5만원 (평일), 7.9만원 (휴일), 휴일 이용: 6.7만원', NULL,
        NULL, '35m', TRUE, 1, now(), now()),
       ('K26', 'K-26', '경기 가평 청평면 고성리 59-1', 'K-26은 실전 다이빙 연습에 최적화된 수영장으로, 예약제로 운영됩니다.', '경기 가평 청평면 고성리 59-1',
        '09:00 - 22:00, 3시간 간격', '평일: 3.3만원, 휴일: 5.5만원', NULL, NULL, '26m', TRUE, 2, now(), now()),
       ('THEME_SCUBA_POOL', '테마 잠수풀(TSN)', '경기 오산시 수청동 607-1',
        '테마 잠수풀은 2레벨 이상 다이버를 위해 평일 종일권을 제공하며, 다이빙의 꿈을 실현할 수 있는 곳입니다.', '경기 오산시 수청동 607-1', '09:00 - 23:00, 4시간 자유 이용',
        '평일: 3.3만원, 휴일: 5.0만원', NULL, '초급 이상', '11m', TRUE, 3, now(), now()),
       ('OLYMPIC_DIVING_POOL', '올림픽수영장 다이빙풀', '서울시 송파구 올림픽로 424 올림픽공원 내 올림픽수영장', '올림픽수영장 다이빙풀은 매월 둘째 주 일요일에 휴무입니다.',
        '서울시 송파구 올림픽로 424', '토요일: 12:00 - 17:00, 일요일: 10:00 - 13:00 / 14:00 - 17:00', '평일: 1.5만원, 휴일: 2.0만원',
        'https://4240735.modoo.at/?link=345es9an', NULL, '5m', TRUE, 4, now(), now()),
       ('SONGDO_SPORTS_PARK_POOL', '송도 스포츠파크 잠수풀', '인천광역시 연수구 인천신항대로 892번길 40',
        '송도 스포츠파크 잠수풀은 송도시에서 운영되며, 예약제로 운영됩니다. 매주 월요일 휴무.', '인천광역시 연수구 인천신항대로 892번길 40',
        '화요일 - 토요일: 18:00 - 21:00, 일요일: 09:00 - 12:00 / 13:00 - 16:00', '일일 이용료: 0.5만원',
        'https://songdopark.incheon.go.kr', NULL, '5m', TRUE, 5, now(), now()),
       ('SEONGNAM_AQUALINE_POOL', '성남 아쿠아라인 다목적풀', '경기도 성남시 중원구 제일로 60', '성남 아쿠아라인 다목적풀은 매월 둘째, 넷째 주 일요일에 휴무입니다.',
        '경기도 성남시 중원구 제일로 60', '09:00 - 22:00', '평일: 1.5만원, 휴일: 2.0만원', NULL, NULL, '5m', TRUE, 6, now(), now()),
       ('WORLDCUP_SCUBA_POOL', '월드컵 스킨스쿠버 다이빙풀', '경기도 수원시 월드컵로 310 스포츠아일랜드', '월드컵 스킨스쿠버 다이빙풀은 매월 둘째 주 수요일 휴무입니다.',
        '경기도 수원시 월드컵로 310', '11월 - 2월: 09:00 - 21:00, 3월 - 10월: 09:00 - 22:00', '평일/휴일: 1.5만원',
        'http://m.suwonpool.com/page/page3', NULL, '5m', TRUE, 7, now(), now()),
       ('DAEBU_WELFARE_POOL', '대부동 복지체육센터', '경기도 안산시 단원구 영전로 126',
        '대부동 복지체육센터는 안산시에서 운영되며, 예약제로 운영됩니다. 매주 월요일 및 공휴일에 휴무.', '경기도 안산시 단원구 영전로 126',
        '09:00 - 12:00 / 14:00 - 17:00 / 17:30 - 20:30', '평일/휴일: 0.75만원',
        'https://www.ansanuc.net/homenew/10502/20005/bbsDetail.do?currentPageNo=1&searchCondition=&searchKeyword=&searchKey=&searchKey1=&searchKey3=&bbsIdx=11882',
        NULL, '5.3m', TRUE, 8, now(), now());


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