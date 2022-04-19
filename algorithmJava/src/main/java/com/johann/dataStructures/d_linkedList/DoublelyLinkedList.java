package com.johann.dataStructures.d_linkedList;

/**
 * @ClassName: DoublelyLinkedList
 * @Description: 双向双端链表
 * @Author: Johann
 * @Version: 1.0
 **/
public class DoublelyLinkedList {

    private Node head;
    private Node tail;
    private int size;

    public DoublelyLinkedList(){
        head = null;
        tail = null;
        size = 0;
    }

    private class Node{
        private Object data;
        private Node next;
        private Node prev;

        public Node(Object obj){
            this.data = obj;
        }
    }

    /**
     * 获取头元素
     * @return
     */
    public Object getHead(){
        if(this.head==null){
            return null;
        }
        return this.head.data;
    }

    /**
     * 获取尾部元素
     * @return
     */
    public Object getTail(){
        if(this.tail==null){
            return null;
        }
        return this.tail.data;
    }

    /**
     * 获取指定位置元素值
     * @see java.util.LinkedList#get(int)
     * @param index
     * @return
     */
    public Object get(int index){
        if (!(index >= 0 && index < size)){
            throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
        }
        // 判断当前index在前半部分还是后半部分，再决定从前往后，还是从后往前查找元素
        if (index < (size >> 1)) {
            Node x = head;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x.data;
        } else {
            Node x = tail;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x.data;
        }
    }

    public int size(){
        return size;
    }

    /**
     * 在双向链表头部插入新节点
     * 新节点作为新的头节点，其next指向旧head
     * 旧head的prev为新节点
     * @param obj
     */
    public Object addHead(Object obj){

        Node node = new Node(obj);

        if (size == 0){
            head = node;
            tail = node;
            size++;
            return obj;
        }

        //旧head的prev指向新节点
        head.prev = node;
        //如果链表原本只有一个节点，此时需要更新尾节点tail的前一个节点指针prev
        if (size == 1){
            tail.prev = node;
        }
        //新节点的next指向旧head
        node.next = head;
        //新节点成为新的head
        head = node;
        size++;
        return obj;
    }

    /**
     * 在尾部新增节点
     * 新节点作为新的尾节点，新节点的prev指向旧tail
     * 旧tail的next指向新节点
     * @param obj
     * @return
     */
    public Object addTail(Object obj){

        Node node = new Node(obj);

        if (tail == null){
            head = node;
            tail = node;
            size++;
            return obj;
        }

        //新节点的prev指向旧tail
        node.prev = tail;
        //旧tail 的next指向新节点
        tail.next = node;
        //如果链表原本只有一个节点，此时需要更新头节点head 的 next
        if (size == 1){
            head.next = node;
        }
        //新节点成为新的tail
        tail = node;
        size++;
        return obj;
    }

    /**
     * 删除头节点
     * 旧head的下一个节点为新head
     * 新head前一个节点指针为null
     * @return
     */
    public Object deleteHead(){
        if(size == 0){
            return null;
        }
        Object obj = head.data;
        Node nextNode = head.next;
        if(nextNode != null){
            //新节点的前一个节点指针置空
            nextNode.prev = null;
        }
        //判断尾节点
        if (size == 1){
            //如果原来链表只有一个节点，此时更新尾节点为null
            tail = null;
        }else if (size == 2){
            //如果原来链表只有两个节点，此时更新尾节点的前一个节点指针为null
            tail.prev = null;
        }
        //旧head后一个变为新的头节点
        head = nextNode;
        size--;
        return obj;
    }


    public String traversal(){
        StringBuilder stringBuilder = new StringBuilder("");
        if (size == 0){
            stringBuilder.append("[]");
        }else if (size == 1){
            stringBuilder.append("[")
              .append(head.data)
              .append("]");
        }else {
            Node currentNode = head;
            int dynamicSize = size;
            while(dynamicSize > 0){
                if(currentNode == head){
                    stringBuilder.append("[")
                            .append(head.data);
                }else if(currentNode.next == null){
                    stringBuilder.append("->")
                            .append(currentNode.data)
                            .append("]");
                }else {
                    stringBuilder.append("->")
                            .append(currentNode.data);
                }
                dynamicSize--;
                currentNode = currentNode.next;
            }
        }
        return stringBuilder.toString();
    }

    public static void sout(DoublelyLinkedList dpl){
        System.out.println("=======");
        System.out.println(dpl.traversal());
        System.out.println("head : "+(dpl.head==null?"null":dpl.head.data));
        System.out.println("tail : "+(dpl.tail==null?"null":dpl.tail.data));
        System.out.println("#######");
    }

    public static void main(String[] args) {
        DoublelyLinkedList dpl = new DoublelyLinkedList();
        dpl.addHead("A");
        sout(dpl);

        dpl.deleteHead();
        sout(dpl);

        dpl.addTail("B");
        dpl.addTail("C");
        sout(dpl);

        dpl.deleteHead();
        sout(dpl);
        dpl.deleteHead();
        sout(dpl);
    }


}
