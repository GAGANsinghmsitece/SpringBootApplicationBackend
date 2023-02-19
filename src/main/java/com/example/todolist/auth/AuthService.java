package com.example.todolist.auth;

import com.example.todolist.User.Role;
import com.example.todolist.User.User;
import com.example.todolist.User.UserRepository;
import com.example.todolist.User.UserService;
import com.example.todolist.config.JWTService;
import com.example.todolist.exception.ApiRequestException;
import com.example.todolist.profile.Profile;
import com.example.todolist.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ProfileService profileService;
    private final UserService userService;
    public AuthenticationResponse register(RegisterRequest request) {
        if(userService.isUserAlreadyExists(request.getEmail())){
            throw new ApiRequestException("User Already exists with this email!!!");
        }
        User user = User
                .builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        Profile profile = profileService.createProfileByUser(user);
        String token = jwtService.generate_token(user);
        return AuthenticationResponse
                .builder()
                .status(true)
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticate(RegisterRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = this.userRepository.findByEmail(request.getEmail()).orElseThrow( ()->new UsernameNotFoundException("User Not Found!!!"));
        String token = jwtService.generate_token(user);
        return AuthenticationResponse
                .builder()
                .status(true)
                .token(token)
                .build();
    }
}
