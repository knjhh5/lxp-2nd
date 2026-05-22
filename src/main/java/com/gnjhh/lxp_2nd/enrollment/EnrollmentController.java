package com.gnjhh.lxp_2nd.enrollment;

import com.gnjhh.lxp_2nd.enrollment.dto.EnrollmentListResponseDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EnrollmentController {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 12;
    private static final int MAX_SIZE = 50;
    private static final String DEFAULT_STATUS = "all";
    private static final String ONGOING_STATUS = "ongoing";
    private static final String DONE_STATUS = "done";

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping({"/member/courses", "/members/me/enrollments"})
    public String getMyEnrollments(
            @RequestParam(required = false) String page,
            @RequestParam(required = false) String pageNumber,
            @RequestParam(defaultValue = "12") String size,
            @RequestParam(defaultValue = "all") String status,
            HttpSession session,
            Model model) {
        Long loginMemberId = (Long) session.getAttribute("loginMemberId");
        if (loginMemberId == null) {
            return "redirect:/auth/login";
        }

        int currentPage = normalizePage(pageNumber != null ? pageNumber : page);
        int pageSize = normalizeSize(size);
        String currentStatus = normalizeStatus(status);

        Page<EnrollmentListResponseDto> enrollmentPage =
                enrollmentService.findMyEnrollments(
                        loginMemberId, currentStatus, currentPage, pageSize);

        if (enrollmentPage.getTotalPages() > 0 && currentPage > enrollmentPage.getTotalPages()) {
            return "redirect:/member/courses?pageNumber=1&size="
                    + pageSize
                    + "&status="
                    + currentStatus;
        }

        model.addAttribute("enrollments", enrollmentPage.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalCount", enrollmentPage.getTotalElements());
        model.addAttribute("totalPages", enrollmentPage.getTotalPages());
        model.addAttribute("status", currentStatus);
        model.addAttribute(
                "ongoingCount", enrollmentService.countOngoingEnrollments(loginMemberId));
        model.addAttribute("doneCount", enrollmentService.countDoneEnrollments(loginMemberId));
        return "course/member/home";
    }

    private int normalizePage(String page) {
        if (page == null) {
            return DEFAULT_PAGE;
        }
        try {
            int parsedPage = Integer.parseInt(page);
            return Math.max(parsedPage, DEFAULT_PAGE);
        } catch (NumberFormatException exception) {
            return DEFAULT_PAGE;
        }
    }

    private int normalizeSize(String size) {
        try {
            int parsedSize = Integer.parseInt(size);
            if (parsedSize < 1) {
                return DEFAULT_SIZE;
            }
            return Math.min(parsedSize, MAX_SIZE);
        } catch (NumberFormatException exception) {
            return DEFAULT_SIZE;
        }
    }

    private String normalizeStatus(String status) {
        if (ONGOING_STATUS.equals(status) || DONE_STATUS.equals(status)) {
            return status;
        }
        return DEFAULT_STATUS;
    }
}
