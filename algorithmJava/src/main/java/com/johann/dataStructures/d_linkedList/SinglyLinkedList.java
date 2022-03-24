package com.johann.dataStructures.d_linkedList;

/**
 * @ClassName: SinglyLinkedList
 * @Description: 单向链表的实现
 * @Author: Johann
 * @Version: 1.0
 **/
public class SinglyLinkedList {

    /**链表长度*/
    private int size;
    /**头节点*/
    private Node head;

    public SinglyLinkedList(){
        size = 0;
        head = null;
    }


    /**链表节点*/
    private class Node{
        private Object data;//当前节点值
        private Node next;//当前节点的下一个节点

        public Node(Object obj){
            this.data = obj;
        }
    }

    /**
     * 在链表头新增元素
    */
    public Object addHead(Object obj){
        //原有的头节点
        Node originalHead = head;
        //将新的元素设为头节点
        head = new Node(obj);

        if(originalHead != null){
            // 如果原有的头节点不为空，则将新的头节点next指向它
            head.next = originalHead;
        }
        size++;
        return obj;
    }


    /**
     * 删除链表头元素
     */
    public Object deleteHead(){
        if(size == 0){
            return null;
        }
        Object obj = head.data;
        head = head.next;
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
     * 在链表中查找指定的元素，若存在则返回该元素；若不存在返回null
     * @param obj
     * @return
     */
    public Object find(Object obj){
        if(size == 0){
            return null;
        }

        if(obj == null){
            return null;
        }

        Node current = head;
        int dynamicSize = size;

        while(dynamicSize > 0){
            if(obj.equals(current.data)){
                return obj;
            }else{
                current = current.next;
            }
            dynamicSize--;
        }
        return null;
    }

    /**
     * 删除指定的元素，若删除成功，返回该元素；若不存在该元素，返回null
     * @param obj
     * @return
     */
    public Object delete(Object obj){

        if(size == 0){
            return null;
        }else if(obj == null){
            return null;
        }else if(obj.equals(head.data)){
            head = head.next;
            size = size--;
        }

        Node current = head;
        Node later = head.next;
        int dynamicSize = size-1;

        while(dynamicSize > 0){
            if(obj.equals(later.data)){
                current.next = later.next;
                size --;
                return obj;
            }else{
                current = later;
                later = later.next;
            }
            dynamicSize--;
        }
        return null;
    }

    /**
     * 遍历链表
     */
    public void traversal(){
        if(size == 0){
            System.out.println("[]");
            return;
        }
        if(size == 1){
            System.out.println("["+head.data+"]");
            return;
        }
        int dynamicSize = size;
        Node current = head;
        while(dynamicSize > 0){
            if(current == head){
                System.out.print("["+current.data);
            }else if(current.next == null){
                System.out.println("->"+current.data+"]");
            }else{
                System.out.print("->"+current.data);
            }
            current = current.next;
            dynamicSize--;
        }
    }

    public static void main(String[] args) {
        SinglyLinkedList singlyLinkedList = new SinglyLinkedList();
        Object o1 = singlyLinkedList.find("A");
        System.out.println(o1);
        singlyLinkedList.addHead("A");
        singlyLinkedList.addHead("B");
        singlyLinkedList.addHead("C");
        singlyLinkedList.addHead("D");
        singlyLinkedList.addHead("E");
        singlyLinkedList.traversal();
        Object o2 = singlyLinkedList.delete("F");
        System.out.println(o2);
        singlyLinkedList.delete("D");
        singlyLinkedList.traversal();
    }

}
