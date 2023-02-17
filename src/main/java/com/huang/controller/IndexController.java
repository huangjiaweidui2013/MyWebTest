package com.huang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author localuser
 * create at 2022/10/31 9:07
 * @description
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @GetMapping("/html")
    public String index(){
        return "index.html";
    }
}
