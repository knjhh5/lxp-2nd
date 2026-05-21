package com.gnjhh.lxp_2nd.contenthistory.domain.entity;

import com.gnjhh.lxp_2nd.content.domain.entity.Content;
import com.gnjhh.lxp_2nd.enrollment.domain.entity.Enrollment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "content_histories")
public class ContentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @Column(name = "is_completed", nullable = false)
    private boolean completed = false;

    @Column(name = "last_date")
    private LocalDateTime lastDate;

    protected ContentHistory() {
    }

    public ContentHistory(Enrollment enrollment, Content content) {
        this.enrollment = enrollment;
        this.content = content;
        this.completed = false;
    }

    public Long getId() {
        return id;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public Content getContent() {
        return content;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDateTime getLastDate() {
        return lastDate;
    }
}
