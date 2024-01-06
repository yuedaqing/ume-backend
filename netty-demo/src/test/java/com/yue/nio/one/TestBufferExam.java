package com.yue.nio.one;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static com.yue.netty.util.ByteBufferUtil.debugAll;

public class TestBufferExam {
    public static void main(String[] args) {
        /**
         * 由于某种原因数据在接收时，被进行了重新组合
         */
        ByteBuffer buffer = ByteBuffer.allocate(32);
        buffer.put("hello,world\nI'm zhangsan\nHo".getBytes(StandardCharsets.UTF_8));
        split(buffer);
        buffer.put("w are you?\n".getBytes(StandardCharsets.UTF_8));
        split(buffer);
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
