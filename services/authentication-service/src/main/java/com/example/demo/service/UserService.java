package com.example.demo.service;

import com.example.demo.dto.*;
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

    public Users findOneById(Long id) {
        return this.userRepository.findOneById(id);
    }

    public Users save(UserRegistrationDTO user) {
        Users u = Users.builder().role(UserRole.valueOf("USER"))
                .firstName(user.getFirstName()).lastName(user.getLastName()).email(user.getEmail()).enabled(true)
                .password(passwordEncoder.encode(user.getPassword())).build();

        return this.userRepository.save(u);
    }

    public void incrementAddNumber(String email) {
        Users user = this.userRepository.findOneByEmail(email);
        Integer number = user.getAddNumber() + 1;
        user.setAddNumber(number);
        this.userRepository.save(user);
    }

    public boolean CheckEmail(String email) {
        Users u = this.findOneByEmail(email);

        if (u == null)
            return false;
        else
            return true;
    }

    public boolean CheckBlock(String email) {
        Users u = this.findOneByEmail(email);

        if (u != null) {
            if (u.isEnabled())
                return true;
            else
                return false;
        }
        return true;
    }

    public Boolean setStateUser(UserSetStateDTO userId) {
        Users u = this.userRepository.findOneById(Long.parseLong(userId.getId()));

        if(u.getEnabled() == true) {
            u.setEnabled(false);
        } else {
            u.setEnabled(true);
        }

        this.userRepository.save(u);

        return true;
    }

    public Boolean deleteUser(UserDeleteDTO userId) {
        this.userRepository.deleteRequest(Long.parseLong(userId.getId()));

        return true;
    }

    public List<Users> findAllByEnabled(Boolean enabled) {
        List<Users> users = this.userRepository.findAllByEnabled(enabled);
        return users;
    }
}