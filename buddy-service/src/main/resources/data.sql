insert into DIVING_POOL (diving_pool_id,
                         diving_pool_name,
                         address,
                         description,
                         is_visible,
                         display_order,
                         updated_date,
                         created_date)
values ('DEEPSTATION', '딥스테이션', '경기도 용인시 처인구 포곡읍', '딥스테이션 설명', TRUE, 0, now(), now()),
       ('PARADIVE', '파라다이브', '경기도 시흥시', '파라다이브 설명', TRUE, 1, now(), now()),
       ('JAMSIL_DIVING_POOL', '잠실 다이빙 풀', '서울특별시 송파구 올림픽로', '잠실 다이빙 풀 설명', TRUE, 2, now(), now()),
       ('DIVELIFE', '다이브라이프', '서울특별시 서초구', '다이브라이프 설명', TRUE, 3, now(), now());
