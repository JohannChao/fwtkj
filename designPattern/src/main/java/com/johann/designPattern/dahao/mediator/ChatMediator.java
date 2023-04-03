package com.johann.designPattern.dahao.mediator;

/** 中介者抽象类
 * @description:
 * @duthor: Johann
 */
public interface ChatMediator {
    void addUser(User user);
    void removeUser(User user);
    void sendMessage(Message msg, User user);
}
