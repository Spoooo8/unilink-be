package com.unilink.auth_server.config;

import com.unilink.auth_server.entity.AuthProfile;
import com.unilink.auth_server.repository.AuthProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnilinkUserDetailsService implements UserDetailsService {

    private final AuthProfileRepository authProfileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthProfile authProfile = authProfileRepository.findByEmail(username).orElseThrow(() -> new
                UsernameNotFoundException("User details not found for the user: " + username));
        List<GrantedAuthority> authorities = authProfile.getAuthorities().stream().map(authority -> new
                SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
        return new User(authProfile.getEmail(), authProfile.getPwd(), authorities);
    }

}