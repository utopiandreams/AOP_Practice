package com.example.NplusOneDetector.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class NPlusOneDetector {
    private final ThreadLocal<NPlusOneLogger> currentThreadLogger;

    public NPlusOneDetector() {
        this.currentThreadLogger = new ThreadLocal<>();
    }

    @Around("execution( * com.example.NplusOneDetector.repository.ItemRepository.findById(*))")
    public Object captureProxy (final ProceedingJoinPoint joinPoint) throws Throwable {
        final Object entity = joinPoint.proceed();
        return new ProxyHandler(getLogger(), entity).getProxy();
    }

    @After("within(@org.springframework.web.bind.annotation.RestController *)")
    public void log() {
        log.info("{}", getLogger());
        currentThreadLogger.remove();
    }

    private NPlusOneLogger getLogger() {
        if(currentThreadLogger.get() == null){
            currentThreadLogger.set(new NPlusOneLogger());
        }
        return currentThreadLogger.get();
    }

}
