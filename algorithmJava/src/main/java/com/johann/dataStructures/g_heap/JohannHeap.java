package com.johann.dataStructures.g_heap;

import com.johann.dataStructures.e_tree.Student;

import javax.xml.soap.Node;

/** 堆示例（数组实现的完全二叉树）
 * @ClassName: JohannHeap
 * @Description: 与优先级队列相比较，堆插入和删除的时间复杂度为 O(logN).而优先级队列的插入时间复杂度为 O(N),删除时间复杂度为 O(1)
 * @see     com.johann.dataStructures.c_queue.JohannPriorityQueue
 * @Author: Johann
 * @Version: 1.0
 **/
public class JohannHeap<T extends Comparable<T>>{

    private Node<T>[] heapArray;
    private int dataSize;

    public JohannHeap(int initialCapacity){
        this.heapArray = new Node[initialCapacity];
        this.dataSize = 0;
    }

    public JohannHeap(){
        this(16);
    }

    public boolean isEmpty(){
        return dataSize==0;
    }

    public boolean isFull(){
        return dataSize==heapArray.length;
    }

    /**
     * 在堆空间插入数据
     * @param value
     * @return
     * @throws Exception
     */
    public Node<T> insert(T value) throws HeapException{
        if (isFull()){
            throw new HeapException("堆空间已满，不可插入");
        }
        //新插入的节点【需要从下往上对比的节点】
        Node<T> newNode = new Node(value);
        heapArray[dataSize] = newNode;
        //当前有效数据项数目+1
        int currentIndex = dataSize++;
        int parentIndex = (currentIndex-1)/2;

        // 先在堆空间最后索引位置（即二叉树最后一个节点）插入新的数据。再将这个节点与它的父节点关键字进行比对【从下往上】
        //如果当前索引比较位置不在根结点，且当前索引位置的关键字 大于 父索引位置的关键字，交换父子位置
        while(currentIndex > 0 && newNode.getData().compareTo(heapArray[parentIndex].getData()) > 0){
            //如果新插入的节点，比它的父结点大，那么父结点替换到当前节点位置。
            heapArray[currentIndex] = heapArray[parentIndex];
            //当前节点更新为原来的父结点
            currentIndex = parentIndex;
            //父结点更新为原来父结点的父结点
            parentIndex = (currentIndex-1)/2;
        }
        //将新节点插入到合适的位置
        heapArray[currentIndex] = newNode;
        return newNode;
    }

    /**
     * 移除根元素，即数组index=0的数据
     */
    public Node<T> removeRoot() throws HeapException{
        if (isEmpty()){
            throw new HeapException("堆空间为空，不可删除");
        }
        //删除根结点数据
        Node<T> removeNode = heapArray[0];
        //最末端节点，替换为根结点，然后需要从上往下和它的子节点进行对比【需要进行自上往下对比的节点】
        Node<T> lastIndexNode = heapArray[--dataSize];
        heapArray[0] = lastIndexNode;

        int currentIndex = 0;
        //int leftChildIndex = currentIndex*2+1;
        //int rightChildIndex = currentIndex*2+2;
        int childIndex = getMaxChildIndex(currentIndex);

        //移除堆空间索引位置为0（即二叉树根结点）的数据，将最后一个节点的数据移到根结点。再将这个节点与它左右子节点中关键字大的那个进行比对。若当前节点关键字小，与关键字更大的子节点交换位置【从上往下】
        //如果当前索引比较位置不超出数组范围，且当前索引位置的关键字 小于 子节点索引位置的关键字，交换父子位置
        while(childIndex < dataSize && (lastIndexNode.getData().compareTo(heapArray[childIndex].getData()) < 0)){
            //如果最末端节点关键字，小于当前索引位置的子节点，将子节点移动到它父亲位置
            heapArray[currentIndex] = heapArray[childIndex];
            //更新当前索引为子节点索引
            currentIndex = childIndex;
            //更新当前节点的子节点索引
            childIndex = getMaxChildIndex(currentIndex);
        }
        //将最后一个节点，插入到合适位置
        heapArray[currentIndex] = lastIndexNode;
        return removeNode;
    }

    /**
     * 获取当前节点的左右子节点中，关键字大的那个节点
     * @param currentIndex
     * @return
     */
    public int getMaxChildIndex(int currentIndex){
        int childIndex = 0;
        if (currentIndex*2+2 < this.dataSize){
            childIndex = heapArray[currentIndex*2+1].getData().compareTo(heapArray[currentIndex*2+2].getData()) > 0 ? currentIndex*2+1 : currentIndex*2+2;
        }else {
            if (currentIndex*2+1 < this.dataSize){
                childIndex = currentIndex*2+1;
            }
        }
        return childIndex;
    }

    /**
     * 遍历数组
     */
    public void traversal(){
        System.out.println("编列堆数组：");
        for (int i=0;i < dataSize;i++){
            System.out.print(heapArray[i].getData());
            if (i != dataSize-1){
                System.out.print(" ---> ");
            }
        }
        System.out.println();
    }


    private class Node<T> {
        private T data;
        public Node(){

        }
        public Node(T data){
            this.data = data;
        }
        public T getData(){
            return this.data;
        }
        public void setData(T data){
            this.data = data;
        }
    }


    public static void main(String[] args) throws HeapException{

        JohannHeap heap = new JohannHeap();
        heap.insert(new Student("Johann12",12));
        heap.insert(new Student("Johann13",13));
        heap.insert(new Student("Johann14",14));
        heap.insert(new Student("Johann15",15));
        heap.insert(new Student("Johann16",16));
        heap.insert(new Student("Johann17",17));
        heap.insert(new Student("Johann18",18));
        heap.traversal();
        heap.removeRoot();
        heap.traversal();


    }

}

class HeapException extends Exception{
    public HeapException(){
        super();
    }
    public HeapException(String message){
        super(message);
    }
}
