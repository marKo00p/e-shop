package com.shopy.trainshop.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class ResourcePathConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/javascript/**").addResourceLocations("classpath:/static/javascript/");
        registry.addResourceHandler("/node_modules/**").addResourceLocations("classpath:/static/node_modules/");
    }
}
