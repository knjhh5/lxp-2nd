package com.gnjhh.lxp_2nd.course;

import com.gnjhh.lxp_2nd.course.domain.entity.Course;
import com.gnjhh.lxp_2nd.course.domain.vo.Status;
import com.gnjhh.lxp_2nd.course.dto.CourseListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query(
            value = """
                    select new com.gnjhh.lxp_2nd.course.dto.CourseListResponseDto(
                        course.id,
                        course.title,
                        instructor.nickname,
                        course.description,
                        course.capacity,
                        count(enrollment.id)
                    )
                    from Course course
                    join course.instructor instructor
                    left join Enrollment enrollment
                        on enrollment.course = course and enrollment.status = :enrollmentStatus
                    where course.status = :courseStatus
                    group by course.id, course.title, instructor.nickname,
                        course.description, course.capacity, course.createdAt
                    order by course.createdAt desc, course.id desc
                    """,
            countQuery = """
                    select count(course)
                    from Course course
                    where course.status = :courseStatus
                    """)
    Page<CourseListResponseDto> findPublicCoursesOrderByLatest(
            @Param("courseStatus") Status courseStatus,
            @Param("enrollmentStatus")
                    com.gnjhh.lxp_2nd.enrollment.domain.vo.Status enrollmentStatus,
            Pageable pageable);

    @Query(
            value = """
                    select new com.gnjhh.lxp_2nd.course.dto.CourseListResponseDto(
                        course.id,
                        course.title,
                        instructor.nickname,
                        course.description,
                        course.capacity,
                        count(enrollment.id)
                    )
                    from Course course
                    join course.instructor instructor
                    left join Enrollment enrollment
                        on enrollment.course = course and enrollment.status = :enrollmentStatus
                    where course.status = :courseStatus
                    group by course.id, course.title, instructor.nickname,
                        course.description, course.capacity, course.createdAt
                    order by count(enrollment.id) desc, course.createdAt desc, course.id desc
                    """,
            countQuery = """
                    select count(course)
                    from Course course
                    where course.status = :courseStatus
                    """)
    Page<CourseListResponseDto> findPublicCoursesOrderByPopularity(
            @Param("courseStatus") Status courseStatus,
            @Param("enrollmentStatus")
                    com.gnjhh.lxp_2nd.enrollment.domain.vo.Status enrollmentStatus,
            Pageable pageable);
}
