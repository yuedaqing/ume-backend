package com.yue.netty.one;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class TestFilesWalkFileTree {
    public static void main(String[] args) throws IOException {
        m1();
        m2();
//        Files.delete(Paths.get("C:\\Users\\YueYue\\Desktop\\带删除"));
        m3();
    }

    /**
     * 统计文件夹下有多少个文件，文件夹
     *
     * @throws IOException
     */
    public static void m1() throws IOException {
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();
        //此方法用到了访问者模式visitor
        Files.walkFileTree(Paths.get("D:\\code\\yue-project\\ume\\back-end\\ume\\netty-demo\\src\\test"), new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("dir = " + dir);
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("file = " + file);
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return super.postVisitDirectory(dir, exc);
            }
        });
        System.out.println("fileCount = " + fileCount);
        System.out.println("dirCount = " + dirCount);
    }

    /**
     * 统计文件夹下有多少个java文件
     *
     * @throws IOException
     */
    public static void m2() throws IOException {
        AtomicInteger javaCount = new AtomicInteger();
        //计算有多少个java文件
        Files.walkFileTree(Paths.get("D:\\code\\yue-project\\ume\\back-end\\ume\\netty-demo\\src\\test"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".java")) {
                    System.out.println("file = " + file);
                    javaCount.incrementAndGet();
                }
                return super.visitFile(file, attrs);
            }
        });
        System.out.println("javaCount = " + javaCount);
    }

    /**
     * 删除多级目录
     *
     * @throws IOException
     */
    public static void m3() throws IOException {
        Files.walkFileTree(Paths.get("C:\\Users\\YueYue\\Desktop\\待删除"), new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("进入目录dir = " + dir);
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("file = " + file);
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                System.out.println("访问失败file = " + file);
                return super.visitFileFailed(file, exc);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("退出目录dir = " + dir);
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }
}
