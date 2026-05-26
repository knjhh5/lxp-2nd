package com.gnjhh.lxp_2nd.course;

import com.gnjhh.lxp_2nd.course.dto.CourseDetailResponse;
import com.gnjhh.lxp_2nd.course.dto.CourseListResponseDto;
import org.springframework.data.domain.Page;
import com.gnjhh.lxp_2nd.course.dto.MyCourseDetailResponse;
import com.gnjhh.lxp_2nd.global.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CourseController {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 6;
    private static final int MAX_SIZE = 50;
    private static final String DEFAULT_SORT = "latest";
    private static final String POPULAR_SORT = "popular";
    private static final String INVALID_PAGE_MESSAGE = "유효하지 않은 페이지 번호입니다";

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public String getCourses(
            @RequestParam(name = "pageNumber", defaultValue = "1") String pageNumberParameter,
            @RequestParam(name = "pageSize", defaultValue = "6") String pageSizeParameter,
            @RequestParam(name = "sortBy", defaultValue = "latest") String sortByParameter,
            Model model) {
        boolean invalidPage = isInvalidPage(pageNumberParameter);
        int pageNumber = normalizePage(pageNumberParameter);
        int pageSize = normalizeSize(pageSizeParameter);
        String sortBy = normalizeSort(sortByParameter);

        Page<CourseListResponseDto> coursePage =
                courseService.findPublicCourses(sortBy, pageNumber, pageSize);

        if (pageNumber > DEFAULT_PAGE
                && (coursePage.getTotalPages() == 0 || pageNumber > coursePage.getTotalPages())) {
            return "redirect:/courses?pageNumber=1&pageSize="
                    + pageSize
                    + "&sortBy="
                    + sortBy;
        }

        if (invalidPage) {
            model.addAttribute("errorMessage", INVALID_PAGE_MESSAGE);
        }
        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalCount", coursePage.getTotalElements());
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        return "course/home";
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

    private String normalizeSort(String sortBy) {
        if (POPULAR_SORT.equals(sortBy)) {
            return POPULAR_SORT;
        }
        return DEFAULT_SORT;
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
        return "/course/member/detail";
    }

}
