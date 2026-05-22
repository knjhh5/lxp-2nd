package com.gnjhh.lxp_2nd.course;

import com.gnjhh.lxp_2nd.course.domain.vo.Status;
import com.gnjhh.lxp_2nd.course.dto.CourseListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {

    private static final String POPULAR_SORT = "popular";

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional(readOnly = true)
    public Page<CourseListResponseDto> findPublicCourses(String sort, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        if (POPULAR_SORT.equals(sort)) {
            return courseRepository.findPublicCoursesOrderByPopularity(
                    Status.PUBLIC,
                    com.gnjhh.lxp_2nd.enrollment.domain.vo.Status.ACTIVE,
                    pageable);
        }
        return courseRepository.findPublicCoursesOrderByLatest(
                Status.PUBLIC,
                com.gnjhh.lxp_2nd.enrollment.domain.vo.Status.ACTIVE,
                pageable);
    }
}
