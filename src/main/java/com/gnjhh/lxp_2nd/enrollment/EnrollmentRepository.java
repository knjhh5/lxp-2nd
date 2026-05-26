package com.gnjhh.lxp_2nd.enrollment;

import com.gnjhh.lxp_2nd.enrollment.domain.entity.Enrollment;
import com.gnjhh.lxp_2nd.enrollment.domain.vo.Status;
import java.util.Optional;
import com.gnjhh.lxp_2nd.enrollment.domain.vo.Status;
import com.gnjhh.lxp_2nd.enrollment.dto.EnrollmentListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // 수강인원 체크 로직
    int countByCourseIdAndStatus(Long courseId, Status status);

    // 중복 재신청 체크 로직
    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);


    @Query(
            value = """
                    select new com.gnjhh.lxp_2nd.enrollment.dto.EnrollmentListResponseDto(
                        enrollment.id,
                        course.id,
                        course.title,
                        instructor.nickname,
                        case
                            when (
                                select count(content.id)
                                from Content content
                                where content.course = course
                            ) = 0 then 0
                            else (
                                (
                                    select count(completedHistory.id)
                                    from ContentHistory completedHistory
                                    where completedHistory.enrollment = enrollment
                                        and completedHistory.completed = true
                                ) * 100 / (
                                    select count(content.id)
                                    from Content content
                                    where content.course = course
                                )
                            )
                        end
                    )
                    from Enrollment enrollment
                    join enrollment.course course
                    join course.instructor instructor
                    where enrollment.student.id = :studentId
                        and enrollment.status = :status
                        and (
                            :learningStatus = 'all'
                            or (:learningStatus = 'ongoing' and (
                                case
                                    when (
                                        select count(content.id)
                                        from Content content
                                        where content.course = course
                                    ) = 0 then 0
                                    else (
                                        (
                                            select count(completedHistory.id)
                                            from ContentHistory completedHistory
                                            where completedHistory.enrollment = enrollment
                                                and completedHistory.completed = true
                                        ) * 100 / (
                                            select count(content.id)
                                            from Content content
                                            where content.course = course
                                        )
                                    )
                                end
                            ) < 100)
                            or (:learningStatus = 'done' and (
                                case
                                    when (
                                        select count(content.id)
                                        from Content content
                                        where content.course = course
                                    ) = 0 then 0
                                    else (
                                        (
                                            select count(completedHistory.id)
                                            from ContentHistory completedHistory
                                            where completedHistory.enrollment = enrollment
                                                and completedHistory.completed = true
                                        ) * 100 / (
                                            select count(content.id)
                                            from Content content
                                            where content.course = course
                                        )
                                    )
                                end
                            ) = 100)
                        )
                    order by coalesce((
                            select max(contentHistory.lastDate)
                            from ContentHistory contentHistory
                            where contentHistory.enrollment = enrollment
                        ), enrollment.createdAt) desc,
                        enrollment.id desc
                    """,
            countQuery = """
                    select count(enrollment)
                    from Enrollment enrollment
                    join enrollment.course course
                    where enrollment.student.id = :studentId
                        and enrollment.status = :status
                        and (
                            :learningStatus = 'all'
                            or (:learningStatus = 'ongoing' and (
                                case
                                    when (
                                        select count(content.id)
                                        from Content content
                                        where content.course = course
                                    ) = 0 then 0
                                    else (
                                        (
                                            select count(completedHistory.id)
                                            from ContentHistory completedHistory
                                            where completedHistory.enrollment = enrollment
                                                and completedHistory.completed = true
                                        ) * 100 / (
                                            select count(content.id)
                                            from Content content
                                            where content.course = course
                                        )
                                    )
                                end
                            ) < 100)
                            or (:learningStatus = 'done' and (
                                case
                                    when (
                                        select count(content.id)
                                        from Content content
                                        where content.course = course
                                    ) = 0 then 0
                                    else (
                                        (
                                            select count(completedHistory.id)
                                            from ContentHistory completedHistory
                                            where completedHistory.enrollment = enrollment
                                                and completedHistory.completed = true
                                        ) * 100 / (
                                            select count(content.id)
                                            from Content content
                                            where content.course = course
                                        )
                                    )
                                end
                            ) = 100)
                        )
                    """)
    Page<EnrollmentListResponseDto> findMyEnrollments(
            @Param("studentId") Long studentId,
            @Param("status") Status status,
            @Param("learningStatus") String learningStatus,
            Pageable pageable);

    @Query(
            """
                    select count(enrollment)
                    from Enrollment enrollment
                    join enrollment.course course
                    where enrollment.student.id = :studentId
                        and enrollment.status = :status
                        and (
                            :learningStatus = 'all'
                            or (:learningStatus = 'ongoing' and (
                                case
                                    when (
                                        select count(content.id)
                                        from Content content
                                        where content.course = course
                                    ) = 0 then 0
                                    else (
                                        (
                                            select count(completedHistory.id)
                                            from ContentHistory completedHistory
                                            where completedHistory.enrollment = enrollment
                                                and completedHistory.completed = true
                                        ) * 100 / (
                                            select count(content.id)
                                            from Content content
                                            where content.course = course
                                        )
                                    )
                                end
                            ) < 100)
                            or (:learningStatus = 'done' and (
                                case
                                    when (
                                        select count(content.id)
                                        from Content content
                                        where content.course = course
                                    ) = 0 then 0
                                    else (
                                        (
                                            select count(completedHistory.id)
                                            from ContentHistory completedHistory
                                            where completedHistory.enrollment = enrollment
                                                and completedHistory.completed = true
                                        ) * 100 / (
                                            select count(content.id)
                                            from Content content
                                            where content.course = course
                                        )
                                    )
                                end
                            ) = 100)
                        )
                    """)
    long countMyEnrollmentsByLearningStatus(
            @Param("studentId") Long studentId,
            @Param("status") Status status,
            @Param("learningStatus") String learningStatus);

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

