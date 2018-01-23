package com.wechat.publicnumberdome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 全局异常处理
 * Created by lpf in 2018/1/23 15:41
 */
@Controller
public class MyErrorController implements ErrorController {
    private static final String ERROR_PATH = "/error";

    @RequestMapping(value=ERROR_PATH)
    public String handleError(){
        return "error/404";
    }

    @Override
    public String getErrorPath() {
        // TODO Auto-generated method stub
        return ERROR_PATH;
    }
}
