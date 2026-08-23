package com.mfano.mpos.services.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mfano.mpos.config.CustomUserDetails;
import com.mfano.mpos.models.security.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomDetailService implements UserDetailsService {
    private final UserService userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        
        return new CustomUserDetails(user);
    }
}
