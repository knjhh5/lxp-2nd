package com.gnjhh.lxp_2nd.member;

import com.gnjhh.lxp_2nd.member.domain.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByLoginId(String loginId);

    boolean existsByNickname(String nickname);

    Optional<Member> findByLoginId(String loginId);
}
