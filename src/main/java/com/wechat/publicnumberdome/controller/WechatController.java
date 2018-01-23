package com.wechat.publicnumberdome.controller;

import com.thoughtworks.xstream.XStream;
import com.wechat.publicnumberdome.service.WechatService;
import com.wechat.publicnumberdome.utils.BCConvert;
import com.wechat.publicnumberdome.utils.SignUtil;
import com.wechat.publicnumberdome.wechat.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * 微信接口控制器
 * Created by lpf in 2018/1/23 14:56
 */
@Controller
@RequestMapping("/wechat")
public class WechatController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WechatService wechatService;
    /**
     * 处理微信请求
     *
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr 随机字符串
     */
    @RequestMapping("/checkSignature")
    @ResponseBody
    public String checkSignature(HttpServletRequest request, String signature, String timestamp, String nonce, String echostr) {
        String result = null;
        // 判断是不是第一次接入接口(微信只有在第一次验证接口时会发送get请求)
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        if (isGet) {
            logger.info("\n第一次验证接口...\n");
            // 验证微信签名
            boolean isTrue = SignUtil.checkSignature(signature, timestamp, nonce);
            if (isTrue) {
                logger.info("\n校验成功\n");
            } else {
                logger.info("\n校验失败\n");
            }
            result = echostr;
        } else {
            try {
                // 处理业务
                result = wechatService.processWechatMag(request);
            } catch (IOException e) {
                // 这里不处理异常，打印出来即可，微信服务器会自动返回异常：(该公众号提供的服务出现故障，请稍后再试)
                e.printStackTrace();
            }
        }
        return result;
    }

}
