-- ========================================================
-- 1. 기존 테이블 삭제 (외래키 의존성 관계 때문에 자식 테이블부터 삭제)
-- ========================================================
DROP TABLE IF EXISTS content_histories;
DROP TABLE IF EXISTS contents;
DROP TABLE IF EXISTS enrollments;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS admins;
DROP TABLE IF EXISTS members;


-- ========================================================
-- 2. 테이블 구조 생성 (DDL)
-- ========================================================

-- Member(회원) 테이블
CREATE TABLE members (
                         id            BIGINT       AUTO_INCREMENT PRIMARY KEY,
                         login_id      VARCHAR(50)  NOT NULL UNIQUE,                                       -- ERD 반영: UNIQUE 추가
                         password_hash VARCHAR(200)  NOT NULL,
                         nickname      VARCHAR(8)   NOT NULL UNIQUE,                                       -- ERD 반영: varchar(8)로 변경
                         user_type     ENUM('STUDENT', 'INSTRUCTOR') NOT NULL DEFAULT 'STUDENT',
                         created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updated_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         deleted_at    TIMESTAMP    NULL DEFAULT NULL
);

-- Admin(관리자) 테이블
CREATE TABLE admins (
                        id            BIGINT       AUTO_INCREMENT PRIMARY KEY,
                        admin_id      VARCHAR(50)  NOT NULL UNIQUE,
                        password_hash VARCHAR(200) NOT NULL,
                        created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Course(강의) 테이블
CREATE TABLE courses (
                         id            BIGINT        AUTO_INCREMENT PRIMARY KEY,
                         title         VARCHAR(100)  NOT NULL,
                         instructor_id BIGINT        NOT NULL,
                         description   TEXT          NOT NULL,                                              -- ERD 반영: NULL → NOT NULL 변경
                         capacity      INT           NOT NULL,
                         status        ENUM('PRIVATE', 'PUBLIC', 'REJECTED') NOT NULL DEFAULT 'PRIVATE',
                         created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updated_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         FOREIGN KEY (instructor_id) REFERENCES members(id),
                         CONSTRAINT chk_course_capacity CHECK (capacity >= 1)
);

-- Enrollment(수강 신청) 테이블
CREATE TABLE enrollments (
                             id            BIGINT    AUTO_INCREMENT PRIMARY KEY,
                             student_id    BIGINT    NOT NULL,
                             course_id     BIGINT    NOT NULL,
                             status        ENUM('ACTIVE', 'CANCELED') NOT NULL DEFAULT 'ACTIVE',
                             progress_rate INT       NOT NULL DEFAULT 0,
                             created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                            -- 핵심: ACTIVE일 때만 유니크 키 값을 가짐
                             active_unique_key VARCHAR(50)
                                 GENERATED ALWAYS AS (
                                     CASE WHEN status = 'ACTIVE'
                                              THEN CONCAT(student_id, '-', course_id)
                                          ELSE NULL
                                         END
                                     ) VIRTUAL,

                             FOREIGN KEY (student_id) REFERENCES members(id),
                             FOREIGN KEY (course_id)  REFERENCES courses(id),
                             CONSTRAINT chk_progress_rate CHECK (progress_rate BETWEEN 0 AND 100),
                             CONSTRAINT uq_active_enrollment UNIQUE (active_unique_key)
);

-- Content(콘텐츠) 테이블
CREATE TABLE contents (
                          id            BIGINT       AUTO_INCREMENT PRIMARY KEY,
                          content_title VARCHAR(100) NOT NULL,
                          course_id     BIGINT       NOT NULL,
                          order_index   INT          NOT NULL,
                          FOREIGN KEY (course_id) REFERENCES courses(id)
);

-- ContentHistory(수강 이력) 테이블
CREATE TABLE content_histories (
                                   id            BIGINT    AUTO_INCREMENT PRIMARY KEY,
                                   enrollment_id BIGINT    NOT NULL,
                                   content_id    BIGINT    NOT NULL,
                                   is_completed  BOOLEAN   NOT NULL DEFAULT FALSE,
                                   last_date     TIMESTAMP NULL DEFAULT NULL,
                                   FOREIGN KEY (enrollment_id) REFERENCES enrollments(id),
                                   FOREIGN KEY (content_id)    REFERENCES contents(id),
                                   UNIQUE KEY uk_enrollment_content (enrollment_id, content_id)
);
