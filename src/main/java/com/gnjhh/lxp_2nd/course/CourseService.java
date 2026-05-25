package com.gnjhh.lxp_2nd.course;

import com.gnjhh.lxp_2nd.content.ContentRepository;
import com.gnjhh.lxp_2nd.course.domain.entity.Course;
import com.gnjhh.lxp_2nd.course.dto.CourseDetailDto;
import com.gnjhh.lxp_2nd.enrollment.EnrollmentRepository;
import com.gnjhh.lxp_2nd.enrollment.domain.vo.Status;
import com.gnjhh.lxp_2nd.member.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {

    private final ContentRepository contentRepository;
    private CourseRepository courseRepository;
    private EnrollmentRepository enrollmentRepository;
    private MemberRepository memberRepository;

    public CourseService(CourseRepository courseRepository,
            EnrollmentRepository enrollmentRepository, MemberRepository memberRepository,
            ContentRepository contentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.memberRepository = memberRepository;
        this.contentRepository = contentRepository;
    }

    @Transactional(readOnly = true)
    public CourseDetailDto getCourseDetail(Long courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의입니다."));

        int enrollCount = enrollmentRepository
                .countByCourseIdAndStatus(courseId, Status.ACTIVE);

        List<CourseDetailDto.ContentDto> contentDtos = contentRepository
                .findByCourseIdOrderByOrderIndex(courseId)
                .stream()
                .map(c -> new CourseDetailDto.ContentDto(c.getContentTitle(), 0))
                .collect(Collectors.toList());

        return new CourseDetailDto(
                course.getId(),
                course.getTitle(),
                course.getInstructor().getNickname(),
                course.getDescription(),
                null,
                enrollCount,
                course.getCapacity(),
                contentDtos

        );
    }


}
