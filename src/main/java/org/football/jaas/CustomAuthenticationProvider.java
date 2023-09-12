package org.football.jaas;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.jaas.AbstractJaasAuthenticationProvider;
import org.springframework.security.authentication.jaas.JaasGrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sun.security.auth.UserPrincipal;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class CustomAuthenticationProvider extends AbstractJaasAuthenticationProvider {
    private AuthenticationProvider delegate;
    private int count = 0;

    public CustomAuthenticationProvider(AuthenticationProvider delegate) {
        this.delegate = delegate;
    }

    @Override
    protected LoginContext createLoginContext(CallbackHandler handler) throws LoginException {
        return new LoginContext("customjaasmodule");
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        Authentication a = delegate.authenticate(authentication);

        if(a.isAuthenticated()){
            a = super.authenticate(a);
        }else{
            throw new BadCredentialsException("Bad credentials");
        }

        return a;
    }

    private List<GrantedAuthority> loadRolesFromDatabaseHere(String name) {
        GrantedAuthority grantedAuthority =new JaasGrantedAuthority(name, new UserPrincipal(name));
        return Arrays.asList(grantedAuthority);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return delegate.supports(authentication);
    }

    /* (non-Javadoc)
     * @see org.springframework.security.authentication.dao.DaoAuthenticationProvider#additionalAuthenticationChecks(org.springframework.security.core.userdetails.UserDetails, org.springframework.security.authentication.UsernamePasswordAuthenticationToken)
     */
}