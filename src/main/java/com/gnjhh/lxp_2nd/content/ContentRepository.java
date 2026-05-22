package com.gnjhh.lxp_2nd.content;

import com.gnjhh.lxp_2nd.content.domain.entity.Content;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findByCourseIdOrderByOrderIndex(Long courseId);
}
