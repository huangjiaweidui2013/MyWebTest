package com.huang.socket.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author localuser
 * create at 2023/1/31 20:16
 * @description Non Blocking IO（非阻塞）服务端
 */
@Slf4j
public class NoBlockingServerWithSelector {
    public static Selector selector;

    public static void main(String[] args) {
        try {
            selector = Selector.open();
            // 相当于serverSocket
            // 1.支持非阻塞  2.数据总是写入buffer,读取也是从buffer中去读  3.可以同时读写
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(9997));

            // 需要把serverSocketChannel注册到多路复用器上
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                // 阻塞
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        //新链接来了
                        handlerAccept(key);
                    } else if (key.isReadable()) {
                        //读消息
                        handlerRead(key);
                    } else if (key.isWritable()) {
                        //写消息
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handlerWrite(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        try {
            while (buffer.hasRemaining()) {
                if (socketChannel.write(buffer) == 0) {
                    break;
                }
            }
            socketChannel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handlerRead(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        try {
            socketChannel.read(byteBuffer);
            log.info("server msg: {}", new String(byteBuffer.array()).trim());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handlerAccept(SelectionKey key) {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();

        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            //不阻塞
            socketChannel.configureBlocking(false);
            socketChannel.write(ByteBuffer.wrap("I am message ".getBytes()));
            //读取客户端的数据
            socketChannel.register(selector, SelectionKey.OP_READ);
            log.info("Accepted connection from {}", socketChannel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
