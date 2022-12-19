package com.johann.jianzhiOffer;
//请实现两个函数，分别用来序列化和反序列化二叉树。
//
// 你需要设计一个算法来实现二叉树的序列化与反序列化。这里不限定你的序列 / 反序列化算法执行逻辑，你只需要保证一个二叉树可以被序列化为一个字符串并且将这个字
//符串反序列化为原始的树结构。 
//
// 提示：输入输出格式与 LeetCode 目前使用的方式一致，详情请参阅 LeetCode 序列化二叉树的格式。你并非必须采取这种方式，你也可以采用其他的方
//法解决这个问题。 
//
// 
//
// 示例： 
// 
// 
//输入：root = [1,2,3,null,null,4,5]
//输出：[1,2,3,null,null,4,5]
// 
//
// 
//
// 注意：本题与主站 297 题相同：https://leetcode-cn.com/problems/serialize-and-deserialize-
//binary-tree/ 
//
// Related Topics 树 深度优先搜索 广度优先搜索 设计 字符串 二叉树 👍 361 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.Deque;
import java.util.LinkedList;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class SolutionJianzhi037 {

    /**序列化二叉树
     * 层序遍历【广度优先搜索】
     * Encodes a tree to a single string.
     * @param root
     * @return
     */
    public String serialize(TreeNode root) {
        if (root == null) {
            return "[]";
        }
        StringBuilder seriaString = new StringBuilder("[");
        Deque<TreeNode> deque = new LinkedList<>();
        seriaString.append(root.val);
        deque.offer(root.left);
        deque.offer(root.right);
        while (!deque.isEmpty()) {
            TreeNode current =  deque.poll();
            seriaString.append(current == null ? "#null" : "#"+current.val);
            if (current != null) {
                deque.offer(current.left);
                deque.offer(current.right);
            }
        }
        seriaString.append("]");
        return seriaString.toString();
    }

    /**反序列化二叉树
     * Decodes your encoded data to tree.
     * @param data
     * @return
     */
    public TreeNode deserialize(String data) {
        if ("[]".equals(data)) {
            return null;
        }
        String[] seriaString = data.substring(1,data.length()-1).split("#");
        TreeNode root = new TreeNode(Integer.parseInt(seriaString[0]));
        int idx = 0;
        Deque<TreeNode> deque = new LinkedList<>();
        deque.offer(root);
        while (!deque.isEmpty()) {
            TreeNode current = deque.poll();
            if (current != null) {
                // 左子节点
                current.left = ((++idx < seriaString.length)&&!"null".equals(seriaString[idx])) ? new TreeNode(Integer.parseInt(seriaString[idx])) : null;
                deque.offer(current.left);
                // 右子节点
                current.right = ((++idx < seriaString.length)&&!"null".equals(seriaString[idx])) ? new TreeNode(Integer.parseInt(seriaString[idx])) : null;
                deque.offer(current.right);
            }
        }
        return root;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(3);
        root.left = node1;
        root.right = node2;
        TreeNode node3 = new TreeNode(4);
        TreeNode node4 = new TreeNode(5);
        node2.left = node3;
        node2.right = node4;
        SolutionJianzhi037 solution = new SolutionJianzhi037();
        String seriaString = solution.serialize(root);
        System.out.println(seriaString);

        TreeNode returnRoot = solution.deserialize(seriaString);
        System.out.println();
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));
//leetcode submit region end(Prohibit modification and deletion)
