package com.gnjhh.lxp_2nd.enrollment;

import com.gnjhh.lxp_2nd.course.CourseService;
import com.gnjhh.lxp_2nd.course.dto.CourseDetailDto;
import jakarta.servlet.http.HttpSession;
import java.util.zip.DataFormatException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/courses/{courseId}")
    public String courseDetail(@PathVariable("courseId") Long courseId, Model model) {
        try {
            CourseDetailDto dto = courseService.getCourseDetail(courseId);
            model.addAttribute("course", dto);
            return "course/detail";
        } catch (IllegalArgumentException e) {
            return "redirect:/error/404";
        }
    }

    @GetMapping("/course/member/detail")
    public String myEnrollmentDetail() {
        return "course/member/detail";
    }


    @PostMapping("/courses/enroll")
    public String enrollcourse(@RequestParam("courseId") Long courseId, HttpSession session,
            RedirectAttributes rttr, Model model) {

        Long studentId = (Long) session.getAttribute("loginMemberId");

        if (studentId == null) {
            rttr.addFlashAttribute("errorMessage", "로그인이 필요한 서비스입니다.");
            return "redirect:/auth/login";

        }

        try {
            enrollmentService.enroll(studentId, courseId);
            rttr.addFlashAttribute("message", "수강신청에 완료되었습니다.");

            model.addAttribute("enrollment", new Object() {
                public int getProgressRate() { return 0; }
                public Object getCourse() {
                    return new Object() {
                        public Long getId() { return courseId; }
                        public String getTitle() { return "수강 신청 완료"; }
                        public String getInstructorName() { return "강사"; }
                        public String getDescription() { return "수강 신청이 완료되었습니다."; }
                        public java.util.List<?> getContents() { return java.util.List.of(); }
                    };
                }
            });
            model.addAttribute("completedCount", 0);
            model.addAttribute("totalCount", 0);
            model.addAttribute("completedContentIds", java.util.List.of());


            return "/course/member/detail";

        } catch (IllegalArgumentException e) {
            rttr.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/error/404";

        }catch (IllegalStateException e){
            String msg = e.getMessage();
            if (msg.contains("이미 신청한 강의입니다.")){
                rttr.addFlashAttribute("errorMassage",msg);
                return "redirect:/course/member/home";
            }
            CourseDetailDto dto = courseService.getCourseDetail(courseId);
            model.addAttribute("course",dto);
            model.addAttribute("errorMessage",msg);
            return "course/detail";

        }catch (DataIntegrityViolationException e){
            rttr.addFlashAttribute("errorMessage","이미 신창한 결과입니다.");
        }
        return "redirect:/course/" + courseId;

    }


}
