package com.yue.netty.nianbaobanbao.lienbased;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * 行解码器
 */
@Slf4j
public class LineBasedClient {

    public static void main(String[] args) {
//        makeString('1',5);
//        makeString('2',2);
//        makeString('3',10);
        send();
    }

    private static void send() {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        //会在连接channel 建立成功后，会触发 active事件
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            ByteBuf buf = ctx.alloc().buffer();
                            char c = '0';
                            Random random = new Random();
                            for (int i = 0; i < 10; i++) {
                                StringBuilder builder = makeString(c, random.nextInt(10) + 1);
                                buf.writeBytes(builder.toString().getBytes());
                                c++;
                            }
                            ctx.writeAndFlush(buf);
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

                        }
                    });
                }
            });
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("client error {}", e.getMessage());
        } finally {
            worker.shutdownGracefully();
        }
    }

    /**
     * 补充10个字节
     *
     * @param c   字节
     * @param len 长度
     * @return 字节数组
     */
    private static StringBuilder makeString(char c, int len) {
        StringBuilder builder = new StringBuilder(len + 2);
        for (int i = 0; i < len; i++) {
            builder.append(c);
        }
        builder.append('\n');
        System.out.println(builder);
        return builder;

    }
}
