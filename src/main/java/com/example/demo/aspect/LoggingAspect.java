package com.example.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut(value = "execution(* com.example.demo.service..*(..))")
    public void allServiceMethods() {
        // Matches all methods in com.example.demo.service package and subpackages
    }

    @Around("allServiceMethods()")
    public Object logExecutionDetails(ProceedingJoinPoint joinPoint) throws Throwable {
        //Capture method details
        //target --> class
        //signature --> method
        //args --> arguments
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
//        String methodArguments = Arrays.stream(joinPoint.getArgs()).map(String::valueOf).collect(Collectors.joining(","));

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        Class<?>[] parameterTypes = methodSignature.getParameterTypes();
        Object[] parameterValues = joinPoint.getArgs();
        String formattedArguments = formatArguments(parameterNames, parameterTypes, parameterValues);

        long startTime = System.currentTimeMillis();
        log.info("Starting execution: {}.{} with arguments: {}", className, methodName, formattedArguments);

        Object result = null;
        try {
            //Proceed with method execution
            result = joinPoint.proceed();
            log.info("Execution successful: {}.{}", className, methodName);
        } catch (Throwable e) {
            log.error("Exception in {}.{}: {}", className, methodName, e.getMessage(), e);
            // Rethrow the exception to propagate it to GlobalExceptionHandler
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("Execution completed: {}.{}({}) Time taken: {} ms", className, methodName, formattedArguments, (endTime - startTime));
        }
        return result; //return the output of the methods
    }

    private String formatArguments(String[] parameterNames, Class<?>[] parameterTypes, Object[] parameterValues) {
        StringBuilder formattedArguments = new StringBuilder();
        for (int i = 0; i < parameterNames.length; i++) {
            formattedArguments.append(parameterTypes[i].getSimpleName())
                    .append(" ")
                    .append(parameterNames[i])
                    .append("=")
                    .append(parameterValues[i]);
            if (i < parameterNames.length - 1) {
                formattedArguments.append(", ");
            }
        }
        return formattedArguments.toString();
    }
}
