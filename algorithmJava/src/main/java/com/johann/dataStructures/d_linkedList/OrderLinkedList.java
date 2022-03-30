package com.johann.dataStructures.d_linkedList;

/**
 * @ClassName: OrderLinkedList
 * @Description: 有序链表
 * @Author: Johann
 * @Version: 1.0
 **/
public class OrderLinkedList {

    private Node head;
    private int size;

    public OrderLinkedList(){
        head = null;
        size = 0;
    }

    private class Node{
        private Integer data;
        private Node next;

        public Node(Integer obj){
            this.data = obj;
        }
    }

    /**
     * 有序链表插入数据
     * @param value
     */
    public void insert(Integer value){
        Node now = new Node(value);

        Node prev = null;
        Node current = head;

        while(current != null && current.data < value){
            prev = current;
            current = current.next;
        }

        if (prev == null){
            now.next = current;
            head = now;
        }else{
            prev.next = now;
            now.next = current;
        }
        size++;
    }

    /**
     * 有序链表删除表头（最小值）
     */
    public Integer deleteHead(){
        if (head == null){
            return null;
        }
        Integer del = head.data;
        head = head.next;
        size--;
        return del;
    }

    /**
     * 遍历链表
     * @param orderLinkedList
     */
    public void traversal(){
        Node node = head;
        System.out.print("OrderLinkedList ： ");
        while(node != null){
            System.out.print(node.data);
            node = node.next;
            if(node != null){
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }


    public static void main(String[] args) {
        OrderLinkedList orderLinkedList = new OrderLinkedList();
        orderLinkedList.insert(5);
        orderLinkedList.traversal();
        orderLinkedList.deleteHead();
        orderLinkedList.traversal();
        orderLinkedList.insert(1);
        orderLinkedList.insert(3);
        orderLinkedList.insert(4);
        orderLinkedList.insert(2);
        orderLinkedList.traversal();
    }
}
