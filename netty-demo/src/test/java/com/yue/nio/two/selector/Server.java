package com.yue.nio.two.selector;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

import static com.yue.netty.util.ByteBufferUtil.debugAll;

/**
 * selector的运用
 * 共有四种事件
 * 1. accept 有连接请求时触发
 * 2. connect 客户端连接建立后触发
 * 3. read 可读事件
 * 4. write 可写事件
 */
@Slf4j
public class Server {

    public static void main(String[] args) throws IOException {
        //1. 创建 selector，管理多个 channel
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);//开启非阻塞
        //2. 建立 selector 和 channel 的联系（注册）
        //SelectionKey 就是将来的事件发生后，通过selector可以知道事件和哪个channel的事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        //key 只关注 accept事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);

        ssc.bind(new InetSocketAddress(8080));
        while (true) {
            //3. selector 方法，没有事件发生，线程阻塞，有事件，线程恢复运行
            // select 在事件未处理时，它不会阻塞，事件发生后要么处理，要么取消，不能置之不理
            selector.select();
            //4. 处理事件，selectedKeys 内部包含了所有发生的事件
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            //selector 会在发生时间后，加入key，但不会删除
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                log.info("key:{}", key);
                // 5. 区分事件类型
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    //处理消息边界问题
                    ByteBuffer buffer = ByteBuffer.allocate(16);
                    SelectionKey scKey = sc.register(selector, 0, buffer);
                    // 只关注读
                    scKey.interestOps(SelectionKey.OP_READ);
                    System.out.println("sc = " + sc);
                } else if (key.isReadable()) {
                    try {
                        SocketChannel channel = (SocketChannel) key.channel();
//                        ByteBuffer buffer = ByteBuffer.allocate(16);
//                        ByteBuffer buffer = ByteBuffer.allocate(4);
                        //获取selectorKey关联的附件
                        ByteBuffer buffer =(ByteBuffer) key.attachment();
                        //如果正常断开，read返回值是-1
                        int read = channel.read(buffer);
                        if (read == -1) {
                            key.cancel();
                        } else {
                            split(buffer);
                            if (buffer.position() == buffer.limit()){
                                ByteBuffer newByteBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                                buffer.flip();
                                newByteBuffer.put(buffer);
                                key.attach(newByteBuffer);

                            }
                            buffer.flip();
//                            debugAll(buffer);
                            System.out.println("Charset.defaultCharset().decode(buffer) = " + Charset.defaultCharset().decode(buffer));
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        //客户端异常断开连接，将key取消（从selectorKey的集合中删除）
                        key.cancel();
                    }
                }

//                key.cancel();//事件取消
            }

        }
    }
    private static void split(ByteBuffer buffer) {
        buffer.flip();//切换读模式
        for (int i = 0; i < buffer.limit(); i++) {
            //找到一条完整数据
            if (buffer.get(i) == '\n') {
                int length = i + 1 - buffer.position();
                //把这条完整消息存入新的ByteBuffer
                ByteBuffer target = ByteBuffer.allocate(length);
                //从buffer 读，向 target 写
                for (int j = 0; j < length; j++) {
                    target.put(buffer.get());
                }
                debugAll(target);
            }

        }
        buffer.compact();//切换写模式
    }
}
