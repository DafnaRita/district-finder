package com.main.config;

import com.main.auth.DAO.UserDao;
import com.main.auth.DAO.UserHebirnate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//import org.springframework.security.core.userdetails.UserHebirnate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceStub implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("обpащение в базу");
        List auths = new ArrayList();
        auths.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserHebirnate userHebirnate = userDao.findByLogin(username);
        System.out.println("user"+userHebirnate.getLogin());
        System.out.println("pass"+userHebirnate.getPassword());
        return new User(true, true, true, true,
                username, userHebirnate.getPassword(), auths);
    }
}