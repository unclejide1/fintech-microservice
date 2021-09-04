//package com.jide.accountservice.infrastructure.security.services;
//
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//
//@Service
//public class MyUserDetailsService implements UserDetailsService {
//
//    private UserDao userDao;
//
//    @Autowired
//    public MyUserDetailsService(UserDao userDao) {
//        this.userDao = userDao;
//    }
//
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userDao.findUserByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//        return AuthenticatedUser.build(user);
//    }
//}
