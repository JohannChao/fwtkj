package com.johann.jianzhiOffer;
//输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。
//
// 
//
// 示例 1： 
//
// 输入：head = [1,3,2]
//输出：[2,3,1] 
//
// 
//
// 限制： 
//
// 0 <= 链表长度 <= 10000 
//
// Related Topics 栈 递归 链表 双指针 👍 350 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

// Definition for singly-linked list.
class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}

class SolutionJianzhi006 {
    /**
     * 辅助栈实现
     * @param head
     * @return
     */
    public int[] reversePrint(ListNode head) {
        if (head == null) {
            return new int[0];
        }
        Deque<Integer> deque = new ArrayDeque();
        do {
            deque.push(head.val);
        } while ((head = head.next) != null);

        int size = deque.size();
        int[] ints = new int[size];
        int i = 0;
        while (i < size) {
            //pop()如果队列为空，抛出异常NoSuchElementException；poll()队列为空，返回null
            ints[i] = deque.pop();
            i++;
        }
        return ints;
    }

    /**
     * 递归实现
     * @param head
     * @return
     */
    public int[] reversePrintRecursion(ListNode head){
        if (head == null) {
            return new int[0];
        }
        ArrayList<Integer> list = new ArrayList();
        recursion(head,list);
        int[] ints = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ints[i] = list.get(i);
        }
        return ints;
    }
    public void recursion(ListNode head, ArrayList list){
        //递归出口
        if (head == null) {
            return;
        }
        recursion(head.next, list);
        list.add(head.val);
    }

    /**
     * 在递归出口处，获取链表长度，创建数组。减少后续遍历
     * https://leetcode.cn/problems/cong-wei-dao-tou-da-yin-lian-biao-lcof/solutions/97270/mian-shi-ti-06-cong-wei-dao-tou-da-yin-lian-biao-d/comments/596683
     */
    //待返回的数组
    int[] result = null;
    //链表长度，即数组长度。数组索引偏移量
    int size = 0, index = 0;
    public int[] reversePrintRecursion2(ListNode head){
        if (head == null) {
            return new int[0];
        }
        recursion2(head);
        return result;
    }

    public void recursion2(ListNode head){
        if (head == null) {
            result = new int[size];
            return;
        }
        //递归过程中，当返回上一圈时，从上一圈执行到的点位开始，继续按顺序执行接下来的代码
        size++;
        recursion2(head.next);
        result[index] = head.val;
        index++;
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        node1.next = node2;
        ListNode node3 = new ListNode(3);
        node2.next = node3;
        ListNode node4 = new ListNode(4);
        node3.next = node4;
        ListNode node5 = new ListNode(5);
        node4.next = node5;
        ListNode node6 = new ListNode(6);
        node5.next = node6;
        ListNode node7 = new ListNode(7);
        node6.next = node7;
        ListNode node8 = new ListNode(8);
        node7.next = node8;

        new SolutionJianzhi006().reversePrintRecursion2(node1);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
