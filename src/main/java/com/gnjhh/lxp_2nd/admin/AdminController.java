package com.gnjhh.lxp_2nd.admin;

import com.gnjhh.lxp_2nd.course.domain.vo.Status;
import com.gnjhh.lxp_2nd.course.dto.CourseAdminListItemResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/courses")
    public String getCourseListForAdmin(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            Model model,
            RedirectAttributes redirectAttributes) {

        // 로그인 기능 merge 이후 세션 확인 로직 추가

        Status statusEnum = null;

        if (status != null) {
            try {
                statusEnum = Status.valueOf(status);
            } catch (IllegalArgumentException e) {
                redirectAttributes.addFlashAttribute("errorMessage", "유효하지 않은 승인 상태입니다.");
                return "redirect:/admin/courses";
            }
        }

        if (pageNumber < 1) {
            redirectAttributes.addFlashAttribute("errorMessage", "유효하지 않은 페이지 번호입니다.");
            return "redirect:/admin/courses?pageNumber=1" + (status != null ? "&status=" + status : "");
        }

        Page<CourseAdminListItemResponseDto> courses = adminService.getCourseListForAdmin(statusEnum, pageNumber, pageSize);

        if (courses.getTotalPages() > 0 && pageNumber > courses.getTotalPages()) {
            redirectAttributes.addFlashAttribute("errorMessage", "유효하지 않은 페이지 번호입니다.");
            return "redirect:/admin/courses?pageNumber=1" + (status != null ? "&status=" + status : "");
        }

        model.addAttribute("courses", courses);
        model.addAttribute("status", status);

        return "admin/home";
    }
}
