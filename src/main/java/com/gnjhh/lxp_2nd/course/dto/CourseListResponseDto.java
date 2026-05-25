package com.gnjhh.lxp_2nd.course.dto;

public class CourseListResponseDto {

    private final Long id;
    private final String title;
    private final String instructorName;
    private final String description;
    private final int capacity;
    private final Long currentStudentCount;
    private final String thumbnailUrl = null;

    public CourseListResponseDto(
            Long id,
            String title,
            String instructorName,
            String description,
            int capacity,
            Long currentStudentCount) {
        this.id = id;
        this.title = title;
        this.instructorName = instructorName;
        this.description = description;
        this.capacity = capacity;
        this.currentStudentCount = currentStudentCount;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public Long getCurrentStudentCount() {
        return currentStudentCount;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
