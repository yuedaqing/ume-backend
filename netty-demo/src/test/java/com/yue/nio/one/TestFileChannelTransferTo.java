package com.yue.nio.one;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

@Slf4j
public class TestFileChannelTransferTo {
    /**
     * 底层会利用操作系统的零拷贝进行优化，比JDK传统输入输出效率高，一次最多传输2g数据
     *
     * @param args
     */
    public static void main(String[] args) {
        String usrDir = System.getProperty("user.dir");
        String filePath = usrDir + File.separator + "netty-demo" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "data.txt";
        String filePathTwo = usrDir + File.separator + "netty-demo" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "dataTwo.txt";
        try (FileChannel from = new FileInputStream(filePath).getChannel();
             FileChannel to = new FileOutputStream(filePathTwo).getChannel()) {
            long size = from.size();
            for (long left = size; left > 0; ) {
                left -= from.transferTo((size - left), from.size(), to);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
