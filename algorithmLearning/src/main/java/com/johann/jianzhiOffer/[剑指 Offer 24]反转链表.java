package com.johann.jianzhiOffer;
//定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。
//
// 
//
// 示例: 
//
// 输入: 1->2->3->4->5->NULL
//输出: 5->4->3->2->1->NULL 
//
// 
//
// 限制： 
//
// 0 <= 节点个数 <= 5000 
//
// 
//
// 注意：本题与主站 206 题相同：https://leetcode-cn.com/problems/reverse-linked-list/ 
//
// Related Topics 递归 链表 👍 506 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class SolutionJianzhi024 {
    /***************************** 方法一，迭代 **********************************/
    /**
     * 将当前的节点的next，指向原来的上一个节点
     * 空间O(n), 时间O(n)
     */
    public ListNode reverseList(ListNode head) {
        // 旧链表中当前节点的上一个节点
        ListNode prev = null;
        // 旧链表中的当前节点
        ListNode current = head;
        // 旧链表中当前节点的下一个节点
        ListNode next = null;

        while (current != null) {
            // 1）获取当前节点【旧链表中的下一个节点】
            next = current.next;
            // 2）将当前节点的下一个节点，指向【旧链表中原本的上一个节点】
            current.next = prev;
            // 3）将当前节点，存储为 prev。
            // 等待下一轮调用时，用于指向【旧链表中的下一个节点】的 next，其实就是为下一个循环的步骤 2）服务
            prev = current;
            // 控制循环，将 current 替换为 current在旧链表中的下一个节点
            current = next;
        }
        // 循环的出口是，current为null，所以链表中真实的最后一个节点应该是 current的上一个节点 prev
        return prev;
    }
    /***************************** 方法一，迭代 **********************************/

    /***************************** 方法二，递归 **********************************/
    // 反转后的链表的头节点
    private ListNode newTrueHead = null;
    /**
     * 如果不需要返回最新的头节点，如何用递归实现反转链表
     * @param head
     */
    public void reverseListRecursionVoid(ListNode head){
        if (head == null || head.next == null) {
            // 旧链表的最后一个节点，就是反转后的链表的头节点
            newTrueHead = head;
            return;
        }
        reverseListRecursionVoid(head.next);
        // 将当前节点在旧链表中的下一个节点的 next 指向这个头节点（A节点的子节点成了A的父节点）
        head.next.next = head;
        /**
         * 这一步的作用其实就是：
         * 当【归】到第一层时，此时 head的真实值 是旧链表的头节点。
         * 执行 "head.next.next = head;"， 将这个真实头节点的下一个节点的 next 指向这个头节点是没问题。
         * 但是，此时这个真实头节点的 next 是有值的，它指向的是 旧链表中它的next节点，我们必须将这个真实头节点的 next 置为null。
         * 如果不置为null，那么旧链表中的头节点和它的下一个节点，互为next节点，这两个节点就像是衔尾蛇一样，没有出口了（打印一下就知道了）
         */
        head.next = null;
    }

    /**
     * 需要返回最新的头节点，如何用递归实现反转链表
     * @param head
     */
    public ListNode reverseListRecursion(ListNode head){
        if (head == null || head.next == null) {
            // 旧链表的最后一个节点，就是反转后的链表的头节点
            //newTrueHead = head;
            return head;
        }
        newTrueHead = reverseListRecursion(head.next);
        // 将当前节点在旧链表中的下一个节点的 next 指向这个头节点（A节点的子节点成了A的父节点）
        head.next.next = head;
        /**
         * 这一步的作用其实就是：
         * 当【归】到第一层时，此时 head的真实值 是旧链表的头节点。
         * 执行 "head.next.next = head;"， 将这个真实头节点的下一个节点的 next 指向这个头节点是没问题。
         * 但是，此时这个真实头节点的 next 是有值的，它指向的是 旧链表中它的next节点，我们必须将这个真实头节点的 next 置为null。
         * 如果不置为null，那么旧链表中的头节点和它的下一个节点，互为next节点，这两个节点就像是衔尾蛇一样，没有出口了（打印一下就知道了）
         */
        head.next = null;
        /**
         * 有返回值的递归，其实是在【归】的过程中，一层一层的向上将【旧链表的最后一个节点】返回
         * 除了在 head.next.next = head; 这一步，在【归】到倒数第二个节点时，对旧链表的最后一个节点做了处理外，此后并没有对这个节点做任何处理。
         * 这一步的处理步骤是：
         * ```
         *   head 是旧链表倒数第二个节点
         *   head.next 是旧链表的最后一个节点
         *   head.next.next = head; 将最后一个节点的 next 指向 倒数第二个节点。
         * ```
         */
        return newTrueHead;
    }
    /***************************** 方法二，递归 **********************************/

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

        SolutionJianzhi024 solutionJianzhi024 = new SolutionJianzhi024();
        solutionJianzhi024.reverseListRecursionVoid(node1);
        ListNode node = solutionJianzhi024.newTrueHead;
        while (node != null) {
            System.out.println(node.val);
            node = node.next;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
