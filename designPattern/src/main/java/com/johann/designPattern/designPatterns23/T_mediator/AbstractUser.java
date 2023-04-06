package com.johann.designPattern.designPatterns23.T_mediator;

import lombok.Data;

/** 交互对象抽象类
 * @description: AbstractUser
 * @auther: Johann
 * @version: 1.0
 */
@Data
public abstract class AbstractUser {
    protected ChatMediator mediator;
    protected String name;
    protected String sex;
    public AbstractUser(ChatMediator mediator, String name,String sex) {
        this.mediator = mediator;
        this.name = name;
        this.sex = sex;
    }

    /** 发送消息
     * @description:
     * @duthor: Johann
     * @param msg:
     * @return: void
     */
    public abstract void send(Message msg);

    /** 接收消息
     * @description:
     * @duthor: Johann
     * @param msg:
     * @return: void
     */
    public abstract void receive(Message msg);
}
