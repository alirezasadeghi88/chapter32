package com.learn.thread.homework;

import java.util.concurrent.Semaphore;


 class Account {
    private static Semaphore semaphore = new Semaphore(1);
    private int balance = 0;

    public int getBalance() {
        return balance;
    }

    public void deposit(int amount){
        try {
            semaphore.acquire();
            int newBalance = balance + amount;
            Thread.sleep(5);
        }catch (InterruptedException ex){

        }finally {
            semaphore.release();
        }
    }
}
