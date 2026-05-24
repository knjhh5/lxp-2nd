package com.gnjhh.lxp_2nd.course;

import com.gnjhh.lxp_2nd.course.dto.CourseDetailResponse;
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
    public String getCourseDetail(@PathVariable Long courseId, Model model) {
        CourseDetailResponse response = courseService.getCourseDetail(courseId);
        model.addAttribute("course", response);
        return "course/detail";
    }

}
