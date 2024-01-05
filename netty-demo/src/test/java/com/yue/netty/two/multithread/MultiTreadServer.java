package com.yue.netty.two.multithread;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

import static com.yue.netty.util.ByteBufferUtil.debugAll;

@Slf4j
public class MultiTreadServer {
    public static void main(String[] args) throws IOException {
        //设置当前线程名称
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //开启非阻塞
        ssc.configureBlocking(false);
        Selector boss = Selector.open();
        SelectionKey bossKey = ssc.register(boss, 0, null);
        //只关注创建连接
        bossKey.interestOps(SelectionKey.OP_ACCEPT);
        //监听端口
        ssc.bind(new InetSocketAddress(8080));
        //1. 创建固定数量的worker
        Worker worker = new Worker("worker-0");

        while (true) {
            boss.select();
            Iterator<SelectionKey> iter = boss.selectedKeys().iterator();
            while (iter.hasNext()) {
                //当前连接的key
                SelectionKey key = iter.next();
                iter.remove();
                //当前的key是创建连接
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    log.info("connected...{}", sc.getRemoteAddress());
                    //关联 selector
                    log.info("before register...{}", sc.getRemoteAddress());
                    worker.register(sc);
                    log.info("after register...{}", sc.getRemoteAddress());
                }

            }
        }

    }

    static class Worker implements Runnable {
        private Thread thread;
        private Selector selector;
        private ConcurrentLinkedDeque<Runnable> queue = new ConcurrentLinkedDeque<>();
        private volatile boolean start = false;

        public Worker(String name) {
            this.name = name;
        }

        private String name;

        //初始化线程和selector
        public void register(SocketChannel sc) throws IOException {
            if (!start) {
                selector = Selector.open();
                thread = new Thread(this, name);
                thread.start();
                start = true;
            }
            //向队列添加了任务，没有立刻执行
            queue.add(() -> {
                try {
                    sc.register(selector, SelectionKey.OP_READ, null);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            //唤醒 select方法
            selector.wakeup();

        }

        @Override
        public void run() {
            while (true) {
                try {
                    //worker-0线程阻塞，需要唤醒wakeup
                    selector.select();
                    Runnable task = queue.poll();
                    if (task != null) {
                        //执行任务里的代码
                        task.run();
                    }
                    Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel sc = (SocketChannel) key.channel();
                            sc.read(buffer);
                            log.info("read...{}", sc.getRemoteAddress());
                            buffer.flip();
                            debugAll(buffer);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
