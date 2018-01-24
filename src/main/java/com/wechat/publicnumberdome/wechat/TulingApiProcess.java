package com.wechat.publicnumberdome.wechat;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 调用图灵机器人api接口，获取智能回复内容
 * Created by lpf in 2018/1/23 16:30
 */
public class TulingApiProcess {
    /**
     * 调用图灵机器人api接口，获取智能回复内容，解析获取自己所需结果
     *
     * @param content
     * @return
     */
    public String getTulingResult(String content) throws Exception {
        // 图灵api接口URL，参数key：是自己图灵机器人接口的APPID info：发送给图灵机器人的信息
        String apiUrl = "http://www.tuling123.com/openapi/api?key=9395d0451d264034a342976d60cab0a5&info=";
        String param = "";
        try {
            //将参数转为url编码
            param = apiUrl + URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        // 发送httpget请求
        //HttpUtil.sendGet(param);
        HttpGet request = new HttpGet(param);
        String result = "";
        HttpResponse response = HttpClients.createDefault().execute(request);
        if (response.getStatusLine().getStatusCode() == 200) {
            result = EntityUtils.toString(response.getEntity());
        }

        // 请求失败处理
        if (null == result) {
            return "不好意思，我没理解你说的话呢……";
        }

        JSONObject json = new JSONObject(result);
        //以code=100000为例，参考图灵机器人api文档
        Integer code = json.getInt("code");
        // 返回为文本内容
        if (100000 <= code && code < 200000) {
            result = json.getString("text");
        } else if(code == 40002){
            result = "你想说什么呢";
        }else{
            result = "我出去出去玩啦，待会再找我聊天吧，嘿嘿 (*╹▽╹*)";
        }
        return result;
    }
}
