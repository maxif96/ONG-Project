package com.alkemy.ong.auth.controller;

import com.alkemy.ong.auth.dto.UserDTO;
import com.alkemy.ong.auth.service.UserDetailsCustomService;
import com.alkemy.ong.auth.utils.JwUtils;
import com.alkemy.ong.service.impl.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    private UserDetailsCustomService userDetailsCustomService;
    private AuthenticationManager authenticationManager;
    private JwUtils jwUtils;
    private MailServiceImpl mailService;


    @Autowired
    public UserAuthController(
            UserDetailsCustomService userDetailsCustomService,
            AuthenticationManager authenticationManager,
            MailServiceImpl mailService,
            JwUtils jwUtils) {
        this.mailService = mailService;
        this.userDetailsCustomService = userDetailsCustomService;
        this.authenticationManager = authenticationManager;
        this.jwUtils = jwUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register (@Valid @RequestBody UserDTO user) throws Exception {

        UserDTO userResponse = userDetailsCustomService.save(user);
        mailService.sendEmailRegisteredUser(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

}
