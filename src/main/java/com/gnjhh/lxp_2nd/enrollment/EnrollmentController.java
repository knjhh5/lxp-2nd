package com.gnjhh.lxp_2nd.enrollment;

import com.gnjhh.lxp_2nd.enrollment.dto.EnrollmentListResponseDto;
import com.gnjhh.lxp_2nd.global.security.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EnrollmentController {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 12;
    private static final int MAX_SIZE = 50;
    private static final String DEFAULT_STATUS = "ongoing";
    private static final String ONGOING_STATUS = "ongoing";
    private static final String DONE_STATUS = "done";
    private static final String INVALID_PAGE_MESSAGE = "유효하지 않은 페이지 번호입니다";

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping({"/member/courses", "/members/me/enrollments"})
    public String getMyEnrollments(
            @RequestParam(defaultValue = "1") String pageNumber,
            @RequestParam(defaultValue = "12") String pageSize,
            @RequestParam(defaultValue = "ongoing") String status,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            Model model) {
        if (customUserDetails == null) {
            return "redirect:/auth/login";
        }
        Long loginMemberId = customUserDetails.getMember().getId();

        boolean invalidPage = isInvalidPage(pageNumber);
        int currentPage = normalizePage(pageNumber);
        int currentPageSize = normalizeSize(pageSize);
        String currentStatus = normalizeStatus(status);

        Page<EnrollmentListResponseDto> enrollmentPage =
                enrollmentService.findMyEnrollments(
                        loginMemberId, currentStatus, currentPage, currentPageSize);

        if (enrollmentPage.getTotalPages() > 0 && currentPage > enrollmentPage.getTotalPages()) {
            return "redirect:/member/courses?pageNumber=1&pageSize="
                    + currentPageSize
                    + "&status="
                    + currentStatus;
        }

        if (invalidPage) {
            model.addAttribute("errorMessage", INVALID_PAGE_MESSAGE);
        }
        model.addAttribute("enrollments", enrollmentPage.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", currentPageSize);
        model.addAttribute("totalCount", enrollmentPage.getTotalElements());
        model.addAttribute("totalPages", enrollmentPage.getTotalPages());
        model.addAttribute("status", currentStatus);
        model.addAttribute(
                "ongoingCount", enrollmentService.countOngoingEnrollments(loginMemberId));
        model.addAttribute("doneCount", enrollmentService.countDoneEnrollments(loginMemberId));
        return "course/member/home";
    }

    private boolean isInvalidPage(String pageNumber) {
        try {
            return Integer.parseInt(pageNumber) < DEFAULT_PAGE;
        } catch (NumberFormatException exception) {
            return true;
        }
    }

    private int normalizePage(String pageNumber) {
        try {
            int parsedPage = Integer.parseInt(pageNumber);
            return Math.max(parsedPage, DEFAULT_PAGE);
        } catch (NumberFormatException exception) {
            return DEFAULT_PAGE;
        }
    }

    private int normalizeSize(String pageSize) {
        try {
            int parsedSize = Integer.parseInt(pageSize);
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
