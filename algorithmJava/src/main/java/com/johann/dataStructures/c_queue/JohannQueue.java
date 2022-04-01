package com.johann.dataStructures.c_queue;

/**　
 * JAVA数组实现队列（循环队列）
 * @ClassName: JohannQueue
 * @Description:
 *     在数组的最末端插入完数据，如果此时该队列不是满队列，即数组前端移除过元素，此时再重新从数组的前端开始插入数据；
 *     移除元素同理。这样就实现了一个循环队列。
 *     在数组中的表现则是：队首指针指向的数组索引号可能小于队尾指针指向的数组索引号，也可能队首指针指向的数组索引号可能大于队尾指针指向的数组索引号（循环插入移除）
 * @Author: Johann
 * @Version: 1.0
 **/
public class JohannQueue {

    private Object[] array;
    //数组中的有效元素数目（队列中元素数目），用于判断是否空队列，是否满队列
    private int elementCount;
    //队列头指针
    private int front;
    //队列尾指针
    private int rear;


    public JohannQueue(int initialCapacity){
        this.array = new Object[initialCapacity];
        this.elementCount = 0;
        this.front = 0;
        this.rear = -1;
    }

    public JohannQueue(){
        this(8);
    }

    /**
     * 在队尾插入数据
     * @param value
     * @return
     */
    public Object insert(Object value){
        if (isFull()){
            System.out.println("WARN : 队列已满");
            return null;
        }else {
            // 在队尾插入之前，如果此时队尾指针指向数组最末位置，此时队尾重置指针为 -1。再重新从数组最前端插入数据
            if (rear == array.length-1){
                rear = -1;
            }
            //rear++;
            //array[rear] = value;
            //在队尾插入数据，并将队尾指针后移
            array[++rear] = value;
            //队列中元素数目+1
            elementCount++;
            return value;
        }
    }

    /**
     * 在队首移除数据
     * @return
     */
    public Object remove(){
        if (isEmpty()){
            System.out.println("WARN : 空队列");
            return null;
        }else {
            Object value = array[front];
            //array[front] = null;
            //front++;
            //移除队首元素，并将队首指针后移
            array[front++] = null;
            // 在队首移除元素后，此时队首的指针指向数组最末位置，此时队首指针重置为 0。再重新从数组最前端开始移除数据
            if (front == array.length){
                front = 0;
            }
            //队列中元素数目-1
            elementCount--;
            return value;
        }
    }

    /**
     * 查看队首元素（即下一个要移除的元素）
     * @return
     */
    public Object peakFront(){
        if (isEmpty()) {
            System.out.println("WARN : 空队列");
            return null;
        }else {
            return array[front];
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
        return elementCount == array.length;
    }


    public static void main(String[] args) {
        JohannQueue queue = new JohannQueue(4);
        System.out.println("remove : "+queue.remove());
        for(int i = 0; i < 5; i++){
            System.out.println("insert : "+queue.insert("整数 ： "+i));
        }
        System.out.println("peakFront : "+queue.peakFront());
        System.out.println("remove : "+queue.remove());
        System.out.println("remove : "+queue.remove());
        System.out.println("insert : "+queue.insert(4));

        System.out.println("peakFront : "+queue.peakFront());
        System.out.println("front : "+queue.front);
        System.out.println("rear : "+queue.rear);

    }


}
