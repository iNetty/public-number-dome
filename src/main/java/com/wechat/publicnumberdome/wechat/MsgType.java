package com.wechat.publicnumberdome.wechat;

/**
 * Created by lpf in 2018/1/19 9:44
 */
public enum MsgType {
    Text("text"),
    Image("image"),
    Music("music"),
    Video("video"),
    Voice("voice"),
    Location("location"),
    Link("link");
    private String msgType = "";

    MsgType(String msgType) {
        this.msgType = msgType;
    }

    /**
     * @return the msgType
     */
    @Override
    public String toString() {
        return msgType;
    }
}
