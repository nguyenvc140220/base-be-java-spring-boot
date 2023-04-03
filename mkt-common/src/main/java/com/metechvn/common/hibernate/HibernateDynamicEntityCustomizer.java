package com.metechvn.common.hibernate;

import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HibernateDynamicEntityCustomizer implements HibernatePropertiesCustomizer {

    private final ApplicationEventPublisher applicationEventPublisher;

    public HibernateDynamicEntityCustomizer(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(
                "hibernate.session_factory.interceptor",
                new DynamicEntityInterceptor(applicationEventPublisher)
        );
    }
}
