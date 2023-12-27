# CommunityService ( 커뮤니티 관리 도메인 )

역할 : 사용자가 자유롭게 게시글을 작성, 조회할 수 있는 커뮤니티 기능을 제공하며, 인기 있는 게시글을 추천하는 서비스 제공.

단일 책임 : 게시판의 게시글 생성, 수정, 삭제 및 조회 등의 기능을 제공합니다. 또한, 사용자의 활동 데이터를 기반으로 한 컨텐츠 추천 기능을 담당.

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
