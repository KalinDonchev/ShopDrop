package com.kalin.shopdrop.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mail")
public class MailController extends BaseController{

    @GetMapping("/send")
    public ModelAndView sendEmail(){
        return super.view("mail/send-email");
    }




}
