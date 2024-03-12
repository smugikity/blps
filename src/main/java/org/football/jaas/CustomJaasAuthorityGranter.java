package org.football.jaas;

import org.football.model.Role;
import org.football.model.User;
import org.football.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.authentication.jaas.AuthorityGranter;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;


public class CustomJaasAuthorityGranter implements AuthorityGranter {
    @Override
    public Set<String> grant(Principal principal) {
        try {
            Long id = ((CustomJaasPrincipal) principal).getId();
            ApplicationContext context = new AnnotationConfigApplicationContext(UserService.class);
            UserService userService = context.getBean(UserService.class);
            User user = userService.findById(id);
            if (user != null) {
                return user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return Set.of();
        }
    }

}