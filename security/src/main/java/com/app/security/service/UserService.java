package com.app.security.service;


import com.app.security.entity.UserCredential;
import com.app.security.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private JWTService jwtService;




    @Autowired
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(13);

    public UserCredential register(UserCredential userCredential){
        userCredential.setPassword(encoder.encode(userCredential.getPassword()));
        return userCredentialRepository.save(userCredential);
    }


    public String verify(UserCredential user) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        if(authentication.isAuthenticated()){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            String token = jwtService.generateToken(user.getUsername(),roles);
            return token;
        }
        else{
            throw new RuntimeException("Invalid username or password");
        }
    }
}
