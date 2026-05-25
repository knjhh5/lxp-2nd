package com.gnjhh.lxp_2nd.enrollment;

import com.gnjhh.lxp_2nd.course.CourseRepository;
import com.gnjhh.lxp_2nd.course.domain.entity.Course;
import com.gnjhh.lxp_2nd.enrollment.domain.entity.Enrollment;
import com.gnjhh.lxp_2nd.enrollment.domain.vo.Status;
import com.gnjhh.lxp_2nd.member.MemberRepository;
import com.gnjhh.lxp_2nd.member.domain.entity.Member;
import java.util.Optional;
import com.gnjhh.lxp_2nd.enrollment.dto.EnrollmentListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class EnrollmentService {
    private static final String ONGOING_LEARNING_STATUS = "ongoing";
    private static final String DONE_LEARNING_STATUS = "done";

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final MemberRepository memberRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
            CourseRepository courseRepository,
            MemberRepository memberRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Enrollment enroll(Long studentId, Long courseId) {

        Member student = memberRepository.getReferenceById(studentId);

        Course course = courseRepository.findByIdWithLock(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의 입니다."));
        //vo 객체가 2개 들어가서 충돌이 일어나서 전체 경로를 적어서 분리를 함.
        if (course.getStatus() != com.gnjhh.lxp_2nd.course.domain.vo.Status.PUBLIC) {
            throw new IllegalStateException("수강 신청이 불가능한 강의입니다.");
        }

        // 총 몇 명이나 강의를 신청했는지 체크하는 로직
        int currentEnrolled = enrollmentRepository.countByCourseIdAndStatus(courseId,
                Status.ACTIVE);
        // 가드 클로즈 사용(자격이 안되는 예외 상황을 메서드 위에서 먼저 검사해서 거르게 하는 구조)
        if (currentEnrolled >= course.getCapacity()) {
            throw new IllegalStateException("신청이 마감된 강의입니다.");
        }

// 시퀀스 다이어그램과 다른 이유 : 재신청을 추가했기 때문에 데이터를 되살리려면 데이터 알맹이를 꺼내와야 하기 때문에 exists 대신 find를 사용.
        Optional<Enrollment> existingOpt = enrollmentRepository.findByStudentIdAndCourseId(
                studentId, courseId);

        if (existingOpt.isPresent()) {
            Enrollment existingEnrollment = existingOpt.get();
            if (existingEnrollment.getStatus() == Status.ACTIVE) {
                throw new IllegalStateException("이미 신청한 강의입니다.");
            }
        }
        return enrollmentRepository.save(new Enrollment(student, course));

    }

    @Transactional(readOnly = true)
    public Enrollment getEnrollmentById(Long enrollmentId) {
        return enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("수강 신청 정보가 없습니다."));
    }

    @Transactional(readOnly = true)
    public Page<EnrollmentListResponseDto> findMyEnrollments(
            Long studentId, String learningStatus, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return enrollmentRepository.findMyEnrollments(
                studentId, Status.ACTIVE, learningStatus, pageable);
    }

    @Transactional(readOnly = true)
    public long countOngoingEnrollments(Long studentId) {
        return enrollmentRepository.countMyEnrollmentsByLearningStatus(
                studentId, Status.ACTIVE, ONGOING_LEARNING_STATUS);
    }

    @Transactional(readOnly = true)
    public long countDoneEnrollments(Long studentId) {
        return enrollmentRepository.countMyEnrollmentsByLearningStatus(
                studentId, Status.ACTIVE, DONE_LEARNING_STATUS);
    }
}
