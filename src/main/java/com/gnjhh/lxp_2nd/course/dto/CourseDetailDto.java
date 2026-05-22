package com.gnjhh.lxp_2nd.course.dto;

import java.util.List;

public class CourseDetailDto {

    private Long id;
    private String title;
    private String instructorName;
    private String description;
    private String thumbnailUrl;
    private int enrollCount;
    private int maxCapacity;
    private List<ContentDto> contents;
    private int totalHours;

    public CourseDetailDto(Long id, String title, String instructorName,
            String description, String thumbnailUrl,
            int enrollCount, int maxCapacity, List<ContentDto> contents,int totalHours) {
        this.id = id;
        this.title = title;
        this.instructorName = instructorName;
        this.description = description;
        this.enrollCount = enrollCount;
        this.maxCapacity = maxCapacity;
        this.contents = contents;
        this.totalHours =totalHours;
    }


    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getInstructorName() { return instructorName; }
    public String getDescription() { return description; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public int getEnrollCount() { return enrollCount; }
    public int getMaxCapacity() { return maxCapacity; }
    public List<ContentDto> getContents() { return contents; }
    public int getTotalHours(){return totalHours;}


    public static class ContentDto {
        private String title;
        private int durationMinutes;

        public ContentDto(String title, int durationMinutes) {
            this.title = title;
            this.durationMinutes = durationMinutes;
        }
        public String getTitle() { return title; }
        public int getDurationMinutes() { return durationMinutes; }
    }
}