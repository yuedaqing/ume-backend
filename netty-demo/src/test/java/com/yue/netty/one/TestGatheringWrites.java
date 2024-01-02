package com.yue.netty.one;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class TestGatheringWrites {
    public static void main(String[] args) {
        ByteBuffer b1 = StandardCharsets.UTF_8.encode("hello");
        ByteBuffer b2 = StandardCharsets.UTF_8.encode("world");
        ByteBuffer b3 = StandardCharsets.UTF_8.encode("你好");
        String usrDir = System.getProperty("user.dir");
        String filePath = usrDir + File.separator + "netty-demo" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "words2.txt";
        try (FileChannel channel = new RandomAccessFile(filePath, "rw").getChannel()) {
            channel.write(new ByteBuffer[]{b1, b2, b3});
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
