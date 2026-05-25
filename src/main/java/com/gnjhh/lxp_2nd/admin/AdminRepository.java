package com.gnjhh.lxp_2nd.admin;

import com.gnjhh.lxp_2nd.admin.domain.entity.Admin;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByAdminId(String adminId);
}
