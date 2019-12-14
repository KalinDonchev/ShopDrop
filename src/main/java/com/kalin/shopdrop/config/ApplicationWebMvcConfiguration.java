package com.kalin.shopdrop.config;

import com.kalin.shopdrop.web.interceptors.FavIconInterceptor;
import com.kalin.shopdrop.web.interceptors.TitleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationWebMvcConfiguration implements WebMvcConfigurer {

    private final TitleInterceptor titleInterceptor;
    private final FavIconInterceptor faviconInterceptor;

    @Autowired
    public ApplicationWebMvcConfiguration(TitleInterceptor titleInterceptor, FavIconInterceptor faviconInterceptor) {
        this.titleInterceptor = titleInterceptor;
        this.faviconInterceptor = faviconInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.titleInterceptor);
        registry.addInterceptor(this.faviconInterceptor);
    }
}
