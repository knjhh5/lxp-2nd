package com.gnjhh.lxp2nd.contenthistory;

import com.gnjhh.lxp2nd.content.Content;
import com.gnjhh.lxp2nd.contenthistory.domain.vo.IsCompleted;
import com.gnjhh.lxp2nd.enrollment.Enrollment;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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

    @Embedded
    private IsCompleted completed = new IsCompleted(false);

    @Column(name = "last_date")
    private LocalDateTime lastDate;

    protected ContentHistory() {
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

    public IsCompleted getCompleted() {
        return completed;
    }

    public LocalDateTime getLastDate() {
        return lastDate;
    }
}
