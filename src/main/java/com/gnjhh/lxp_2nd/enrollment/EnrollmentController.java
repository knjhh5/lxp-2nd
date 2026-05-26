package com.gnjhh.lxp_2nd.enrollment;

import com.gnjhh.lxp_2nd.course.CourseService;
import com.gnjhh.lxp_2nd.course.dto.CourseDetailResponse;
import com.gnjhh.lxp_2nd.enrollment.domain.entity.Enrollment;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DataIntegrityViolationException;
import com.gnjhh.lxp_2nd.enrollment.dto.EnrollmentListResponseDto;
import com.gnjhh.lxp_2nd.global.security.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String enrollcourse(@RequestParam("courseId") Long courseId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            RedirectAttributes rttr, Model model) {

        Long studentId = userDetails.getMember().getId();

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

        if (currentPage > DEFAULT_PAGE
                && (enrollmentPage.getTotalPages() == 0
                        || currentPage > enrollmentPage.getTotalPages())) {
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
