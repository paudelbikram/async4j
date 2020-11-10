package com.async4j.core.proxy;

import java.lang.reflect.Proxy;

public class AsyncProxyFactory
{
    public static<T> T getDynamicProxy( T t, Class<? super T> interfaceType) {
        AsyncMethodInterceptor handler = new AsyncMethodInterceptor(t);
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(),
                new Class<?>[]{interfaceType}, handler
        );
    }



}
