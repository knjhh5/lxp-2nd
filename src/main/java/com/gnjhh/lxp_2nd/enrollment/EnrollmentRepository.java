package com.gnjhh.lxp_2nd.enrollment;

import com.gnjhh.lxp_2nd.enrollment.domain.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query("""
            SELECT COUNT(e) > 0
            FROM Enrollment e
            WHERE e.student.id = :studentId
              AND e.course.id = :courseId
              AND e.status = com.gnjhh.lxp_2nd.enrollment.domain.vo.Status.ACTIVE
            """)
    boolean existsActiveEnrollment(
            @Param("studentId") Long studentId,
            @Param("courseId") Long courseId
    );

}
