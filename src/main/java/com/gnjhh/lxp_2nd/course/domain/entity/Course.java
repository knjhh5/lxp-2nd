package com.gnjhh.lxp_2nd.course.domain.entity;

import com.gnjhh.lxp_2nd.course.domain.vo.Status;
import com.gnjhh.lxp_2nd.member.domain.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    private Member instructor;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.PRIVATE;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected Course() {
    }

    public Course(String title, Member instructor, String description, int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity must be at least 1");
        }
        this.title = title;
        this.instructor = instructor;
        this.description = description;
        this.capacity = capacity;
        this.status = Status.PRIVATE;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Member getInstructor() {
        return instructor;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
      
    // 강의 상태 변경
    public void changeStatus(Status newStatus) {
        if (this.status != Status.PRIVATE) {
            throw new IllegalStateException("PRIVATE 강의만 변경 가능합니다");
        }
        this.status = newStatus;
    }
}
