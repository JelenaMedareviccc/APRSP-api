package com.example.project.services.implementation;

import com.example.project.exception.NotFoundException;
import com.example.project.models.Authuser;
import com.example.project.repositories.AuthuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This class is used to define custom loadUserByUsername method. The UserDetailsService interface is used to retrieve user-related data.
 * It is used by the DaoAuthenticationProvider to load details about the user during authentication.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private AuthuserRepository authuserRepository;

    @Autowired
    public UserDetailsServiceImpl(AuthuserRepository authuserRepository) {
        this.authuserRepository = authuserRepository;
    }

    /**
     * This method is used to find a user entity based on the username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final Authuser user = authuserRepository.findByUsername(username);
        if (user == null) {
            throw new NotFoundException();

        }
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .authorities(user.getRole().getName())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}