package com.huang.socket.bio;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

/**
 * @author localuser
 * create at 2023/1/31 20:05
 * @description BIO（阻塞IO）客户端
 */
@Slf4j
public class BlockingClient {
    public static void main(String[] args) {

        try {
            //建立连接
            Socket socket = new Socket("localhost", 9999);
            //向服务端写数据
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("我是客户端: " + RandomUtil.randomString(10) + "，收到请回答！！\n");
            bufferedWriter.flush();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = bufferedReader.readLine();
            log.info("服务端回复的数据： {}", line);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
