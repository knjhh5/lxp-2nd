package com.gnjhh.lxp_2nd.enrollment;

import com.gnjhh.lxp_2nd.enrollment.domain.entity.Enrollment;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/members/me/enrollments")
    public String getMyEnrollments(HttpSession session, Model model) {
        Long loginMemberId = (Long) session.getAttribute("loginMemberId");
        if (loginMemberId == null) {
            return "redirect:/auth/login";
        }

        List<Enrollment> enrolledCourses = enrollmentService.findActiveEnrollments(loginMemberId);
        model.addAttribute("enrolledCourses", enrolledCourses);
        return "course/member/home";
    }
}
