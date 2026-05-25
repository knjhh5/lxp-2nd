package com.gnjhh.lxp_2nd.course.dto;

import com.gnjhh.lxp_2nd.course.domain.vo.Status;

public class CourseAdminListItemResponseDto {

    private final Long courseId;
    private final String title;
    private final String instructorName;
    private final Integer capacity;
    private final Status status;
    private final Long activeCount;
    private final Long canceledCount;
    private final Integer remainingSeats;

    public CourseAdminListItemResponseDto(CourseAdminListResponseDto dto,Long activeCount, Long canceledCount, Integer remainingSeats) {
        this.courseId = dto.getCourseId();
        this.title = dto.getTitle();
        this.instructorName = dto.getInstructorName();
        this.capacity = dto.getCapacity();
        this.status = dto.getStatus();
        this.activeCount = activeCount;
        this.canceledCount = canceledCount;
        this.remainingSeats = remainingSeats;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Status getStatus() {
        return status;
    }

    public Long getActiveCount() {
        return activeCount;
    }

    public Long getCanceledCount() {
        return canceledCount;
    }

    public Integer getRemainingSeats() {
        return remainingSeats;
    }
}
