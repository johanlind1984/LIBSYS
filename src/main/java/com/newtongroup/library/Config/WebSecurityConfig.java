package com.newtongroup.library.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailService;

    @Autowired
    private AuthenticationSuccesHandler authenticationSuccesHandler;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/register-visitor/*").hasAnyRole("ADMIN", "BOSS", "LIBRARIAN")
                .antMatchers("/admin/*", "/register/*").hasAnyRole("ADMIN", "BOSS")
                .antMatchers("/librarian/**").hasRole("LIBRARIAN")
                .antMatchers("/visitor/**").hasRole("VISITOR")
                .antMatchers("/login*").permitAll()
                .antMatchers("/search*").permitAll()
                .antMatchers("/").permitAll()
                .and()
                .formLogin().successHandler(authenticationSuccesHandler)
                .and().logout();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
