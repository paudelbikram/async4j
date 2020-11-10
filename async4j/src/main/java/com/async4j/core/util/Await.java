package com.async4j.core.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public final class Await
{
    public static<T> T await(final CompletableFuture<T> completableFuture ) throws ExecutionException, InterruptedException
    {
        return completableFuture.get();
    }
}
