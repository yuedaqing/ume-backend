package com.yue.nio.two.writeserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;

public class WriteServer {

    public static void main(String[] args) throws IOException {

        //server--------start---------
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        //server--------end---------

        //监听端口
        ssc.bind(new InetSocketAddress(8080));
        while (true) {
            selector.select();
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                //如果是建立链接
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    SelectionKey sckey = sc.register(selector, 0, null);
                    //建立链接完成
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < 3000000; i++) {
                        builder.append("a");
                    }
                    //1. 向客户端发送数据
                    ByteBuffer buffer = Charset.defaultCharset().encode(builder.toString());

                    //2. 返回实际写入的字节数
                    int write = sc.write(buffer);
                    System.out.println("write = " + write);

                    //3. 判断是否有剩余内容
                    if (buffer.hasRemaining()) {
                        //4. 关注可写事件
                        sckey.interestOps(sckey.interestOps() + SelectionKey.OP_WRITE);
//                        sckey.interestOps(sckey.interestOps() | SelectionKey.OP_WRITE);
                        //5. 把未写完的数据挂到 sckey上
                        sckey.attach(buffer);
                    }
//                    while (buffer.hasRemaining()){
//                        int write = sc.write(buffer);
//                        System.out.println("write = " + write);
//                    }

                } else if (key.isWritable()) {
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel sc = (SocketChannel) key.channel();
                    //每次写入的字节数
                    int write = sc.write(buffer);
                    System.out.println("write = " + write);
                    //6. 清理操作。查看buffer是否还有可写内容
                    if (!buffer.hasRemaining()) {
                        //清楚buffer
                        key.attach(null);
                        //不需要关注 可写事件
                        key.interestOps(key.interestOps() - SelectionKey.OP_WRITE);
                    }
                }
            }
        }
    }
}
