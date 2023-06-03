package com.graphy.backend.global.config.logaspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("within(*..*Controller) && !within(*..JobController)")
    public void onRequest() {}

    @Around("onRequest()")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        long start = System.currentTimeMillis();
        log.info("Request: {} {}: {}", request.getMethod(), request.getRequestURL(), Arrays.toString(pjp.getArgs()));

        Object result = pjp.proceed();
        long end = System.currentTimeMillis();
        String status = result.toString().split(",")[0].substring(1);
        log.info("Response: {}:{}ms", status, end - start);
        return result;
    }
}