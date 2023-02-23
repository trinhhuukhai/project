package com.project.controller;

import com.project.dto.request.AuthRequest;
import com.project.dto.request.RegisterRequest;
import com.project.dto.response.AuthResponse;
import com.project.model.Cart;
import com.project.model.User;
import com.project.response.ResponseCode;
import com.project.response.ResponseResult;
import com.project.service.AuthService;
import com.project.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")

public class AuthController {

    public final AuthService service;

    @Autowired
    private TokenService tokenService;

//    @PostMapping("/register")
    @RequestMapping(value = "/v1/admin/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request){
        return ResponseEntity.ok(service.authenticate(request));

    }
//    @CrossOrigin("http://localhost:3000")
    @GetMapping({"/getAllUser"})
    @PreAuthorize("hasAuthority('ADMIN')")
    List<User> getAll(){
        return (List<User>) service.getAllUser();
    }


    @GetMapping("/logout")
    public ResponseResult logout(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (org.springframework.util.StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            tokenService.deleteToken(bearerToken.substring(7));
            return ResponseResult.success("Thành công");
        }
        return new ResponseResult(ResponseCode.ERROR);
    }
}
