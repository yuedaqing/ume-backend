package com.yue.netty.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 通过TCP协议连接redis
 */
@Slf4j
public class TestRedis {

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        final byte[] LINE = new byte[]{13, 10};
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LoggingHandler());
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            ByteBuf buf = ctx.alloc().buffer();
                            // *3 表示数组的大小
                            buf.writeBytes("*3".getBytes());
                            buf.writeBytes("\r\n".getBytes(StandardCharsets.UTF_8));
//                            buf.writeBytes(LINE);
                            buf.writeBytes("$3".getBytes());
                            buf.writeBytes("\r\n".getBytes(StandardCharsets.UTF_8));
//                            buf.writeBytes(LINE);
                            buf.writeBytes("set".getBytes());
                            buf.writeBytes("\r\n".getBytes(StandardCharsets.UTF_8));
//                            buf.writeBytes(LINE);
                            buf.writeBytes("$4".getBytes());
                            buf.writeBytes("\r\n".getBytes(StandardCharsets.UTF_8));
//                            buf.writeBytes(LINE);
                            buf.writeBytes("user".getBytes());
                            buf.writeBytes("\r\n".getBytes(StandardCharsets.UTF_8));
//                            buf.writeBytes(LINE);
                            buf.writeBytes("$8".getBytes());
                            buf.writeBytes("\r\n".getBytes(StandardCharsets.UTF_8));
//                            buf.writeBytes(LINE);
                            buf.writeBytes("zhangsan".getBytes());
                            buf.writeBytes("\r\n".getBytes(StandardCharsets.UTF_8));
//                            buf.writeBytes(LINE);
                            ctx.writeAndFlush(buf);
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            log.info("未转换的结果 {}", msg);
                            ByteBuf buf = (ByteBuf) msg;

                            log.info("接收结果：{}", buf.toString(Charset.defaultCharset()));
                        }
                    });

                }
            });
            ChannelFuture channelFuture = bootstrap.connect("localhost", 6379).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("client error {}", e.getMessage());
        }
    }

}
