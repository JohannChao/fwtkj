package com.johann.dataStructures.d_linkedList;

/**
 * @ClassName: SinglyLinkedStack
 * @Description:  使用 单向链表实现 栈
 * @Author: Johann
 * @Version: 1.0
 **/
public class SinglyLinkedStack {
    private SinglyLinkedList singlyLinkedList;

    public SinglyLinkedStack(){
        singlyLinkedList = new SinglyLinkedList();
    }

    /**
     * 添加元素
     */
    public void push(Object obj){
        singlyLinkedList.addHead(obj);
    }

    /**
     * 删除元素
     */
    public void pop(){
        singlyLinkedList.deleteHead();
    }

    /**
     * 判断栈是否为空
     */
    public boolean isEmpty(){
        return singlyLinkedList.isEmpty();
    }

    /**
     * 遍历栈元素
     */
    public void traversal(){
        singlyLinkedList.traversal();
    }
}
