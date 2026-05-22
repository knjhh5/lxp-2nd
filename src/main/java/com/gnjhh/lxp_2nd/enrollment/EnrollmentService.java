package com.gnjhh.lxp_2nd.enrollment;

import com.gnjhh.lxp_2nd.course.CourseRepository;
import com.gnjhh.lxp_2nd.course.domain.entity.Course;
import com.gnjhh.lxp_2nd.enrollment.domain.entity.Enrollment;
import com.gnjhh.lxp_2nd.enrollment.domain.vo.Status;
import com.gnjhh.lxp_2nd.member.MemberRepository;
import com.gnjhh.lxp_2nd.member.domain.entity.Member;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service

public class EnrollmentService {

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
    public void enroll(Long studentId, Long courseId) {

        Member student = memberRepository.getReferenceById(studentId);

        Course course = courseRepository.findByIdWithLock(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의 입니다."));

        // 총 몇 명이나 강의를 신청했는지 체크하는 로직
        int currentEnrolled = enrollmentRepository.countByCourseIdAndStatus(courseId,
                Status.ACTIVE);
        // 가드 클로즈 사용(자격이 안되는 예외 상황을 메서드 위에서 먼저 검사해서 거르게 하는 구조)
        if (currentEnrolled >= course.getCapacity()) {
            throw new IllegalArgumentException("신청 마감된 강의입니다.");
        }

// 시퀀스 다이어그램과 다른 이유 : 재신청을 추가했기 때문에 데이터를 되살리려면 데이터 알맹이를 꺼내와야 하기 때문에 exists 대신 find를 사용.
        Optional<Enrollment> existingOpt = enrollmentRepository.findByStudentIdAndCourseId(studentId,courseId);

        if (existingOpt.isPresent()) {
            Enrollment existingEnrollment = existingOpt.get();
            if (existingEnrollment.getStatus() == Status.ACTIVE) {
                throw new IllegalArgumentException("이미 수강 중인 강의 입니다.");
            }
        }
            enrollmentRepository.save(new Enrollment(student,course));
        }
    }

