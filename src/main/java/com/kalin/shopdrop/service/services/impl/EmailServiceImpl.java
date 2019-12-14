package com.kalin.shopdrop.service.services.impl;

import com.kalin.shopdrop.constants.MailConstants;
import com.kalin.shopdrop.service.models.LogServiceModel;
import com.kalin.shopdrop.service.models.UserServiceModel;
import com.kalin.shopdrop.service.services.EmailService;
import com.kalin.shopdrop.service.services.LogService;
import com.kalin.shopdrop.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final UserService userService;
    private final LogService logService;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, UserService userService, LogService logService) {
        this.javaMailSender = javaMailSender;
        this.userService = userService;
        this.logService = logService;
    }


    @Override
    public void sendEmail(String toUser, String fromUser, String text, String productName) {
        UserServiceModel user = this.userService.getByUsername(fromUser);
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toUser);
        message.setSubject(String.format(MailConstants.SUBJECT_MESSAGE, fromUser));
        message.setText(String.format("%s%n-----%n%s", text, String.format(MailConstants.BODY_MESSAGE, fromUser, user.getEmail(), productName)));

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setDescription("Email sent");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.seedLogInDB(logServiceModel);

        javaMailSender.send(message);

    }
}
