package com.example.demo.controller;

import com.example.demo.model.Mail;
import com.example.demo.service.MailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender mailSender;

    @Override
    public void sendMail(Mail mail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(mail.getMailSubject());
            //"technicalkeeda.com"
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom(), "Zahtev od "+mail.getMailFrom()));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent(),true);

            mailSender.send(mimeMessageHelper.getMimeMessage());

        }catch(MessagingException e){
            e.printStackTrace();
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }


    }
}
