package com.yue.ume.juc.thread;

import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadCq3 {

    //声明一个锁
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        //创建两个Condition对象
        Condition c1 = lock.newCondition();
        Condition c2 = lock.newCondition();
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i <= 100; i++) {
            stack.add(i);
        }

        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            while (true) {
                lock.lock();
                // 打印偶数
                try {
                    if (stack.peek() % 2 != 0) {
                        c1.await();
                    }
                    System.out.println(Thread.currentThread().getName() + "-----" + stack.pop());
                    c2.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();


        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                lock.lock();
                try {
                    // 打印奇数
                    if (stack.peek() % 2 != 1) {
                        c2.await();
                    }
                    System.out.println(Thread.currentThread().getName() + "-----" + stack.pop());
                    c1.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();
    }
}