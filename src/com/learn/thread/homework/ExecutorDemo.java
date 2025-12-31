package com.learn.thread.homework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.execute(new PrintChar(10,'a'));
        executor.execute(new PrintChar(10,'b'));
        executor.execute(new PrintNum(10));

        executor.shutdown();
    }
}
