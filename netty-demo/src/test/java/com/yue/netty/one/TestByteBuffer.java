package com.yue.netty.one;


import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class TestByteBuffer {


    public static void main(String[] args) {
        String usrDir = System.getProperty("user.dir");
        String filePath = usrDir + File.separator + "netty-demo" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "data.txt";
        //FileChannel
        //1.输入输出流
        //2.RandomAccessFile
        try (FileChannel channel = new FileInputStream(filePath).getChannel()) {
            //准备缓冲区，容量为10
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (true) {
                //从Channel 读取数据，向buffer写入
                int len = channel.read(buffer);
                log.info("读取到的字节数:{}", len);
                if (len == -1) {
                    break;
                }
                //打印 buffer 的内容
                buffer.flip();//切换读模式
                while (buffer.hasRemaining()) { //检查是否还有剩余未读数据
                    byte b = buffer.get();
                    log.info("读取到的数据：{}", (char) b);
                }
                buffer.clear();//切换写模式
            }
        } catch (IOException e) {
        }
    }
}