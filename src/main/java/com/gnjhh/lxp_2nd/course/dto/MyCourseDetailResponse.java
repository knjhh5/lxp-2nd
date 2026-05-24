package com.gnjhh.lxp_2nd.course.dto;

import com.gnjhh.lxp_2nd.content.dto.ContentProgressResponse;
import java.util.List;

public class MyCourseDetailResponse {

    private final Long courseId;
    private final String title;
    private final String instructorNickname;
    private final String description;
    private final int progressRate;
    private final List<ContentProgressResponse> contents;

    private MyCourseDetailResponse(Long courseId, String title, String instructorNickname,
            String description, int progressRate,
            List<ContentProgressResponse> contents) {
        this.courseId = courseId;
        this.title = title;
        this.instructorNickname = instructorNickname;
        this.description = description;
        this.progressRate = progressRate;
        this.contents = contents;
    }

    public static MyCourseDetailResponse of(Long courseId, String title, String instructorNickname,
            String description, int progressRate,
            List<ContentProgressResponse> contents) {
        return new MyCourseDetailResponse(courseId, title, instructorNickname, description,
                progressRate, contents);
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getInstructorNickname() {
        return instructorNickname;
    }

    public String getDescription() {
        return description;
    }

    public int getProgressRate() {
        return progressRate;
    }

    public List<ContentProgressResponse> getContents() {
        return contents;
    }
}