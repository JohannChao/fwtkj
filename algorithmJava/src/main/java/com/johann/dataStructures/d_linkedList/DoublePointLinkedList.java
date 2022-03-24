package com.johann.dataStructures.d_linkedList;

/**
 * @ClassName: DoublePointLinkedList
 * @Description: 单向双端链表
 * @Author: Johann
 * @Version: 1.0
 **/
public class DoublePointLinkedList {

    private Node head;
    private Node tail;
    private int size;

    private class Node{
        private Object data;
        private Node next;

        public Node(Object obj){
            this.data = obj;
        }
    }

    public DoublePointLinkedList(){
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * 链表头部插入新节点
     * @param obj
     * @return
     */
    public Object addHead(Object obj){
        Node node = new Node(obj);

        if(head == null){
            head = node;
            tail = node;
        }else{
            // 新增节点的next指向原来的旧头节点
            node.next = head;
            head = node;
        }
        size++;
        return obj;
    }

    /**
     * 在链表尾部加入新节点
     * @param obj
     * @return
     */
    public Object addTail(Object obj){
        Node node = new Node(obj);
        if (head == null){
            head = node;
            tail = node;
            size++;
        }else{
            // 原先尾节点的next指向新增节点
            tail.next = node;
            tail = node;
            size++;
        }
        return obj;
    }

    /**
     * 删除头节点
     * @return
     */
    public Object deleteHead(){
        if(size == 0){
            return null;
        }
        Object obj = head.data;
        head = head.next;
        if (size == 1){
            tail = null;
        }
        size--;
        return obj;
    }

    /**
     * 是否为空
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * 获取链表元素个数
     * @return
     */
    public int getSize(){
        return size;
    }

    /**
     * 遍历链表
     */
    public void traversal(){
        if(size == 0){
            System.out.println("[]");
            return;
        }else if(size == 1){
            System.out.println("["+head.data+"]");
            return;
        }
        int dynamicSize = size;
        Node current = head;
        while(dynamicSize > 0){
            if(current == head){
                System.out.print("["+current.data);
            }else if(current.next == null){
                System.out.print("->"+current.data+"]");
            }else{
                System.out.print("->"+current.data);
            }
            current = current.next;
            dynamicSize--;
        }
        System.out.println();
    }

}
