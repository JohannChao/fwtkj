package com.johann.dataStructures.e_tree;

import com.johann.dataStructures.c_queue.JohannQueue;
import com.johann.dataStructures.d_linkedList.DoublePointLinkedQueue;
import com.johann.dataStructures.h_graph.JohannGraph;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @ClassName: JohannBinaryTree
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class JohannBinaryTree {

    private Node root;


    private class Node{
        private int data;
        private Node leftChild;
        private Node rightChild;

        public Node(){

        }

        public Node(int value){
            this.data = value;
        }
    }

    public JohannBinaryTree(){

    }

    public JohannBinaryTree(Node root){
        this.root = root;
    }

    public JohannBinaryTree(int value){
        this.root = new Node(value);
    }

    /**
     * 查找二叉搜索树节点
     * @param value
     * @return
     */
    public Node find(int value){
        Node current = this.root;

        while(current!=null){
            //如果当前值大于该节点，则向该节点右侧树继续查找
            if(value > current.data){
                current = current.rightChild;
            //如果当前值小于该节点，则向该节点左侧树继续查找
            }else if(value < current.data){
                current = current.leftChild;
            }else{
                return current;
            }
        }
        return null;
    }


    /**
     * 插入新的节点
     * @param value
     */
    public void insert(int value){
        Node current = root;

        while(current!=null){
            //如果当前值大于该节点，则向该节点右侧树继续查找
            if (value > current.data){
                //如果当前树的右子树为空，则新增节点，作为该节点的右子节点
                if(current.rightChild == null){
                    current.rightChild = new Node(value);
                    break;
                }else{
                    current = current.rightChild;
                }
            //如果当前值小于该节点，则向该节点左侧树继续查找
            }else if(value < current.data){
                //如果当前树的左子树为空，则新增节点，作为该节点的左子节点
                if(current.leftChild == null){
                    current.leftChild = new Node(value);
                    break;
                }else{
                    current = current.leftChild;
                }
            }else {
                System.out.println("该树已存在此值，禁止插入");
                break;
            }
        }

        if (root == null){
            this.root = new Node(value);
        }
    }


    /**
     * 二叉树的遍历
     * 比较常用的有前序遍历，中序遍历和后序遍历
     * 1），中序遍历:左子树——》根节点——》右子树
     * 2），前序遍历:根节点——》左子树——》右子树
     * 3），后序遍历:左子树——》右子树——》根节点
     * 4），层序遍历：【从上到下，从左到右依次遍历】
     * @param node
     */
    public void traversal(Node node){

    }

    /**
     * 前序遍历 【根节点 --> 左子树 --> 右子树】
     * @param node
     */
    public void preOrder(Node node){
        if (node == null){
            return;
        }else {
            System.out.print(node.data+" ");
            preOrder(node.leftChild);
            preOrder(node.rightChild);
        }
    }

    /**
     * 中序遍历 【左子树 --> 根节点 --> 右子树】
     * 一个非完全二叉树，如何遍历使得打印顺序为 完全二叉树呢？ 缺值用 “空” 代替
     * @param node
     */
    public void infixOrder(Node node){
        if (node == null){
            return;
        }else {
            infixOrder(node.leftChild);
            System.out.print(node.data+" ");
            infixOrder(node.rightChild);
        }
    }

    /**
     * 后序遍历 【左子树 --> 右子树 --> 根节点】
     * @param node
     */
    public void postOrder(Node node){
        if (node == null){
            return;
        }else {
            postOrder(node.leftChild);
            postOrder(node.rightChild);
            System.out.print(node.data+" ");
        }
    }

    /**
     * 层序遍历 【从上到下，从左到右依次遍历】
     * 广度优先搜索(BFS)
     * {@link JohannGraph#breadthFirstSearch()}
     * TODO
     * @param node
     */
    public void sequenceOrder(Node node) {
        Deque<Node> deque = new ArrayDeque<>();
        System.out.print(node.data+" ");
        deque.offer(node);
        while (!deque.isEmpty()) {
            if (deque.peek().leftChild!=null) {
                System.out.print(deque.peek().leftChild.data+" ");
                deque.offer(deque.peek().leftChild);
            }
            if (deque.peek().rightChild!=null) {
                System.out.print(deque.peek().rightChild.data+" ");
                deque.offer(deque.peek().rightChild);
            }
            deque.pop();
        }
    }


    /**
     * 查找最大值【查找最小值同理 node.leftChild】
     * @param node
     * @return
     */
    public int findMax(Node node){
        while(node.rightChild!=null){
            node = node.rightChild;
        }
        return node.data;
    }

    /**
     * 删除节点
     * @param key
     * @return
     */
    public String delete(int key){
        Node current = root;
        //待删除节点
        Node bingoNode = null;
        //待删除节点的父节点
        Node parentNode = null;
        boolean isLeftChild = false;
        //查找需要删除的节点
        while(current!=null){
            //左树
            if(key < current.data){
                isLeftChild = true;
                parentNode = current;
                current = current.leftChild;
            //右树
            }else if(key > current.data){
                isLeftChild = false;
                parentNode = current;
                current = current.rightChild;
            }else{
                bingoNode = current;
                break;
            }
        }
        //找到待删除的节点
        if (bingoNode!=null){
            //待删除的节点是叶子节点
            if (bingoNode.leftChild==null && bingoNode.rightChild==null){
                //待删除的节点不是根节点
                if (parentNode!=null){
                    if (isLeftChild){
                        parentNode.leftChild = null;
                        //bingoNode = null;
                        return "删除叶子节点，且待删除节点是左子节点";
                    }else{
                        parentNode.rightChild = null;
                        //bingoNode = null;
                        return "删除叶子节点，且待删除节点是右子节点";
                    }
                //待删除的节点是根节点
                }else{
                    root = null;
                    return "删除根节点";
                }
                //current = null;
            }else{//待删除的节点不是叶子节点
                //待删除的节点。有一个子节点（左子节点 or 右子节点）
                if(bingoNode.leftChild==null || bingoNode.rightChild==null){
                    //待删除的节点不是根节点,此时将父节点的子节点引用，指向待删除节点的子节点
                    if (parentNode!=null){
                        if (isLeftChild){
                            parentNode.leftChild = bingoNode.leftChild==null ? bingoNode.rightChild : bingoNode.leftChild;
                            return "删除有一个子节点的非叶子节点，且待删除节点是左子节点";
                        }else {
                            parentNode.rightChild = bingoNode.leftChild==null ? bingoNode.rightChild : bingoNode.leftChild;
                            return "删除有一个子节点的非叶子节点，且待删除节点是右子节点";
                        }

                    //待删除的节点是根节点,此时将子节点设为新的根节点
                    }else{
                        root = bingoNode.leftChild==null ? bingoNode.rightChild : bingoNode.leftChild;
                        return "删除有一个子节点的根节点";
                    }
                //待删除的节点，有两个子节点
                }else {
                    //待删除节点，存在左右子节点的处理方法。
                    return dealSubsequentNode(parentNode,bingoNode,isLeftChild);
                }
            }
        }else{
            return "找不到待删除的节点";
        }
    }

    /**
     * 待删除节点，存在左右子节点的处理方法。
     *
     *  <p>待删除节点的中序后继节点是什么，即这个后继节点代替待删除节点位置后，二叉树的中序遍历顺序不变。
     *
     *  <p>获取待删除节点的中序后继节点，
     *  【比待删除节点大的节点集合中的最小的节点，即待删除节点右子树中的最左边的节点（最小值），也可能是这个右子树的根节点】
     *
     *  <p>1，如果待删除节点的右节点没有子节点，此时这个待删除节点的右节点代替待删除节点的位置，即为后继节点。
     *  【即，待删除节点的父节点的子节点（可能是左，可能是右）指向这个替换节点，这个替换节点的左子节点指向待删除节点的左子节点。】
     *  <p>2，如果待删除节点的右节点没有左子节点但存在右子节点，此时这个待删除节点的右子节点代替待删除节点的位置，即为后继节点。
     *  【即，待删除节点的父节点的子节点（可能是左，可能是右）指向这个替换节点，这个替换节点的左子节点指向待删除节点的左子节点。】
     *  <p>3，如果待删除节点的右节点有左子节点，找到最小的左节点，这个最小的左节点代替待删除节点的位置，这个左节点即为后继节点。
     *  接下来需要操作的步骤是：
     *      3.1，待删除节点的父节点的子节点（可能是左，可能是右）指向这个替换节点；
     *      3.2，替换节点的左子节点指向待删除结点的左子节点；
     *      3.3，替换结点的右子节点指向待删除结点的右子节点；
     *      3.4，如果这个替换节点没有右子节点（替换节点，肯定没有左子节点），则将替换结点原来的父结点的左子节点置为空。
     *          如果这个替换节点有右子节点，则将替换结点原来的父结点的左子节点指向这个替换节点的右子节点。
     *
     * @param bingoParentNode 待删除节点的父结点
     * @param bingoNode 待删除结点
     * @param isLeftChild 待删除节点是否是它父结点的 左子节点
     */
    public String dealSubsequentNode(Node bingoParentNode,Node bingoNode,boolean isLeftChild){
        // 返回结果
        String resultStr = "";
        //查找后继节点（初始为：待删除结点的右子节点）
        Node subsequentNode= bingoNode.rightChild;
        //后继节点的父结点
        Node subsequentParentNode = null;
        //查找后继节点
        while (subsequentNode.leftChild != null){
            subsequentParentNode = subsequentNode;
            subsequentNode = subsequentNode.leftChild;
        }

        // 如果后继节点是待删除节点的右子节点（即这个待删除节点的右子树的根结点，待删除节点的右子树不存在左节点）
        if (subsequentNode == bingoNode.rightChild){
            //此时相当于将待删除节点的 右子树，整体向上移动一个高度
            if (subsequentNode.rightChild != null){
                resultStr = "后继节点是待删除节点的右子节点（待删除节点右子树的根结点，右子树不存在左子节点），且后继节点有右子节点。";
            }else{
                resultStr = "后继节点是待删除节点的右子节点（待删除节点右子树的根结点，右子树节不存左子节点），且后继节点没有子节点。";
            }

            //后继节点的左子节点指向，待删除节点的左子节点
            subsequentNode.leftChild = bingoNode.leftChild;

            //如果当前待删除节点不是根结点，此时将待删除节点的父节点的子节点，指向后继节点
            if (bingoParentNode != null){
                if (isLeftChild){
                    bingoParentNode.leftChild = subsequentNode;
                    resultStr += "删除有两个子节点的非叶子节点，且待删除节点是左子节点。";
                }else{
                    bingoParentNode.rightChild = subsequentNode;
                    resultStr += "删除有两个子节点的非叶子节点，且待删除节点是右子节点。";
                }
            //待删除的节点是根节点,此时将后继节点设为新的根节点
            }else {
                root = subsequentNode;
                resultStr += "删除有两个子节点的根节点。";
            }

        // 待删除节点的右子树，存在左节点。此时 后继节点是待删除节点的右子树中的最左子节点
        }else {
            //如果后继节点的右子节点不为空，则将后继节点的父结点的左子节点指向，这个后继节点的右子节点
            if (subsequentNode.rightChild != null){
                subsequentParentNode.leftChild = subsequentNode.rightChild;
                resultStr = "后继节点是待删除节点的右子树的最小节点（右子树节存在左子节点），且后继节点有右子节点。";
            //如果后继节点的右子节点为空，则将后继节点的父结点的左子节点置为空，
            }else {
                subsequentParentNode.leftChild = null;
                resultStr = "后继节点是待删除节点的右子树的最小节点（右子树节存在左子节点），且后继节点没有子节点。";
            }
            //subsequentParentNode.leftChild = subsequentNode.rightChild;

            //后继节点的左子节点指向，待删除节点的左子节点；后继节点的右子节点指向，待删除节点的右子节点
            subsequentNode.leftChild = bingoNode.leftChild;
            subsequentNode.rightChild = bingoNode.rightChild;

            //如果当前待删除节点不是根结点，此时将待删除节点的父节点的子节点，指向后继节点
            if (bingoParentNode != null){
                if (isLeftChild){
                    bingoParentNode.leftChild = subsequentNode;
                    resultStr += "删除有两个子节点的非叶子节点，且待删除节点是左子节点。";
                }else{
                    bingoParentNode.rightChild = subsequentNode;
                    resultStr += "删除有两个子节点的非叶子节点，且待删除节点是右子节点。";
                }
            //待删除的节点是根节点,此时将后继节点设为新的根节点
            }else {
                root = subsequentNode;
                resultStr += "删除有两个子节点的根节点。";
            }
        }
        return resultStr;
    }






    public static void main(String[] args) {
        JohannBinaryTree tree = new JohannBinaryTree(15);
        tree.insert(9);
        tree.insert(18);
        tree.insert(5);
        tree.insert(11);
        tree.insert(6);
        tree.insert(16);
        tree.insert(28);
        tree.insert(25);
        tree.insert(30);
        tree.insert(29);
        tree.insert(32);
        tree.insert(35);
        tree.insert(23);
        tree.insert(26);
        tree.insert(24);
        tree.insert(27);

        System.out.println("前序遍历：");
        tree.preOrder(tree.root);
        System.out.println();
        System.out.println("中序遍历：");
        tree.infixOrder(tree.root);
        System.out.println();
        System.out.println("后序遍历：");
        tree.postOrder(tree.root);
        System.out.println();
        System.out.println("层序遍历：");
        tree.sequenceOrder(tree.root);
        System.out.println();

        //删除叶子节点，且待删除节点是右子节点
        System.out.println();
        System.out.println("删除节点 27 ");
        System.out.println(tree.delete(27));
        System.out.println("中序遍历：");
        tree.infixOrder(tree.root);
        System.out.println();

        //删除有一个子节点的非叶子节点，且待删除节点是左子节点
        System.out.println();
        System.out.println("删除节点 5 ");
        System.out.println(tree.delete(5));
        System.out.println("中序遍历：");
        tree.infixOrder(tree.root);
        System.out.println();

        //后继节点是待删除节点的右子节点（待删除节点右子树的根结点，右子树节不存左子节点），且后继节点没有子节点。删除有两个子节点的非叶子节点，且待删除节点是左子节点。
        System.out.println();
        System.out.println("删除节点 9 ");//后继节点是 17
        System.out.println(tree.delete(9));
        System.out.println("中序遍历：");
        tree.infixOrder(tree.root);
        System.out.println();

        //后继节点是待删除节点的右子节点（待删除节点右子树的根结点，右子树不存在左子节点），且后继节点有右子节点。删除有两个子节点的非叶子节点，且待删除节点是右子节点。
        System.out.println();
        System.out.println("删除节点 30 ");//后继节点是 32
        System.out.println(tree.delete(30));
        System.out.println("中序遍历：");
        tree.infixOrder(tree.root);
        System.out.println();

        //后继节点是待删除节点的右子树的最小节点（右子树节存在左子节点），且后继节点有右子节点。删除有两个子节点的非叶子节点，且待删除节点是右子节点。
        System.out.println();
        System.out.println("删除节点 18 ");//后继节点是 23
        System.out.println(tree.delete(18));
        System.out.println("中序遍历：");
        tree.infixOrder(tree.root);
        System.out.println();

        //后继节点是待删除节点的右子树的最小节点（右子树节存在左子节点），且后继节点没有子节点。删除有一个子节点的根节点。
        System.out.println();
        System.out.println("删除节点 15 ");//后继节点是16
        System.out.println(tree.delete(15));
        System.out.println("中序遍历：");
        tree.infixOrder(tree.root);
        System.out.println();




    }


}
