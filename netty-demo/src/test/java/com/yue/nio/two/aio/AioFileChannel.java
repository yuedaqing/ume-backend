package com.yue.nio.two.aio;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static com.yue.netty.util.ByteBufferUtil.debugAll;

/**
 * 文件异步IO
 */
@Slf4j
public class AioFileChannel {
    public static void main(String[] args) throws IOException {
        String usrDir = System.getProperty("user.dir");
        String filePath = usrDir + File.separator + "netty-demo" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "data.txt";
        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get(filePath), StandardOpenOption.READ)) {
            //参数1 ByteBuffer
            //参数2 读取的起始位置
            //参数3 附件
            //参数4 回调对象
            ByteBuffer buffer = ByteBuffer.allocate(16);
            log.info("read begin...");
            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    //read成功
                    log.info("read completed...读取到的字节数{}",result);
                    attachment.flip();
                    debugAll(attachment);

                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    //read异常
                    log.info("read failed...");

                }
            });
            log.info("read end...");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.in.read();
    }
}
