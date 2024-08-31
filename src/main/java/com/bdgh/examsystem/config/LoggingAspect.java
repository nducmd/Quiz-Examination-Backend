package com.bdgh.examsystem.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("execution(* com.bdgh.examsystem.service.*.*(..))")
    public void serviceLayer() {}

    @Before("serviceLayer()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Executing method: {}", joinPoint.getSignature().toShortString());
        log.info("Arguments: {}", joinPoint.getArgs());
    }

    @Around("serviceLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed(); // Thực thi phương thức
            long timeTaken = System.currentTimeMillis() - startTime;
            log.info("Method {} executed in {} ms", joinPoint.getSignature().toShortString(), timeTaken);
            log.info("Result: {}", result);
            return result;
        } catch (Throwable throwable) {
            long timeTaken = System.currentTimeMillis() - startTime;
            log.error(
                    "Method {} executed in {} ms with exception: {}",
                    joinPoint.getSignature().toShortString(),
                    timeTaken,
                    throwable.getMessage());
            throw throwable; // Rethrow exception để tiếp tục xử lý
        }
    }
}
