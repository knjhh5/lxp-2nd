package com.gnjhh.lxp_2nd.enrollment;

import com.gnjhh.lxp_2nd.course.CourseService;
import com.gnjhh.lxp_2nd.course.dto.CourseDetailDto;
import com.gnjhh.lxp_2nd.course.dto.CourseDetailResponse;
import com.gnjhh.lxp_2nd.enrollment.domain.entity.Enrollment;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final CourseService courseService;

    public EnrollmentController(EnrollmentService enrollmentService,
            CourseService courseService) {
        this.enrollmentService = enrollmentService;
        this.courseService = courseService;
    }

    @GetMapping("/course/member/detail")
    public String myEnrollmentDetail(
            @ModelAttribute("enrollmentId") Long enrollmentId,
            Model model) {

        if (enrollmentId != null && enrollmentId != 0L) {
            Enrollment enrollment = enrollmentService.getEnrollmentById(enrollmentId);
            int totalCount = enrollment.getCourse().getContents().size();

            model.addAttribute("enrollment", enrollment);
            model.addAttribute("completedCount", 0);
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("completedContentIds", java.util.List.of());
        }

        return "course/member/detail";
    }


    @PostMapping("/enrollments")
    public String enrollcourse(@RequestParam("courseId") Long courseId, HttpSession session,
            RedirectAttributes rttr, Model model) {

        Long studentId = (Long) session.getAttribute("loginMemberId");

        if (studentId == null) {
            rttr.addFlashAttribute("errorMessage", "로그인이 필요한 서비스입니다.");
            return "redirect:/auth/login";

        }

        try {
            Enrollment enrollment = enrollmentService.enroll(studentId, courseId);
            rttr.addFlashAttribute("message", "수강 신청이 완료되었습니다.");
            rttr.addFlashAttribute("enrollmentId", enrollment.getId());

            return "redirect:/course/member/detail";

        } catch (IllegalArgumentException e) {
            rttr.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/error/404";

        } catch (IllegalStateException e) {
            String msg = e.getMessage();
            if (msg.contains("이미 신청한 강의입니다.")) {
                rttr.addFlashAttribute("errorMessage", msg);
                return "redirect:/course/member/home";
            }

            CourseDetailResponse dto = courseService.getCourseDetail(courseId);
            model.addAttribute("course", dto);
            model.addAttribute("errorMessage", msg);
            return "course/detail";

        } catch (DataIntegrityViolationException e) {
            rttr.addFlashAttribute("errorMessage", "이미 신청한 강의입니다.");
            return "redirect:/course/member/home";
        }


    }


}
