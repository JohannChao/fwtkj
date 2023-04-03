package com.johann.designPattern.dahao.mediator;

/** 交互对象实现类——用户
 * @Description: UserInChat
 * @Auther: Johann
 * @Version: 1.0
 */
public class UserInChat extends User {

    public UserInChat(ChatMediator mediator, String name, String sex) {
        super(mediator, name ,sex);
    }

    /** 发送消息
     * @description:
     * @duthor: Johann
     * @param msg:
     * @return: void
     */
    @Override
    public void send(Message msg) {
        System.out.println("["+this.name + "]发送消息：" + msg.getContent());
        mediator.sendMessage(msg, this);
    }

    /** 接收消息
     * @description:
     * @duthor: Johann
     * @param msg:
     * @return: void
     */
    @Override
    public void receive(Message msg) {
        System.out.println("["+this.name + "]接收消息：" + msg.getContent());
    }
}
