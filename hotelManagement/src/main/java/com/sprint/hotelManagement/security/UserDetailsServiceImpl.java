package com.sprint.hotelManagement.security;

import com.sprint.hotelManagement.entity.User;
import com.sprint.hotelManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Loading user details for: " + username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("User not found: " + username);
                    return new UsernameNotFoundException("User Not Found with username: " + username);
                });
        System.out.println("User found: " + user.getUsername() + ", Password: " + user.getPassword());
        System.out.println("Roles: " + user.getRoles());

        return UserDetailsImpl.build(user);
    }
}
