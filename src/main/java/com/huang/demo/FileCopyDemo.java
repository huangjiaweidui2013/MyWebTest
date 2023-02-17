package com.huang.demo;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author localuser
 * create at 2022/8/17 11:07
 * @description 文件复制的几种方式 demo
 */
@Slf4j
public class FileCopyDemo {
    public static void main(String[] args) {
        String srcPath = "D:\\copy\\ideaIC-2022.1.3.exe";
        String destPath1 = "D:\\copy\\ideaIC-2022.1.31.exe";
        String destPath2 = "D:\\copy\\ideaIC-2022.1.32.exe";
        String destPath3 = "D:\\copy\\ideaIC-2022.1.33.exe";
        try {
            Files.deleteIfExists(Path.of(destPath1));
            Files.deleteIfExists(Path.of(destPath2));
            Files.deleteIfExists(Path.of(destPath3));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long start = System.currentTimeMillis();
        copyFileWithFiles(srcPath, destPath1);
        long end1 = System.currentTimeMillis();
//        copyFileWithChannel(srcPath, destPath2);
        long end2 = System.currentTimeMillis();
        copyBigFileWithChannelMap(srcPath, destPath3);
        long end3 = System.currentTimeMillis();
        log.info("Files.copy: {}, FileChannel: {}, FileWithChannelMap: {}", end1 - start, end2 - end1, end3 - end2);
    }

    public static void copyFileWithFiles(String srcPath, String destPath) {
        try {
            Path path = Path.of(destPath);
//            Files.createFile(path);
            Files.copy(Path.of(srcPath), path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public static void copyFileWithChannel(String srcPath, String destPath) {
        try (
                FileInputStream inputStream = new FileInputStream(srcPath);
                FileOutputStream outputStream = new FileOutputStream(srcPath);
                FileChannel srcChannel = inputStream.getChannel();
                FileChannel destChannel = outputStream.getChannel();
        ) {
            Files.createFile(Path.of(destPath));
            destChannel.transferFrom(srcChannel, 0, srcChannel.size());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 复制2GB以下的文件
     *
     * @param srcPath
     * @param destPath
     */
    public static void copyFileWithChannelMap(String srcPath, String destPath) {
        try (
                //java.io.RandomAccessFile类，可以设置读、写模式的IO流类。
                //"r"表示：只读--输入流，只读就可以。
                RandomAccessFile srcFile = new RandomAccessFile(srcPath, "r");
                //"rw"表示：读、写--输出流，需要读、写。
                RandomAccessFile destFile = new RandomAccessFile(destPath, "rw");

                // 获得FileChannel管道对象
                FileChannel srcChannel = srcFile.getChannel();
                FileChannel destChannel = destFile.getChannel();
        ) {

            // 获取文件的大小
            long size = srcChannel.size();

            // 直接把硬盘中的文件映射到内存中
            // 不能复制大于2G的文件，因为map的第三个参数被限制在Integer.MAX_VALUE(字节) = 2G。
            MappedByteBuffer srcBuffer = srcChannel.map(FileChannel.MapMode.READ_ONLY, 0, size);
            MappedByteBuffer destBuffer = destChannel.map(FileChannel.MapMode.READ_WRITE, 0, size);

            for (int i = 0; i < size; i++) {
                // 读取字节
                byte b = srcBuffer.get();
                destBuffer.put(b);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 复制2GB以上的文件
     *
     * @param srcPath
     * @param destPath
     */
    public static void copyBigFileWithChannelMap(String srcPath, String destPath) {
        try (
                //java.io.RandomAccessFile类，可以设置读、写模式的IO流类。
                //"r"表示：只读--输入流，只读就可以。
                RandomAccessFile srcFile = new RandomAccessFile(srcPath, "r");
                //"rw"表示：读、写--输出流，需要读、写。
                RandomAccessFile destFile = new RandomAccessFile(destPath, "rw");

                // 获得FileChannel管道对象
                FileChannel srcChannel = srcFile.getChannel();
                FileChannel destChannel = destFile.getChannel();
        ) {

            // 获取文件的大小
            long size = srcChannel.size();

            // 每次复制500MB的内容
            long everySize = 1024 * 1024 * 500L;

            // 循环读取的次数
            long count = size % everySize == 0 ? size / everySize : size / everySize + 1;
            for (int i = 0; i < count; i++) {
                // 每次开始复制的位置
                long start = everySize * i;
                // 每次实际复制的大小
                long trueSize = Math.min(size - start, everySize);

                // 直接把硬盘中的文件映射到内存中
                MappedByteBuffer srcBuffer = srcChannel.map(FileChannel.MapMode.READ_ONLY, start, trueSize);
                MappedByteBuffer destBuffer = destChannel.map(FileChannel.MapMode.READ_WRITE, start, trueSize);

                for (int j = 0; j < trueSize; j++) {
                    // 读取字节
                    byte b = srcBuffer.get();
                    destBuffer.put(b);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static void test() {
        var str = "abc";
        System.out.println(str);

    }

}
