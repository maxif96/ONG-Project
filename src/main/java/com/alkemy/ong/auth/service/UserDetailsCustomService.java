package com.alkemy.ong.auth.service;

import com.alkemy.ong.auth.dto.request.AuthenticationRequest;
import com.alkemy.ong.auth.dto.response.Jwt;
import com.alkemy.ong.auth.dto.request.UserDTO;
import com.alkemy.ong.auth.service.mapper.UserAuthMapper;
import com.alkemy.ong.auth.utils.JwUtils;
import com.alkemy.ong.exception.EmailAlreadyExists;
import com.alkemy.ong.model.Users;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.MailService;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.amazonaws.services.kms.model.AlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

@Service
public class UserDetailsCustomService implements UserDetailsService {

    @Autowired
    private UserAuthMapper userMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private JwUtils jwUtils;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Users user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with that email not found."));

        Collection<GrantedAuthority> authorities = Collections
                .singleton(new SimpleGrantedAuthority(user.getRole().getName()));

        return new User(user.getEmail(), user.getPassword(), authorities);

    }

    @Transactional
    public Jwt save (UserDTO userDTO) throws IOException, EmailAlreadyExists {
        String encryptPass = passwordEncoder.encode(userDTO.getPassword());
        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()) throw new EmailAlreadyExists("Email is already in use");
        Users user = userMapper.userDTOtoEntity(userDTO);
        user.setPassword(encryptPass);
        user.setRole(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
        mailService.sendEmailRegisteredUser(userDTO.getEmail());
        String jwt = jwUtils.generateToken(loadUserByUsername(user.getEmail()));

        return Jwt.builder()
                .token(jwt)
                .build();
    }

    public Jwt authentication (AuthenticationRequest authenticationRequest){
        if (!userRepository.existsByEmail(authenticationRequest.getEmail())) throw new BadCredentialsException("Email doesn't exist");
        UserDetails userDetails;
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
            userDetails = (UserDetails) auth.getPrincipal();
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Email or password incorrect", e);
        }

        final String jwt =  jwUtils.generateToken(userDetails);

        return Jwt.builder()
                .token(jwt)
                .build();
    }
}
