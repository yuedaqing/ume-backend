package com.yue.netty.codec;

import com.yue.netty.codec.MessageCodec;
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
                new LengthFieldBasedFrameDecoder(1024,12,4,0,0),
                new LoggingHandler(LogLevel.DEBUG),
                new MessageCodec());
        LoginRequestMessage message = new LoginRequestMessage();
        message.setUsername("zhangsan");
        message.setPassword("123456");

        //消息出战 encode
        channel.writeOutbound(message);

        //消息入站 decode
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null,message,buf);
        channel.writeInbound(buf);
    }
}
