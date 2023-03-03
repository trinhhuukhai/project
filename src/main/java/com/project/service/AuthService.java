package com.project.service;

import com.project.model.Cart;
import com.project.model.Role;
import com.project.model.Token;
import com.project.model.User;
import com.project.repository.UserRepository;
import com.project.dto.request.AuthRequest;
import com.project.dto.request.RegisterRequest;
import com.project.dto.response.AuthResponse;
import com.project.response.ResponseCode;
import com.project.response.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private  final  JwtService jwtService;

    private final TokenService tokenService;

    private  final AuthenticationManager authenticationManager;
    public AuthResponse register(RegisterRequest request){
        var user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .address((request.getAddress()))
                .role(request.getRole())
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public  AuthResponse authenticate(AuthRequest request){

        authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
                  request.getUsername(),
                  request.getPassword()
          )
        );
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();


        var jwtToken = jwtService.generateToken(user);


        // logout tất cả các nơi đang dùng
        List<Token> listOldToken = tokenService.findAllByUsername(user.getUsername());
        for (Token token: listOldToken) {
            tokenService.deleteToken(token.getValue());
        }

        // Luu token vào database
        Token tokenObj = new Token();
        tokenObj.setUsername(user.getUsername());
        tokenObj.setUserId(user.getId());
        tokenObj.setValue(jwtToken);
        tokenService.save(tokenObj);

        return AuthResponse.builder()
                .token(jwtToken)
//                .user(user)
                .roleName(String.valueOf(user.getRole()))
                .name(user.getName())
                .username(user.getUsername())
                .address(user.getAddress())
                .build();
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }



}
