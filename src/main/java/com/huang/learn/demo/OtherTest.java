package com.huang.learn.demo;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author localuser
 * create at 2022/10/13 17:41
 * @description
 */
@Slf4j
public class OtherTest {
    public static void main(String[] args) {
        String name = FilenameUtils.getName("..\\..\\综合图纸\\5819-copy.dwg");
        log.info("name: {}", name);

    }

    public static void listFiles(String path){
        FileUtil.loopFiles(path, File::isDirectory);
    }

    public static void listRetainAll() {
        String prefix = "list: ";
        List<String> list = new ArrayList<>();
        list.add("a123");
        list.add("b234");
        list.add("c345");
        System.out.println(prefix + String.join(",", list));
        List<String> list2 = new ArrayList<>();
        list2.add("a123");
        list2.add("bb234");
        list2.add("cc345");
        //交集
//        list2.retainAll(list);
        list2.removeAll(list);
        list2.forEach(log::info);


    }

}
