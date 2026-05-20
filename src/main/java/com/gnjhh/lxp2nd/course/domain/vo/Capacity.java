package com.gnjhh.lxp2nd.course.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Capacity {

    @Column(name = "capacity", nullable = false)
    private int value;

    protected Capacity() {
    }

    public Capacity(int value) {
        if (value < 1) {
            throw new IllegalArgumentException("정원은 1명 이상이어야 합니다.");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
