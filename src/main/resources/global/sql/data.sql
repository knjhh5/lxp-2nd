-- ========================================================
-- 3. 페이지네이션 및 MVP 검증용 대용량 데이터 삽입 (DML)
-- ========================================================

-- [Members] 데이터 생성 (nickname varchar(8) 제약 조건 반영)
INSERT INTO members (login_id, password_hash, nickname, user_type)
VALUES ('student1', '$2a$10$8.Xk4w3tMTdRjTS8T4nCsOndLOpoazUQQ3WfdJ/UBPJk7oUnwREUe', '김수강',
        'STUDENT'),
       ('student2', '$2a$10$A31gjICNf.zU0YXPMmSwzOoDkYLtcKzRyJ6BXiYbzRSgmJcdJ8HZm', '박공부',
        'STUDENT'),
       ('lxp', '$2a$10$bObgMV/G1pic.zuKzwVy4eRdAEeUow1/yTxcVQqPm9yF36wctnpju', 'lxplxp', 'STUDENT'),
       ('instructor1', 'hashed_pass_capacity_test_333333333333333', '이강사', 'INSTRUCTOR'),
       ('instructor2', 'hashed_pass_capacity_test_444444444444444', '최티처', 'INSTRUCTOR');

-- [Admins] 데이터 생성
INSERT INTO admins (admin_id, password_hash)
VALUES ('admin', '$2a$10$IIZAZ8LoIalxdfrSdSVxMeNtKHnVcjmxvDoKvAHWgdQm/.HiS5kXS');

-- [Courses] 대용량 페이지네이션용 강의 데이터 (총 25개)
INSERT INTO courses (title, instructor_id, description, capacity, status)
VALUES ('Java 웹 개발 마스터 클래스', 5, '자바 백엔드 개발자의 필수 코스입니다.', 30, 'PUBLIC'),
       ('MySQL 데이터베이스 모델링 기초', 5, 'ERD 설계부터 정규화까지 배웁니다.', 50, 'PUBLIC'),
       ('Spring Boot 애플리케이션 실전', 5, '스프링 부트로 그럴듯한 웹 서비스를 만듭니다.', 20, 'PUBLIC'),
       ('HTML/CSS 웹 표준 레이아웃', 4, '퍼블리싱의 기초를 다지는 강의', 100, 'PUBLIC'),
       ('JavaScript 핵심 개념과 DOM 제어', 4, '모던 자바스크립트 기본기 다지기', 40, 'PUBLIC'),
       ('React.js 프론트엔드 마스터', 4, '리액트 컴포넌트 설계와 상태 관리', 25, 'PUBLIC'),
       ('Next.js SSR 완벽 가이드', 4, '서버 사이드 렌더링과 SEO 최적화', 30, 'PUBLIC'),
       ('TypeScript 타입 시스템 이해', 5, '안정적인 자바스크립트 코딩을 위한 타입스크립트', 45, 'PUBLIC'),
       ('AWS 클라우드 인프라 구축 기초', 5, 'EC2, RDS, S3를 활용한 서비스 배포', 15, 'PUBLIC'),
       ('Docker와 쿠버네티스 컨테이너 기술', 5, 'DevOps 입문을 위한 컨테이너 기초', 20, 'PUBLIC'),
       ('Python 데이터 분석 실무', 4, '판다스와 넘파이를 활용한 데이터 전처리', 60, 'PUBLIC'),
       ('공공데이터 활용 AI 예측 모델링', 4, '머신러닝 기초 알고리즘 배우기', 25, 'PUBLIC'),
       ('알고리즘 코딩 테스트 합격 패스', 5, '자바 실전 알고리즘 문제 풀이', 80, 'PUBLIC'),
       ('Git & GitHub 협업 워크플로우', 5, '브랜치 전략과 PR 기반 협업 완벽 마스터', 200, 'PUBLIC'),
       ('Linux 서버 명령어 및 쉘 스크립트', 5, '리눅스 환경 실무 핵심 가이드', 35, 'PUBLIC'),
       ('Redis 캐시 서버 활용 전략', 5, '조회 성능 개선을 위한 레디스 활용법', 30, 'PUBLIC'),
       ('Spring Security 보안 심화', 4, '안전한 인증과 인가를 위한 고급 과정', 15, 'PRIVATE'),
       ('쉽게 배우는 블록체인 입문', 4, '내용 부실로 승인 반려된 강좌 예시', 10, 'REJECTED'),
       ('Node.js 익스프레스 서버 구축', 4, '자바스크립트로 백엔드 개발하기', 40, 'PUBLIC'),
       ('GraphQL API 디자인과 실무', 4, 'REST API를 넘어선 새로운 데이터 쿼리 언어', 30, 'PUBLIC'),
       ('Vue.js 3 시작하기', 4, '쉽고 빠른 프론트엔드 프레임워크', 50, 'PUBLIC'),
       ('Kotlin 스프링 부트 웹 개발', 5, '코틀린으로 작성하는 모던 백엔드', 25, 'PUBLIC'),
       ('JPA 자바 ORM 표준 프로그래밍', 5, '엔티티 매핑부터 성능 최적화까지', 40, 'PUBLIC'),
       ('Jenkins 기반 CI/CD 자동화 구축', 5, '지속적 통합과 배포 파이프라인 만들기', 20, 'PUBLIC'),
       ('컴퓨터 구조와 운영체제 핵심 이론', 5, '개발자 면접 대비 CS 기초 지식', 100, 'PRIVATE');


