package com.gnjhh.lxp_2nd.member;

import com.gnjhh.lxp_2nd.member.dto.MemberCreateRequestDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signup(MemberCreateRequestDto dto, Model model) {
        if (dto.getLoginId() == null || dto.getLoginId().isBlank()) {
            model.addAttribute("errorMessage", "아이디는 필수 입력 항목입니다.");
            return "auth/signup";
        }
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            model.addAttribute("errorMessage", "비밀번호는 필수 입력 항목입니다.");
            return "auth/signup";
        }
        if (dto.getNickname() == null || dto.getNickname().isBlank()) {
            model.addAttribute("errorMessage", "닉네임은 필수 입력 항목입니다.");
            return "auth/signup";
        }
        if (dto.getPassword().length() < 8 || !dto.getPassword().matches(".*[a-zA-Z].*")
                || !dto.getPassword().matches(".*[0-9].*")) {
            model.addAttribute("errorMessage", "비밀번호는 8자 이상이며 영문과 숫자를 포함해야 합니다.");
            return "auth/signup";
        }
        if (dto.getNickname().length() > 8) {
            model.addAttribute("errorMessage", "닉네임은 8자 이하여야 합니다.");
            return "auth/signup";
        }

        try {
            memberService.signup(dto);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/signup";
        }

        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }
}
