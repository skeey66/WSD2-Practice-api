# 📘 WSD Practice - User 관리 REST API  
Spring Boot 기반의 사용자 관리(User CRUD) REST API 구현 과제입니다.  
구현 사항: HTTP 메소드별 API 구현, Middleware(요청 로그 출력) 적용, 표준 응답 포맷 적용등


---

## 📁 프로젝트 구조

```
src/main/java/kr/ac/jbnu/ksh/wsdteaching
 ├── common
 │     └── ApiResponse.java
 ├── config
 │     └── LoggingFilter.java
 ├── user
 │     ├── controller
 │     │     └── UserController.java
 │     ├── service
 │     │     └── UserService.java
 │     ├── domain
 │     │     └── User.java
 │     ├── dto
 │     │     └── UserDto.java
 │     └── request
 │           ├── CreateUserRequest.java
 │           ├── UpdateUserRequest.java
 │           └── LoginRequest.java
 └── WsdTeachingApplication.java
```

> 📌 **핵심 특징**  
> - MVC 구조 준수  
> - Request/Response DTO 분리  
> - 객체 캡슐화(getter/setter) 적용  
> - 응답 표준화(ApiResponse)  
> - LoggingFilter를 통한 Request/Response 미들웨어 구현  
> - 2xx, 4xx, 5xx 응답 코드 모두 활용  

---

## 📡 API 엔드포인트 목록

총 **8개의 REST API** + **2개의 에러 테스트 API** 구현 완료.

### 🔵 POST (2개)
| 기능 | Method | URL |
|------|--------|-----|
| 회원가입 | POST | `/api/v1/users` |
| 로그인 | POST | `/api/v1/users/login` |

---

### 🟢 GET (2개)
| 기능 | Method | URL |
|------|--------|-----|
| 전체 사용자 조회 | GET | `/api/v1/users` |
| 특정 사용자 조회 | GET | `/api/v1/users/{id}` |

---

### 🟠 PUT (2개)
| 기능 | Method | URL |
|------|--------|-----|
| 사용자 전체 정보 수정 | PUT | `/api/v1/users/{id}` |
| 이메일만 수정 | PUT | `/api/v1/users/{id}/email?email=newEmail` |

---

### 🔴 DELETE (2개)
| 기능 | Method | URL |
|------|--------|-----|
| 사용자 삭제 | DELETE | `/api/v1/users/{id}` |
| 전체 사용자 삭제 | DELETE | `/api/v1/users` |

---

### ⚠️ Error 테스트 API (2개)
| 기능 | Method | URL |
|------|--------|-----|
| 500 Internal Error | GET | `/api/v1/users/error/internal` |
| 501 Not Implemented | GET | `/api/v1/users/error/not-implemented` |

---

## 📌 표준 응답 형식 (ApiResponse)

모든 API는 아래 형식으로 응답합니다:

```json
{
  "status": "success or error",
  "data": { ... },
  "message": "설명 메시지"
}
```

---

## 📝 Postman 테스트 예시

### 1) 회원가입 (POST)
```
POST /api/v1/users
```

Body (JSON):
```json
{
  "username": "seunghyun",
  "email": "test@naver.com",
  "password": "1234"
}
```

응답:
```json
{
  "status": "success",
  "data": {
    "id": 1,
    "username": "seunghyun",
    "email": "test@naver.com"
  },
  "message": "User created"
}
```

---

## 🧩 Middleware (LoggingFilter)

모든 요청/응답을 콘솔에 출력합니다.

예시 출력:
```
[LoggingFilter] 요청 시작: POST /api/v1/users
[LoggingFilter] 요청 완료: status=201
```

---

## 📚 사용 기술

- **Java 17**
- **Spring Boot 3.x**
- **Gradle**
- **Postman (API 테스트)**

---
