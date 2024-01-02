# BuddyService ( 버디 일정 도메인 )

- 역할 : 사용자들이 버디 이벤트를 주최하고, 참여하며, 관리할 수 있는 기능.
- 단일 책임 : 일정 생성, 관리, 참가자 관리 및 마감과 같은 이벤트 관련 모든 프로세스를 책임집니다.
  이벤트 데이터를 기반으로 사용자에게 맞춤형 이벤트를 추천한다.

---

## 비즈니스 인터페이스

> base-url: buddy-service/{version}

## Buddy Event API ( ver 1 )

### Query

- 버디 이벤트 조회 하기
  > GET /event
- 내가 모집 중인 버디 이벤트 조회 하기
  > GET /event/my
- 버디 이벤트 참가 신청자 조회 하기
  > GET /event/{eventId}/applicants
- 사용자 맞춤형 버디 이벤트 추천
  > GET /event/recommendations

### Command

- 버디 일정 이벤트 생성 하기
  > POST /event
- 버디 일정 참가 신청 요청 하기
  > POST /event/{eventId}/participations
- 버디 일정 이벤트 삭제 하기
  > DELETE /event/{eventId}
- 버디 일정 이벤트 수정 하기
  > PUT /event/{eventId}
