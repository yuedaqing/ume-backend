package com.yue.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static com.yue.netty.util.ByteBufUtil.log;

/**
 * 注意：切片后，会长度有限制。限制的长度就是当前切片的长度。不能增加
 */
public class TestSlice {
    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});
        log(buf);
        //在切片的过程中，没有发生数据复制
        ByteBuf f1 = buf.slice(0, 5);
        f1.retain();
        ByteBuf f2 = buf.slice(5, 5);
        log(f1);
        log(f2);
        //释放内存
        f1.release();
        log(f1);
        //修改值，证明是同一个对象
        f1.setByte(0,'b');
        log(f1);
    }
}
