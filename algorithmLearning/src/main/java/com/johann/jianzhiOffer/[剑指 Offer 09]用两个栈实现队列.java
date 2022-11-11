package com.johann.jianzhiOffer;
//用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，分别完成在队列尾部插入整数和在队列头部删除整数的
//功能。(若队列中没有元素，deleteHead 操作返回 -1 ) 
//
// 
//
// 示例 1： 
//
// 
//输入：
//["CQueue","appendTail","deleteHead","deleteHead","deleteHead"]
//[[],[3],[],[],[]]
//输出：[null,null,3,-1,-1]
// 
//
// 示例 2： 
//
// 
//输入：
//["CQueue","deleteHead","appendTail","appendTail","deleteHead","deleteHead"]
//[[],[],[5],[2],[],[]]
//输出：[null,-1,null,null,5,2]
// 
//
// 提示： 
//
// 
// 1 <= values <= 10000 
// 最多会对 appendTail、deleteHead 进行 10000 次调用 
// 
//
// Related Topics 栈 设计 队列 👍 627 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class CQueue {
    //链表长度
    private Integer size;
    //头部节点
    private Node head;
    //尾部节点
    private Node tail;
    /**
     * 使用双端单向链表实现队列
     */
    private class Node{
        Integer val;
        Node next;
        public Node(Integer val){
            this.val = val;
        }
    }

    public CQueue() {
        head = null;
        size=0;
    }
    
    public void appendTail(int value) {
        Node node = new Node(value);
        if (size == 0){
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }
    
    public int deleteHead() {
        int res = -1;
        if (size == 0) {
            return res;
        }
        res = head.val;
        head = head.next;
        if (size == 1){
            tail = null;
        }
        size--;
        return res;
    }


    public static void main(String[] args) {
        CQueue cQueue = new CQueue();
        System.out.println(cQueue.deleteHead());
        cQueue.appendTail(1);
        System.out.println(cQueue.deleteHead());
        cQueue.appendTail(2);
        cQueue.appendTail(3);
        cQueue.appendTail(4);
    }
}

/**
 * Your CQueue object will be instantiated and called as such:
 * CQueue obj = new CQueue();
 * obj.appendTail(value);
 * int param_2 = obj.deleteHead();
 */
//leetcode submit region end(Prohibit modification and deletion)
