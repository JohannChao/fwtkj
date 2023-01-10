package com.johann.javaFunctionalProgramming.jdkFunctionDemo;

import lombok.Data;

import java.util.function.Function;

/** 开闭原则
 * @ClassName: OCPDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
@Data
public class OCPDemo {
        @Data
        public static class Message{
            private String addr;
            private String title;
            private String content;
        }
        private Message message;
        public void send(Function<Message , String> function){
            function.apply(message);
        }

        public static void main(String ...s){
            Message message = new Message();
            message.setTitle("this is a qq msg");
            OCPDemo demo = new OCPDemo();
            demo.setMessage(message);
//            demo.send(new Function<Message, Boolean>() {
//                @Override
//                public Boolean apply(Message msg) {
//                    System.out.println("send qq:\t"+msg.getTitle());
//                    return true;
//                }
//            });
            demo.send(msg -> {
                System.out.println("send qq:\t"+msg.getTitle());
                return msg.getTitle();
            });
        }
}
