package com.yue.nio.one;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

import static com.yue.netty.util.ByteBufferUtil.debugAll;

public class TestByteBufferString {
    public static void main(String[] args) {
        //1. 字符串转为String
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put("hello".getBytes(StandardCharsets.UTF_8));
        debugAll(buffer);

        //2. Charset
        ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("hello");
        debugAll(buffer1);

        //3. wrap
        ByteBuffer buffer2 = ByteBuffer.wrap("hello".getBytes(StandardCharsets.UTF_8));
        debugAll(buffer2);

        //4.转为字符串
        String string = StandardCharsets.UTF_8.decode(buffer2).toString();
        System.out.println("string = " + string);
        buffer.flip();
        CharBuffer decode = StandardCharsets.UTF_8.decode(buffer);
        System.out.println("decode = " + decode);
    }
}
