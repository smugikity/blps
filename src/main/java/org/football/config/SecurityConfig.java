package org.football.config;

import org.football.jaas.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.jaas.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
//    @Autowired
//    XmlUserRepository xmlUserRepository;
//    @Autowired
//    UserRepository userRepository;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JaasAuthenticationProvider jaasAuthenticationProvider() {
        JaasAuthenticationProvider provider = new JaasAuthenticationProvider();
        provider.setLoginConfig(new ClassPathResource("jaas.config"));
        provider.setLoginContextName("customjaasmodule");
        JaasAuthenticationCallbackHandler[] c = {new JaasNameCallbackHandler(),new JaasPasswordCallbackHandler()};
        provider.setCallbackHandlers(c);
        AuthorityGranter[] a = {new CustomJaasAuthorityGranter()};
        provider.setAuthorityGranters(a);
        return provider;
    }

    @Bean
    SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
        .authorizeRequests(authorize -> {
            try {
                authorize
                    .requestMatchers("/api/auth/users").hasAuthority("ADMIN")
                    .requestMatchers("/api/auth/logout").not().anonymous()
                    .requestMatchers("/api/auth/**").not().authenticated()
                    .requestMatchers(HttpMethod.GET ,"/api/**").authenticated()
                       .requestMatchers("/api/**").hasAuthority("ADMIN")
                    .and().httpBasic(Customizer.withDefaults());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return http.build();
    }


}