-- [Enrollments] 수강 신청 내역 (총 8개)
INSERT INTO enrollments (student_id, course_id, status, progress_rate)
VALUES (1, 1, 'ACTIVE', 66),  -- enrollment_id=1 : 수강중 (5강 중 2강 완료)
       (1, 2, 'ACTIVE', 0),   -- enrollment_id=2 : 신규 신청 (진도율 0)
       (1, 3, 'ACTIVE', 100), -- enrollment_id=3 : 완강 (진도율 100)
       (1, 4, 'CANCELED', 0), -- enrollment_id=4 : 수강 취소 내역
       (2, 1, 'ACTIVE', 33),  -- enrollment_id=5 : 수강중 (5강 중 1강 완료)
       (2, 3, 'ACTIVE', 0),   -- enrollment_id=6 : 신규 신청
       (2, 13, 'ACTIVE', 20), -- enrollment_id=7 : 수강중 (5강 중 1강 완료)
       (2, 14, 'CANCELED', 0);
-- enrollment_id=8 : 수강 취소 내역


-- ========================================================
-- [Contents] 강의별 컨텐츠 데이터 (총 125개 / 강의당 5개)
-- content_id 채번 순서:
--   course  1 (Java)          → content_id  1 ~  5
--   course  2 (MySQL)         → content_id  6 ~ 10
--   course  3 (Spring Boot)   → content_id 11 ~ 15
--   course  4 (HTML/CSS)      → content_id 16 ~ 20
--   course  5 (JavaScript)    → content_id 21 ~ 25
--   course  6 (React)         → content_id 26 ~ 30
--   course  7 (Next.js)       → content_id 31 ~ 35
--   course  8 (TypeScript)    → content_id 36 ~ 40
--   course  9 (AWS)           → content_id 41 ~ 45
--   course 10 (Docker)        → content_id 46 ~ 50
--   course 11 (Python)        → content_id 51 ~ 55
--   course 12 (AI 예측)       → content_id 56 ~ 60
--   course 13 (알고리즘)      → content_id 61 ~ 65
--   course 14 (Git)           → content_id 66 ~ 70
--   course 15 (Linux)         → content_id 71 ~ 75
--   course 16 (Redis)         → content_id 76 ~ 80
--   course 17 (Security)      → content_id 81 ~ 85
--   course 18 (블록체인)      → content_id 86 ~ 90
--   course 19 (Node.js)       → content_id 91 ~ 95
--   course 20 (GraphQL)       → content_id 96 ~ 100
--   course 21 (Vue.js)        → content_id 101 ~ 105
--   course 22 (Kotlin)        → content_id 106 ~ 110
--   course 23 (JPA)           → content_id 111 ~ 115
--   course 24 (Jenkins)       → content_id 116 ~ 120
--   course 25 (CS 이론)       → content_id 121 ~ 125
-- ========================================================

