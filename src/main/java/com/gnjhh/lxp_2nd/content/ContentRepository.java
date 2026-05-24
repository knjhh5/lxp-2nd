package com.gnjhh.lxp_2nd.content;

import com.gnjhh.lxp_2nd.content.domain.entity.Content;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {

    // 강의에 속한 콘텐츠 목록을 order_index 오름차순으로 조회
    List<Content> findByCourseIdOrderByOrderIndexAsc(Long courseId);

}
