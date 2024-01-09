package com.yue.netty.protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.stomp.StompHeaders.CONTENT_LENGTH;

/**
 * HTTP协议
 */
@Slf4j
public class TestHttp {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    //netty的http解密器
                    ch.pipeline().addLast(new HttpServerCodec());
                    //SimpleChannelInboundHandler 可以根据消息类型区分，进行选择处理
                    ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
                            log.info("获取请求：{}", msg.uri());
                            log.info("请求协议版本：{}", msg.protocolVersion());
                            log.info("请求头信息：{}", msg.headers());
                            log.info("请求方法：{}", msg.method());
                            //返回响应
                            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                            byte[] bytes = "<h1>Hello,World</h1>".getBytes(StandardCharsets.UTF_8);
                            //设置响应内容改的长度，否则会导致不知道内容长度，请求一直加载。
                            response.headers().setInt(CONTENT_LENGTH, bytes.length);
                            response.content().writeBytes(bytes);
                            ctx.writeAndFlush(response);
                        }
                    });
//                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
//                        @Override
//                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                            log.info("msg的类型：{}",msg.getClass());
//                            //判断HTTP请求类型
//                            if (msg instanceof HttpRequest){
//
//                            }else if (msg instanceof HttpContent){
//
//                            }
//                        }
//                    });

                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("server error {}", e.getMessage());
        }
    }
}
