package com.gnjhh.lxp_2nd.course;

import com.gnjhh.lxp_2nd.course.dto.CourseListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CourseController {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 12;
    private static final int MAX_SIZE = 50;
    private static final String DEFAULT_SORT = "latest";
    private static final String POPULAR_SORT = "popular";

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public String getCourses(
            @RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "12") String size,
            @RequestParam(defaultValue = "latest") String sort,
            Model model) {
        int currentPage = normalizePage(page);
        int pageSize = normalizeSize(size);
        String currentSort = normalizeSort(sort);

        Page<CourseListResponseDto> coursePage =
                courseService.findPublicCourses(currentSort, currentPage, pageSize);

        if (coursePage.getTotalPages() > 0 && currentPage > coursePage.getTotalPages()) {
            return "redirect:/courses?page=1&size=" + pageSize + "&sort=" + currentSort;
        }

        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalCount", coursePage.getTotalElements());
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("sort", currentSort);
        return "course/home";
    }

    private int normalizePage(String page) {
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

    private String normalizeSort(String sort) {
        if (POPULAR_SORT.equals(sort)) {
            return POPULAR_SORT;
        }
        return DEFAULT_SORT;
    }
}
