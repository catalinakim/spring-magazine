package com.lina.springmagazine.model;

public enum UserRoleEnum {
    //방법3
    USER(Authority.USER),  // 사용자 권한
    ADMIN(Authority.ADMIN);  // 관리자 권한

    private final String authority;  //-> getAuthority()로 값 빼내기 위해서

    //생성자, 처음 만들어질때 값을 넘겨주고, 값을 설정
    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {  //값을 빼낼때
        return this.authority;
    }

    public static class Authority { //내부 클래스를 선언
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}
