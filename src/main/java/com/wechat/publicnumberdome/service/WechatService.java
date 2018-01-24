package com.wechat.publicnumberdome.service;

import com.thoughtworks.xstream.XStream;
import com.wechat.publicnumberdome.utils.BCConvert;
import com.wechat.publicnumberdome.utils.WechatUtil;
import com.wechat.publicnumberdome.wechat.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 微信接口服务类
 * Created by lpf in 2018/1/23 16:22
 */
@Service
public class WechatService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 处理请求 方式一
     *
     * @param request 请求对象
     * @return 结果字符串
     * @throws IOException 文件操作异常
     */
    /*public String processWechatMag(HttpServletRequest request) throws Exception {

        // 处理接收消息
        ServletInputStream in = request.getInputStream();

        // 将POST流转换为XStream对象
        XStream xs = SerializeXmlUtil.createXstream();
        xs.processAnnotations(InputMessage.class);
        xs.processAnnotations(OutputMessage.class);

        // 将指定节点下的xml节点数据映射为对象
        xs.alias("xml", InputMessage.class);


        // 将流转换为字符串
        StringBuilder xmlMsg = new StringBuilder();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            xmlMsg.append(new String(b, 0, n, "UTF-8"));
        }

        // 将xml内容转换为InputMessage对象
        InputMessage inputMsg = (InputMessage) xs.fromXML(xmlMsg.toString());

        String servername = inputMsg.getToUserName();// 发送给谁
        String custermname = inputMsg.getFromUserName();// 由谁发送
        long createTime = inputMsg.getCreateTime();// 发送时间
        Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间

        // 取得消息类型
        String msgType = inputMsg.getMsgType();

        // 根据消息类型获取对应的消息内容
        // 返回内容
        String result = "";
        // 这里只处理文本消息
        if (msgType.equals(MsgType.Text.toString())) {
            // 获取消息内容
            String msgContent = inputMsg.getContent();
            // 发送请求方的信息
            logger.info("\n\n开发者微信号：" + inputMsg.getToUserName());
            logger.info("发送方帐号：" + inputMsg.getFromUserName());
            logger.info("消息创建时间：" + inputMsg.getCreateTime() + new Date(createTime * 1000L));
            logger.info("消息内容：" + msgContent);
            logger.info("消息Id：" + inputMsg.getMsgId() + "\n\n");

            if (msgContent != null) {
                // 转换全角符号
                msgContent = BCConvert.qj2bj(msgContent);
                result = new TulingApiProcess().getTulingResult(msgContent);
            } else {
                result = "你什么也没说哦";
            }

        }
        // 因为最终回复给微信的也是xml格式的数据，所有需要将其封装为文本类型返回消息
        return FormatXmlProcess.formatXmlAnswer(inputMsg.getFromUserName(), inputMsg.getToUserName(), result);
    }*/


    /**
     * 处理请求 方式二
     *
     * @param request 请求对象
     * @return 结果字符串
     * @throws IOException 文件操作异常
     */
    /*public String processWechatMag(HttpServletRequest request) throws Exception {

        // 读取接收到的xml消息
        StringBuffer sb = new StringBuffer();
        InputStream is = request.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String s;
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        String xml = sb.toString(); //接收到微信端发送过来的xml数据
        // 解析xml数据

        ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml);

        // 调用图灵机器人回复
        String result = new TulingApiProcess().getTulingResult(xmlEntity.getContent());

        // 因为最终回复给微信的也是xml格式的数据，所有需要将其封装为文本类型返回消息
        return FormatXmlProcess.formatXmlAnswer(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), result);

    }*/

    /**
     * 处理请求 方式三
     *
     * @param request 请求对象
     * @return 结果字符串
     * @throws IOException 文件操作异常
     */
    public String processWechatMag(HttpServletRequest request) throws Exception {
        // 处理接收消息
        Map<String, String> map = WechatUtil.parseXml(request);

        // 获取参数
        String url, content, toUserName, fromUserName, msgType, msgId;
        Integer createTime;
        if (map != null && map.size() > 0) {
            url = map.get("URL");
            content = map.get("Content");
            toUserName = map.get("ToUserName");// 发送给谁
            fromUserName = map.get("FromUserName");// 由谁发送
            createTime = Integer.parseInt(map.get("CreateTime"));// 发送时间
            msgType = map.get("MsgType");// 消息类型
            msgId = map.get("MsgId");// 消息类型
        } else {
            return FormatXmlProcess.formatXmlAnswer("to", "form", "我好像没有理解你说的话呢");
        }


        // 根据消息类型获取对应的消息内容
        // 返回内容
        String result = "";
        // 这里只处理文本消息
        if (msgType.equals(MsgType.Text.toString())) {

            // 发送请求方的信息
            Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间
            logger.info("\n\n开发者微信号：" + toUserName);
            logger.info("URL：" + url);
            logger.info("发送方帐号：" + fromUserName);
            logger.info("消息创建时间：" + createTime + new Date(createTime * 1000L));
            logger.info("消息内容：" + content);
            logger.info("消息Id：" + msgId);
            logger.info("返回时间：" + returnTime + "\n\n");

            if (content != null) {
                // 转换全角符号
                content = BCConvert.qj2bj(content);
                result = new TulingApiProcess().getTulingResult(content);
            } else {
                result = "你什么也没说哦";
            }

        }
        // 因为最终回复给微信的也是xml格式的数据，所有需要将其封装为文本类型返回消息
        return FormatXmlProcess.formatXmlAnswer(toUserName, fromUserName, result);
    }
}
