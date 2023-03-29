package com.metechvn.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.annotation.*;
import java.lang.reflect.Method;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface KafkaSession {

    @Aspect
    @Component
    class KafkaSessionAnnotationProcessor {

        @Before("@annotation(com.metechvn.config.KafkaSession)")
        public void evaluate(final JoinPoint joinPoint) {
            RequestContextHolder.setRequestAttributes(new KafkaRequestAttribute());


//            final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//            final Object[] args = joinPoint.getArgs();
//            final Method method = methodSignature.getMethod();
//            final Annotation[][] parameterAnnotations = method.getParameterAnnotations();// Business implementation


//            RequestContextHolder.resetRequestAttributes();
        }
    }
}

