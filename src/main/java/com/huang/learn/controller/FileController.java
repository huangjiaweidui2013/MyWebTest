package com.huang.learn.controller;

import com.huang.learn.entity.Progress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: huang lang
 * @description: 文件接口
 * @date: 2022/1/14 8:49
 */
@Controller
@Slf4j
public class FileController {
    private HttpSession session;
    private static final String PROGRESS = "progress";

    /**
     * 先调用这个方法获取session，才能实现真实的上传进度条  http://localhost:8765/upload
     *
     * @param request
     * @return
     */
    @GetMapping("/upload")
    public String index(HttpServletRequest request) {
        session = request.getSession();
        return "redirect:upload_real_lay_ui.html";
    }


    /**
     * 先获取session，才能实现真实的上传进度条  http://localhost:8765/upload
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        if (file.isEmpty()) {
            return map;
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
            map.put("data", "success");
            return map;
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    @PostMapping("/progress")
    @ResponseBody
    public Progress progress(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Progress progress = (Progress) session.getAttribute(PROGRESS);
        if (progress == null) {
            progress = new Progress();
            progress.setBytesRead(0L);
            progress.setContentLength(1L);
            progress.setItems(1);
        }
        return progress;
    }

}
