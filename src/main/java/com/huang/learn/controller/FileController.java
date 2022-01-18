package com.huang.learn.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author: huang lang
 * @description: 文件接口
 * @date: 2022/1/14 8:49
 */
@RestController
@Slf4j
public class FileController {

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空";
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 文件上传后的路径
        String filePath = null;
        try {
            filePath = new File("").getCanonicalPath() + "/tmp/uploadFile/";
        } catch (IOException e) {
            e.printStackTrace();
        }
        //存储路径
        String tagFilePath = filePath + "store/" + fileName;
        File dest = new File(tagFilePath);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName + "上传失败";
    }

}
