package com.huang.learn.listener;

import com.huang.learn.entity.Progress;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * @author: huang lang
 * @description: 文件上传进度监听
 * @date: 2022/1/14 10:30
 */
@Slf4j
@Component
public class FileUploadProgressListener implements ProgressListener {
    private static final String PROGRESS = "progress";
    private HttpSession session;

    public void setSession(HttpSession session) {
        this.session = session;
        //保存上传状态
        Progress progress = new Progress();
        progress.setBytesRead(0L);
        progress.setContentLength(0L);
        session.setAttribute(PROGRESS, progress);
    }

    @Override
    public void update(long pBytesRead, long pContentLength, int pItems) {
        Progress progress = (Progress) session.getAttribute(PROGRESS);
        if (progress.getBytesRead() == progress.getContentLength() && progress.getBytesRead() != 0) {
            session.removeAttribute(PROGRESS);
        }
        progress.setBytesRead(pBytesRead);
        progress.setContentLength(pContentLength);
        progress.setItems(pItems);
        session.setAttribute(PROGRESS, progress);
        log.info("文件上传进度监听: 已读取比特数: {}, 文件总比特数: {}, 正读的第几个文件: {}", pBytesRead, pContentLength, pItems);
    }
}
