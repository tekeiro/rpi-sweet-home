package org.keirobm.rpisweethome.runner;


import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class TaskRunner {

    private final ExecutorService executorService;

    public <R> CompletableFuture<R> submit(Supplier<R> task) {
        return CompletableFuture.supplyAsync(task, executorService);
    }

    public CompletableFuture<Void> submit(Runnable task) {
        return CompletableFuture.runAsync(task, executorService);
    }

}
