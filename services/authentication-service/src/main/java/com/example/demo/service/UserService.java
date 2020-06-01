package com.example.demo.service;

import com.example.demo.dto.UserLoginDTO;
import com.example.demo.dto.UserRegistrationDTO;
import com.example.demo.model.*;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.TokenUtils;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.dto.UserTokenState;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;





    public Users register(UserRegistrationDTO userRegisterDTO) {
        Users u = this.userService.findOneByEmail(userRegisterDTO.getEmail());
        if (u != null)
            return null;

        return this.userService.save(userRegisterDTO);
    }

    public UserTokenState login(UserLoginDTO user) throws NotFoundException {
        Users u = this.findOneByEmailAndPassword(user.getEmail(), user.getPassword());
        if (u == null)
            return null;

        List<Authority> auth = this.authorityService.findAllByRoleName(u.getRole().getRole());
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), auth));

        //ubaci username(email) + password u kontext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Kreiraj token
        Users userToken = (Users) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(userToken.getEmail(), userToken.getRole().getRole());
        int expiresIn = tokenUtils.getExpiredIn();

        return new UserTokenState(jwt, (long) expiresIn, userToken.isFirstTimeLogged());
    }

    public Users findOneByEmailAndPassword(String email, String password) throws NotFoundException {
        Users user = this.userRepository.findOneByEmail(email);
        if (!this.passwordEncoder.matches(password, user.getPassword()))
            throw new NotFoundException("Not existing user");

        return user;
    }

    public Users findOneByEmail(String email) {
        return this.userRepository.findOneByEmail(email);
    }

    public Users save(UserRegistrationDTO user) {
        Users u = Users.builder().role(UserRole.valueOf("USER"))
                .firstName(user.getFirstName()).lastName(user.getLastName()).email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword())).build();

        return this.userRepository.save(u);
    }

    public boolean CheckEmail(String email) {
        Users u = this.findOneByEmail(email);

        if (u == null)
            return false;
        else
            return true;
    }
}