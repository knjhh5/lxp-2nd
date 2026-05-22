package com.gnjhh.lxp_2nd.member.dto;

public class MemberCreateRequestDto {

    private final String loginId;
    private final String password;
    private final String nickname;

    public MemberCreateRequestDto(String loginId, String password, String nickname) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

}