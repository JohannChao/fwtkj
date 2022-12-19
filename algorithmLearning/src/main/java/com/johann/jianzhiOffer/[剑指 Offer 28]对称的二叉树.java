package com.johann.jianzhiOffer;
//请实现一个函数，用来判断一棵二叉树是不是对称的。如果一棵二叉树和它的镜像一样，那么它是对称的。
//
// 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。 
//
// 1 / \ 2 2 / \ / \ 3 4 4 3 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的: 
//
// 1 / \ 2 2 \ \ 3 3 
//
// 
//
// 示例 1： 
//
// 输入：root = [1,2,2,3,4,4,3]
//输出：true
// 
//
// 示例 2： 
//
// 输入：root = [1,2,2,null,3,null,3]
//输出：false 
//
// 
//
// 限制： 
//
// 0 <= 节点个数 <= 1000 
//
// 注意：本题与主站 101 题相同：https://leetcode-cn.com/problems/symmetric-tree/ 
//
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 401 👎 0


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
class SolutionJianzhi028 {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }else {
            //return testSymmetric(root);
            return testSymmetricRecursion(root,root);
        }
    }

    /**
     * 递归方式判断二叉树是否是对称的
     * @return
     */
    public boolean testSymmetricRecursion(TreeNode node,TreeNode mirrorNode){
        if (node == null && mirrorNode == null) {
            return true;
        }
        if ((node == null || mirrorNode == null) || node.val != mirrorNode.val) {
            return false;
        }
        return testSymmetricRecursion(node.left,mirrorNode.right) && testSymmetricRecursion(node.right,mirrorNode.left);
    }


    /**
     * 迭代方式判断二叉树是否是对称的
     * @param root
     * @return
     */
    public boolean testSymmetric(TreeNode root){
        Deque<TreeNode> deque = new ArrayDeque<>();
        Deque<TreeNode> mirrorDeque = new ArrayDeque<>();
        deque.offer(root);
        mirrorDeque.offer(root);
        while (!deque.isEmpty()) {
            // 迭代遍历二叉树的同时，遍历二叉树的镜像节点
            TreeNode current = deque.poll();
            TreeNode mirrorCurrent = mirrorDeque.poll();
            assert mirrorCurrent != null;
            if (current.left != null) {
                // 如果当前节点的left 与 镜像节点的right 不相同，直接退出遍历迭代，返回false
                if (mirrorCurrent.right == null || current.left.val != mirrorCurrent.right.val) {
                    return false;
                }else {
                    // 如果当前节点的left 与 镜像节点的right 相同，将这两个互为镜像节点的子节点插入到队列中
                    deque.offer(current.left);
                    mirrorDeque.offer(mirrorCurrent.right);
                }
            }else if (mirrorCurrent.right != null){
                return false;
            }

            if (current.right != null) {
                if (mirrorCurrent.left == null || current.right.val != mirrorCurrent.left.val) {
                    return false;
                }else {
                    deque.offer(current.right);
                    mirrorDeque.offer(mirrorCurrent.left);
                }
            }else if (mirrorCurrent.left != null){
                return false;
            }
        }
        return true;
    }

}
//leetcode submit region end(Prohibit modification and deletion)
