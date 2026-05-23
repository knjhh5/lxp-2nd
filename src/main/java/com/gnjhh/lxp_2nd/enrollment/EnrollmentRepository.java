package com.gnjhh.lxp_2nd.enrollment;

import com.gnjhh.lxp_2nd.enrollment.domain.entity.Enrollment;
import com.gnjhh.lxp_2nd.enrollment.domain.vo.Status;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // 수강인원 체크 로직
    int countByCourseIdAndStatus(Long courseId, Status status);

    // 중복 재신청 체크 로직
    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);

}

