package com.example.demo.controller;

import com.example.demo.KtbackendApplication;
import com.example.demo.dto.*;
import com.example.demo.model.Mail;
import com.example.demo.model.Users;
import com.example.demo.security.Acl;
import com.example.demo.service.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Acl acl;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/login")
    public ResponseEntity<UserTokenState> login(@Valid @RequestBody UserLoginDTO user) throws  NotFoundException {
        return new ResponseEntity<>(this.userService.login(user), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/mail")
    public ResponseEntity<?> mailSestre(@RequestBody MailView mailView){

        //mailView.setMailFrom("jovan.jenjic@gmail.com");
       // mailView.setDodatak("Poslaoo");
       // mailView.setMailTo("Od kuma");
        System.out.println("USO JE");
        Mail mail = new Mail();
        mail.setMailFrom(mailView.getMailFrom());
        mail.setMailTo("koske.koske035@gmail.com");
        mail.setMailSubject("Zahtev za registraciju od "+mailView.getMailFrom());
        mail.setMailContent("Prihvacen je zahtev za registraciju od "+mailView.getMailTo()+" "+mailView.getDodatak()+"<html><body><a href='http://localhost:3000/login/"+mailView.getMailFrom()+"'>Link za prihvtanje</a></body></html>");
        MailService mailService = KtbackendApplication.getCtx();
        mailService.sendMail(mail);


        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/enable")
    public ResponseEntity<?> enablee(@Valid @RequestBody UserEmailDTO mail) throws  NotFoundException {
        return new ResponseEntity<>(this.userService.update(mail.getUserEmail()), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/registration")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationDTO userRegisterView) {
        return new ResponseEntity<>(this.userService.register(userRegisterView), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/email-check")
    public ResponseEntity<?> checkEmail(@RequestBody UserCheckEmailDTO user){
        return new ResponseEntity<>(this.userService.CheckEmail(user.getEmail()), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/blockedUser")
    public ResponseEntity<?> blockedUser(@RequestBody UserCheckEmailDTO user){
        return new ResponseEntity<>(this.userService.CheckBlock(user.getEmail()), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path= "/setStateUser")
    public ResponseEntity<?> setStateUser(@RequestBody UserSetStateDTO userId) {
        return new ResponseEntity<>(this.userService.setStateUser(userId), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path= "/getAllUsersByEnabled")
    public ResponseEntity<?> getAllUsersByEnabled(@RequestBody UserEnabledDTO enabled) {
        return new ResponseEntity<>(this.userService.findAllByEnabled(enabled.getEnabled()), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path= "/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestBody UserDeleteDTO userId) {
        return new ResponseEntity<>(this.userService.deleteUser(userId), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path= "/getLoggedUser")
    public ResponseEntity<?> getLoggedUserId(@RequestBody UserEmailDTO email) {
        System.out.println("Prosledjeni mejl je "+email.getUserEmail());
        return new ResponseEntity<>(this.userService.findOneByEmail(email.getUserEmail()), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path = "/getUserById")
    public ResponseEntity<?> getUserById(@RequestParam Long id) {
        return new ResponseEntity<>(this.userService.findOneById(id), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(path = "/incrementAddNumber")
    public ResponseEntity<?> incrementAddNumber(@RequestBody UserEmailDTO email) {
        this.userService.incrementAddNumber(email.getUserEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/aclSecurity")
    public ResponseEntity<?> aclSecurity(@RequestBody AclDTO aclDTO) throws Exception {
        this.acl.addRestorePermissionsAcl(aclDTO.getRole());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/aclSecurityLogout")
    public ResponseEntity<?> aclSecurityLogout() throws Exception {
        this.acl.addRestorePermissionsAcl("USER");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}