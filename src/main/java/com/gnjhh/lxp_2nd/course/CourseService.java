package com.gnjhh.lxp_2nd.course;

import com.gnjhh.lxp_2nd.content.ContentRepository;
import com.gnjhh.lxp_2nd.content.domain.entity.Content;
import com.gnjhh.lxp_2nd.course.domain.entity.Course;
import com.gnjhh.lxp_2nd.course.dto.CourseDetailResponse;
import com.gnjhh.lxp_2nd.global.exception.CourseNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final ContentRepository contentRepository;

    public CourseService(CourseRepository courseRepository,
            ContentRepository contentRepository) {
        this.courseRepository = courseRepository;
        this.contentRepository = contentRepository;
    }

    @Transactional(readOnly = true)
    public CourseDetailResponse getCourseDetail(Long courseId) {
        // 강의 조회
        Course course = courseRepository.findById(courseId)
                .orElseThrow(CourseNotFoundException::new);

        // 해당 강의의 콘텐츠 목록 조회
        List<Content> contents = contentRepository.findByCourseIdOrderByOrderIndexAsc(courseId);

        return CourseDetailResponse.of(course, contents);
    }
}
