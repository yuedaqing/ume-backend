package com.yue.ume.juc.lock;

/**
 * @author YueYue
 */
public class ThreadDemo {
    public static void main(String[] args) {
        Share share = new Share();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.insert();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "线程1").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.sub();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "线程2").start();
    }
}

class Share {
    private int number = 0;

    //+1
    public synchronized void insert() throws InterruptedException {
        if (number != 0) {
            this.wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName() +"::"+ number);
        //通知其它线程
        this.notifyAll();
    }

    //-1
    public synchronized void sub() throws InterruptedException {
        if (number != 1) {
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName() +"::"+ number);
        //通知其它线程
        this.notifyAll();
    }
}
