package com.yue.netty.nianbaobanbao.fixedlength;

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
 * 定长消息解码器 ch.pipeline().addLast(new FixedLengthFrameDecoder(10));
 */
@Slf4j
public class FixedLengthClient {

    public static void main(String[] args) {
//        fill10Bytes('1', 5);
//        fill10Bytes('2', 2);
//        fill10Bytes('3', 10);
//        for (int i = 0; i < 10; i++) {
//            send();
//            log.info("finish...");
//        }
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
                                byte[] bytes = fill10Bytes(c, random.nextInt(10) + 1);
                                buf.writeBytes(bytes);
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
    private static byte[] fill10Bytes(char c, int len) {
        byte[] bytes = new byte[10];
        if (len == 10) {
            for (int i = 0; i < len; i++) {
                bytes[i] = (byte) c;
            }
        } else {
            for (int i = 0; i < 10; i++) {
                if (i > len - 1) {
                    bytes[i] = '_';
                } else {
                    bytes[i] = (byte) c;
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        for (byte aByte : bytes) {
            builder.append((char) aByte);
        }
        System.out.println(builder);
        return bytes;

    }
}
