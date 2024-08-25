package com.yue.netty.client;


import com.yue.netty.codec.MessageCodecSharable;
import com.yue.netty.message.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ChatClient {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        CountDownLatch waitLogin = new CountDownLatch(1);
        AtomicBoolean loginStatus = new AtomicBoolean(false);
        AtomicBoolean EXIT = new AtomicBoolean(false);
        Scanner scanner = new Scanner(System.in);
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(group);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0));
                    ch.pipeline().addLast(MESSAGE_CODEC);
                    ch.pipeline().addLast(new IdleStateHandler(0, 30, 0));
                    ch.pipeline().addLast(new ChannelDuplexHandler() {
                        @Override
                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                            IdleStateEvent event = (IdleStateEvent) evt;
                            if (event.state() == IdleState.WRITER_IDLE) {
                                ctx.writeAndFlush(new PingMessage());
                                log.debug("发送心跳包");
                            }
                        }
                    });
                    // 用来判断是不是 读空闲时间过长，或 写空闲时间过长
                    // 3s 内如果没有向服务器写数据，会触发一个 IdleState#WRITER_IDLE 事件
//                    ch.pipeline().addLast(new IdleStateHandler(0, 3, 0));
//                    // ChannelDuplexHandler 可以同时作为入站和出站处理器
//                    ch.pipeline().addLast(new ChannelDuplexHandler() {
//                        // 用来触发特殊事件
//                        @Override
//                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//                            IdleStateEvent event = (IdleStateEvent) evt;
//                            // 触发了写空闲事件
//                            if (event.state() == IdleState.WRITER_IDLE) {
////                                log.debug("3s 没有写数据了，发送一个心跳包");
//                                ctx.writeAndFlush(new PingMessage());
//                            }
//                        }
//                    });
                    ch.pipeline().addLast("client handler", new ChannelInboundHandlerAdapter() {
                        /**
                         * 创建连接时执行的处理器，用于执行登陆操作
                         */
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            // 开辟额外线程，用于用户登陆及后续操作
                            new Thread(() -> {
                                Scanner scanner = new Scanner(System.in);
                                System.out.println("请输入用户名");
                                String username = scanner.nextLine();
                                System.out.println("请输入密码");
                                String password = scanner.nextLine();
                                // 创建包含登录信息的请求体
                                LoginRequestMessage message = new LoginRequestMessage(username, password);
                                // 发送到channel中
                                ctx.writeAndFlush(message);
                                System.out.println("正在登录中...");
                                // 阻塞，直到登陆成功后CountDownLatch被设置为0
                                try {
                                    waitLogin.await();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                // 执行后续操作
                                if (!loginStatus.get()) {
                                    // 登陆失败，关闭channel并返回
                                    ctx.channel().close();
                                    return;
                                }
                                // 登录成功后，执行其他操作
                                while (true) {
                                    System.out.println("==================================");
                                    System.out.println("send [username] [content]");
                                    System.out.println("gsend [group name] [content]");
                                    System.out.println("gcreate [group name] [m1,m2,m3...]");
                                    System.out.println("gmembers [group name]");
                                    System.out.println("gjoin [group name]");
                                    System.out.println("gquit [group name]");
                                    System.out.println("quit");
                                    System.out.println("==================================");
                                    String command = scanner.nextLine();
                                    // 获得指令及其参数，并发送对应类型消息
                                    String[] commands = command.split(" ");
                                    switch (commands[0]) {
                                        case "send":
                                            ctx.writeAndFlush(new ChatRequestMessage(username, commands[1], commands[2]));
                                            break;
                                        case "gsend":
                                            ctx.writeAndFlush(new GroupChatRequestMessage(username, commands[1], commands[2]));
                                            break;
                                        case "gcreate":
                                            // 分割，获得群员名
                                            String[] members = commands[2].split(",");
                                            Set<String> set = new HashSet<>(Arrays.asList(members));
                                            // 把自己加入到群聊中
                                            set.add("zhangsan");
                                            ctx.writeAndFlush(new GroupCreateRequestMessage(commands[1], set));
                                            break;
                                        case "gmembers":
                                            ctx.writeAndFlush(new GroupMembersRequestMessage(commands[1]));
                                            break;
                                        case "gjoin":
                                            ctx.writeAndFlush(new GroupJoinRequestMessage(username, commands[1]));
                                            break;
                                        case "gquit":
                                            ctx.writeAndFlush(new GroupQuitRequestMessage(username, commands[1]));
                                            break;
                                        case "quit":
                                            ctx.channel().close();
                                            return;
                                        default:
                                            System.out.println("指令有误，请重新输入");
                                            continue;
                                    }
                                }
                            }, "login channel").start();
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            log.debug("{}", msg);
                            if (msg instanceof LoginResponseMessage) {
                                // 如果是登录响应信息
                                LoginResponseMessage message = (LoginResponseMessage) msg;
                                boolean isSuccess = message.isSuccess();
                                // 登录成功，设置登陆标记
                                if (isSuccess) {
                                    loginStatus.set(true);
                                }
                                System.out.println(message.getReason());
                                // 登陆后，唤醒登陆线程
                                waitLogin.countDown();
                            }
                        }
                    });
                }
            });
            Channel channel = bootstrap.connect("localhost", 8080).sync().channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.error("client error", e);
        } finally {
            group.shutdownGracefully();
        }
    }
}
