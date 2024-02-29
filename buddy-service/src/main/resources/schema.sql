drop table if exists diving_pool;
CREATE TABLE diving_pool
(
    diving_pool_id   varchar(20)  NOT NULL,
    diving_pool_name varchar(30)  NOT NULL,
    address          VARCHAR(255) NOT NULL,
    description      VARCHAR(255) NOT NULL,
    is_visible       Boolean      NOT NULL DEFAULT false,
    display_order    integer      NOT NULL DEFAULT 0,
    updated_date     timestamp             default current_timestamp NOT NULL,
    created_date     timestamp             default current_timestamp NOT NULL
);
ALTER TABLE diving_pool
    ADD CONSTRAINT PK_DIVING_POOL PRIMARY KEY (diving_pool_id);



drop table if exists events_diving_pool_mapping;
CREATE TABLE events_diving_pool_mapping
(
    diving_pool_id varchar(20) NOT NULL,
    event_id       bigint      NOT NULL
);
ALTER TABLE events_diving_pool_mapping
    ADD CONSTRAINT PK_EVENTS_DIVING_POOL_MAPPING PRIMARY KEY (diving_pool_id, event_id);



drop table if exists buddy_event_conditions;
CREATE TABLE buddy_event_conditions
(
    event_id         bigint      NOT NULL,
    freediving_level varchar(20) NULL,
    genders          varchar(20) NULL,
    age_groups       varchar(20) NULL
);
ALTER TABLE buddy_event_conditions
    ADD CONSTRAINT PK_BUDDY_EVENT_CONDITIONS PRIMARY KEY (event_id);



drop table if exists buddy_event_join_requests;
CREATE TABLE buddy_event_join_requests
(
    user_id      bigint      NOT NULL,
    event_id     bigint      NOT NULL,
    status       varchar(20) NOT NULL,
    created_date timestamp   NOT NULL,
    updated_date timestamp   NOT NULL
);
ALTER TABLE buddy_event_join_requests
    ADD CONSTRAINT PK_BUDDY_EVENT_JOIN_REQUESTS PRIMARY KEY (user_id, event_id);



drop table if exists buddy_events;
CREATE TABLE buddy_events
(
    event_id          bigint        NOT NULL generated always as identity,
    user_id           bigint        NOT NULL,
    event_start_date  timestamp     NOT NULL,
    event_end_date    timestamp     NOT NULL,
    participant_count integer       NOT NULL,
    event_concepts    VARCHAR(255)  NULL,
    car_share_yn      boolean       NOT NULL,
    status            varchar(20)   NOT NULL,
    kakao_room_code   varchar(10)   NULL,
    comment           varchar(1000) NULL,
    updated_date      timestamp     NOT NULL,
    created_date      timestamp     NOT NULL
);
ALTER TABLE buddy_events
    ADD CONSTRAINT PK_BUDDY_EVENTS PRIMARY KEY (event_id);

COMMENT ON TABLE buddy_events IS '임시테이블';
COMMENT ON COLUMN buddy_events.event_id IS '버디 일정 이벤트 식별 ID';
COMMENT ON COLUMN buddy_events.user_id IS '유저 ID 시퀀스';
COMMENT ON COLUMN buddy_events.event_start_date IS '버디 일정 시작 날짜+시간';
COMMENT ON COLUMN buddy_events.event_end_date IS '버디 일정 종료 날짜+시간';
COMMENT ON COLUMN buddy_events.participant_count IS '버디 일정 모집하는 인원 수';
COMMENT ON COLUMN buddy_events.updated_date IS 'JPA Auditing 관리';
COMMENT ON COLUMN buddy_events.created_date IS 'JPA Auditing 관리';

ALTER TABLE events_diving_pool_mapping
    ADD CONSTRAINT FK_buddy_events_TO_events_diving_pool_mapping_1 FOREIGN KEY (event_id)
        REFERENCES buddy_events (event_id);

ALTER TABLE buddy_event_conditions
    ADD CONSTRAINT FK_buddy_events_TO_buddy_event_conditions_1 FOREIGN KEY (event_id)
        REFERENCES buddy_events (event_id);

ALTER TABLE buddy_event_join_requests
    ADD CONSTRAINT FK_buddy_events_TO_buddy_event_join_requests_1 FOREIGN KEY (event_id)
        REFERENCES buddy_events (event_id);
