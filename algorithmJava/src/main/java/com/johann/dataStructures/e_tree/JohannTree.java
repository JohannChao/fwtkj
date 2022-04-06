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

    public JohannTree(){

    }

    public JohannTree(Node root){
        this.root = root;
    }

    public JohannTree(int value){
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
     * 前序遍历
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
     * 中序遍历
     * TODO 一个非完全二叉树，如何遍历使得打印顺序为 完全二叉树呢？ 缺值用 “空” 代替
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
     * 后序遍历
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
                        return "删除叶子结点，且该节点是左子节点";
                    }else{
                        parentNode.rightChild = null;
                        //bingoNode = null;
                        return "删除叶子结点，且该节点是右子节点";
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
                            return "删除有一个子节点的非叶子节点，且该节点是左子节点";
                        }else {
                            parentNode.rightChild = bingoNode.leftChild==null ? bingoNode.rightChild : bingoNode.leftChild;
                            return "删除有一个子节点的非叶子节点，且该节点是右子节点";
                        }

                    //待删除的节点是根节点,此时将子节点设为新的根节点
                    }else{
                        root = bingoNode.leftChild==null ? bingoNode.rightChild : bingoNode.leftChild;
                        return "删除有一个子节点的根节点";
                    }
                //待删除的节点，有两个子节点
                }else {
                    // TODO
                    return "";
                }
            }
        }else{
            return "找不到待删除的节点";
        }
    }


    public static void main(String[] args) {
        JohannTree tree = new JohannTree(4);
        tree.insert(2);
        tree.insert(7);
        tree.insert(1);
        tree.insert(3);
        tree.insert(6);
        tree.insert(8);
        tree.insert(5);
        tree.insert(9);
        System.out.println("前序遍历：");
        tree.preOrder(tree.root);
        System.out.println();
        System.out.println("中序遍历：");
        tree.infixOrder(tree.root);
        System.out.println();
        System.out.println("后序遍历：");
        tree.postOrder(tree.root);
        System.out.println();

        //删除叶子结点，且该节点是左子节点
        System.out.println(tree.delete(1));
        System.out.println("中序遍历：");
        tree.infixOrder(tree.root);
        System.out.println();

        //删除叶子结点，且该节点是左子节点
        System.out.println(tree.delete(5));
        System.out.println("中序遍历：");
        tree.infixOrder(tree.root);
        System.out.println();

        //删除叶子结点，且该节点是右子节点
        System.out.println(tree.delete(9));
        System.out.println("中序遍历：");
        tree.infixOrder(tree.root);
        System.out.println();


    }


}
