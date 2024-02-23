package com.project.aftas.Models.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.project.aftas.Models.user.Permission.ADMIN_CREATE;
import static com.project.aftas.Models.user.Permission.ADMIN_DELETE;
import static com.project.aftas.Models.user.Permission.ADMIN_READ;
import static com.project.aftas.Models.user.Permission.ADMIN_UPDATE;
import static com.project.aftas.Models.user.Permission.JURY_CREATE;
import static com.project.aftas.Models.user.Permission.JURY_DELETE;
import static com.project.aftas.Models.user.Permission.JURY_READ;
import static com.project.aftas.Models.user.Permission.JURY_UPDATE;
import static com.project.aftas.Models.user.Permission.MEMBER_CREATE;
import static com.project.aftas.Models.user.Permission.MEMBER_DELETE;
import static com.project.aftas.Models.user.Permission.MEMBER_READ;
import static com.project.aftas.Models.user.Permission.MEMBER_UPDATE;

@RequiredArgsConstructor
public enum Role {

    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    JURY_READ,
                    JURY_UPDATE,
                    JURY_DELETE,
                    JURY_CREATE,
                    MEMBER_READ,
                    MEMBER_UPDATE,
                    MEMBER_DELETE,
                    MEMBER_CREATE
            )
    ),
    JURY(
            Set.of(
                    JURY_READ,
                    JURY_UPDATE,
                    JURY_DELETE,
                    JURY_CREATE
            )
    ),
    MEMBER(
            Set.of(
                    MEMBER_READ,
                    MEMBER_UPDATE,
                    MEMBER_DELETE,
                    MEMBER_CREATE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
