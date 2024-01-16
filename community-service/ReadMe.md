# CommunityService ( 커뮤니티 관리 도메인 )

역할 : 사용자가 자유롭게 게시글을 작성, 조회할 수 있는 커뮤니티 기능을 제공하며, 인기 있는 게시글을 추천하는 서비스 제공.

단일 책임 : 게시판의 게시글 생성, 수정, 삭제 및 조회 등의 기능을 제공합니다. 또한, 사용자의 활동 데이터를 기반으로 한 컨텐츠 추천 기능을 담당.

게시판 : 자유게시판 , 모임후기 , QNA, 공지사항 ? , 대표추천글 , 보관글 , 꿀팁공유

---

# 비즈니스 인터페이스

    base-uri: community-service/v1.0

---

## Board API

### Query

- 게시판 목록 조회
  > GET /boards
- 게시판 조회
  > GET /boards/{boardId}

### Command

- 게시판 생성
  > POST /boards
- 게시판 수정
  > PUT /boards/{boardId}
- 게시판 삭제
  > DELETE /boards/{boardId}

---

## Article API

### Query

(param: 인기, 최근, 검색, 정렬 등)

- 게시글 목록 조회
  > GET /boards/{boardId}/articles
- 게시글 조회
  > GET /boards/{boardId}/articles/{articleId}
- 나의 게시글 목록 조회
  > GET /boards/my/articles

### Command

- 게시글 작성
  > POST /boards/{boardId}/articles
- 게시글 수정
  > PUT /boards/{boardId}/articles/{articleId}
- 게시글 삭제
  > DELETE /boards/{boardId}/articles/{articleId}

---

## Comment API

### Query

- 댓글 목록 조회
  > GET /boards/{boardId}/articles/{articleId}/comments
- 댓글 조회
  > GET /boards/{boardId}/articles/{articleId}/comments/{commentId}

### Command

- 댓글 작성
  > POST /boards/{boardId}/articles/{articleId}/comments
- 댓글 수정
  > PUT /boards/{boardId}/articles/{articleId}/comments/{commentId}
- 댓글 삭제
  > DELETE /boards/{boardId}/articles/{articleId}/comments/{commentId}

---

# 세부 요구 기능

## 게시판

- 게시판은 관리자만 생성 가능하다.
- 게시판은 카테고리(타입)이 있다.
    - 카테고리별 게시글의 속성을 정한다.(댓글, 신고 가능, 사진 첨부 여부)
    - 버디매칭 : 버디매칭 소개글 타입의 게시판(버디매칭 모집시에만 생성 가능)
    - 자유게시판 : 누구나 작성, 해시태그
    - 질의응답 : 자유게시판과 동일

## 게시글

- 무조건 게시판에 속한다.
- 버디매칭 작성 시, 1:1관계의 게시글(버디매칭 소개글)이 생성 필요.
- 신고 기능
- 사진 첨부
- 추천과 비추천의 합산을 총점으로 인기/논란

## 댓글

- 답글 기능이 있다
- 추천시 추천 분석에 의해 상단 고정 기능
- 버디매칭 내 Q&A는 댓글의 기능들이다.
    - 여기선 articleId가 버디매칭 글ID => 해당 버디매칭 소개글을 게시글로하여 ID를 취함.
- 신고 ( 및 표시하지 않게 되는 ) 기능
- 추천과 비추천의 합산을 총점으로 인기/논란

# 시나리오

- 자유게시판:
    - 게시글 작성하기 -> (제목, 내용, 작성자id, 댓글) -> 

