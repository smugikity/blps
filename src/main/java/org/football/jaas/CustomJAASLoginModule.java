package org.football.jaas;

import org.springframework.security.authentication.jaas.JaasAuthenticationCallbackHandler;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.Map;

public class CustomJAASLoginModule implements LoginModule {

    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map<String, ?> sharedState;
    private Map<String, ?> options;

    private String username;
    private boolean isAuthenticated = false;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler =  callbackHandler;
        this.sharedState = sharedState;
        this.options = options;
        System.out.println(callbackHandler.toString());
    }

    @Override
    public boolean login() throws LoginException {
        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("Username");
        callbacks[1] = new PasswordCallback("Password", false);

        try {
            callbackHandler.handle(callbacks);
            String enteredUsername = ((NameCallback) callbacks[0]).getName();
            char[] enteredPassword = ((PasswordCallback) callbacks[1]).getPassword();
            System.out.println(enteredUsername);
            // Implement logic to validate credentials against your XML file or data source.
            // For simplicity, we'll assume a hardcoded username and password here.
            if ("testbaby".equals(enteredUsername) && "testbaby".equals(String.valueOf(enteredPassword))) {
                System.out.println("authed");
                username = enteredUsername;
                isAuthenticated = true;
                return true;
            } else {
                throw new LoginException("Authentication failed");
            }
        } catch (IOException | UnsupportedCallbackException e) {
            throw new LoginException("Error during authentication: " + e.getMessage());
        }
    }

    @Override
    public boolean commit() throws LoginException {
        if (isAuthenticated) {
            subject.getPrincipals().add(new CustomPrincipal(1L,username));
            return true;
        }
        return false;
    }

    @Override
    public boolean abort() throws LoginException {
        return false;
    }

    @Override
    public boolean logout() throws LoginException {
        subject.getPrincipals().removeIf(principal -> principal instanceof CustomPrincipal);
        return true;
    }
}
