package com.gnjhh.lxp_2nd.content.dto;

import com.gnjhh.lxp_2nd.content.domain.entity.Content;

public class ContentResponse {

    private final Long id;
    private final String contentTitle;
    private final int orderIndex;

    private ContentResponse(Long id, String contentTitle, int orderIndex) {
        this.id = id;
        this.contentTitle = contentTitle;
        this.orderIndex = orderIndex;
    }

    public static ContentResponse from(Content content) {
        return new ContentResponse(
                content.getId(),
                content.getContentTitle(),
                content.getOrderIndex()
        );
    }

    public Long getId() {
        return id;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public int getOrderIndex() {
        return orderIndex;
    }
}
