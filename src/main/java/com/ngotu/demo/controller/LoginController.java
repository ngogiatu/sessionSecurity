package com.ngotu.demo.controller;

import com.ngotu.demo.model.UserLogin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created at 24/07/2024 by Ngo Tu
 *
 * @author: Ngo Tu
 */
@RestController
@RequestMapping("api")
public class LoginController {
    // http://localhost:8080/api/get-user
    @GetMapping("tu")
    public ResponseEntity<?> getUser(){

        UserLogin u = UserLogin.builder()
                .username("ngo gia tu")
                .password("123456")
                .roles(new String[]{"ADMIN"})
                .build();
        return ResponseEntity.ok(u);
    }
    // http://localhost:8080/api/duong-dan-cua-tu
    @GetMapping("trang")
    public ResponseEntity<?> getTu(){
        UserLogin u = UserLogin.builder()
                .username("Em Trang")
                .password("123456")
                .roles(new String[]{"TRANG"})
                .build();
        return ResponseEntity.ok(u);
    }

    // lấy dữ liệu ng dùng từ security context
    @GetMapping (value = "/user")
    public String currentUserName(Principal principal) {
        return principal.getName();
    }
}
