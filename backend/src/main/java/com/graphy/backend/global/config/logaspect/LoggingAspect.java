package com.graphy.backend.global.config.logaspect;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("within(*..*Controller)")
    public void onRequest() {}

    @Around("onRequest()")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        long start = System.currentTimeMillis();
        try {
            return pjp.proceed(pjp.getArgs());
        } finally {
            log.info("Request: {} {}: {}", request.getMethod(), request.getRequestURL(), paramMapToString(request.getParameterMap()));
            long end = System.currentTimeMillis();
            Object result = pjp.proceed();
            log.info("Response: {} {}: {} ({}ms)", request.getMethod(), request.getRequestURL(), result, end - start);
        }
    }

    private String paramMapToString(Map<String, String[]> paraStringMap) {
        return paraStringMap.entrySet().stream()
                .map(entry -> String.format("%s : %s",
                        entry.getKey(), Arrays.toString(entry.getValue())))
                .collect(Collectors.joining(", "));
    }
}