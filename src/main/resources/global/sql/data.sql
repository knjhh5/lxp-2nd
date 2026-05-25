-- ========================================================
-- 3. 페이지네이션 및 MVP 검증용 대용량 데이터 삽입 (DML)
-- ========================================================

-- [Members] 데이터 생성 (nickname varchar(8) 제약 조건 반영)
INSERT INTO members (login_id, password_hash, nickname, user_type)
VALUES ('student1', 'hashed_pass_capacity_test_1111111111111111', '김수강', 'STUDENT'),
       ('student2', 'hashed_pass_capacity_test_2222222222222222', '박공부', 'STUDENT'),
       ('lxp', '$2a$10$bObgMV/G1pic.zuKzwVy4eRdAEeUow1/yTxcVQqPm9yF36wctnpju', 'lxplxp', 'STUDENT'),
       ('instructor1', 'hashed_pass_capacity_test_333333333333333', '이강사', 'INSTRUCTOR'),
       ('instructor2', 'hashed_pass_capacity_test_444444444444444', '최티처', 'INSTRUCTOR');

-- [Admins] 데이터 생성
INSERT INTO admins (admin_id, password_hash)
VALUES ('admin', '$2a$10$IIZAZ8LoIalxdfrSdSVxMeNtKHnVcjmxvDoKvAHWgdQm/.HiS5kXS');

-- [Courses] 대용량 페이지네이션용 강의 데이터 (총 25개)
INSERT INTO courses (title, instructor_id, description, capacity, status)
VALUES ('Java 웹 개발 마스터 클래스', 3, '자바 백엔드 개발자의 필수 코스입니다.', 30, 'PUBLIC'),
       ('MySQL 데이터베이스 모델링 기초', 3, 'ERD 설계부터 정규화까지 배웁니다.', 50, 'PUBLIC'),
       ('Spring Boot 애플리케이션 실전', 3, '스프링 부트로 그럴듯한 웹 서비스를 만듭니다.', 20, 'PUBLIC'),
       ('HTML/CSS 웹 표준 레이아웃', 4, '퍼블리싱의 기초를 다지는 강의', 100, 'PUBLIC'),
       ('JavaScript 핵심 개념과 DOM 제어', 4, '모던 자바스크립트 기본기 다지기', 40, 'PUBLIC'),
       ('React.js 프론트엔드 마스터', 4, '리액트 컴포넌트 설계와 상태 관리', 25, 'PUBLIC'),
       ('Next.js SSR 완벽 가이드', 4, '서버 사이드 렌더링과 SEO 최적화', 30, 'PUBLIC'),
       ('TypeScript 타입 시스템 이해', 3, '안정적인 자바스크립트 코딩을 위한 타입스크립트', 45, 'PUBLIC'),
       ('AWS 클라우드 인프라 구축 기초', 3, 'EC2, RDS, S3를 활용한 서비스 배포', 15, 'PUBLIC'),
       ('Docker와 쿠버네티스 컨테이너 기술', 3, 'DevOps 입문을 위한 컨테이너 기초', 20, 'PUBLIC'),
       ('Python 데이터 분석 실무', 4, '판다스와 넘파이를 활용한 데이터 전처리', 60, 'PUBLIC'),
       ('공공데이터 활용 AI 예측 모델링', 4, '머신러닝 기초 알고리즘 배우기', 25, 'PUBLIC'),
       ('알고리즘 코딩 테스트 합격 패스', 3, '자바 실전 알고리즘 문제 풀이', 80, 'PUBLIC'),
       ('Git & GitHub 협업 워크플로우', 3, '브랜치 전략과 PR 기반 협업 완벽 마스터', 200, 'PUBLIC'),
       ('Linux 서버 명령어 및 쉘 스크립트', 3, '리눅스 환경 실무 핵심 가이드', 35, 'PUBLIC'),
       ('Redis 캐시 서버 활용 전략', 3, '조회 성능 개선을 위한 레디스 활용법', 30, 'PUBLIC'),
       ('Spring Security 보안 심화', 4, '안전한 인증과 인가를 위한 고급 과정', 15, 'PRIVATE'),
       ('쉽게 배우는 블록체인 입문', 4, '내용 부실로 승인 반려된 강좌 예시', 10, 'REJECTED'),
       ('Node.js 익스프레스 서버 구축', 4, '자바스크립트로 백엔드 개발하기', 40, 'PUBLIC'),
       ('GraphQL API 디자인과 실무', 4, 'REST API를 넘어선 새로운 데이터 쿼리 언어', 30, 'PUBLIC'),
       ('Vue.js 3 시작하기', 4, '쉽고 빠른 프론트엔드 프레임워크', 50, 'PUBLIC'),
       ('Kotlin 스프링 부트 웹 개발', 3, '코틀린으로 작성하는 모던 백엔드', 25, 'PUBLIC'),
       ('JPA 자바 ORM 표준 프로그래밍', 3, '엔티티 매핑부터 성능 최적화까지', 40, 'PUBLIC'),
       ('Jenkins 기반 CI/CD 자동화 구축', 3, '지속적 통합과 배포 파이프라인 만들기', 20, 'PUBLIC'),
       ('컴퓨터 구조와 운영체제 핵심 이론', 3, '개발자 면접 대비 CS 기초 지식', 100, 'PRIVATE');


