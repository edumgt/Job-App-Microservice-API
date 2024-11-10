package com.app.security.controller;


import com.app.security.entity.UserCredential;
import com.app.security.service.JWTService;
import com.app.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    private JWTService jwtService;

    @PostMapping("/register")
    public UserCredential register(@RequestBody UserCredential userCredential){
        return userService.register(userCredential);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserCredential user){
        return userService.verify(user);
    }

    @GetMapping("/greet")
    public String greet(){
        return "Hello";
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token, UserDetails userDetails){
        if(jwtService.validateToken(token,userDetails))
            return "Token is Valid";
        else
            return "Token is invalid";
    }

}
