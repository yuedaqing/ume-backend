package com.yue.netty.nianbaobanbao.lengthfield;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TestLengthFieldDecoder {
    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(
                /**
                 * 16进制 1位 4bit，8bit等于1个字节
                 * 参数1 最大长度
                 * 参数2 长度偏移量：长度的偏移量
                 * 参数3 长度本身字节数
                 * 参数4 长度调整：以长度字段为基准，还有几个字节是内容
                 * 参数5 解析的结果剥离的字节数
                 * 如何正确解析出内容：长度本身字节数+长度调整字节数=解析结果剥离的字节数
                 */
                new LengthFieldBasedFrameDecoder(1024, 0, 8, 2, 10),
                new LoggingHandler(LogLevel.DEBUG)
        );
        //4个字节的长度，实际内容
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        send(buf, "Hello, world");
        send(buf, "Hi!");
        channel.writeInbound(buf);
    }

    private static void send(ByteBuf buf, String content) {
        //实际内容
        byte[] bytes = content.getBytes();
        //实际长度
        int length = bytes.length;
        buf.writeLong(length);
        buf.writeBytes("12".getBytes());
        buf.writeBytes(bytes);
    }
}
