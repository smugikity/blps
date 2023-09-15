package org.football.jaas;

import org.football.model.ERole;
import org.football.model.User;
import org.football.model.XmlUser;
import org.football.repository.UserRepository;
import org.football.repository.XmlUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class CustomJaasAuthorityGranter implements AuthorityGranter {

    @Override
    public Set<String> grant(Principal principal) {
        Long id = ((CustomJaasPrincipal) principal).getId();
        XmlUser user = XmlUserRepository.findById(id);
        if (user != null) {
            return user.getRoles().stream().map(ERole::getName).collect(Collectors.toSet());
        }
        return Set.of();
    }

}