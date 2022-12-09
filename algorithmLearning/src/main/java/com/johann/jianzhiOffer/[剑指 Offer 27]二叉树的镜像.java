package com.johann.jianzhiOffer;
//请完成一个函数，输入一个二叉树，该函数输出它的镜像。
//
// 例如输入： 
//
// 4 / \ 2 7 / \ / \ 1 3 6 9 镜像输出： 
//
// 4 / \ 7 2 / \ / \ 9 6 3 1 
//
// 
//
// 示例 1： 
//
// 输入：root = [4,2,7,1,3,6,9]
//输出：[4,7,2,9,6,3,1]
// 
//
// 
//
// 限制： 
//
// 0 <= 节点个数 <= 1000 
//
// 注意：本题与主站 226 题相同：https://leetcode-cn.com/problems/invert-binary-tree/ 
//
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 323 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class SolutionJianzhi027 {

    /**
     * 该方法没有改变原来的二叉树
     * @param root
     * @return
     */
    public TreeNode mirrorTree(TreeNode root) {
        if (root == null) {
            return null;
        }else {
            // 广度优先搜索
            Deque<TreeNode> deque = new ArrayDeque<>();
            deque.offer(root);
            // 镜像二叉树根节点
            TreeNode newRoot = new TreeNode(root.val);
            // 记录镜像二叉树的遍历节点
            Deque<TreeNode> newDeque = new ArrayDeque<>();
            newDeque.offer(newRoot);
            while (!deque.isEmpty()) {
                TreeNode current = deque.poll();
                TreeNode newCurrent = newDeque.poll();
                assert newCurrent != null;
                // 如果原树的当前节点存在左子树，在新树中，将这个左子树设置为右子树
                if (current.left != null) {
                    deque.offer(current.left);
                    TreeNode newRight = new TreeNode(current.left.val);
                    newCurrent.right = newRight;
                    newDeque.offer(newRight);
                }
                // 如果原树的当前节点存在右子树，在新树中，将这个右子树设置为左子树
                if (current.right != null) {
                    deque.offer(current.right);
                    TreeNode newLeft = new TreeNode(current.right.val);
                    newCurrent.left = newLeft;
                    newDeque.offer(newLeft);
                }
            }
            return newRoot;
        }
    }

    /**
     * 直接在原树的基础上修改
     * @param root
     * @return
     */
    public TreeNode mirrorTree2(TreeNode root) {
        if (root == null) {
            return null;
        }else {
            // 广度优先搜索
            Deque<TreeNode> deque = new ArrayDeque<>();
            deque.offer(root);
            while (!deque.isEmpty()) {
                TreeNode current = deque.poll();
                // 如果原树的当前节点存在左子树，将其反转为右子树；如果原树的当前节点存在右子树，将其反转为左子树
                TreeNode left = current.left;
                TreeNode right = current.right;
                if (left != null) {
                    deque.offer(left);
                }
                current.right = left;
                if (right != null) {
                    deque.offer(right);
                }
                current.left = right;
            }
            return root;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
