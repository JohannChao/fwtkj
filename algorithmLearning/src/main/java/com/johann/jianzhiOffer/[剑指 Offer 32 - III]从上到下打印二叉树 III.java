package com.johann.jianzhiOffer;
//请实现一个函数按照之字形顺序打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右到左的顺序打印，第三行再按照从左到右的顺序打印，其他行以此类推。
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
// 返回其层次遍历结果： 
//
// [
//  [3],
//  [20,9],
//  [15,7]
//]
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
// Related Topics 树 广度优先搜索 二叉树 👍 259 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class SolutionJianzhi032III {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> deque = new ArrayDeque<>();
        // 将根节点入队
        deque.add(root);
        boolean levelFlag = true;
        // 队列不为空
        while (!deque.isEmpty()) {
            // 当前层数的节点list
            List<Integer> levelVal = new ArrayList<>();
            // 当前层数的节点数
            int queueSize = deque.size();
            for (int i=0;i<queueSize;i++){
                TreeNode current = deque.poll();
                TreeNode levelValNode = levelFlag ? deque.poll() : deque.pollLast();
                // 将当前层的节点值保存到list
                levelVal.add(levelValNode.val);
                if (current.left != null) {
                    deque.offer(current.left);
                }
                if (current.right != null) {
                    deque.offer(current.right);
                }
            }
            levelFlag = !levelFlag;
            result.add(levelVal);
        }
        return result;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
