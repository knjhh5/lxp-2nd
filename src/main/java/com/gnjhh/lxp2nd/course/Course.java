package com.gnjhh.lxp2nd.course;

import com.gnjhh.lxp2nd.content.Content;
import com.gnjhh.lxp2nd.course.domain.vo.Capacity;
import com.gnjhh.lxp2nd.enrollment.Enrollment;
import com.gnjhh.lxp2nd.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

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

    @Embedded
    private Capacity capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CourseStatus status = CourseStatus.PRIVATE;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<Content> contents = new ArrayList<>();

    protected Course() {
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

    public Capacity getCapacity() {
        return capacity;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public List<Content> getContents() {
        return contents;
    }
}
