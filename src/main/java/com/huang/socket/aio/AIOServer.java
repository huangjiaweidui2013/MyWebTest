package com.huang.socket.aio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author localuser
 * create at 2023/2/1 10:46
 * @description AIO（异步IO）服务端
 */
@Slf4j
public class AIOServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        AsynchronousServerSocketChannel serverSocketChannel =
                AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(9996));
        serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

            @Override
            public void completed(AsynchronousSocketChannel socketChannel, Object attachment) {
                log.info("2-线程名称： {}", Thread.currentThread().getName());
                try {
                    log.info("RemoteAddress: {}", socketChannel.getRemoteAddress());
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    // socketChannel异步的读取数据到 byteBuffer 中
                    socketChannel.read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            log.info("3-线程名称： {}", Thread.currentThread().getName());
                            byteBuffer.flip();
                            log.info(new String(byteBuffer.array(), 0, result));
                            socketChannel.write(ByteBuffer.wrap("HelloClient".getBytes()));
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            exc.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });
        log.info("1-线程名称： {}", Thread.currentThread().getName());
        Thread.sleep(Integer.MAX_VALUE);
    }
}
