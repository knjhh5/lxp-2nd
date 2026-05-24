package com.gnjhh.lxp_2nd.content.dto;

public class ContentProgressResponse {

    private final Long contentId;
    private final String contentTitle;
    private final int orderIndex;
    private final boolean completed;

    private ContentProgressResponse(Long contentId, String contentTitle, int orderIndex,
            boolean completed) {
        this.contentId = contentId;
        this.contentTitle = contentTitle;
        this.orderIndex = orderIndex;
        this.completed = completed;
    }

    public static ContentProgressResponse of(Long contentId, String contentTitle, int orderIndex,
            boolean completed) {
        return new ContentProgressResponse(contentId, contentTitle, orderIndex, completed);
    }

    public Long getContentId() {
        return contentId;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public boolean isCompleted() {
        return completed;
    }
}