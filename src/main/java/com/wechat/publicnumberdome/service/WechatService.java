package com.wechat.publicnumberdome.service;

import com.thoughtworks.xstream.XStream;
import com.wechat.publicnumberdome.utils.BCConvert;
import com.wechat.publicnumberdome.wechat.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * 微信接口服务类
 * Created by lpf in 2018/1/23 16:22
 */
@Service
public class WechatService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 处理请求
     * @param request 请求对象
     * @return 结果字符串
     * @throws IOException 文件操作异常
     */
     public String processWechatMag(HttpServletRequest request) throws IOException {
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
        String servername = inputMsg.getToUserName();// 服务端
        String custermname = inputMsg.getFromUserName();// 客户端
        long createTime = inputMsg.getCreateTime();// 接收时间
        Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间

        // 取得消息类型
        String msgType = inputMsg.getMsgType();

        // 根据消息类型获取对应的消息内容
        // 返回内容
        String result = "欢迎关注我";
        // 这里只接受文本消息
        if (msgType.equals(MsgType.Text.toString())) {
            // 获取消息内容
            String msgContent = inputMsg.getContent();
            // 发送请求方的信息
            logger.info("\n\n开发者微信号：" + inputMsg.getToUserName());
            logger.info("发送方帐号：" + inputMsg.getFromUserName());
            logger.info("消息创建时间：" + inputMsg.getCreateTime() + new Date(createTime * 1000l));
            logger.info("消息内容：" + msgContent);
            logger.info("消息Id：" + inputMsg.getMsgId() + "\n\n");

            // 根据发送的用户名和查询密码查询该用户的用电信息
            if (msgContent != null && !msgContent.isEmpty() && (msgContent.contains("&") || msgContent.contains("＆"))) {
                // 转换全角符号
                msgContent = BCConvert.qj2bj(msgContent);
                result = new TulingApiProcess().getTulingResult(msgContent);
            }
        }
        // 因为最终回复给微信的也是xml格式的数据，所有需要将其封装为文本类型返回消息
        return new FormatXmlProcess().formatXmlAnswer(inputMsg.getFromUserName(), inputMsg.getToUserName(), result);
    }
}
