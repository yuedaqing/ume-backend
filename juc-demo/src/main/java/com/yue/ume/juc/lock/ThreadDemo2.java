package com.yue.ume.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadDemo2 {
    public static void main(String[] args) {
        Share2 share = new Share2();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    share.insert();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "aa").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {

                    share.sub();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "bb").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {

                    share.insert();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "cc").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {

                    share.sub();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "dd").start();

    }
}

class Share2 {
    private int number = 0;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void insert() throws InterruptedException {
        lock.lock();
        try {
            while (number != 0) {
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName()+"::"+number);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void sub() throws InterruptedException {
        lock.lock();
        try {
            while (number != 1) {
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName()+"::"+number);
            condition.signalAll();

        } finally {
            lock.unlock();
        }
    }
}
