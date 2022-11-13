package com.johann.jianzhiOffer;
//请实现 copyRandomList 函数，复制一个复杂链表。在复杂链表中，每个节点除了有一个 next 指针指向下一个节点，还有一个 random 指针指
//向链表中的任意节点或者 null。 
//
// 
//
// 示例 1： 
//
// 
//
// 输入：head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
//输出：[[7,null],[13,0],[11,4],[10,2],[1,0]]
// 
//
// 示例 2： 
//
// 
//
// 输入：head = [[1,1],[2,1]]
//输出：[[1,1],[2,1]]
// 
//
// 示例 3： 
//
// 
//
// 输入：head = [[3,null],[3,0],[3,null]]
//输出：[[3,null],[3,0],[3,null]]
// 
//
// 示例 4： 
//
// 输入：head = []
//输出：[]
//解释：给定的链表为空（空指针），因此返回 null。
// 
//
// 
//
// 提示： 
//
// 
// -10000 <= Node.val <= 10000 
// Node.random 为空（null）或指向链表中的节点。 
// 节点数目不超过 1000 。 
// 
//
// 
//
// 注意：本题与主站 138 题相同：https://leetcode-cn.com/problems/copy-list-with-random-
//pointer/ 
//
// 
//
// Related Topics 哈希表 链表 👍 612 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.LinkedHashMap;

// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
class SolutionJIanzhi035 {

    /***************************** 方法二 **********************************/
    /**
     * 和方法1一个思路，但是少用一个数组，只引入一个哈希表
     * 时间O(n),空间O(n)
     * @param head
     * @return
     */
    public Node copyRandomList2(Node head) {
        return null;
    }
    /***************************** 方法二 **********************************/

    /***************************** 方法三 **********************************/
    /**
     * 不引入哈希表，在原来的链表基础上扩展
     * 时间O(n),空间O(1)
     * @param head
     * @return
     */
    public Node copyRandomList3(Node head) {
        return null;
    }
    /***************************** 方法三 **********************************/

    /***************************** 方法一 **********************************/
    /**
     * 新链表，深复制完后存放在数组中
     */
    Node[] newNodeArray = null;
    /**
     * 旧链表元素，放入Map< 当前元素,当前元素索引 >
     */
    LinkedHashMap<Node,Integer> oldNodeMap = null;
    int size = 0,index = 0;
    /**
     * 借助辅助数组和哈希表，递归实现
     * 时间O(n),空间O(n)
     * 1),遍历旧链表，将旧链表中的元素，按照索引索引顺序，放入数组中（原题是深复制，因此数组中的元素，必须重新创建）
     * 2),遍历旧链表，将数组中的元素依次组装成新的链表
     *
     * @param head
     * @return
     */
    public Node copyRandomList(Node head) {
        if (head == null){
            return null;
        }
        recursion(head);
        //遍历查出旧链表中random元素的 random_index
        for (Node current : oldNodeMap.keySet()) {
//            Integer currentIndex = oldNodeMap.get(current);
//            Node currentRandom = current.random;
//            int randomIndex = (current.random==null ? -1 : oldNodeMap.get(currentRandom));
//            // 根据旧链表中元素的random值，将当前数组元素的random与random_index索引对应上
//            newNodeArray[currentIndex].random = (randomIndex > -1 ? newNodeArray[randomIndex] : null);
            // 根据旧链表中元素的random值，将当前数组元素的random与random_index索引对应上
            newNodeArray[oldNodeMap.get(current)].random = (current.random != null ? newNodeArray[oldNodeMap.get(current.random)] : null);
        }
        return newNodeArray[0];
    }

    /**
     * 递归实现
     * 数组的填充是从后往前填充的
     * 此处不使用 ArrayList 的原因是：
     *   在不知道旧链表长度的情况下，向ArrayList中添加数据，如果旧链表长度很长的话，可能会引起频繁扩容。
     * @param head
     */
    public void recursion(Node head){
        if (head == null){
            newNodeArray = new Node[size];
            oldNodeMap = new LinkedHashMap<>(size);
            index = size-1;
            return;
        }
        size++;
        recursion(head.next);
        // 深复制
        newNodeArray[index] = new Node(head.val);
        // 将当前数组元素的next，指向上一层新建的元素
        newNodeArray[index].next = (index==size-1 ? null : newNodeArray[index+1]);
        // 旧链表数据插入到hashMap中
        oldNodeMap.put(head,index);
        index--;
    }
    /***************************** 方法一 **********************************/

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        Node node8 = new Node(8);
        node1.next = node2;
        node1.random = null;

        node2.next = node3;
        node2.random = node1;

        node3.next = node4;
        node3.random = node2;

        node4.next = node5;
        node4.random = node6;

        node5.next = node6;
        node5.random = node4;

        node6.next = node7;
        node6.random = node6;

        node7.next = node8;
        node7.random = null;

        node8.random = node5;

        new SolutionJIanzhi035().copyRandomList(node1);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
