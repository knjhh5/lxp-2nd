package com.gnjhh.lxp2nd.contenthistory.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class IsCompleted {

    @Column(name = "is_completed", nullable = false)
    private boolean value;

    protected IsCompleted() {
    }

    public IsCompleted(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
}
