package com.gnjhh.lxp_2nd.global.security;

import com.gnjhh.lxp_2nd.admin.AdminRepository;
import com.gnjhh.lxp_2nd.admin.domain.entity.Admin;
import com.gnjhh.lxp_2nd.member.MemberRepository;
import com.gnjhh.lxp_2nd.member.domain.entity.Member;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;

    public CustomUserDetailsService(MemberRepository memberRepository,
            AdminRepository adminRepository) {
        this.memberRepository = memberRepository;
        this.adminRepository = adminRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Admin  조회
        Admin admin = adminRepository.findByAdminId(username).orElse(null);
        if (admin != null) {
            return new AdminDetails(admin);
        }

        // Member 조회
        Member member = memberRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다."));

        return new CustomUserDetails(member);
    }
}
