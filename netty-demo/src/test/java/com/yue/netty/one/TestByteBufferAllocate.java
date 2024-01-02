package com.yue.netty.one;

import java.nio.ByteBuffer;

public class TestByteBufferAllocate {
    public static void main(String[] args) {
        System.out.println("ByteBuffer.allocate(16).getClass() = " + ByteBuffer.allocate(16).getClass());
        System.out.println("ByteBuffer.allocateDirect(16).getClass() = " + ByteBuffer.allocateDirect(16).getClass());
        /**
         * class java.nio.HeapByteBuffer -java 堆内存，读写效率较低，受到垃圾回收（GC）的影响
         * class java.nio.DirectByteBuffer -直接内存，读写效率高（少一次数据拷贝），不会受到java垃圾回收（GC）的影响，分配内存效率低
         */
    }
}
