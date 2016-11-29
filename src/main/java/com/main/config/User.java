package com.main.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@JsonIgnoreProperties({"authorities"})
public class User implements UserDetails {

    private static final long serialVersionUID = 6980833944761230840L;
    private boolean enabled;
    private boolean credentialsNonExpired;
    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private String username;
    private String password;
    private List authorities;

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(List authorities) {
        this.authorities = authorities;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public List getAuthorities() {
        return authorities;
    }

    public User(boolean enabled,
                boolean credentialsNonExpired,
                boolean accountNonLocked,
                boolean accountNonExpired,
                String username,
                String password,
                List authorities) {
        this.enabled = enabled;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.accountNonExpired = accountNonExpired;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
}