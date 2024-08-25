package com.yue.nio.one;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 拷贝多级目录
 */
public class TestFileCopy {
    public static void main(String[] args) throws IOException {
        String source = "C:\\Users\\yueyue\\Desktop\\待拷贝";
        String target = "C:\\Users\\yueyue\\Desktop\\待拷贝2";
        Files.walk(Paths.get(source)).forEach(path -> {
            try {
                String targetName = path.toString().replace(source,target);
                //是目录
                if (Files.isDirectory(path)) {
                    Files.createDirectories(Paths.get(targetName));
                }
                //是文件
                if (Files.isRegularFile(path)) {
                    Files.copy(path,Paths.get(targetName));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
