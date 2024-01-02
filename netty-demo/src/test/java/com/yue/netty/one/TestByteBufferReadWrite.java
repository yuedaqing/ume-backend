package com.yue.netty.one;

import java.nio.ByteBuffer;

import static com.yue.netty.util.ByteBufferUtil.debugAll;

public class TestByteBufferReadWrite {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 0x61); // a
        debugAll(buffer);
        buffer.put(new byte[]{0x62, 0x63, 0x64}); // b c d
        debugAll(buffer);
//        System.out.println("buffer.get() = " + buffer.get());
        buffer.flip();//切换读模式
        System.out.println("buffer.get() = " + buffer.get());
        debugAll(buffer);
        buffer.compact();//切换写模式
        debugAll(buffer);
        buffer.put(new byte[]{0x65, 0x66}); // e f
        debugAll(buffer);

    }
}
