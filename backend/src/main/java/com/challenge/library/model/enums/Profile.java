package com.challenge.library.model.enums;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum Profile {
    ADMIN,
    USER,
    ANONYMOUS;

    public Set<String> getAuthorities() {
        return switch (this) {
            case ADMIN -> new HashSet<>(List.of("ROLE_ADMIN", "loan.rent", "loan.return", "book.add", "book.remove", "user.inactivate"));
            case USER -> new HashSet<>(List.of("ROLE_USER", "loan.rent", "loan.return"));
            case ANONYMOUS -> new HashSet<>(List.of("ROLE_ANONYMOUS"));
        };
    }
}
