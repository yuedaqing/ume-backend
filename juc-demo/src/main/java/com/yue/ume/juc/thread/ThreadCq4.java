package com.yue.ume.juc.thread;

import java.util.concurrent.Semaphore;

public class ThreadCq4 {

    //利用信号量来限制
    private static Semaphore s1 = new Semaphore(1);
    private static Semaphore s2 = new Semaphore(1);

    public static void main(String[] args) {

        try {
            //首先调用s2为 acquire状态
            s1.acquire();
//			s2.acquire();  调用s1或者s2先占有一个
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        new Thread(()->{
            while(true) {
                try {
                    s1.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A");
                s2.release();
            }
        }).start();

        new Thread(()->{
            while(true) {
                try {
                    s2.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("B");
                s1.release();
            }
        }).start();
    }
}
