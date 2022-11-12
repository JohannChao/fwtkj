package com.johann.jianzhiOffer;
//定义栈的数据结构，请在该类型中实现一个能够得到栈的最小元素的 min 函数在该栈中，调用 min、push 及 pop 的时间复杂度都是 O(1)。
//
// 
//
// 示例: 
//
// MinStack minStack = new MinStack();
//minStack.push(-2);
//minStack.push(0);
//minStack.push(-3);
//minStack.min();   --> 返回 -3.
//minStack.pop();
//minStack.top();      --> 返回 0.
//minStack.min();   --> 返回 -2.
// 
//
// 
//
// 提示： 
//
// 
// 各函数的调用总次数不超过 20000 次 
// 
//
// 
//
// 注意：本题与主站 155 题相同：https://leetcode-cn.com/problems/min-stack/ 
//
// Related Topics 栈 设计 👍 405 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class MinStack {
    /**
     * 定义一个存放数据的栈，以及存放最小值的栈
     */
    JohannStack dataStack,minStack;

    /** initialize your data structure here. */
    public MinStack() {
        dataStack = new JohannStack();
        minStack = new JohannStack();
    }
    
    public void push(int x) {
        dataStack.push(x);
        boolean minFlag = (minStack.peak() == null || minStack.peak() > x);
        minStack.push((minFlag ? x : minStack.peak()));
    }

    /**
     * 弹出顶端节点的时候，如果该节点是最小值，如何确定下一个最小的节点？
     */
    public void pop() {
        dataStack.pop();
        minStack.pop();
    }
    
    public int top() {
        return dataStack.peak() == null ? -1 : dataStack.peak();
    }
    
    public int min() {
        return minStack.peak() == null ? -1 : minStack.peak();
    }
}

class JohannStack{
    /** 顶节点 */
    private JohannStackNode head;
    /** 节点长度 */
    private Integer size;
    private static class JohannStackNode{
        Integer val;
        JohannStackNode next;
        public JohannStackNode(){}
        public JohannStackNode(Integer val){
            this.val= val;
        }
    }

    public JohannStack() {
        size = 0;
    }

    /**
     * 从顶端压入新元素
     * @param x
     */
    public void push(int x) {
        // 原来的顶节点
        JohannStackNode originalHead = head;
        head = new JohannStackNode(x);
        head.next = originalHead;
        size++;
    }

    /**
     * 弹出顶节点元素
     */
    public Integer pop() {
        if (size == 0){
            return null;
        }else {
            Integer originalHeadVal = head.val;
            head = head.next;
            size--;
            return originalHeadVal;
        }
    }

    /**
     * 查看顶节点元素
     */
    public Integer peak() {
        if (size == 0){
            return null;
        }else {
            return head.val;
        }
    }
}


/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.min();
 */
//leetcode submit region end(Prohibit modification and deletion)
