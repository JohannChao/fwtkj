package com.johann.designPattern.dahao.mediator;

import lombok.Data;

/** 聊天室消息
 * @Description: Message
 * @Auther: Johann
 * @Version: 1.0
 */
@Data
public class Message {
    private String content;
    private String toSex;

    public Message() {

    }
    public Message(String content,String toSex) {
        this.content = content;
        this.toSex = toSex;
    }
}
