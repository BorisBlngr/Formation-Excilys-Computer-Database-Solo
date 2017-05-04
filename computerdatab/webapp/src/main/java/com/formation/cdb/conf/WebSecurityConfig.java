package com.formation.cdb.conf;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author excilys
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String REALM_NAME = "digest";
    @Autowired
    DataSource dataSource;

    /**
     * @param auth auth
     * @throws Exception exc
     */
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

      auth.jdbcAuthentication().dataSource(dataSource)
        .usersByUsernameQuery(
            "select username,password, enabled from users where username=?")
        .authoritiesByUsernameQuery(
            "select username, role from user_roles where username=?");
    }

    /**
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        DigestAuthenticationEntryPoint authenticationEntryPoint = new DigestAuthenticationEntryPoint();
        authenticationEntryPoint.setKey("cdb");
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
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
                .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/dashboard")
                .permitAll();
    }


    /**
     * @param username username
     * @param password pass
     * @return digest
     */
    private static String digest(String username, String password) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }

        String data = username + ":" + REALM_NAME + ":" + password;
        return new String(Hex.encode(digest.digest(data.getBytes())));
    }

    /**
     * @param args args
     */
    public static void main(String[] args) {
        System.out.println(digest("user", "pwd"));
    }
}
