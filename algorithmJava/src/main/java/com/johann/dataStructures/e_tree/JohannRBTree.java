package com.johann.dataStructures.e_tree;

/**
 * @ClassName: JohannRBTree
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class JohannRBTree<T extends Comparable<T>> {

    private Node root;

    public JohannRBTree(){

    }

    public JohannRBTree(Node root){
        this.root = root;
    }

    public JohannRBTree(T value){
        this.root = new Node(value);
        root.isRed = false;
    }

    private class Node<T extends Comparable<T>>{

        private T data;
        private Node<T> leftChild;
        private Node<T> rightChild;
        private Node<T> parent;
        private boolean isRed;

        public Node(){

        }

        public Node(T value){
            this.data = value;
            this.isRed = true;
        }

        public Node(T value,Node leftChild,Node rightChild,Node parent,boolean isRed){
            this.data = value;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.parent = parent;
            this.isRed = isRed;
        }

    }

    /**
     * 红黑树左旋
     *
     * 1，旧顶端节点的右子节点，占据顶端节点位置，成为新的顶端节点。
     *     即，旧顶端节点的父结点的子节点指向新的顶端节点，新顶端节点的父结点指向旧顶端节点的父结点。
     * 3，如果新顶端节点（旧顶端节点的右子节点），有左子节点，此时，这个子节点成为旧顶端节点的右子节点。
     *     即，新顶端节点的左子节点的父结点指向旧顶端节点，旧顶端节点的右子节点指向新顶端节点的左子节点。
     * 2，旧顶端节点，成为新顶端节点的左子节点。
     *     即，旧顶端节点的父结点指向新顶端节点，新顶端节点的左子节点指向旧顶端节点。
     *
     *
     * @param topNode 左旋操作顶端节点
     */
    public void leftRotate(Node topNode){

        if (topNode == null){
            System.out.println("顶端节点为空，禁止左旋操作！");
            return;
        }
        if (topNode.rightChild == null){
            System.out.println("顶端节点右子节点为空，禁止左旋操作！");
            return;
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
    }

    /**
     * 红黑树右旋
     *
     *  1，旧顶端节点的左子节点，占据顶端节点位置，成为新的顶端节点。
     *     即，旧顶端节点的父结点的子节点指向新的顶端节点，新顶端节点的父结点指向旧顶端节点的父结点。
     * 3，如果新顶端节点（旧顶端节点的左子节点），有右子节点，此时，这个子节点成为旧顶端节点的左子节点。
     *     即，新顶端节点的右子节点的父结点指向旧顶端节点，旧顶端节点的左子节点指向新顶端节点的右子节点。
     * 2，旧顶端节点，成为新顶端节点的右子节点。
     *     即，旧顶端节点的父结点指向新顶端节点，新顶端节点的右子节点指向旧顶端节点。
     *
     * @param topNode 右旋操作顶端节点
     */
    public void rightRotate(Node topNode){
        if (topNode == null){
            System.out.println("顶端节点为空，禁止右旋操作！");
            return;
        }
        if (topNode.leftChild == null){
            System.out.println("顶端节点左子节点为空，禁止右旋操作！");
            return;
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
    }


    /**
     * 插入新的节点
     * @param value
     */
    public void insert(Comparable<T> value){
        Node current = root;

        Node newNode = new Node(value);

        while(current!=null){
            //如果当前值大于该节点，则向该节点右侧树继续查找
            if (current.data.compareTo(value) < 0){
                //如果当前树的右子树为空，则新增节点，作为该节点的右子节点
                if(current.rightChild == null){
                    current.rightChild = newNode;
                    newNode.parent = current;
                    break;
                }else{
                    current = current.rightChild;
                }
                //如果当前值小于该节点，则向该节点左侧树继续查找
            }else if(current.data.compareTo(value) > 0){
                //如果当前树的左子树为空，则新增节点，作为该节点的左子节点
                if(current.leftChild == null){
                    current.leftChild = newNode;
                    newNode.parent = current;
                    break;
                }else{
                    current = current.leftChild;
                }
            }
        }
        if (current == null){
            this.root = newNode;
        }

        //红黑树自我调整
        insertFixup(newNode);
    }

    /**
     * 红黑树 自我调整
     * @param insert
     */
    public void insertFixup(Node insert){
        //如果插入的是根结点，此时直接将根节点颜色置为黑即可。
        if (insert.parent == null){
            insert.isRed = false;
            return;
        //如果插入节点的父结点颜色为黑，此时无需其他操作，直接返回即可。
        }else if (!insert.parent.isRed){
            return;
        }
        Node current = insert;

        //循环条件：只要有两个连续的红色节点（即当前节点和它的父结点都是红），就会继续
        while (current.parent != null && current.parent.isRed){

            // 1, 当前节点的父结点和叔叔节点都是红色
            if(uncleOf(current)!=null && uncleOf(current).isRed){
                current.parent.isRed = false;
                uncleOf(current).isRed = false;
                current.parent.parent.isRed = true;
                //当前节点替换为 祖父节点
                current = uncleOf(current).parent;
                //回到循环起点
                continue;
            }

            Node parent = current.parent;
            Node grand = current.parent.parent;
            if (grand == null){
                break;
            }

            // 2-3 当前节点的父结点是红，它的叔叔节点是黑（注：空节点是黑色）
            // 2, 父结点是祖父节点的左子节点
            if (grand.leftChild == parent){
                // 2.1，当前节点是父结点的左子节点，此时直接变色，以祖父节点右旋就可以了。执行完 IF ，会退出循环
                if (parent.leftChild == current){
                    parent.isRed = false;
                    grand.isRed = true;
                    rightRotate(grand);
                // 2.2，当前节点是父结点的右子节点，此时以父结点左旋。然后 current 替换为 左旋前的父结点。接下来会再重新走 步骤 2.1
                }else {
                    leftRotate(parent);
                    current = parent;
                }
            // 3, 父结点是祖父节点的右子节点
            }else if(grand.rightChild == parent){
                // 3.1，当前节点是父结点的右子节点，此时直接变色，以祖父节点左旋就可以了。执行完 IF ，会退出循环
                if (parent.rightChild == current){
                    parent.isRed = false;
                    grand.isRed = true;
                    leftRotate(grand);
                // 3.2，当前节点是父结点的左子节点，此时以父结点右旋。然后 current 替换为 右旋前的父结点。接下来会再重新走 步骤 3.1
                }else {
                    rightRotate(parent);
                    current = parent;
                }
            }
        }
        //根结点置为黑
        this.root.isRed = false;
    }

    /**
     * 获取当前节点的叔叔节点
     * @param son
     * @return
     */
    public Node uncleOf(Node son){
        if (son == null){
            return null;
        }
        if (son.parent == null){
            return null;
        }
        if (son.parent.parent == null){
            return null;
        }
        if (son.parent.parent.leftChild == son.parent){
            return son.parent.parent.rightChild;
        }else {
            return son.parent.parent.leftChild;
        }
    }

    public void infixOrder(Node node){
        if (node == null){
            return;
        }else {
            infixOrder(node.leftChild);
            System.out.println("【data ："+node.data+" left："+(node.leftChild==null?null:"notNUll")+" right："+(node.rightChild==null?null:"notNUll")+" parent："+(node.parent==null?null:"notNUll")+" color: "+(node.isRed?"R":"B")+(node==this.root?"  <<ROOT>>":"")+"】");
            infixOrder(node.rightChild);
        }
    }

//    public void test(){
//        JohannRBTree rbTree = new JohannRBTree(new Student("z11",11));
//        rbTree.insert(new Student("z2",2));
//        rbTree.insert(new Student("z14",14));
//        rbTree.insert(new Student("z1",1));
//        rbTree.insert(new Student("z7",7));
//
//        rbTree.infixOrder(rbTree.root);
//        System.out.println();
//
//        Node current = rbTree.root.leftChild;
//        System.out.println("current : "+current.data +"  current.leftChild: "+(current.leftChild==null?null:current.leftChild.data)+"  current.rightChild: "+(current.rightChild==null?null:current.rightChild.data)+"  current.parent: "+(current.parent==null?null:current.parent.data));
//
//        leftRotate(current);
//
//        System.out.println("current : "+current.data +"  current.leftChild: "+(current.leftChild==null?null:current.leftChild.data)+"  current.rightChild: "+(current.rightChild==null?null:current.rightChild.data)+"  current.parent: "+(current.parent==null?null:current.parent.data));
//    }


    public static void main(String[] args) {
        //JohannRBTree rbTree = new JohannRBTree();
        //rbTree.test();

//        JohannRBTree tree = new JohannRBTree(new Student("z2",2));
//        tree.insert(new Student("z1",1));
//        tree.insert(new Student("z7",7));
//        tree.insert(new Student("z5",5));
//        tree.insert(new Student("z9",9));
//        tree.insert(new Student("z4",4));
//        tree.insert(new Student("z10",10));
//        tree.insert(new Student("z11",11));
//        tree.infixOrder(tree.root);

        JohannRBTree tree = new JohannRBTree(new Student("zz",1));
        tree.insert(new Student("zz",2));
        tree.insert(new Student("zz",3));
        tree.insert(new Student("zz",4));
        tree.insert(new Student("zz",5));
        tree.insert(new Student("zz",6));
        tree.insert(new Student("zz",7));
        tree.infixOrder(tree.root);
        System.out.println("===========");
        tree.insert(new Student("zz",8));
        tree.infixOrder(tree.root);
//        tree.insert(new Student("zz",9));
//        tree.insert(new Student("zz",10));

    }



}
