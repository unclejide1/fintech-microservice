package com.jide.accountservice.infrastructure.security.services;



import com.jide.accountservice.domain.dao.UserDao;
import com.jide.accountservice.domain.entities.User;
import com.jide.accountservice.infrastructure.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserDao userDao;

    @Autowired
    public MyUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with Email: " + email));
        return AuthenticatedUser.build(user);
    }
}
