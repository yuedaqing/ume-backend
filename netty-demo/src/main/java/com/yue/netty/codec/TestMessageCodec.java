package com.yue.netty.codec;

import com.yue.netty.message.LoginRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TestMessageCodec {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                //线程不安全
                new LengthFieldBasedFrameDecoder(1024,12,4,0,0),
                //线程安全，可以被共享
                new LoggingHandler(LogLevel.DEBUG),
                new MessageCodecSharable());
        LoginRequestMessage message = new LoginRequestMessage();
        message.setUsername("zhangsan");
        message.setPassword("123456");

        //消息出战 encode
//        channel.writeOutbound(message);

        //消息入站 decode
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null,message,buf);
//        channel.writeInbound(buf);
        ByteBuf slice1 = buf.slice(0, 100);
        ByteBuf slice2 = buf.slice(100, buf.readableBytes() - 100);
        //引用计数+1
        slice1.retain();
        channel.writeInbound(slice1);
        channel.writeInbound(slice2);

    }
}
