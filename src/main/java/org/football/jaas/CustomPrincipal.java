package org.football.jaas;

import java.security.Principal;

public class CustomPrincipal implements Principal {
    private String username;
    private Long id;

    public CustomPrincipal(Long id,String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }
    public Long getId() {
        return id;
    }
}
