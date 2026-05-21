package com.gnjhh.lxp_2nd.course;

import com.gnjhh.lxp_2nd.course.domain.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
