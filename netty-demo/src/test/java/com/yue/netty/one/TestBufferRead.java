package com.yue.netty.one;

import java.nio.ByteBuffer;

import static com.yue.netty.util.ByteBufferUtil.debugAll;

public class TestBufferRead {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd'});
        buffer.flip();
        //从头开始读
//        buffer.get(new byte[4]);
//        debugAll(buffer);
//        buffer.rewind();
//        System.out.println("buffer.get() = " + (char) buffer.get());
        //mark & reset
        //mark 做一个标记，记录position位置，reset 是将position 重置到mark的位置
//        System.out.println("buffer.get() = " + (char) buffer.get());
//        System.out.println("buffer.get() = " + (char) buffer.get());
//        buffer.mark();// 加标记，索引2的位置
//        System.out.println("buffer.get() = " + (char) buffer.get());
//        System.out.println("buffer.get() = " + (char) buffer.get());
//        buffer.reset();//将position重置到索引2
//        System.out.println("buffer.get() = " + (char) buffer.get());
//        System.out.println("buffer.get() = " + (char) buffer.get());
        //get(i)不会改变position位置
        System.out.println("buffer.get(3) = " + (char) buffer.get(3));
        debugAll(buffer);
    }
}
