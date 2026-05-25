package com.gnjhh.lxp_2nd.enrollment.dto;

public class EnrollmentListResponseDto {

    private final Long id;
    private final CourseSummaryDto course;
    private final int progressRate;

    public EnrollmentListResponseDto(
            Long id, Long courseId, String courseTitle, String instructorName, Number progressRate) {
        this.id = id;
        this.course = new CourseSummaryDto(courseId, courseTitle, instructorName);
        this.progressRate = progressRate == null ? 0 : progressRate.intValue();
    }

    public Long getId() {
        return id;
    }

    public CourseSummaryDto getCourse() {
        return course;
    }

    public int getProgressRate() {
        return progressRate;
    }

    public static class CourseSummaryDto {

        private final Long id;
        private final String title;
        private final String instructorName;

        public CourseSummaryDto(Long id, String title, String instructorName) {
            this.id = id;
            this.title = title;
            this.instructorName = instructorName;
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
    }
}