-- course 1 : Java 웹 개발 마스터 클래스 (content_id 1~5)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: 개발 환경 세팅 및 Hello World', 1, 1),
       ('2강: 변수와 제어문 마스터하기', 1, 2),
       ('3강: 객체지향 프로그래밍의 이해', 1, 3),
       ('4강: 예외 처리와 컬렉션 프레임워크', 1, 4),
       ('5강: 멀티스레드와 동시성 프로그래밍', 1, 5);

-- course 2 : MySQL 데이터베이스 모델링 기초 (content_id 6~10)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: 데이터베이스 개요 및 설치', 2, 1),
       ('2강: DDL, DML 쿼리 뽀개기', 2, 2),
       ('3강: 서브쿼리와 조인(JOIN) 성능 최적화', 2, 3),
       ('4강: 인덱스 설계와 실행 계획 분석', 2, 4),
       ('5강: 트랜잭션과 락(Lock) 이해', 2, 5);

-- course 3 : Spring Boot 애플리케이션 실전 (content_id 11~15)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: 스프링 프레임워크 vs 스프링 부트', 3, 1),
       ('2강: REST API 컨트롤러 설계', 3, 2),
       ('3강: JPA 연동 및 비즈니스 로직 구현', 3, 3),
       ('4강: AWS 인프라에 배포하기', 3, 4),
       ('5강: 테스트 코드 작성 및 CI 연동', 3, 5);

-- course 4 : HTML/CSS 웹 표준 레이아웃 (content_id 16~20)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: 웹 표준과 시맨틱 마크업 이해', 4, 1),
       ('2강: CSS 박스 모델과 레이아웃 기초', 4, 2),
       ('3강: Flexbox 완벽 정리', 4, 3),
       ('4강: CSS Grid 실전 레이아웃 구성', 4, 4),
       ('5강: 반응형 웹 미디어 쿼리 적용', 4, 5);

-- course 5 : JavaScript 핵심 개념과 DOM 제어 (content_id 21~25)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: 변수, 타입, 스코프 이해', 5, 1),
       ('2강: 함수와 클로저 심화', 5, 2),
       ('3강: 비동기 처리와 Promise', 5, 3),
       ('4강: DOM API 조작 실전', 5, 4),
       ('5강: 이벤트 핸들링과 버블링', 5, 5);

-- course 6 : React.js 프론트엔드 마스터 (content_id 26~30)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: React 환경 세팅과 JSX 문법', 6, 1),
       ('2강: 컴포넌트 설계와 Props 활용', 6, 2),
       ('3강: useState / useEffect 훅 완벽 이해', 6, 3),
       ('4강: Context API와 전역 상태 관리', 6, 4),
       ('5강: React Router와 SPA 페이지 구성', 6, 5);

-- course 7 : Next.js SSR 완벽 가이드 (content_id 31~35)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: Next.js 프로젝트 구조와 라우팅', 7, 1),
       ('2강: SSR과 SSG의 차이와 선택 기준', 7, 2),
       ('3강: getServerSideProps / getStaticProps 활용', 7, 3),
       ('4강: API Routes로 백엔드 연동하기', 7, 4),
       ('5강: SEO 최적화와 메타 태그 설정', 7, 5);

-- course 8 : TypeScript 타입 시스템 이해 (content_id 36~40)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: TypeScript 설치와 기본 타입 선언', 8, 1),
       ('2강: 인터페이스와 타입 별칭', 8, 2),
       ('3강: 제네릭(Generic) 완벽 정리', 8, 3),
       ('4강: 유틸리티 타입과 조건부 타입', 8, 4),
       ('5강: TypeScript로 리팩토링 실전 적용', 8, 5);

-- course 9 : AWS 클라우드 인프라 구축 기초 (content_id 41~45)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: AWS 계정 생성과 IAM 권한 설정', 9, 1),
       ('2강: EC2 인스턴스 생성 및 SSH 접속', 9, 2),
       ('3강: RDS 데이터베이스 연동', 9, 3),
       ('4강: S3 버킷 생성과 정적 파일 호스팅', 9, 4),
       ('5강: ELB와 Auto Scaling 구성', 9, 5);

