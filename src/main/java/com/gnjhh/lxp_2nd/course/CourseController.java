package com.gnjhh.lxp_2nd.course;

import com.gnjhh.lxp_2nd.course.dto.CourseDetailResponse;
import com.gnjhh.lxp_2nd.course.dto.MyCourseDetailResponse;
import com.gnjhh.lxp_2nd.global.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses/{courseId}")
    public String getCourseDetail(@PathVariable("courseId") Long courseId, Model model) {
        CourseDetailResponse response = courseService.getCourseDetail(courseId);
        model.addAttribute("course", response);
        return "course/detail";
    }

    @GetMapping("/members/me/enrollments/{courseId}")
    public String getMyCourseDetail(@PathVariable("courseId") Long courseId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model) {
        Long loginMemberId = userDetails.getMember().getId();
        MyCourseDetailResponse course = courseService.getMyCourseDetail(loginMemberId, courseId);
        model.addAttribute("course", course);
        return "/course/member/detail-debug";
    }

    // 강의 목록 조회 (테스트 시 필요해서 만든 임시 엔드포인트 - 지오님 pr merge 시에 삭제)
    @GetMapping("/courses")
    public String course() {
        return "course/home";
    }
}
