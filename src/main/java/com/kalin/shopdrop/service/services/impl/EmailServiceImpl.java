package com.kalin.shopdrop.service.services.impl;

import com.kalin.shopdrop.constants.MailConstants;
import com.kalin.shopdrop.service.models.UserServiceModel;
import com.kalin.shopdrop.service.services.EmailService;
import com.kalin.shopdrop.service.services.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final UserService userService;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, UserService userService) {
        this.javaMailSender = javaMailSender;
        this.userService = userService;
    }


    @Override
    public void sendEmail(String toUser, String fromUser, String text, String productName) throws NotFoundException {
        UserServiceModel user = this.userService.getByUsername(fromUser);
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toUser);
        message.setSubject(String.format(MailConstants.SUBJECT_MESSAGE, fromUser));
        message.setText(String.format("%s%n-----%n%s", text, String.format(MailConstants.BODY_MESSAGE, fromUser, user.getEmail(), productName)));

        javaMailSender.send(message);

    }
}
