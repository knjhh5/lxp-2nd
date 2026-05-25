package com.gnjhh.lxp_2nd.global.exception;

/*
 * 리소스를 찾을 수 없을 때 사용하는 공통 예외 상위 클래스.
 * 이를 상속받은 모든 예외는 GlobalExceptionHandler에서 404로 처리된다.
 */
public abstract class NotFoundException extends RuntimeException {

    protected NotFoundException(String message) {
        super(message);
    }
    
}