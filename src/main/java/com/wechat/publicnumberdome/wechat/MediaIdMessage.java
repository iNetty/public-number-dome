package com.wechat.publicnumberdome.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by lpf in 2018/1/19 9:43
 */
public class MediaIdMessage {
    @XStreamAlias("MediaId")
    @XStreamCDATA
    private String MediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
