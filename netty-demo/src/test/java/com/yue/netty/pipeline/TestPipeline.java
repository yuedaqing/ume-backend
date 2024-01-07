package com.yue.netty.pipeline;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
public class TestPipeline {
    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //1.通过 channel 拿到pipeline
                        ChannelPipeline pipeline = ch.pipeline();
                        //2.添加处理器 head -> h1 -> tail
                        // head->1->2->3->tail
                        // tail->3->2->1->head
                        pipeline.addLast("handler1", new ChannelInboundHandlerAdapter() {

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("1");
                                ByteBuf buf = (ByteBuf) msg;
                                String name = buf.toString(Charset.defaultCharset());
                                super.channelRead(ctx, name);
                            }
                        });
                        pipeline.addLast("handler2", new ChannelInboundHandlerAdapter() {

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("2");
                                Student student = new Student(msg.toString());
//                                super.channelRead(ctx, student);
                                // 将数据传递给下一个handler ，super.channelRead方法与ctx.fireChannelRead二选一，否则调用链会断开。
                                //pipeline是一个双向链表
                                ctx.fireChannelRead(student);
                            }
                        });
                        pipeline.addLast("handler3", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("3,结果{},类型：{}", msg.toString(), msg.getClass());
                                /**
                                 * NioSocketChannel 与 ChannelHandlerContext的区别
                                 * head->h1->h2->h3->h4->tail
                                 * 1. ctx会从当前节点，向前调用写出。
                                 * 1.1 注意：h4是写入，在h3调用writeAndFlush，会在当前节点反转向前调用写入的handler。从而导致写入链未调用到，写入失败。
                                 *
                                 * 2.ch会从末尾节点，向前调用写出
                                 * 2.1 写出顺序：tail->h6->h5->h4，进行依次写出
                                 *
                                 */
//                                ctx.writeAndFlush(ctx.alloc().buffer().writeBytes("server...".getBytes(StandardCharsets.UTF_8)));
                                ch.writeAndFlush(ctx.alloc().buffer().writeBytes("server...".getBytes(StandardCharsets.UTF_8)));
                            }
                        });
                        pipeline.addLast("handler4",
                                new ChannelOutboundHandlerAdapter() {
                                    @Override
                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                        log.info("写入4");
                                        super.write(ctx, msg, promise);
                                    }
                                });
                        pipeline.addLast("handler5",
                                new ChannelOutboundHandlerAdapter() {
                                    @Override
                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                        log.info("写入5");
                                        super.write(ctx, msg, promise);
                                    }
                                });
                        pipeline.addLast("handler6",
                                new ChannelOutboundHandlerAdapter() {
                                    @Override
                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                        log.info("写入6");
                                        super.write(ctx, msg, promise);
                                    }
                                });
                    }
                }).bind(8080);
    }

    @Data
    private static class Student {
        private String name;

        public Student(String name) {
            this.name = name;
        }
    }

}
