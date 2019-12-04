package com.kalin.shopdrop.service.services;

import javassist.NotFoundException;

public interface EmailService {

    void sendEmail(String toUser, String fromUser, String text, String productName) throws NotFoundException;

}
