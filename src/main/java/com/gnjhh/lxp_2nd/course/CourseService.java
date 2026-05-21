package com.gnjhh.lxp_2nd.course;

import com.gnjhh.lxp_2nd.course.domain.entity.Course;
import com.gnjhh.lxp_2nd.course.domain.vo.Status;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findPublicCourses() {
        return courseRepository.findByStatus(Status.PUBLIC);
    }
}
