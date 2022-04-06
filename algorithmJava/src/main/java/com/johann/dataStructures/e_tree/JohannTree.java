package com.johann.dataStructures.e_tree;

/**
 * @ClassName: JohannTree
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class JohannTree {

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
            }
        }
    }


    /**
     * 二叉树的遍历
     * 比较常用的有前序遍历，中序遍历和后序遍历
     * 1），中序遍历:左子树——》根节点——》右子树
     * 2），前序遍历:根节点——》左子树——》右子树
     * 3），后序遍历:左子树——》右子树——》根节点
     * @param node
     */
    public void traversal(Node node){

    }

    /**
     * 中序遍历
     * https://www.cnblogs.com/ysocean/p/8032642.html
     * @param node
     */
    public void infixOrder(Node node){

    }


}
