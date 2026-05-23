package com.gnjhh.lxp_2nd.course.dto;

import com.gnjhh.lxp_2nd.content.domain.entity.Content;
import com.gnjhh.lxp_2nd.content.dto.ContentResponse;
import com.gnjhh.lxp_2nd.course.domain.entity.Course;
import java.util.List;

public class CourseDetailResponse {
    
    private final Long courseId;
    private final String title;
    private final String instructorName;
    private final String description;
    private final List<ContentResponse> contents;

    private CourseDetailResponse(Long courseId, String title, String instructorName,
            String description, List<ContentResponse> contents) {
        this.courseId = courseId;
        this.title = title;
        this.instructorName = instructorName;
        this.description = description;
        this.contents = contents;
    }

    public static CourseDetailResponse of(Course course, List<Content> contents) {
        List<ContentResponse> contentResponses = contents.stream()
                .map(ContentResponse::from)
                .toList();

        return new CourseDetailResponse(
                course.getId(),
                course.getTitle(),
                course.getInstructor().getNickname(),
                course.getDescription(),
                contentResponses
        );
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

    public String getDescription() {
        return description;
    }

    public List<ContentResponse> getContents() {
        return contents;
    }
}
