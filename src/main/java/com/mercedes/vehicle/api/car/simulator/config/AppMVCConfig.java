package com.mercedes.vehicle.api.car.simulator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC ayarlarını barındırır.
 */
@Configuration
public class AppMVCConfig implements WebMvcConfigurer {

    @Autowired
    private CustomHandlerInterceptorAdapter requestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor);
    }
}
