package com.yue.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class HelloServer {
    public static void main(String[] args) {
        //1. 服务器端的启动器，负责组装netty组件
        new ServerBootstrap()
                //2. 理解为处理数据的工人
                /**
                 * 1）可以管理多个channel的IO操作，并且一旦工人负责了某个channel，就要负责到底（绑定）
                 * 2）工人既可以执行IO操作，也可以进行任务处理，每位工人有任务队列，队列里可以堆放多个channel的待处理任务，任务分为：普通任务、定时任务
                 * 3）工人按照pipeline顺序，依次按照handler的规划（代码）处理数据，可以为每道工序指定不同的工人
                 */
                .group(new NioEventLoopGroup())
                //3. 选择服务器的ServerSocketChannel实现
                .channel(NioServerSocketChannel.class)
                //4. 负责处理读写，决定了能执行哪些操作
                .childHandler(
                        //5. Channel 代表和客户端进行数据读写的通道。Initializer初始化，负责添加其它handler
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                                //7. 添加具体handler ，将ByteBuf转为字符串
                                //pipeline负责发布事件（读、读取完成）传播给每个handler，handler对感兴趣的事件进行处理
                                nioSocketChannel.pipeline().addLast(new StringDecoder());
                                //自定义的handler Inbound 入站，读数据，Outbound 出站，写数据
                                nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {

                                    //读事件
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        super.channelRead(ctx, msg);
                                        //打印上一步转换好的字符串
                                        System.out.println("msg = " + msg);
                                    }
                                });
                            }
                            //6. 绑定端口
                        }).bind(8080);
    }
}
