package com.gnjhh.lxp_2nd.contenthistory;

import com.gnjhh.lxp_2nd.contenthistory.domain.entity.ContentHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContentHistoryRepository extends JpaRepository<ContentHistory, Long> {

    @Query("""
            SELECT ch
            FROM ContentHistory ch
            JOIN ch.enrollment e
            WHERE e.student.id = :studentId
              AND e.course.id = :courseId
              AND e.status = com.gnjhh.lxp_2nd.enrollment.domain.vo.Status.ACTIVE
            """)
    List<ContentHistory> findActiveByStudentIdAndCourseId(
            @Param("studentId") Long studentId,
            @Param("courseId") Long courseId
    );

}
