package com.gnjhh.lxp_2nd.content.domain.entity;

import com.gnjhh.lxp_2nd.course.domain.entity.Course;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contents")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content_title", nullable = false, length = 100)
    private String contentTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "order_index", nullable = false)
    private int orderIndex;

    protected Content() {
    }

    public Content(String contentTitle, Course course, int orderIndex) {
        if (orderIndex < 1) {
            throw new IllegalArgumentException("orderIndex must be at least 1");
        }
        this.contentTitle = contentTitle;
        this.course = course;
        this.orderIndex = orderIndex;
    }

    public Long getId() {
        return id;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public Course getCourse() {
        return course;
    }

    public int getOrderIndex() {
        return orderIndex;
    }
}
