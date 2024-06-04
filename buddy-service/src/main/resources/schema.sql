drop table if exists diving_pool;
CREATE TABLE diving_pool
(
    diving_pool_id    VARCHAR(30)  NOT NULL,
    diving_pool_name  VARCHAR(30)  NOT NULL,
    address           VARCHAR(255) NOT NULL,
    description       VARCHAR(255) NOT NULL,
    simple_address    VARCHAR(255),
    operating_hours   VARCHAR(255),
    price_info        VARCHAR(255),
    website_url       VARCHAR(255),
    recommended_level VARCHAR(50),
    depth             VARCHAR(50),
    contact_number    VARCHAR(50),
    regular_closure   VARCHAR(50),
    is_visible        BOOLEAN      NOT NULL DEFAULT false,
    display_order     INTEGER      NOT NULL DEFAULT 0,
    updated_date      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_date      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);
ALTER TABLE diving_pool
    ADD CONSTRAINT PK_DIVING_POOL PRIMARY KEY (diving_pool_id);



drop table if exists buddy_event_diving_pool_mapping;
CREATE TABLE buddy_event_diving_pool_mapping
(
    diving_pool_id varchar(30) NOT NULL,
    event_id       bigint      NOT NULL,
    updated_date   timestamp   NOT NULL,
    created_date   timestamp   NOT NULL
);
ALTER TABLE buddy_event_diving_pool_mapping
    ADD CONSTRAINT PK_BUDDY_EVENT_DIVING_POOL_MAPPING PRIMARY KEY (diving_pool_id, event_id);



drop table if exists buddy_event_join_requests;
CREATE TABLE buddy_event_join_requests
(
    user_id      bigint      NOT NULL,
    event_id     bigint      NOT NULL,
    status       varchar(30) NOT NULL,
    created_date timestamp   NOT NULL,
    updated_date timestamp   NOT NULL
);
ALTER TABLE buddy_event_join_requests
    ADD CONSTRAINT PK_BUDDY_EVENT_JOIN_REQUESTS PRIMARY KEY (user_id, event_id);

drop table if exists buddy_event_concept_mapping;
CREATE TABLE buddy_event_concept_mapping
(
    event_id     bigint      NOT NULL,
    concept_id   varchar(30) NOT NULL,
    created_date timestamp   NOT NULL,
    updated_date timestamp   NOT NULL
);
ALTER TABLE buddy_event_concept_mapping
    ADD CONSTRAINT PK_BUDDY_EVENT_CONCEPT_MAPPING PRIMARY KEY (event_id, concept_id);



drop table if exists buddy_event_view_count;
CREATE TABLE buddy_event_view_count
(
    event_id     bigint    NOT NULL,
    view_count   integer   NOT NULL DEFAULT 0,
    created_date timestamp NOT NULL
);
ALTER TABLE buddy_event_view_count
    ADD CONSTRAINT PK_BUDDY_EVENT_VIEW_COUNT PRIMARY KEY (event_id);

drop table if exists buddy_event_like_count;
CREATE TABLE buddy_event_like_count
(
    event_id     bigint    NOT NULL,
    like_count   integer   NOT NULL DEFAULT 0,
    created_date timestamp NOT NULL
);
ALTER TABLE buddy_event_like_count
    ADD CONSTRAINT PK_BUDDY_EVENT_LIKE_COUNT PRIMARY KEY (event_id);

drop table if exists buddy_event_like_mapping;
CREATE TABLE buddy_event_like_mapping
(
    user_id      bigint    NOT NULL,
    event_id     bigint    NOT NULL,
    is_deleted   Boolean   NOT NULL,
    created_date timestamp NOT NULL,
    updated_date timestamp NOT NULL
);
ALTER TABLE buddy_event_like_mapping
    ADD CONSTRAINT PK_BUDDY_EVENT_LIKE_MAPPING PRIMARY KEY (user_id, event_id);


drop table if exists buddy_event;
CREATE TABLE buddy_event
(
    event_id          bigint        NOT NULL generated always as identity,
    user_id           bigint        NOT NULL,
    event_start_date  timestamp     NOT NULL,
    event_end_date    timestamp     NOT NULL,
    participant_count integer       NOT NULL,
    car_share_yn      boolean       NOT NULL,
    gender_type       varchar(30)   NOT NULL,
    status            varchar(30)   NOT NULL,
    kakao_room_code   varchar(10)   NULL,
    freediving_level  integer       NULL,
    comment           varchar(1000) NULL,
    image_url         varchar(200)  NULL,
    updated_date      timestamp     NOT NULL,
    created_date      timestamp     NOT NULL
);
ALTER TABLE buddy_event
    ADD CONSTRAINT PK_BUDDY_EVENT PRIMARY KEY (event_id);

ALTER TABLE buddy_event_diving_pool_mapping
    ADD CONSTRAINT FK_buddy_event_TO_buddy_event_diving_pool_mapping_1 FOREIGN KEY (event_id)
        REFERENCES buddy_event (event_id);


ALTER TABLE buddy_event_join_requests
    ADD CONSTRAINT FK_buddy_event_TO_buddy_event_join_requests_1 FOREIGN KEY (event_id)
        REFERENCES buddy_event (event_id);

ALTER TABLE buddy_event_concept_mapping
    ADD CONSTRAINT FK_buddy_event_TO_buddy_event_concept_mapping_1 FOREIGN KEY (event_id)
        REFERENCES buddy_event (event_id);

ALTER TABLE buddy_event_view_count
    ADD CONSTRAINT FK_buddy_event_TO_buddy_event_view_count_1 FOREIGN KEY (event_id)
        REFERENCES buddy_event (event_id);

ALTER TABLE buddy_event_like_count
    ADD CONSTRAINT FK_buddy_event_TO_buddy_event_like_count_1 FOREIGN KEY (event_id)
        REFERENCES buddy_event (event_id);

ALTER TABLE buddy_event_like_mapping
    ADD CONSTRAINT FK_buddy_event_TO_buddy_event_like_mapping_1 FOREIGN KEY (event_id)
        REFERENCES buddy_event (event_id);


drop table if exists buddy_event_concept;
CREATE TABLE buddy_event_concept
(
    concept_id    varchar(30)  NOT NULL,
    concept_name  varchar(20)  NOT NULL,
    concept_desc  varchar(100) NOT NULL,
    enabled       boolean      NOT NULL DEFAULT false,
    display_order int          NOT NULL,
    created_date  timestamp    NOT NULL,
    updated_date  timestamp    NOT NULL
);
ALTER TABLE buddy_event_concept
    ADD CONSTRAINT PK_BUDDY_EVENT_CONCEPT PRIMARY KEY (concept_id);


-- 관심사 선호 테이블

DROP TABLE IF EXISTS user_diving_pool;
CREATE TABLE user_diving_pool
(
    user_id        BIGINT      NOT NULL,
    diving_pool_id VARCHAR(30) NOT NULL,
    PRIMARY KEY (user_id, diving_pool_id)
);

DROP TABLE IF EXISTS user_buddy_event_concept;
CREATE TABLE user_buddy_event_concept
(
    user_id    BIGINT      NOT NULL,
    concept_id VARCHAR(30) NOT NULL,
    PRIMARY KEY (user_id, concept_id)
);
