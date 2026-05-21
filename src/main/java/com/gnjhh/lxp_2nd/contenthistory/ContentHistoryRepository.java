package com.gnjhh.lxp_2nd.contenthistory;

import com.gnjhh.lxp_2nd.contenthistory.domain.entity.ContentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentHistoryRepository extends JpaRepository<ContentHistory, Long> {
}
