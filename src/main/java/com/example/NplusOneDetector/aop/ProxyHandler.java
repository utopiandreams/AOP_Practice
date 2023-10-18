package com.example.NplusOneDetector.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class ProxyHandler implements MethodInterceptor {
    private final NPlusOneLogger logger;
    private final Object entity;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        final Object result = invocation.proceed();

        String resultName = result.getClass().getName();
        System.out.println("resultName = " + resultName);
        Field[] declaredFields = result.getClass().getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field ->
                log.info("fieldName = {}, fieldType = {}", field.getName(), field.getType().getName())
        );
        return result;
    }

    public Object getProxy() {
        if(entity instanceof Optional<?>) {
            ProxyFactory proxyFactory = new ProxyFactory(((Optional<?>) entity).get());
            proxyFactory.addAdvice(this);
            System.out.println("entity = " + entity);
            return Optional.ofNullable(proxyFactory.getProxy());
        }
        return null;
    }
}
