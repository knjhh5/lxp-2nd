package com.gnjhh.lxp_2nd.course;

import com.gnjhh.lxp_2nd.course.domain.entity.Course;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public String getCourses(Model model) {
        List<Course> courses = courseService.findPublicCourses();
        model.addAttribute("courses", courses);
        return "course/home";
    }
}
