package com.huang.socket.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author localuser
 * create at 2023/1/31 19:53
 * @description BIO（阻塞IO）服务端
 */
@Slf4j
public class BlockingServer {
    private static final ExecutorService exec = Executors.newCachedThreadPool();

    public static void main(String[] args) {

        try {
            //监听端口
            ServerSocket serverSocket = new ServerSocket(9999);

            //等待客户端的连接过来,如果没有连接过来，就会阻塞
            while (true) {
                // 阻塞IO中一个线程只能处理一个连接
                Socket socket = serverSocket.accept();
                exec.execute(() -> {
                    //获取数据
                    String line = null;
                    try {
                        // 获取Socket中的输入流
                        BufferedReader bufferedReader =
                                new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        line = bufferedReader.readLine();
                        log.info("接收到的来自客户端的数据： " + line);

                        BufferedWriter bufferedWriter =
                                new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        bufferedWriter.write("ok\n");
                        bufferedWriter.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                        try {
                            serverSocket.close();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
