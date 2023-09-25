package com.Articles.controller;

import com.Articles.entity.User;
import com.Articles.payload.JWTAuthResponse;
import com.Articles.payload.LoginDto;
import com.Articles.payload.SignUpDto;
import com.Articles.repository.UserRepository;
import com.Articles.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;

    //http://localhost:8080/api/auth/signin
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto
                                                                    loginDto){
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    //http://localhost:8080/api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        // add check for username exists in a DB
        Boolean usernameExists = userRepository.existsByUsername(signUpDto.getUsername());
        if(usernameExists){
            return new ResponseEntity<>("Username is already taken!",HttpStatus.BAD_REQUEST);
        }
        // add check for email exists in DB
        Boolean emailExists = userRepository.existsByEmail(signUpDto.getEmail());
        if(emailExists){
            return new ResponseEntity<>("Email is already taken!",HttpStatus.BAD_REQUEST);
        }
        // create user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully",HttpStatus.OK);
    }
}
