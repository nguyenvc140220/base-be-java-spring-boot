package com.metechvn.logging;

import com.metechvn.common.tenant.Domain2TenantInterceptor;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    LogInterceptor logInterceptor;

    private final Domain2TenantInterceptor domain2TenantInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor);
        registry.addInterceptor(domain2TenantInterceptor);
    }

    @Override
    public void addCorsMappings(@NotNull CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);

        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*");
    }
}
