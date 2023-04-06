package com.johann.designPattern.designPatterns23.T_mediator;

import lombok.Data;

/** 聊天室消息
 * @description: Message
 * @auther: Johann
 * @version: 1.0
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
