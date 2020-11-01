package com.example.project.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * This class represents all possible values for a role name. It's used to ensure that all roles can have only certain names/types
 */
public enum RoleEnum implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
