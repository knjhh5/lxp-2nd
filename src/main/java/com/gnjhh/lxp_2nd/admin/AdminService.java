package com.gnjhh.lxp_2nd.admin;

import com.gnjhh.lxp_2nd.course.CourseRepository;
import com.gnjhh.lxp_2nd.course.domain.vo.Status;
import com.gnjhh.lxp_2nd.course.dto.CourseAdminListItemResponseDto;
import com.gnjhh.lxp_2nd.course.dto.CourseAdminListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    private final CourseRepository courseRepository;

    public AdminService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Page<CourseAdminListItemResponseDto> getCourseListForAdmin(Status status, int page){
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<CourseAdminListResponseDto> courses = courseRepository.findCoursesWithEnrollmentCount(status, pageable);

        return courses.map(dto -> {
            if (dto.getStatus() == Status.PUBLIC) {
                Integer remainingSeats = Math.max(0, dto.getCapacity() - dto.getActiveCount().intValue());
                return new CourseAdminListItemResponseDto(dto, dto.getActiveCount(), dto.getCanceledCount(), remainingSeats);
            } else {
                return new CourseAdminListItemResponseDto(dto, null, null, null);
            }
        });
    }
}
