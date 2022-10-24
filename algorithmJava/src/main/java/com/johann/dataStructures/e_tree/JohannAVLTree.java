package com.johann.dataStructures.e_tree;

/** 平衡二叉树
 * @ClassName: JohannAVLTree
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class JohannAVLTree<T extends Comparable<T>> {

    private Node root;


    public JohannAVLTree(Node root){
        this.root = root;
    }

    public JohannAVLTree(T value){
        this.root = new Node(value);
    }

    private class Node<T extends Comparable<T>>{

        private T data;
        private Node<T> leftChild;
        private Node<T> rightChild;
        private Node<T> parent;
        //相较于二叉树，多了一个 height 当前节点高度
        private int height;

        //private int bf;

        public Node(){

        }

        public Node(T value){
            this.data = value;
            this.height = 1;
        }

        /**
         * 递归获取当前节点的高度
         * @param node
         * @return
         */
        public int getHeight(Node<T> node){
            if(node == null){
                return 0;
            }else {
                int leftHeight = getHeight(node.leftChild);
                int rightHeight = getHeight(node.rightChild);
                return Math.max(leftHeight,rightHeight)+1;
            }
        }

    }

    /**
     *  LL情况的旋转(根据顶节点右旋)
     *
     * @param topNode 旧的顶节点
     * @return 新的顶节点
     */
    public Node leftLeftRotation(Node topNode){

        if (topNode == null){
            System.out.println("顶端节点为空，禁止右旋操作！");
            return topNode;
        }
        if (topNode.leftChild == null){
            System.out.println("顶端节点左子节点为空，禁止右旋操作！");
            return topNode;
        }

        Node oldTopParent = topNode.parent;
        Node newTop = topNode.leftChild;

        //1,旧顶端节点的父结点的子节点指向新的顶端节点，新顶端节点的父结点指向旧顶端节点的父结点。
        //顶端节点是根结点
        if (oldTopParent == null){
            this.root = newTop;
            newTop.parent = null;
        }else {
            if (oldTopParent.leftChild == topNode){
                oldTopParent.leftChild = newTop;
            }else {
                oldTopParent.rightChild = newTop;
            }
            newTop.parent = oldTopParent;
        }
        //2,新顶端节点的右子节点的父结点指向旧顶端节点，旧顶端节点的左子节点指向新顶端节点的右子节点。
        if (newTop.rightChild != null){
            Node newTop_oldRight = newTop.rightChild;
            topNode.leftChild = newTop_oldRight;
            newTop_oldRight.parent = topNode;
        }else {
            topNode.leftChild = null;
        }
        //3,旧顶端节点的父结点指向新顶端节点，新顶端节点的右子节点指向旧顶端节点。
        newTop.rightChild = topNode;
        topNode.parent = newTop;

        //右旋后，对操作节点的高度重新赋值
        topNode.height = Math.max(topNode.leftChild.height,topNode.rightChild.height)+1;
        newTop.height = Math.max(newTop.leftChild.height,topNode.height)+1;

        return newTop;
    }

    /**
     * RR情况的旋转(根据顶节点左旋)
     *
     * @param topNode 旧的顶节点
     * @return 新的顶节点
     */
    public Node rightRightRotation(Node topNode){
        if (topNode == null){
            System.out.println("顶端节点为空，禁止左旋操作！");
            return topNode;
        }
        if (topNode.rightChild == null){
            System.out.println("顶端节点右子节点为空，禁止左旋操作！");
            return topNode;
        }

        Node oldTopParent = topNode.parent;
        Node newTop = topNode.rightChild;

        //1,旧顶端节点的父结点的子节点指向新的顶端节点，新顶端节点的父结点指向旧顶端节点的父结点。
        //顶端节点是根结点
        if (oldTopParent == null){
            this.root = newTop;
            newTop.parent = null;
        }else {
            if (oldTopParent.leftChild == topNode){
                oldTopParent.leftChild = newTop;
            }else {
                oldTopParent.rightChild = newTop;
            }
            newTop.parent = oldTopParent;
        }
        //2,新顶端节点的左子节点的父结点指向旧顶端节点，旧顶端节点的右子节点指向新顶端节点的左子节点。
        if (newTop.leftChild != null){
            Node newTop_oldLeft = newTop.leftChild;
            topNode.rightChild = newTop_oldLeft;
            newTop_oldLeft.parent = topNode;
        }else {
            topNode.rightChild = null;
        }
        //3,旧顶端节点的父结点指向新顶端节点，新顶端节点的左子节点指向旧顶端节点。
        newTop.leftChild = topNode;
        topNode.parent = newTop;


        //左旋后，对操作节点的高度重新赋值
        topNode.height = Math.max(topNode.leftChild.height,topNode.rightChild.height)+1;
        newTop.height = Math.max(topNode.height,newTop.rightChild.height)+1;

        return newTop;
    }

    /**
     * LR情况的旋转
     *
     * 1，先对顶节点的左子节点(L)做一次左旋转（rightRightRotation），使其变成 LL的情况
     * 2，然后再对顶节点对其做一次右旋转（leftLeftRotation）
     *
     * @param topNode
     * @return
     */
    public Node leftRightRotation(Node topNode){
        //先对顶节点的左子节点(L)做一次左旋转（rightRightRotation），使其变成 LL的情况
        rightRightRotation(topNode.leftChild);

        //再对顶节点对其做一次右旋转（leftLeftRotation）
        return leftLeftRotation(topNode);
    }

    /**
     * RL情况的旋转
     *
     * 1，先对顶节点的右子节点(R)做一次右旋转（leftLeftRotation），使其变成 RR的情况
     * 2，然后再对顶节点对其做一次左旋转（rightRightRotation）
     *
     * @param topNode
     * @return
     */
    public Node rightLeftRotation(Node topNode){
        //先对顶节点的右子节点(R)做一次右旋转（leftLeftRotation），使其变成 RR的情况
        leftLeftRotation(topNode.rightChild);

        //再对顶节点对其做一次左旋转（rightRightRotation）
        return rightRightRotation(topNode);
    }

    boolean flag = true;

    /**插入新的节点
     * 使用递归的方式，插入新的节点，便于对插入节点相关的父结点的高度进行更新，以及后续定位失衡的节点（即该节点的左右子树高度差值的绝对值 > 1）
     * @param current root
     * @param value 插入的节点值
     */
    public void insert(Node current,Comparable<T> value) throws InsertException{

        //Node current = root;
        if (current == null){
            //当前节点设置为根结点
            this.root = new Node(value);
            return;
        }

        //current节点的左边子树
        if(current.data.compareTo(value) > 0){
            if (current.leftChild==null){
                Node newNode = new Node(value);
                current.leftChild = newNode;
                newNode.parent = current;
            }else {
                insert(current.leftChild,value);
            }
            // 插入节点后，若AVL树失去平衡，则进行相应的调节。
            if (current.leftChild.height - current.rightChild.height == 2) {
                if (current.leftChild.data.compareTo(value) > 0){
                    current = leftLeftRotation(current);
                } else {
                    current = leftRightRotation(current);
                }
            }

        }else if(current.data.compareTo(value) < 0){
            if (current.rightChild==null){
                Node newNode = new Node(value);
                current.rightChild = newNode;
                newNode.parent = current;
            }else {
                insert(current.rightChild,value);
            }
            // 插入节点后，若AVL树失去平衡，则进行相应的调节。
            if (current.rightChild.height - current.leftChild.height == 2) {
                if (current.leftChild.data.compareTo(value) < 0){
                    current = rightRightRotation(current);
                } else {
                    current = rightLeftRotation(current);
                }
            }
        }else {
            System.out.println("已存在相同的节点，禁止插入");
            //如果插入失败，直接return回去，停止递归后续操作。即height不会被递归更新
            throw new InsertException("插入异常：已存在相同的节点，禁止插入");
        }
        //递归更新 插入节点父结点，祖父节点等节点的 height
        current.height = Math.max(current.leftChild.height,current.rightChild.height)+1;
    }


    /**
    * @Description: 删除结点(z)，返回根节点
    * @Param: [tree, z]  AVL树的根结点,待删除的结点
    * @return: com.johann.dataStructures.e_tree.JohannAVLTree<T>.Node<T>  根节点
    * @Author: Johann
    * @Date: 2022/10/19
    */
    private Node<T> remove(Node<T> tree, Node<T> z) {
        // 根为空 或者 没有要删除的节点，直接返回null。
        if (tree==null || z==null){
            return null;
        }

        int cmp = z.data.compareTo(tree.data);
        // 待删除的节点在"tree的左子树"中
        if (cmp < 0) {
            tree.leftChild = remove(tree.leftChild, z);
            // 删除节点后，若AVL树失去平衡，则进行相应的调节。
            if (tree.rightChild.height - tree.leftChild.height == 2) {
                Node<T> r =  tree.rightChild;
                if (r.leftChild.height > r.rightChild.height){
                    tree = rightLeftRotation(tree);
                } else{
                    tree = rightRightRotation(tree);
                }
            }
        // 待删除的节点在"tree的右子树"中
        } else if (cmp > 0) {
            tree.rightChild = remove(tree.rightChild, z);
            // 删除节点后，若AVL树失去平衡，则进行相应的调节。
            if (tree.leftChild.height - tree.rightChild.height == 2) {
                Node<T> l =  tree.leftChild;
                if (l.rightChild.height > l.leftChild.height){
                    tree = leftRightRotation(tree);
                }else{
                    tree = leftLeftRotation(tree);
                }
            }
        } else {    // tree是对应要删除的节点。
            // tree的左右孩子都非空
            if ((tree.leftChild!=null) && (tree.rightChild!=null)) {
                if (tree.leftChild.height > tree.rightChild.height) {
                    // 如果tree的左子树比右子树高；
                    // 则(01)找出tree的左子树中的最大节点
                    //   (02)将该最大节点的值赋值给tree。
                    //   (03)删除该最大节点。
                    // 这类似于用"tree的左子树中最大节点"做"tree"的替身；
                    // 采用这种方式的好处是：删除"tree的左子树中最大节点"之后，AVL树仍然是平衡的。
                    Node<T> max = maximum(tree.leftChild);
                    tree.data = max.data;
                    tree.leftChild = remove(tree.leftChild, max);
                } else {
                    // 如果tree的左子树不比右子树高(即它们相等，或右子树比左子树高1)
                    // 则(01)找出tree的右子树中的最小节点
                    //   (02)将该最小节点的值赋值给tree。
                    //   (03)删除该最小节点。
                    // 这类似于用"tree的右子树中最小节点"做"tree"的替身；
                    // 采用这种方式的好处是：删除"tree的右子树中最小节点"之后，AVL树仍然是平衡的。
                    Node<T> min = maximum(tree.rightChild);
                    tree.data = min.data;
                    tree.rightChild = remove(tree.rightChild, min);
                }
            } else {
                Node<T> tmp = tree;
                tree = (tree.leftChild!=null) ? tree.leftChild : tree.rightChild;
                tmp = null;
            }
        }

        return tree;
    }


    /**
     * 查找最大结点：返回tree为根结点的AVL树的最大结点。
     * @param tree
     * @return
     */
     private Node<T> maximum(Node<T> tree) {
         if (tree == null){
             return null;
         }
         while(tree.rightChild != null){
             tree = tree.rightChild;
         }
         return tree;
     }

     public T maximum() {
         Node<T> p = maximum(root);
         if (p != null){
             return p.data;
         }
         return null;
     }

}

/**
 * 自定义插入失败异常，用于终止递归
 */
class InsertException extends Exception{
    public InsertException(){
        super();
    }
    public InsertException(String message){
        super(message);
    }
}