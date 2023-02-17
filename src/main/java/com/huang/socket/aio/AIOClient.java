package com.huang.socket.aio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

/**
 * @author localuser
 * create at 2023/2/1 11:42
 * @description AIO（异步IO）客户端
 */
@Slf4j
public class AIOClient {
    private final AsynchronousSocketChannel socketChannel;

    public AIOClient() throws IOException {
        socketChannel = AsynchronousSocketChannel.open();
    }

    public static void main(String[] args) throws IOException {
        new AIOClient().connect("localhost", 9996);
    }

    public void connect(String host, int port) {
        // 客户端向服务端发起连接
        socketChannel.connect(new InetSocketAddress(host, port), null, new CompletionHandler<Void, Object>() {
            @Override
            public void completed(Void result, Object attachment) {
                try {
                    socketChannel.write(ByteBuffer.wrap("这是一条测试数据".getBytes())).get();
                    log.info("已发送到服务端");
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });

        ByteBuffer b = ByteBuffer.allocate(1024);
        // 客户端接收服务端的数据，获取的数据写入到b中
        socketChannel.read(b, null, new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer result, Object attachment) {
                // 服务端返回数据的长度result
                log.info("I/O操作完成：{}", result);
                log.info("获取反馈结果：{}", new String(b.array()).trim());
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
