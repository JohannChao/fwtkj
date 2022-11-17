package com.johann.dataStructures.d_linkedList;

/**
 * @ClassName: DoublePointLinkedQueue
 * @Description: 使用单向双端链表实现 队列
 * @Author: Johann
 * @Version: 1.0
 **/
public class DoublePointLinkedQueue {

    private DoublePointLinkedList doublePointLinkedList;

    public DoublePointLinkedQueue(){
        doublePointLinkedList = new DoublePointLinkedList();
    }

    public Object offer(Object obj){
        return doublePointLinkedList.addTail(obj);
    }

    public Object poll(){
        return doublePointLinkedList.deleteHead();
    }

    public Object peak() {
        return doublePointLinkedList.getHead();
    }
    public int getSize(){
        return doublePointLinkedList.getSize();
    }

    public boolean isEmpty(){
        return doublePointLinkedList.isEmpty();
    }

    public void traversal(){
        doublePointLinkedList.traversal();
    }


}
