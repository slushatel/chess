package com.javamonkeys.security;

import com.javamonkeys.dao.user.IUserDao;
import com.javamonkeys.dao.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Inject
    IUserDao userDao;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userDao.getUserByEmail(email);

        if (user != null) {

            Set<GrantedAuthority> roles = new HashSet();
            roles.add(new SimpleGrantedAuthority(UserRoleEnum.ROLE_USER.name()));

            UserDetails userDetails =
                    new org.springframework.security.core.userdetails.User(user.getEmail(),
                            user.getPassword(),
                            roles);

            return userDetails;

        } else {
            throw new UsernameNotFoundException("No user with email '" + email + "' found!");
        }
    }
}
