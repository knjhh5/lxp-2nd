package com.gnjhh.lxp_2nd.course;

import com.gnjhh.lxp_2nd.course.domain.entity.Course;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import com.gnjhh.lxp_2nd.course.domain.vo.Status;
import com.gnjhh.lxp_2nd.course.dto.CourseAdminListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Course c where c.id = :id")
    Optional<Course> findByIdWithLock(@Param("id") Long id);

    @Query("""
                SELECT c.id AS courseId,
                       c.title AS title,
                       m.nickname AS instructorName,
                       c.capacity AS capacity,
                       c.status AS status,
                       COUNT(CASE WHEN e.status = 'ACTIVE' THEN 1 END) AS activeCount,
                       COUNT(CASE WHEN e.status = 'CANCELED' THEN 1 END) AS canceledCount
                FROM Course c
                JOIN c.instructor m
                LEFT JOIN Enrollment e ON e.course = c
                WHERE (:status IS NULL OR c.status = :status)
                AND m.userType = 'INSTRUCTOR'
                GROUP BY c.id, c.title, m.nickname, c.capacity, c.status
            """)
    Page<CourseAdminListResponseDto> findCoursesWithEnrollmentCount(@Param("status") Status status,
            Pageable pageable);
}
