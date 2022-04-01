package com.johann.dataStructures.c_queue;

/**　
 * 数组实现一个根据特定规则排序的优先级队列
 * @ClassName: JohannPriorityQueue
 * @Description:
 * 优先级队列中，数组中的元素存储肯定是连续不间断的。
 * 设计思路如下：
 *     1，一个数组中，根据优先级大小，优先级大的在数组后面，优先级小的在数组前面；
 *     2，插入数据的时候，根据优先级大小，选定合适的位置插入位置index，index后面的其他元素依次向后移动，完成后队列中的 elementCount++；
 *     3，移除的时候，先移除优先级大的数据，即先移除数组后面的元素。
 *     由于数组元素连续，所以队列中的有效元素数目 elementCount，某种程度可以充当尾指针（elementCount-1）。移除元素后，尾指针向前移动（elementCount--）。
 * @Author: Johann
 * @Version: 1.0
 **/
public class JohannPriorityQueue<E> {

    private Object[] elementData;
    //队列中有效的元素个数
    private int elementCount;

    public JohannPriorityQueue(int initialCapacity){
        this.elementData = new Object[initialCapacity];
        this.elementCount = 0;
    }

    public JohannPriorityQueue(){
        this(8);
    }

    /**
     * 根据优先级大小，插入数据；优先级大的数据在后面，小的在前面
     * @param e
     * @return
     */
    public E insert(E e){
        if (isFull()){
            System.out.println("WARN : 满队列禁止插入数据");
            return null;
        }else if(isEmpty()){
            elementData[elementCount++] = e;
            return e;
        }else {
            int j = elementCount-1;
            while(j >= 0 && compare(e,elementData[j]) < 0){
                elementData[j+1] = elementData[j];
                j--;
            }
            elementData[++j] = e;
            elementCount++;
            return e;
        }
    }

    /**
     * 在队尾移除优先级最大的数据，队尾指针前移。
     * @return
     */
    public Object remove(){
        if (isEmpty()){
            System.out.println("WARN : 空队列不允许移除数据");
            return null;
        }else {
            Object value = elementData[elementCount-1];
            elementCount--;
            return value;
        }
    }

    /**
     * 查看优先级最大的数据
     * @return
     */
    public Object peakMaxPriority(){
        if (isEmpty()){
            System.out.println("WARN : 空队列不允许查看数据");
            return null;
        }else {
            return elementData[elementCount-1];
        }
    }

    /**
     * 是否空队列
     * @return
     */
    public boolean isEmpty(){
        return elementCount == 0;
    }

    /**
     * 是否满队列
     * @return
     */
    public boolean isFull(){
        return elementCount == elementData.length;
    }

    /**
     * 特定规则确定优先级
     * @param former
     * @param latter
     * @return
     */
    public Integer compare(Object former,Object latter){
        if (former.hashCode() > latter.hashCode()){
            return 1;
        }else if (former.hashCode() < latter.hashCode()){
            return -1;
        }else {
            return 0;
        }
    }

    public static void main(String[] args) {
        JohannPriorityQueue<Integer> priorityQueue = new JohannPriorityQueue(4);
        System.out.println("insert : "+priorityQueue.insert(1));
        System.out.println("insert_peakMaxPriority : "+priorityQueue.peakMaxPriority());
        System.out.println("insert : "+priorityQueue.insert(4));
        System.out.println("insert_peakMaxPriority : "+priorityQueue.peakMaxPriority());
        System.out.println("insert : "+priorityQueue.insert(3));
        System.out.println("insert_peakMaxPriority : "+priorityQueue.peakMaxPriority());
        System.out.println("insert : "+priorityQueue.insert(2));
        System.out.println("insert_peakMaxPriority : "+priorityQueue.peakMaxPriority());
        System.out.println("insert : "+priorityQueue.insert(0));
        System.out.println("insert_peakMaxPriority : "+priorityQueue.peakMaxPriority());

        while(priorityQueue.elementCount > 0){
            System.out.println("remove : "+priorityQueue.remove());
            System.out.println("remove_peakMaxPriority : "+priorityQueue.peakMaxPriority());
        }

    }


}
