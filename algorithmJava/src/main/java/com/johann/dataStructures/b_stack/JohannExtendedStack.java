package com.johann.dataStructures.b_stack;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * 可以自动扩容的栈
 * @ClassName: JohannExtendedStack
 * @Description:
 * @see     java.util.Stack
 * @Author: Johann
 * @Version: 1.0
 **/
public class JohannExtendedStack {
    private Object[] elementData;
    //数组中有效的元素数（elementCount-1，其实指向的是最大索引，即栈顶指针）
    private int elementCount;
    //数组需要扩容时，每次自动扩充的量；如果为 0 ，则每次扩充为原来的两倍
    private int capacityIncrement;

    public JohannExtendedStack(int initialCapacity,int capacityIncrement){
        if (initialCapacity < 0){
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        }
        this.elementData = new Object[initialCapacity];
        this.capacityIncrement = capacityIncrement;
        this.elementCount = 0;
    }

    public JohannExtendedStack(int initialCapacity){
        this(initialCapacity,0);
    }

    public JohannExtendedStack(){
        //在JDK中实现的栈，初始默认的容量是 10
        this(8);
    }

    /**
     * 新数据压入栈内
     * @param value
     * @return
     */
    public Object push(Object value){
        ensureCapacityGrow(elementCount+1);
        //elementData[elementCount] = value;
        //elementCount++;
        elementData[elementCount++] = value;
        return value;
    }

    /**
     * 访问栈顶元素
     * @return
     */
    public Object peak(){
        if (elementCount == 0){
            throw new EmptyStackException();
        }
        return elementData[elementCount-1];
    }

    /**
     * 弹出栈顶元素
     * @return
     */
    public Object pop(){
        Object value = peak();
        removeElementAt(elementCount-1);
        return value;
    }

    /**
     * 移除指定索引位置的元素
     * @param index
     */
    public void removeElementAt(int index){
        //如果要删除元素的索引号大于最大索引号，数组越界
        if (index > elementCount-1){
            throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
        }
        if (index < 0){
            throw new ArrayIndexOutOfBoundsException(index );
        }
        //当前要删除元素后面，还有多少元素。这些元素都需要向前移动一个位置
        int j = (elementCount-1) - index;
        if (j > 0){
            /**
             * 将指定源数组中的数组从指定位置开始复制到目标数组的指定位置
             * src - 源数组。
             * srcPos – 源数组中的起始位置。
             * dest - 目标数组。
             * destPos – 目标数据中的起始位置。
             * length – 要复制的数组元素的数量。
             */
            System.arraycopy(elementData,index+1,elementData,index,j);
        }
        //原来最后一个有效位置的数组元素置为null，数组中有效的元素数-1
        //elementCount--;
        //elementData[elementCount] = null;
        elementData[--elementCount] = null;
    }


    /**
     * 数组是否需要扩容
    * @Param: [minCapacity] 新增完成后数组的元素数目
    * @return: void
    * @Author: Johann
    */
    public void ensureCapacityGrow(int minCapacity){
        //新增完成后数组的元素数目，大于旧数组的长度，此时需要扩容
        if (minCapacity - elementData.length > 0){
            System.out.println("数组即将扩容");
            int oldCapacity = elementData.length;
            //自动扩容步长为 0，则扩容为原来长度的两倍
            int newCapacity = oldCapacity + (capacityIncrement > 0 ? capacityIncrement :oldCapacity);
            //如果扩容后的数组长度大于 Integer.MAX_VALUE，此时新数组长度定义为 Integer.MAX_VALUE
            if (newCapacity > Integer.MAX_VALUE){
                newCapacity = Integer.MAX_VALUE;
            }
            //扩容后的新数组
            elementData = Arrays.copyOf(elementData,newCapacity);
        }
    }

    /**
     * 是否为空栈
     * @return
     */
    public boolean isEmpty(){
        return elementCount == 0;
    }

    public static void main(String[] args) {
        JohannExtendedStack johannExtendedStack = new JohannExtendedStack(4);
        johannExtendedStack.push("中");
        johannExtendedStack.push("华");
        johannExtendedStack.push("人");
        johannExtendedStack.push("民");
        johannExtendedStack.push("共");
        johannExtendedStack.push("和");
        johannExtendedStack.push("国");
        johannExtendedStack.push(1);
        johannExtendedStack.push(9);
        johannExtendedStack.push(4);
        johannExtendedStack.push(9);

        System.out.println("访问栈顶 : "+johannExtendedStack.peak());
        while(johannExtendedStack.elementCount > 0){
            System.out.println("弹出栈顶数据 : "+johannExtendedStack.pop());
        }
        System.out.println("弹出栈顶数据 : "+johannExtendedStack.pop());
    }


}