-- course 10 : Docker와 쿠버네티스 컨테이너 기술 (content_id 46~50)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: Docker 개념과 이미지 빌드', 10, 1),
       ('2강: Docker Compose로 멀티 컨테이너 구성', 10, 2),
       ('3강: 쿠버네티스 아키텍처 이해', 10, 3),
       ('4강: Pod, Deployment, Service 실습', 10, 4),
       ('5강: Helm 차트로 배포 자동화', 10, 5);

-- course 11 : Python 데이터 분석 실무 (content_id 51~55)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: Python 환경 설정과 Jupyter 활용', 11, 1),
       ('2강: NumPy 배열 연산 기초', 11, 2),
       ('3강: Pandas DataFrame 조작 실전', 11, 3),
       ('4강: 데이터 시각화 Matplotlib / Seaborn', 11, 4),
       ('5강: 실무 데이터 전처리 프로젝트', 11, 5);

-- course 12 : 공공데이터 활용 AI 예측 모델링 (content_id 56~60)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: 공공데이터 포털 API 활용법', 12, 1),
       ('2강: 데이터 수집과 정제 파이프라인', 12, 2),
       ('3강: 선형 회귀와 분류 알고리즘 기초', 12, 3),
       ('4강: Scikit-learn으로 모델 학습 및 평가', 12, 4),
       ('5강: 예측 결과 시각화 및 보고서 작성', 12, 5);

-- course 13 : 알고리즘 코딩 테스트 합격 패스 (content_id 61~65)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: 시간 복잡도와 배열', 13, 1),
       ('2강: 스택과 큐 실전 문제 풀이', 13, 2),
       ('3강: 깊이 우선 탐색(DFS) 완벽 이해', 13, 3),
       ('4강: 너비 우선 탐색(BFS) 완벽 이해', 13, 4),
       ('5강: 동적 프로그래밍(DP) 핵심 문제 풀이', 13, 5);

-- course 14 : Git & GitHub 협업 워크플로우 (content_id 66~70)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: Git 버전 관리 기초 명령어', 14, 1),
       ('2강: 브랜치 전략 (GitFlow / Trunk Based)', 14, 2),
       ('3강: Pull Request와 코드 리뷰 프로세스', 14, 3),
       ('4강: 충돌(Conflict) 해결과 Rebase 실전', 14, 4),
       ('5강: GitHub Actions로 CI 자동화 입문', 14, 5);

-- course 15 : Linux 서버 명령어 및 쉘 스크립트 (content_id 71~75)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: 리눅스 파일 시스템과 기본 명령어', 15, 1),
       ('2강: 프로세스와 서비스 관리', 15, 2),
       ('3강: 권한 관리와 사용자 계정 설정', 15, 3),
       ('4강: Bash 쉘 스크립트 작성 기초', 15, 4),
       ('5강: Cron 스케줄러와 로그 관리 자동화', 15, 5);

-- course 16 : Redis 캐시 서버 활용 전략 (content_id 76~80)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: Redis 설치와 데이터 구조 이해', 16, 1),
       ('2강: String / Hash / List 자료형 활용', 16, 2),
       ('3강: 캐시 전략 (Look-Aside / Write-Through)', 16, 3),
       ('4강: TTL과 메모리 정책 설정', 16, 4),
       ('5강: Spring Boot와 Redis 연동 실전', 16, 5);

-- course 17 : Spring Security 보안 심화 (content_id 81~85)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: Spring Security 아키텍처 이해', 17, 1),
       ('2강: 폼 로그인과 HTTP Basic 인증 구현', 17, 2),
       ('3강: JWT 기반 무상태 인증 설계', 17, 3),
       ('4강: OAuth2 소셜 로그인 연동', 17, 4),
       ('5강: 메서드 수준 인가와 보안 테스트', 17, 5);

