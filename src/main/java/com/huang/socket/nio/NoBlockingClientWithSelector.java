package com.huang.socket.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author localuser
 * create at 2023/1/31 20:16
 * @description Non Blocking IO（非阻塞）客户端
 */
@Slf4j
public class NoBlockingClientWithSelector {
    public static Selector selector;

    public static void main(String[] args) {
        try {
            selector = Selector.open();
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("localhost", 9997));

            // 需要把 SocketChannel 注册到多路复用器上
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            while (true) {
                // 阻塞
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isConnectable()) {
                        handlerConnect(key);
                    } else if (key.isReadable()) {
                        handlerRead(key);
                    } else if (key.isWritable()) {

                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handlerRead(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        try {
            socketChannel.read(byteBuffer);
            log.info("client msg: {}", new String(byteBuffer.array()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handlerConnect(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        try {
            if (socketChannel.isConnectionPending()) {
                socketChannel.finishConnect();
            }
            //不阻塞
            socketChannel.configureBlocking(false);
            socketChannel.write(ByteBuffer.wrap("I am message ".getBytes()));
            //读取客户端的数据
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
