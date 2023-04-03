package com.johann.designPattern.dahao.mediator;

import java.util.ArrayList;
import java.util.List;

/** 中介者实现类——聊天室
 * @Description: ChatRoom
 * @Auther: Johann
 * @Version: 1.0
 */
public class ChatRoom implements ChatMediator {
    private List<User> chatUsers;

    public ChatRoom() {
        this.chatUsers = new ArrayList<>();
    }

    /** 添加用户
     * @description:
     * @duthor: Johann
     * @param user:
     * @return: void
     */
    @Override
    public void addUser(User user) {
        this.chatUsers.add(user);
        System.out.println("["+user.name + "]加入聊天室");
    }

    /** 删除用户
     * @description:
     * @duthor: Johann
     * @param user:
     * @return: void
     */
    @Override
    public void removeUser(User user) {
        this.chatUsers.remove(user);
        System.out.println("["+user.name + "]离开聊天室");
    }

    /** 中介者分发消息，可以根据消息中的接收性别进行分发
     * @description:
     * @duthor: Johann
     * @param msg:
     * @param user:
     * @return: void
     */
    @Override
    public void sendMessage(Message msg, User user) {
        if (msg.getToSex()==null||msg.getToSex().isEmpty()){
            chatUsers.forEach(u -> {
                if (!u.equals(user)) {
                    u.receive(msg);
                }
            });
        }else {
            chatUsers.forEach(u -> {
                if (!u.equals(user)&&msg.getToSex().equals(u.getSex())) {
                    u.receive(msg);
                }
            });
        }
    }
}
