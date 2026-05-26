package com.gnjhh.lxp_2nd.course;
import com.gnjhh.lxp_2nd.course.domain.vo.Status;
import com.gnjhh.lxp_2nd.course.dto.CourseListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.gnjhh.lxp_2nd.content.ContentRepository;
import com.gnjhh.lxp_2nd.content.domain.entity.Content;
import com.gnjhh.lxp_2nd.content.dto.ContentProgressResponse;
import com.gnjhh.lxp_2nd.contenthistory.ContentHistoryRepository;
import com.gnjhh.lxp_2nd.contenthistory.domain.entity.ContentHistory;
import com.gnjhh.lxp_2nd.course.domain.entity.Course;
import com.gnjhh.lxp_2nd.course.dto.CourseDetailResponse;
import com.gnjhh.lxp_2nd.course.dto.MyCourseDetailResponse;
import com.gnjhh.lxp_2nd.enrollment.EnrollmentRepository;
import com.gnjhh.lxp_2nd.global.exception.CourseNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CourseService {

    private static final String POPULAR_SORT = "popular";

    private final CourseRepository courseRepository;
    private final ContentRepository contentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ContentHistoryRepository contentHistoryRepository;

    public CourseService(CourseRepository courseRepository, ContentRepository contentRepository,
            EnrollmentRepository enrollmentRepository,
            ContentHistoryRepository contentHistoryRepository) {
        this.courseRepository = courseRepository;
        this.contentRepository = contentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.contentHistoryRepository = contentHistoryRepository;
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

    public MyCourseDetailResponse getMyCourseDetail(Long loginMemberId, Long courseId) {
        // 강의 조회
        Course course = courseRepository.findById(courseId)
                .orElseThrow(CourseNotFoundException::new);

        // 본인이 수강 신청한 강의인지 확인 (수강 취소이면 안됨)
        if (!enrollmentRepository.existsActiveEnrollment(loginMemberId, courseId)) {
            throw new AccessDeniedException("신청하지 않은 강의입니다");
        }

        // 강의 콘텐츠 목록 조회
        List<Content> contents =
                contentRepository.findByCourseIdOrderByOrderIndexAsc(course.getId());

        // 수강 이력에서 완료한 콘텐츠 ID set 생성
        List<ContentHistory> histories = contentHistoryRepository
                .findActiveByStudentIdAndCourseId(loginMemberId, courseId);
        Set<Long> completedContentIds = histories.stream()
                .filter(ContentHistory::isCompleted)
                .map(history -> history.getContent().getId())
                .collect(Collectors.toSet());

        // 콘텐츠별 완료 여부 매핑
        List<ContentProgressResponse> contentResponses = contents.stream()
                .map(content -> ContentProgressResponse.of(
                        content.getId(),
                        content.getContentTitle(),
                        content.getOrderIndex(),
                        completedContentIds.contains(content.getId()) // 콘텐츠 재생 여부
                ))
                .collect(Collectors.toList());

        // 진도율 계산
        int progressRate = calculateProgressRate(contents.size(), completedContentIds.size());

        return MyCourseDetailResponse.of(
                course.getId(),
                course.getTitle(),
                course.getInstructor().getNickname(),
                course.getDescription(),
                progressRate,
                contentResponses
        );
    }

    private int calculateProgressRate(int totalCount, int completedCount) {
        if (totalCount == 0) {
            return 0;
        }
        return (int) Math.round((double) completedCount / totalCount * 100);
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
