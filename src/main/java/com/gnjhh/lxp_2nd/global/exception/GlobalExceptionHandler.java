package com.gnjhh.lxp_2nd.global.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /*
     * 리소스를 찾을 수 없는 경우 (404)
     * NotFoundException을 상속한 모든 도메인 예외(CourseNotFoundException 등)가 여기서 처리된다.
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(NotFoundException e, Model model) {
        log.warn("리소스를 찾을 수 없음: {}", e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "error/404";
    }


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDeniedException(AccessDeniedException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/403";
    }

}
