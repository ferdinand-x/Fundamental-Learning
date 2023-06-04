package com.paradise.code.util;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CompletableFutureTest {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Integer> list = IntStream.rangeClosed(1, 1000).boxed().collect(Collectors.toList());

        CompletableFuture<Void> allCF = CompletableFuture.allOf(list
                .stream()
                .map(v -> CompletableFuture.supplyAsync(() -> v, executor))
                .toArray(CompletableFuture[]::new));

        allCF.join();
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);
    }
}
