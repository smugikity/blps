package org.football.jaas;

import org.football.model.User;
import org.football.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.util.Map;


public class CustomJaasLoginModule implements LoginModule {

    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map<String, ?> sharedState;
    private Map<String, ?> options;

    private String username;
    private boolean isAuthenticated = false;


    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;
    }

    @Override
    public boolean login() throws LoginException {
        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("Username");
        callbacks[1] = new PasswordCallback("Password", false);
        try {
            callbackHandler.handle(callbacks);
            String enteredUsername = ((NameCallback) callbacks[0]).getName();
            String enteredPassword = String.valueOf(((PasswordCallback) callbacks[1]).getPassword());
            ApplicationContext context = new AnnotationConfigApplicationContext(UserService.class);
            UserService userService = context.getBean(UserService.class);
            User user = userService.findByUsernameAndPassword(enteredUsername, enteredPassword);
            if (user != null) {
                username = enteredUsername;
                isAuthenticated = true;
                subject.getPrincipals().add(new CustomJaasPrincipal(user.getId(), username));
                return true;
            } else {
                throw new LoginException("Authentication failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new LoginException("Error during authentication: " + e.getMessage());
        }
    }

    @Override
    public boolean commit() throws LoginException {
        return isAuthenticated;
    }

    @Override
    public boolean abort() throws LoginException {
        return false;
    }

    @Override
    public boolean logout() throws LoginException {
        subject.getPrincipals().removeIf(principal -> principal instanceof CustomJaasPrincipal);
        return true;
    }
}
