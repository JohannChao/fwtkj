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
    /**
     * 迭代方式查找子结构
     * @param A
     * @param B
     * @return
     */
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if (A == null || B == null) {
            return false;
        }else {
            return traversalCompare(A,B);
        }
    }

    /**
     * 判断B树是否是A树的子结构
     * @param rootA
     * @param rootB
     * @return
     */
    public boolean traversalCompare(TreeNode rootA,TreeNode rootB){
        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.offer(rootA);
        /**
         * 外循环，遍历A树，查找A树中与B树根节点值相同的节点
         */
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
                boolean isSubFlag = true;
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
                             * 应该在内循环的下方进行判断，如果从 current节点开始遍历的情形下，不满足"子结构"条件，则继续遍历 A树
                             */
                            //return false;
                            isSubFlag = false;
                            break;
                        }
                    }
                    if (currentB.right!=null){
                        if (currentA.right != null && currentB.right.val == currentA.right.val) {
                            dequeA.offer(currentA.right);
                            dequeB.offer(currentB.right);
                        }else {
                            //return false;
                            isSubFlag = false;
                            break;
                        }
                    }
                }
                /**
                 * 如果 isSubFlag 值为true，则此时B树是A树的子结构，直接返回true。
                 * 否则，继续遍历A树，查找 A树中其他满足条件的节点
                 */
                if (isSubFlag) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 递归 + DFS 查找子结构
     * @param A
     * @param B
     * @return
     */
    public boolean isSubStructure2(TreeNode A, TreeNode B) {
        //边界条件判断，如果A和B有一个为空，返回false
        if (A==null||B==null){
            return false;
        }
        //先从根节点判断B是不是A的子结构，如果不是在分别从左右两个子树判断，
        //只要有一个为true，就说明B是A的子结构
        return sameRootDfs(A,B)||isSubStructure2(A.left,B)||isSubStructure2(A.right,B);


    }

    private boolean sameRootDfs(TreeNode A, TreeNode B){
        //这里如果B为空，说明B已经访问完了，确定是A的子结构
        if(B==null){
            return true;
        }
        //如果B不为空A为空，或者这两个节点值不同，说明B树不是A的子结构，直接返回false
        if (A==null||A.val!=B.val){
            return false;
        }
        //当前节点比较完之后还要继续判断左右子节点
        return sameRootDfs(A.left,B.left)&&sameRootDfs(A.right,B.right);
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
        TreeNode nodeA_2 = new TreeNode(0);
        TreeNode nodeA_3 = new TreeNode(1);
        rootA.left = nodeA_2;
        rootA.right = nodeA_3;
        TreeNode nodeA_4 = new TreeNode(-4);
        TreeNode nodeA_5 = new TreeNode(-3);
        nodeA_2.left = nodeA_4;
        nodeA_2.right = nodeA_5;

        TreeNode rootB = new TreeNode(1);
        TreeNode nodeB_2 = new TreeNode(-4);
        rootB.left = nodeB_2;

        SolutionJianzhi026 solutionJianzhi026 = new SolutionJianzhi026();
        System.out.println(solutionJianzhi026.isSubStructure(rootA,rootB));
    }
}
//leetcode submit region end(Prohibit modification and deletion)
