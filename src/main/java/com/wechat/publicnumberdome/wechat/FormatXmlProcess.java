package com.wechat.publicnumberdome.wechat;

import java.util.Date;

/**
 * 封装文字类的返回消息
 * Created by lpf in 2018/1/18 16:57
 */
public class FormatXmlProcess {
    /**
     * 封装最终的xml格式结果
     *
     * @param to 发给谁
     * @param from 发送人
     * @param content 返回内容
     * @return 封装后的结果字符串(XML)
     */
    public String formatXmlAnswer(String to, String from, String content) {
        StringBuffer sb = new StringBuffer();
        Date date = new Date();
        sb.append("<xml><ToUserName><![CDATA[").append(to).append("]]></ToUserName><FromUserName><![CDATA[")
                .append(from)
                .append("]]></FromUserName><CreateTime>")
                .append(date.getTime())
                .append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[")
                .append(content)
                .append("]]></Content><FuncFlag>0</FuncFlag></xml>");
        return sb.toString();
    }
}
