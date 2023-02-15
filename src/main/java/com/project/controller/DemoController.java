package com.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping({"/api/admin/forAdmin"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("ok, hello xin chao cac ban");
    }
}
