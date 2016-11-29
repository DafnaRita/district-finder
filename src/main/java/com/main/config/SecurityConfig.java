package com.main.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.LinkedHashMap;

import static org.hibernate.criterion.Restrictions.and;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceStub userDetailsService;

    // регистрируем нашу реализацию UserDetailsService
    // а также PasswordEncoder для приведения пароля в формат SHA1
    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService);
                //пока на кодировку хранения забили, да
                //.passwordEncoder(getShaPasswordEncoder());
    }

    @Bean
    public Http403ForbiddenEntryPoint http403ForbiddenEntryPoint() {
        System.out.println("403 bean");
        return new Http403ForbiddenEntryPoint();
    }

    @Bean
    public UserDetailsServiceStub userDetailsService() {
        return new UserDetailsServiceStub();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/*").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .exceptionHandling();
    }
}