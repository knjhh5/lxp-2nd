package com.gnjhh.lxp_2nd.enrollment;

import com.gnjhh.lxp_2nd.enrollment.domain.vo.Status;
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

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
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
