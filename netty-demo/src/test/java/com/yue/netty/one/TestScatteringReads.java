package com.yue.netty.one;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static com.yue.netty.util.ByteBufferUtil.debugAll;

@Slf4j
public class TestScatteringReads {
    public static void main(String[] args) {
        String usrDir = System.getProperty("user.dir");
        String filePath = usrDir + File.separator + "netty-demo" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "words.txt";
        try (FileChannel channel = new RandomAccessFile(filePath, "r").getChannel()) {
            ByteBuffer b1 = ByteBuffer.allocate(3);
            ByteBuffer b2 = ByteBuffer.allocate(3);
            ByteBuffer b3 = ByteBuffer.allocate(5);
            channel.read(new ByteBuffer[]{b1,b2,b3});
            b1.flip();
            b2.flip();
            b3.flip();
            debugAll(b1);
            debugAll(b2);
            debugAll(b3);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
