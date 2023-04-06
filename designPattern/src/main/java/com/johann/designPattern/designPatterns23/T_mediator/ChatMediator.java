package com.johann.designPattern.designPatterns23.T_mediator;

/** 中介者抽象接口
 * @description:
 * @author: Johann
 */
public interface ChatMediator {
    void addUser(AbstractUser user);
    void removeUser(AbstractUser user);
    void sendMessage(Message msg, AbstractUser user);
}