-- [Enrollments] 수강 신청 내역 (총 8개)
INSERT INTO enrollments (student_id, course_id, status, progress_rate)
VALUES (1, 1, 'ACTIVE', 66),  -- 수강중 (3강 중 2강 완료)
       (1, 2, 'ACTIVE', 0),   -- 신규 신청 (진도율 0)
       (1, 3, 'ACTIVE', 100), -- 완강 (진도율 100)
       (1, 4, 'CANCELED', 0), -- 수강 취소 내역
       (2, 1, 'ACTIVE', 33),  -- 수강중 (3강 중 1강 완료)
       (2, 3, 'ACTIVE', 0),   -- 신규 신청
       (2, 13, 'ACTIVE', 25), -- 수강중 (4강 중 1강 완료)
       (2, 14, 'CANCELED', 0);
-- 수강 취소 내역


-- [Contents] 주차별 컨텐츠 데이터 (총 14개)
-- 1번 강의 (Java)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: 개발 환경 세팅 및 Hello World', 1, 1),
       ('2강: 변수와 제어문 마스터하기', 1, 2),
       ('3강: 객체지향 프로그래밍의 이해', 1, 3);

-- 2번 강의 (MySQL)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: 데이터베이스 개요 및 설치', 2, 1),
       ('2강: DDL, DML 쿼리 뽀개기', 2, 2),
       ('3강: 서브쿼리와 조인(JOIN) 성능 최적화', 2, 3);

-- 3번 강의 (Spring Boot)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: 스프링 프레임워크 vs 스프링 부트', 3, 1),
       ('2강: REST API 컨트롤러 설계', 3, 2),
       ('3강: JPA 연동 및 비즈니스 로직 구현', 3, 3),
       ('4강: AWS 인프라에 배포하기', 3, 4);

-- 13번 강의 (알고리즘)
INSERT INTO contents (content_title, course_id, order_index)
VALUES ('1강: 시간 복잡도와 배열', 13, 1),
       ('2강: 스택과 큐 실전 문제 풀이', 13, 2),
       ('3강: 깊이 우선 탐색(DFS) 완벽 이해', 13, 3),
       ('4강: 너비 우선 탐색(BFS) 완벽 이해', 13, 4);


-- [Content Histories] 수강 진도 이력 데이터 (총 12개)
-- 학생 1의 1번 강의 수강 내역 → 3강 중 1, 2강 완료
INSERT INTO content_histories (enrollment_id, content_id, is_completed, last_date)
VALUES (1, 1, TRUE, CURRENT_TIMESTAMP),
       (1, 2, TRUE, CURRENT_TIMESTAMP),
       (1, 3, FALSE, CURRENT_TIMESTAMP);

-- 학생 1의 3번 강의 수강 내역 → 4강 모두 완료
INSERT INTO content_histories (enrollment_id, content_id, is_completed, last_date)
VALUES (3, 7, TRUE, CURRENT_TIMESTAMP),
       (3, 8, TRUE, CURRENT_TIMESTAMP),
       (3, 9, TRUE, CURRENT_TIMESTAMP),
       (3, 10, TRUE, CURRENT_TIMESTAMP);

-- 학생 2의 1번 강의 수강 내역 → 3강 중 1강 완료
INSERT INTO content_histories (enrollment_id, content_id, is_completed, last_date)
VALUES (5, 1, TRUE, CURRENT_TIMESTAMP),
       (5, 2, FALSE, CURRENT_TIMESTAMP),
       (5, 3, FALSE, CURRENT_TIMESTAMP);

-- 학생 2의 13번 강의 수강 내역 → 4강 중 1강 완료
INSERT INTO content_histories (enrollment_id, content_id, is_completed, last_date)
VALUES (7, 11, TRUE, CURRENT_TIMESTAMP),
       (7, 12, FALSE, CURRENT_TIMESTAMP);