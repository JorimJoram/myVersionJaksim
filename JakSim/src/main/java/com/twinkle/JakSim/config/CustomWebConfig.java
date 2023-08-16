package com.twinkle.JakSim.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomWebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String connectPath = "/image/**";
        String resourcePath = "file:////Users/janghyolim/code/JakSim2.0/JakSim/src/main/resources/static/image/";

        registry.addResourceHandler(connectPath).addResourceLocations(resourcePath);
    }
}
