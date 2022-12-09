package com.johann.jianzhiOffer;
//输入两棵二叉树A和B，判断B是不是A的子结构。(约定空树不是任意一个树的子结构)
//
// B是A的子结构， 即 A中有出现和B相同的结构和节点值。 
//
// 例如: 给定的树 A: 
//
// 3 / \ 4 5 / \ 1 2 给定的树 B： 
//
// 4 / 1 返回 true，因为 B 与 A 的一个子树拥有相同的结构和节点值。 
//
// 示例 1： 
//
// 输入：A = [1,2,3], B = [3,1]
//输出：false
// 
//
// 示例 2： 
//
// 输入：A = [3,4,5,1,2], B = [4,1]
//输出：true 
//
// 限制： 
//
// 0 <= 节点个数 <= 10000 
//
// Related Topics 树 深度优先搜索 二叉树 👍 661 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.*;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class SolutionJianzhi026 {
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if (A == null || B == null) {
            return false;
        }else {
            return traversalCompare(A,B);
        }
    }

    /**
     * 判断B树是否是A树的子结构
     * FIXME
     * @param rootA
     * @param rootB
     * @return
     */
    public boolean traversalCompare(TreeNode rootA,TreeNode rootB){
        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.offer(rootA);
        while (!deque.isEmpty()) {
            TreeNode current = deque.poll();
            if (current.left!=null){
                deque.offer(current.left);
            }
            if (current.right!=null){
                deque.offer(current.right);
            }
            // 找到与B树root值相同的 A树节点。这是判断B树是否是A树的子结构的起始位置
            if (current.val == rootB.val) {
                Deque<TreeNode> dequeB = new ArrayDeque<>();
                Deque<TreeNode> dequeA = new ArrayDeque<>();
                dequeB.offer(rootB);
                dequeA.offer(current);
                while (!dequeB.isEmpty()) {
                    TreeNode currentB = dequeB.poll();
                    TreeNode currentA = dequeA.poll();
                    if (currentB.left!=null){
                        if (currentA.left != null && currentB.left.val == currentA.left.val) {
                            dequeA.offer(currentA.left);
                            dequeB.offer(currentB.left);
                        }else {
                            /**
                             * 由于A树可能存在值重复的节点，所以，此处不能直接返回 false，而是应该终止内循环
                             * A树: [4,2,3,4,5,6,7,8,9] B树: [4,8,9]
                             * 应该在内循环的下方进行判断，在循环结束以后判断 dequeB 是否为空，若为空，那么此时 B树是A树的子结构。
                             * 否则，继续遍历 A树
                             */
                            //return false;
                            break;
                        }
                    }
                    if (currentB.right!=null){
                        if (currentA.right != null && currentB.right.val == currentA.right.val) {
                            dequeA.offer(currentA.right);
                            dequeB.offer(currentB.right);
                        }else {
                            //return false;
                            break;
                        }
                    }
                }
                /**
                 * 在循环结束以后判断 dequeB 是否为空，若为空，那么此时 B树是A树的子结构。否则，继续遍历 A树
                 */
                if (dequeB.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 层序遍历二叉树，并将节点值插入到列表中
     * @param root
     * @return List<Integer>
     */
    public List<Integer> traversal(TreeNode root){
        List<Integer> list = new ArrayList<>();
        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.offer(root);
        while (!deque.isEmpty()) {
            TreeNode current = deque.poll();
            list.add(current.val);
            if (current.left!=null){
                deque.offer(current.left);
            }
            if (current.right!=null){
                deque.offer(current.right);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        TreeNode rootA = new TreeNode(1);
        TreeNode nodeA_2 = new TreeNode(2);
        TreeNode nodeA_3 = new TreeNode(3);
        rootA.left = nodeA_2;
        rootA.right = nodeA_3;
        TreeNode nodeA_4 = new TreeNode(4);
        nodeA_2.left = nodeA_4;

        TreeNode rootB = new TreeNode(3);

        SolutionJianzhi026 solutionJianzhi026 = new SolutionJianzhi026();
        System.out.println(solutionJianzhi026.isSubStructure(rootA,rootB));
    }
}
//leetcode submit region end(Prohibit modification and deletion)
