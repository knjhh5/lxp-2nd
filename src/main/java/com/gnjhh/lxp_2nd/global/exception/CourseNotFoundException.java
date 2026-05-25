package com.gnjhh.lxp_2nd.global.exception;

public class CourseNotFoundException extends NotFoundException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 강의입니다";

    public CourseNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public CourseNotFoundException(String message) {
        super(message);
    }
}
