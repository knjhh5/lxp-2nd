package com.gnjhh.lxp_2nd.admin;

import com.gnjhh.lxp_2nd.course.CourseRepository;
import com.gnjhh.lxp_2nd.course.domain.entity.Course;
import com.gnjhh.lxp_2nd.course.domain.vo.Status;
import com.gnjhh.lxp_2nd.course.dto.CourseAdminListItemResponseDto;
import com.gnjhh.lxp_2nd.course.dto.CourseAdminListResponseDto;
import com.gnjhh.lxp_2nd.global.exception.CourseNotFoundException;
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
    public Page<CourseAdminListItemResponseDto> getCourseListForAdmin(Status status, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
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

    @Transactional
    public void changeCourseStatus(Long courseId, Status status) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(CourseNotFoundException::new);
        course.changeStatus(status);
    }
}
