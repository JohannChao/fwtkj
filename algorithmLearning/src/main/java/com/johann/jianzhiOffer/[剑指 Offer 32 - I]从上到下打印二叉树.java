package com.johann.jianzhiOffer;
//从上到下打印出二叉树的每个节点，同一层的节点按照从左到右的顺序打印。
//
// 
//
// 例如: 给定二叉树: [3,9,20,null,null,15,7], 
//
//     3
//   / \
//  9  20
//    /  \
//   15   7
// 
//
// 返回： 
//
// [3,9,20,15,7]
// 
//
// 
//
// 提示： 
//
// 
// 节点总数 <= 1000 
// 
//
// Related Topics 树 广度优先搜索 二叉树 👍 236 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import sun.reflect.generics.tree.Tree;

import java.util.*;

// Definition for a binary tree node.
 class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
}

class SolutionJianzhi032I {
    public int[] levelOrder(TreeNode root) {
        if (root == null) {
            return new int[0];
        }
        int[] nums = new int[1000];
        Deque<TreeNode> deque = new ArrayDeque<>();
        // 将根节点入队
        deque.add(root);
        int index = 0;
        nums[index] = root.val;
        // 队列不为空
        while (!deque.isEmpty()) {
            TreeNode current = deque.poll();
            if (current.left != null) {
                deque.add(current.left);
                nums[++index] = current.left.val;
            }
            if (current.right != null) {
                deque.add(current.right);
                nums[++index] = current.right.val;
            }
        }
        return Arrays.copyOf(nums,index+1);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
