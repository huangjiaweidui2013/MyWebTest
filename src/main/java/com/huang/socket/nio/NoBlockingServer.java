package com.huang.socket.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author localuser
 * create at 2023/1/31 20:16
 * @description Non Blocking IO（非阻塞）服务端
 */
@Slf4j
public class NoBlockingServer {
    public static List<SocketChannel> channelList = new ArrayList<>();

    public static void main(String[] args) {
        try {
            // 相当于serverSocket
            // 1.支持非阻塞  2.数据总是写入buffer,读取也是从buffer中去读  3.可以同时读写
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(9998));

            while (true) {
                // 这里将不再阻塞
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    socketChannel.configureBlocking(false);
                    channelList.add(socketChannel);
                } else {
                    log.info("没有请求过来！！！");
                }
                for (SocketChannel client : channelList) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int num = client.read(byteBuffer);
                    if (num > 0) {
                        log.info("客户端端口： {}， 客户端数据： {}", client.socket().getPort(), new String(byteBuffer.array()));
                    } else {
                        log.info("等待客户端写数据！");
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
