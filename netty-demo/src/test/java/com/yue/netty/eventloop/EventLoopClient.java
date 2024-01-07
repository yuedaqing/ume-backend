package com.yue.netty.eventloop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;

@Slf4j
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())

                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                //connect方法是异步非阻塞的，发起调用的是main线程，真正执行连接的是nio线程
                .connect(new InetSocketAddress("localhost", 8080));
//                .sync()
//                .channel();
//        netty 2.1 使用
//        channelFuture.sync();
//        Channel channel = channelFuture.channel();
//        System.out.println("channel = " + channel);
//        channel.writeAndFlush("1");
        //netty 2.2使用 addListener(回调对象)方法异步处理结果
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            //在nio线程连接建立好之后，会调用 operationComplete
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Channel channel = channelFuture.channel();
                log.info(channel.toString());
                channel.writeAndFlush("hello world");
            }
        });
    }

}
