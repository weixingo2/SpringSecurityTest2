package com.springSecurity.controller.MailAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class MailController {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    @RequestMapping("/send")
    public String send(String to,String subject,String context){

        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();

        simpleMailMessage.setFrom(from);

        simpleMailMessage.setTo(to);

        simpleMailMessage.setText(context);
        simpleMailMessage.setSubject(subject);

        javaMailSender.send(simpleMailMessage);

        return "success";
    }

}
