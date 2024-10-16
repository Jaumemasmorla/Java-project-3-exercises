package com.javaexercises.client.security.service;

import com.javaexercises.client.security.DTO.AuthenticationRequest;
import com.javaexercises.client.security.DTO.AuthenticationResponse;
import com.javaexercises.client.clients.DTO.RegisterRequest;
import com.javaexercises.client.clients.model.Role;
import com.javaexercises.client.clients.model.User;
import com.javaexercises.client.clients.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
        List<String> adminEmails = Arrays.asList("admin@example.com", "admin@example1.com");
        Role role = adminEmails.contains(request.getEmail()) ? Role.ADMIN : Role.USER;

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
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));


            System.out.println("Authenticated user: " + user.getEmail());

            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("roles", user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList());

            var jwtToken = JwtService.generateToken(extraClaims, user);
            return AuthenticationResponse.builder()
                    .jwtToken(jwtToken)
                    .build();

        } catch (Exception e) {

            System.err.println("Authentication failed: " + e.getMessage());
            throw new BadCredentialsException("Invalid email or password");
        }
    }


}

