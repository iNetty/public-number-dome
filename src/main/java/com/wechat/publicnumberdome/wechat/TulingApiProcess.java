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
    public String getTulingResult(String content) {
        /** 此处为图灵api接口，参数key需要自己去注册申请，先以11111111代替 */
        String apiUrl = "http://www.tuling123.com/openapi/api?key=9395d0451d264034a342976d60cab0a5&info=";
        String param = "";
        try {
            param = apiUrl + URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } //将参数转为url编码

        /** 发送httpget请求 */
        //HttpUtil.sendGet(param);
        HttpGet request = new HttpGet(param);
        String result = "";
        try {
            HttpResponse response = HttpClients.createDefault().execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /** 请求失败处理 */
        if (null == result) {
            return "不好意思，我没理解你说的话呢……";
        }

        try {
            JSONObject json = new JSONObject(result);
            //以code=100000为例，参考图灵机器人api文档
            if (100000 == json.getInt("code")) {
                result = json.getString("text");
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
