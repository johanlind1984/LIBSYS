package com.newtongroup.library.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class imageConfig {

    @Configuration
    public class WebMvcConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry
                    .addResourceHandler("@{/images/libsys.png}")
                    .addResourceLocations("file:static/images/libsys.png")
                    .setCachePeriod(0);
        }
    }
}
