package com.johann.designPattern.dahao.mediator;

/** 中介者模式Demo
 * @Description: ZyhMediatorDemo
 * @Auther: Johann
 * @Version: 1.0
 */
public class ZyhMediatorDemo {
    public static void main(String[] args) {
        ChatMediator chatMediator = new ChatRoom();
        User user1 = new UserInChat(chatMediator, "Johann","male");
        User user2 = new UserInChat(chatMediator, "Jessie","female");
        User user3 = new UserInChat(chatMediator, "Peter","male");
        User user4 = new UserInChat(chatMediator, "William","male");
        User user5 = new UserInChat(chatMediator, "Jennifer","female");
        chatMediator.addUser(user1);
        chatMediator.addUser(user2);
        chatMediator.addUser(user3);
        chatMediator.addUser(user4);
        chatMediator.addUser(user5);
        Message message1 = new Message("Hello","female");
        user1.send(message1);
        Message message2 = new Message("Hi",null);
        user2.send(message2);
        chatMediator.removeUser(user4);
    }
}
