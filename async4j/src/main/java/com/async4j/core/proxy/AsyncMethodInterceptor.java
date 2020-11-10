package com.async4j.core.proxy;

import com.async4j.core.Async;
import com.async4j.core.util.PrimitiveDefaults;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.concurrent.CompletableFuture;

import static com.async4j.core.util.Await.await;


public class AsyncMethodInterceptor implements InvocationHandler
{
    private final Object implementation;

    public AsyncMethodInterceptor(final Object implementation)
    {
        this.implementation = implementation;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        boolean isMethodAsync = doesAsyncAnnotationExist(method);
        if (isMethodAsync)
        {
            CompletableFuture<?> completableFuture = CompletableFuture.supplyAsync(()->{
                try {
                    return method.invoke(implementation, args);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return new Exception(e);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    return new Exception(e);
                }
            });
            if ((boolean)args[0])
            {
                //waiting
                return await(completableFuture);
            }
            Class returnType = method.getReturnType();
            if (returnType.isPrimitive() && (!returnType.equals(void.class)))
            {
                return PrimitiveDefaults.getDefaultValue(returnType);
            }
            else
            {
                return null;
            }
        }
        else
        {
            //Everything else, just pass them through
            return method.invoke(implementation, args);
        }
    }


    private boolean doesAsyncAnnotationExist(AnnotatedElement element)
    {
        Annotation[] annotations = element.getAnnotations();
        for(Annotation annotation: annotations)
        {
            if (annotation instanceof Async)
            {
                return true;
            }
        }
        return false;
    }

}
