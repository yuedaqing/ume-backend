package com.yue.ume.juc.thread;

import java.util.Stack;

//首先我们来看一种比较简单的方式
public class ThreadCq {

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        for(int i=1;i<100;i++) {
            stack.add(i);
        }
        Draw draw = new Draw(stack);
        new Thread(draw).start();
        new Thread(draw).start();
    }
}

class Draw implements Runnable{

    private Stack<Integer> stack;
    public Draw(Stack<Integer> stack) {
        this.stack = stack;
    }

    @Override
    public void run() {
        while(!stack.isEmpty()) {
            synchronized (this) {
                notify();
                System.out.println(Thread.currentThread().getName()+"---"+stack.pop());
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}