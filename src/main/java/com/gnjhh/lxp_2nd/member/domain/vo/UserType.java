package com.gnjhh.lxp_2nd.member.domain.vo;

public enum UserType {
    STUDENT("STUDENT"),
    INSTRUCTOR("INSTRUCTOR");

    private final String value;

    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
