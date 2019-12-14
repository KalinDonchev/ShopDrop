package com.kalin.shopdrop.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FavIconInterceptor extends HandlerInterceptorAdapter {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String link = "https://res.cloudinary.com/kalindonchev-cloud/image/upload/v1576338996/zt5opuucuhnygfszrxx8.png";

        if (modelAndView != null) {
            modelAndView.addObject("favicon", link);
        }
    }
}
