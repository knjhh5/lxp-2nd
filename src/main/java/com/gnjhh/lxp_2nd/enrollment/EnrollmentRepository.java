package com.gnjhh.lxp_2nd.enrollment;

import com.gnjhh.lxp_2nd.enrollment.domain.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
