package com.futures.futures.service;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.springframework.stereotype.Component;

@Component
public class FutureService {

    public class t1 extends Thread {

        @Override
        public void run () {
            System.out.println("t1 thread");
        }
    }

    public class t2 implements Runnable {

        t1 x = new t1();

        @Override
        public void run() {
            System.out.println("t2 runnable");
            x.start();
        }
        
    }

    public class t3 implements Callable<Integer> {

        t2 y = new t2();

        @Override
        public Integer call() throws Exception {
            Thread z = new Thread(y);
            System.out.println("t3 callable");
            z.start();
            return 1;
        }
        
    }

    public class t4 implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.println("t3 callable");
            return 1;
        }
        
    }

    public void startThreads () throws Exception {
        
        // Without future
        Integer b = new t3().call();
        System.out.println("Without futures : " + b);

        // With futures using future task
        FutureTask<Integer> ft = new FutureTask<>(new t4());
        Thread t = new Thread(ft);
        t.start();
        System.out.println("With futures using future task : " + ft.get());

        // With futures using executors
        ExecutorService e = Executors.newFixedThreadPool(3);
        Future<Integer> val = e.submit(new t4());
        System.out.println("With futures using executors : " + val.get());

        // Using completable futures
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> "World");

        CompletableFuture<String> combined = cf1.thenCombine(cf2, (a1, a2) -> a1 + " " + a2);
        System.out.println("Completable future : " + combined.get());

        System.out.println("End");
    }
    
}
