package com.yue.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

import static com.yue.netty.util.ByteBufUtil.log;

@Slf4j
public class TestByteBuf {
    public static void main(String[] args) {
        //创建直接内存
//        ByteBufAllocator.DEFAULT.heapBuffer(10);
        //创建堆内存
//        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.directBuffer();
        //netty的ByteBuf是自动扩种，可以指定初始容量
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            builder.append("a");
        }
        buffer.writeBytes(builder.toString().getBytes(StandardCharsets.UTF_8));
        log(buffer);
    }
}
