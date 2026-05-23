package com.gnjhh.lxp_2nd.admin;

import com.gnjhh.lxp_2nd.course.domain.vo.Status;
import com.gnjhh.lxp_2nd.course.dto.CourseAdminListItemResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/courses")
    public String getCourseListForAdmin(
            @RequestParam(required = false) Status status,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            Model model) {

        // 로그인 기능 merge 이후 세션 확인 로직 추가

        Page<CourseAdminListItemResponseDto> courses = adminService.getCourseListForAdmin(status, pageNumber, pageSize);
        model.addAttribute("courses", courses);

        return "admin/home";
    }
}
