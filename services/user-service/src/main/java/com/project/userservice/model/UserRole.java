package com.project.userservice.model;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("ADMIN"), USER("USER"), AGENT("AGENT"), FIRM("FIRM");

    private String role;

    UserRole(String role) {
        this.role = role;
    }
}
