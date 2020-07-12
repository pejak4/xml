package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.model.*;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.Acl;
import com.example.demo.security.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Acl acl;
/*
    public void setAuthenticationManager(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
*/

    public Users register(UserRegistrationDTO userRegisterDTO) {
        Users u = this.userService.findOneByEmail(userRegisterDTO.getEmail());
        if (u != null) {
            log.error("Email: " + userRegisterDTO.getEmail() + " has already been taken by other user.");
            return null;
        }

        return this.userService.save(userRegisterDTO);
    }

    public UserTokenState login(UserLoginDTO user) {
        Users u = this.findOneByEmailAndPassword(user.getEmail(), user.getPassword());
        if (u == null) {
            log.error("Client entered bad credentials.");
            return null;
        }

        List<Authority> auth = this.authorityService.findAllByRoleName(u.getRole().getRole());
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), auth));

        //ubaci username(email) + password u kontext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Kreiraj token
        Users userToken = (Users) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(userToken.getEmail(), userToken.getRole().getRole());
        int expiresIn = tokenUtils.getExpiredIn();
        log.info("User: " + u + " has successfully logged.");

        return new UserTokenState(jwt, (long) expiresIn, userToken.isFirstTimeLogged());
    }

    public Users findOneByEmailAndPassword(String email, String password) {
        Users user = this.userRepository.findOneByEmail(email);
        if (!BCrypt.checkpw(password, user.getPassword())) {
            log.warn("Client has entered bad password");
            return null;
        }

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
                .firstName(user.getFirstName()).lastName(user.getLastName()).email(user.getEmail()).enabled(false)
                .password(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12))).build();
        log.info("New user: " + u + " has been registered");

        return this.userRepository.save(u);
    }

    public Users update(String mail){
        Users u = this.findOneByEmail(mail);
        u.setEnabled(true);
        return this.userRepository.save(u);
    }

    public void incrementAddNumber(String email) {
        Users user = this.userRepository.findOneByEmail(email);
        Integer number = user.getAddNumber() + 1;
        user.setAddNumber(number);
        log.info("User " + user + " has added " + number + " advertisements until now");
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
            log.info("User: " + u + " has been blocked from now!");
        } else {
            u.setEnabled(true);
            log.info("User: " + u + " has been unblocked from now!");
        }

        this.userRepository.save(u);

        return true;
    }

    public Boolean deleteUser(UserDeleteDTO userId) {
        this.userRepository.deleteRequest(Long.parseLong(userId.getId()));
        log.info("Some user has been deleted");
        return true;
    }

    public void setRole(UserDeleteDTO userId) {
        Users u = this.userRepository.findOneById(Long.parseLong(userId.getId()));
        if(u.getRole().getRole().equals("ADMIN")) {
            u.setRole(UserRole.USER);
        } else {
            u.setRole(UserRole.ADMIN);
        }
        this.userRepository.save(u);
    }

    public List<Users> findAllByEnabled(Boolean enabled) {
        List<Users> users = this.userRepository.findAllByEnabled(enabled);
        for(Users u : users) {
            u.setFirstName(StringEscapeUtils.escapeHtml4(u.getFirstName()));
            u.setLastName(StringEscapeUtils.escapeHtml4(u.getLastName()));
            u.setEmail(StringEscapeUtils.escapeHtml4(u.getEmail()));
            u.setPassword(StringEscapeUtils.escapeHtml4(u.getPassword()));
        }
        return users;
    }

    public void addRestorePermissions(String role) throws IOException {
        this.acl.addRestorePermissionsAcl(role);
    }

    public void incrementNumOfDeclineRentalRequest(UserSetStateDTO userSetStateDTO) {
        Users u = this.findOneById(Long.parseLong(userSetStateDTO.getId()));
        u.setNumOfDeclineRentalRequest(u.getNumOfDeclineRentalRequest() + 1);

        this.userRepository.save(u);
    }
}