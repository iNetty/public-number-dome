package com.wechat.publicnumberdome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页
 * Created by lpf in 2018/1/23 15:03
 */
@Controller
public class IndexController {

    @RequestMapping({"/","/index"})
    public String index(){
        return "index";
    }
}
