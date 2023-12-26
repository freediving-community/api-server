# BuddyService ( 버디 일정 도메인 )

- 역할 : 사용자들이 버디 이벤트를 주최하고, 참여하며, 관리할 수 있는 기능.
- 단일 책임 : 일정 생성, 관리, 참가자 관리 및 마감과 같은 이벤트 관련 모든 프로세스를 책임집니다.
  이벤트 데이터를 기반으로 사용자에게 맞춤형 이벤트를 추천한다.

## 비즈니스 인터페이스

### Query

- 버디 일정 이벤트 조회 하기 ( 'searchBuddyEvents' )
  > GET buddy-service/v1/buddyEvents
- 내가 모집 중인 버디 일정 조회 하기 ( 'listMyRecruitingEvents' )
  > GET buddy-service/v1/myEvents/recruiting
- 버디 일정 참가 신청자 조회 하기 ( 'viewEventApplicants' )
  > GET buddy-service/v1/buddyEvents/{eventId}/applicants
- 사용자 맞춤형 버디 일정 추천 ( 'recommendCustomBuddyEvents' )
  > GET buddy-service/v1/buddyEvents/recommendations

### Command

- 버디 일정 이벤트 생성 하기 ( 'createBuddyEvent' )
  > POST buddy-service/v1/buddyEvents
    - 버디 일정 참가 신청 요청 하기 ( 'requestEventParticipation' )
  > POST buddy-service/v1/buddyEvents/{eventId}/participations
- 버디 일정 이벤트 삭제 하기 ( 'deleteBuddyEvent' )
  > DELETE buddy-service/v1/buddyEvents/{eventId}
- 버디 일정 이벤트 수정 하기 ( 'updateBuddyEvent' )
  > PUT buddy-service/v1/buddyEvents/{eventId}

## Package Architecture

```bash
├── com.freediving.buddyservice
│   ├── adapter
│   │   ├── in
│   │   │   ├── web
│   │   │   │   ├── v1
│   │   │   │   │   ├── BuddyEventControllerV1.java
│   │   │   │   │   ├── CreateBuddyEventRequestV1.java
│   │   │   │   │   └── UpdateBuddyEventRequestV1.java
│   │   │   │   ├── v2
│   │   │   │   │   ├── BuddyEventControllerV2.java
│   │   │   │   │   ├── CreateBuddyEventRequestV2.java
│   │   │   │   │   └── UpdateBuddyEventRequestV2.java
│   │   │   └── out
│   │   │       ├── BuddyEventEntity.java
│   │   │       ├── BuddyEventRepository.java
│   │   │       └── BuddyEventRepositoryAdapter.java
│   ├── application
│   │   ├── port
│   │   │   ├── in
│   │   │   │   ├── v1
│   │   │   │   │   ├── CreateBuddyEventCommandV1.java
│   │   │   │   │   └── UpdateBuddyEventCommandV1.java
│   │   │   │   ├── v2
│   │   │   │   │   ├── CreateBuddyEventCommandV2.java
│   │   │   │   │   └── UpdateBuddyEventCommandV2.java
│   │   │   │   └── BuddyEventManagementUseCase.java
│   │   │   └── out
│   │   │       ├── CreateBuddyEventPort.java
│   │   │       ├── UpdateBuddyEventPort.java
│   │   │       └── BuddyEventManagementPort.java
│   │   └── service
│   │       └── out
│   │           └── BuddyEventService.java
│   └── domain
│       └── BuddyEvent.java
└── Dockerfile
```