-- course 18 : 쉽게 배우는 블록체인 입문 (content_id 86~90) -- REJECTED
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: 블록체인의 개념과 분산 원장 이해', 18, 1),
       ('2강: 암호화 해시와 디지털 서명 기초', 18, 2),
       ('3강: 비트코인과 이더리움 비교', 18, 3),
       ('4강: 스마트 컨트랙트 개요', 18, 4),
       ('5강: 블록체인 활용 사례 분석', 18, 5);

-- course 19 : Node.js 익스프레스 서버 구축 (content_id 91~95)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: Node.js 이벤트 루프와 비동기 모델', 19, 1),
       ('2강: Express 라우터와 미들웨어 설계', 19, 2),
       ('3강: RESTful API 설계 및 구현', 19, 3),
       ('4강: MySQL 연동과 ORM(Sequelize) 활용', 19, 4),
       ('5강: JWT 인증과 에러 핸들링 미들웨어', 19, 5);

-- course 20 : GraphQL API 디자인과 실무 (content_id 96~100)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: GraphQL vs REST API 비교', 20, 1),
       ('2강: 스키마 정의와 리졸버 작성', 20, 2),
       ('3강: Query / Mutation / Subscription 구현', 20, 3),
       ('4강: DataLoader로 N+1 문제 해결', 20, 4),
       ('5강: Apollo Server와 클라이언트 연동', 20, 5);

-- course 21 : Vue.js 3 시작하기 (content_id 101~105)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: Vue 3 프로젝트 세팅과 Composition API', 21, 1),
       ('2강: 반응형 데이터와 computed / watch', 21, 2),
       ('3강: 컴포넌트 통신 (Props / Emits)', 21, 3),
       ('4강: Vue Router로 SPA 구성', 21, 4),
       ('5강: Pinia 상태 관리 라이브러리 활용', 21, 5);

-- course 22 : Kotlin 스프링 부트 웹 개발 (content_id 106~110)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: Kotlin 문법 기초와 Java 비교', 22, 1),
       ('2강: 코틀린 데이터 클래스와 확장 함수', 22, 2),
       ('3강: Spring Boot + Kotlin 프로젝트 구성', 22, 3),
       ('4강: JPA 엔티티 설계와 Repository 구현', 22, 4),
       ('5강: REST API 구현과 예외 처리 전략', 22, 5);

-- course 23 : JPA 자바 ORM 표준 프로그래밍 (content_id 111~115)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: JPA 개요와 영속성 컨텍스트 이해', 23, 1),
       ('2강: 엔티티 매핑과 연관관계 설정', 23, 2),
       ('3강: JPQL과 Querydsl 기초', 23, 3),
       ('4강: 즉시 로딩 vs 지연 로딩과 N+1 문제', 23, 4),
       ('5강: 2차 캐시와 성능 최적화 전략', 23, 5);

-- course 24 : Jenkins 기반 CI/CD 자동화 구축 (content_id 116~120)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: Jenkins 설치와 파이프라인 개념', 24, 1),
       ('2강: Freestyle vs Pipeline Job 비교', 24, 2),
       ('3강: GitHub Webhook 연동과 자동 빌드 설정', 24, 3),
       ('4강: Docker 이미지 빌드 및 푸시 자동화', 24, 4),
       ('5강: 배포 파이프라인 완성과 슬랙 알림 연동', 24, 5);

-- course 25 : 컴퓨터 구조와 운영체제 핵심 이론 (content_id 121~125) -- PRIVATE
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: 컴퓨터 구성 요소와 폰 노이만 구조', 25, 1),
       ('2강: CPU 명령어 사이클과 인터럽트', 25, 2),
       ('3강: 메모리 계층 구조와 캐시 원리', 25, 3),
       ('4강: 프로세스와 스레드, 스케줄링 알고리즘', 25, 4),
       ('5강: 데드락과 동기화 기법 (뮤텍스 / 세마포어)', 25, 5);


-- ========================================================
-- [Content Histories] 수강 진도 이력 데이터
-- content_id 매핑:
--   course 1 (Java)        : 1~5
--   course 2 (MySQL)       : 6~10
--   course 3 (Spring Boot) : 11~15
--   course 13 (알고리즘)   : 61~65
-- ========================================================

