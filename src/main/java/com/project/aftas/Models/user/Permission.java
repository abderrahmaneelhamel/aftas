package com.project.aftas.Models.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    JURY_READ("jury:read"),
    JURY_UPDATE("jury:update"),
    JURY_CREATE("jury:create"),
    JURY_DELETE("jury:delete"),
    MEMBER_READ("member:read"),
    MEMBER_UPDATE("member:update"),
    MEMBER_CREATE("member:create"),
    MEMBER_DELETE("member:delete");

    @Getter
    private final String permission;
}
