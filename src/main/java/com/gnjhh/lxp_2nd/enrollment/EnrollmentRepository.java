package com.gnjhh.lxp_2nd.enrollment;

import com.gnjhh.lxp_2nd.enrollment.domain.entity.Enrollment;
import com.gnjhh.lxp_2nd.enrollment.domain.vo.Status;
import com.gnjhh.lxp_2nd.enrollment.dto.EnrollmentListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query(
            value = """
                    select new com.gnjhh.lxp_2nd.enrollment.dto.EnrollmentListResponseDto(
                        enrollment.id,
                        course.id,
                        course.title,
                        instructor.nickname,
                        enrollment.progressRate
                    )
                    from Enrollment enrollment
                    join enrollment.course course
                    join course.instructor instructor
                    left join ContentHistory contentHistory
                        on contentHistory.enrollment = enrollment
                    where enrollment.student.id = :studentId
                        and enrollment.status = :status
                        and (
                            :learningStatus = 'all'
                            or (:learningStatus = 'ongoing' and enrollment.progressRate < 100)
                            or (:learningStatus = 'done' and enrollment.progressRate = 100)
                        )
                    group by enrollment.id, course.id, course.title, instructor.nickname,
                        enrollment.progressRate, enrollment.createdAt
                    order by coalesce(max(contentHistory.lastDate), enrollment.createdAt) desc,
                        enrollment.id desc
                    """,
            countQuery = """
                    select count(enrollment)
                    from Enrollment enrollment
                    where enrollment.student.id = :studentId
                        and enrollment.status = :status
                        and (
                            :learningStatus = 'all'
                            or (:learningStatus = 'ongoing' and enrollment.progressRate < 100)
                            or (:learningStatus = 'done' and enrollment.progressRate = 100)
                        )
                    """)
    Page<EnrollmentListResponseDto> findMyEnrollments(
            @Param("studentId") Long studentId,
            @Param("status") Status status,
            @Param("learningStatus") String learningStatus,
            Pageable pageable);

    long countByStudentIdAndStatusAndProgressRateLessThan(
            Long studentId, Status status, int progressRate);

    long countByStudentIdAndStatusAndProgressRate(
            Long studentId, Status status, int progressRate);
}