-- 학생 1의 1번 강의(Java) 수강 내역 → 5강 중 1, 2강 완료
INSERT INTO content_histories (enrollment_id, content_id, is_completed, last_date)
VALUES (1, 1, TRUE, CURRENT_TIMESTAMP),
       (1, 2, TRUE, CURRENT_TIMESTAMP),
       (1, 3, FALSE, CURRENT_TIMESTAMP),
       (1, 4, FALSE, CURRENT_TIMESTAMP),
       (1, 5, FALSE, CURRENT_TIMESTAMP);

-- 학생 1의 3번 강의(Spring Boot) 수강 내역 → 5강 모두 완료
INSERT INTO content_histories (enrollment_id, content_id, is_completed, last_date)
VALUES (3, 11, TRUE, CURRENT_TIMESTAMP),
       (3, 12, TRUE, CURRENT_TIMESTAMP),
       (3, 13, TRUE, CURRENT_TIMESTAMP),
       (3, 14, TRUE, CURRENT_TIMESTAMP),
       (3, 15, TRUE, CURRENT_TIMESTAMP);

-- 학생 2의 1번 강의(Java) 수강 내역 → 5강 중 1강 완료
INSERT INTO content_histories (enrollment_id, content_id, is_completed, last_date)
VALUES (5, 1, TRUE, CURRENT_TIMESTAMP),
       (5, 2, FALSE, CURRENT_TIMESTAMP),
       (5, 3, FALSE, CURRENT_TIMESTAMP),
       (5, 4, FALSE, CURRENT_TIMESTAMP),
       (5, 5, FALSE, CURRENT_TIMESTAMP);

-- 학생 2의 13번 강의(알고리즘) 수강 내역 → 5강 중 1강 완료
INSERT INTO content_histories (enrollment_id, content_id, is_completed, last_date)
VALUES (7, 61, TRUE, CURRENT_TIMESTAMP),
       (7, 62, FALSE, CURRENT_TIMESTAMP),
       (7, 63, FALSE, CURRENT_TIMESTAMP),
       (7, 64, FALSE, CURRENT_TIMESTAMP),
       (7, 65, FALSE, CURRENT_TIMESTAMP);


-- ========================================================
-- [추가] 학생 3 (lxplxp) 의 수강 데이터 (상세 조회 테스트용)
-- ========================================================

-- [Enrollments] 학생 3의 수강 신청 내역 (총 4개)
INSERT INTO enrollments (student_id, course_id, status, progress_rate)
VALUES (3, 1, 'ACTIVE', 20),  -- enrollment_id=9  : Java 강의 진행 중 (5강 중 1강 완료)
       (3, 3, 'ACTIVE', 100), -- enrollment_id=10 : Spring Boot 완강 (5강 전부 완료)
       (3, 13, 'ACTIVE', 0),  -- enrollment_id=11 : 알고리즘 신규 신청 (학습 이력 없음)
       (3, 2, 'CANCELED', 0);
-- enrollment_id=12 : MySQL 수강 취소 (403 테스트용)

-- [Content Histories] 학생 3의 수강 진도 이력
-- 학생 3 → 1번 강의(Java): 5강 중 1강만 완료
INSERT INTO content_histories (enrollment_id, content_id, is_completed, last_date)
VALUES (9, 1, TRUE, CURRENT_TIMESTAMP),
       (9, 2, FALSE, CURRENT_TIMESTAMP),
       (9, 3, FALSE, CURRENT_TIMESTAMP),
       (9, 4, FALSE, CURRENT_TIMESTAMP),
       (9, 5, FALSE, CURRENT_TIMESTAMP);

-- 학생 3 → 3번 강의(Spring Boot): 5강 모두 완료 (완강)
INSERT INTO content_histories (enrollment_id, content_id, is_completed, last_date)
VALUES (10, 11, TRUE, CURRENT_TIMESTAMP),
       (10, 12, TRUE, CURRENT_TIMESTAMP),
       (10, 13, TRUE, CURRENT_TIMESTAMP),
       (10, 14, TRUE, CURRENT_TIMESTAMP),
       (10, 15, TRUE, CURRENT_TIMESTAMP);