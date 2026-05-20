package com.gnjhh.lxp2nd.enrollment.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProgressRate {

    @Column(name = "progress_rate", nullable = false)
    private int value;

    protected ProgressRate() {
    }

    public ProgressRate(int value) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("진도율은 0 이상 100 이하이어야 합니다.");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
