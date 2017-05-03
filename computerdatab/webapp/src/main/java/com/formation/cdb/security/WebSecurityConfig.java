package com.formation.cdb.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String REALM_NAME = "digest";
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        DigestAuthenticationEntryPoint authenticationEntryPoint = new DigestAuthenticationEntryPoint();
        authenticationEntryPoint.setKey("sewatech");
        authenticationEntryPoint.setRealmName(REALM_NAME);

        DigestAuthenticationFilter filter = new DigestAuthenticationFilter();
        filter.setAuthenticationEntryPoint(authenticationEntryPoint);
        filter.setUserDetailsService(userDetailsService());
        filter.setPasswordAlreadyEncoded(true);
            
        http
            .authorizeRequests()
                .antMatchers("/dashboard", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
            .addFilter(filter)
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
            .formLogin()
                //.loginPage("/login")
                .defaultSuccessUrl("/dashboard")
                //.failureUrl("/login?error")
                .permitAll()
                .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        Properties users = new Properties();
        users.setProperty("user", digest("user", "pwd") + ",USER");
        users.setProperty("admin", digest("admin", "admin") + ",USER,ADMIN");
        return new InMemoryUserDetailsManager(users);
    }

    private String digest(String username, String password) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }

        String data = username + ":" + REALM_NAME + ":" + password;
        return new String(Hex.encode(digest.digest(data.getBytes())));
    }
}
