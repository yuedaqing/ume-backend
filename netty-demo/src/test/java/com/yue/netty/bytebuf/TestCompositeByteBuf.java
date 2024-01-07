package com.yue.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

import static com.yue.netty.util.ByteBufUtil.log;

/**
 * 零拷贝——CompositeByteBuf
 */
public class TestCompositeByteBuf {
    public static void main(String[] args) {
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer(10);
        buf1.writeBytes(new byte[]{1, 2, 3, 4, 5});

        ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer(10);
        buf2.writeBytes(new byte[]{6, 7, 8, 9, 10});

        //会发生两次数据的拷贝
//        ByteBuf buf3 = ByteBufAllocator.DEFAULT.buffer(10);
//        buf3.writeBytes(buf1).writeBytes(buf2);

        //CompositeByteBuf方法的好处：避免了内存的复制
        //缺点：带来复杂维护，需要计算2个buf的指针
        CompositeByteBuf compositeByteBuf = ByteBufAllocator.DEFAULT.compositeBuffer();
        compositeByteBuf.addComponents(true, buf1, buf2);
        log(compositeByteBuf);
    }
}
