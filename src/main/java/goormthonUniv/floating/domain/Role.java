package goormthonUniv.floating.domain;

import lombok.Getter;

@Getter
public enum Role {
    GUEST("ROLE_GUEST","비 로그인 사용자"),
    COMMON("ROLE_USER","일반 사용자"),
    ADMIN("ROLE_ADMIN","관리자");


    private String key;
    private String desc;

    Role(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }
}
