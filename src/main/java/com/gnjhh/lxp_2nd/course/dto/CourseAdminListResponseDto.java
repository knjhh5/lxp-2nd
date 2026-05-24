package com.gnjhh.lxp_2nd.course.dto;

import com.gnjhh.lxp_2nd.course.domain.vo.Status;

/**
 * F18 강의 관리 조회 - Repository 쿼리 결과 Projection
 * 잔여석은 포함하지 않으며 Service에서 계산 후 CourseAdminListItemResponseDto로 변환된다.
 */
public interface CourseAdminListResponseDto {

    Long getCourseId();
    String getTitle();
    String getInstructorName();
    Integer getCapacity();
    Status getStatus();
    Long getActiveCount();
    Long getCanceledCount();
}
