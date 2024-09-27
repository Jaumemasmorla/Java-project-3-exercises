package com.javaexercises.__exercisies.security.service;

import com.javaexercises.__exercisies.security.DTO.AuthenticationRequest;
import com.javaexercises.__exercisies.security.DTO.AuthenticationResponse;
import com.javaexercises.__exercisies.security.DTO.RegisterRequest;
import com.javaexercises.__exercisies.security.model.Role;
import com.javaexercises.__exercisies.security.model.User;
import com.javaexercises.__exercisies.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequest request) {
        Role role = request.getEmail().equals("admin@example.com") ? Role.ADMIN : Role.USER;
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        userRepository.save(user);


        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("roles", List.of(role.name()));

        var jwtToken = JwtService.generateToken(extraClaims, user);
        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();


        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("roles", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());

        var jwtToken = JwtService.generateToken(extraClaims, user);
        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

}

