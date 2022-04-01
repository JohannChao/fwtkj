package com.johann.dataStructures.b_stack;

/**
 * 数组实现栈
 * @ClassName: JohannStack
 * @Description: 该类中实现的栈，存在以下问题：
 *
 *   ①、上面栈的实现初始化容量之后，后面是不能进行扩容的（虽然栈不是用来存储大量数据的），如果说后期数据量超过初始容量之后怎么办？（自动扩容）
 *
 * 　②、我们是用数组实现栈，在定义数组类型的时候，也就规定了存储在栈中的数据类型，那么同一个栈能不能存储不同类型的数据呢？（声明为Object）
 *
 * 　③、栈需要初始化容量，而且数组实现的栈元素都是连续存储的，那么能不能不初始化容量呢？（改为由链表实现）
 *
 * @Author: Johann
 * @Version: 1.0
 **/
public class JohannStack {
    private Integer[] array;
    //存储栈的长度
    private Integer maxSize;
    //存储栈顶指针（即数组的索引号）
    private Integer top;

    public JohannStack(){
        this.array = new Integer[8];
        this.maxSize = 8;
        this.top = -1;
    }

    public JohannStack(Integer size){
        this.array = new Integer[size];
        this.maxSize = size;
        this.top = -1;
    }

    /**
     * 将数据压入栈内，并移动栈顶指针
     * @param value
     * @return
     */
    public Integer push(Integer value){
        if (top < maxSize-1){
            //top++;
            //array[top] = value;
            array[++top] = value;
            return value;
        }else {
            System.out.println("WARN : 栈内空间已满");
            return null;
        }
    }

    /**
     * 弹出栈顶数据，并移动栈顶指针
     * @return
     */
    public Integer pop(){
        if (top == -1){
            System.out.println("WARN : 空栈");
            return null;
        }
        //Integer value = array[top];
        //top --;
        //return value;
        return array[top--];
    }

    /**
     * 访问栈顶数据
     * @return
     */
    public Integer peak(){
        if (top == -1){
            System.out.println("WARN : 空栈");
            return null;
        }
        return array[top];
    }

    /**
     * 是否空栈
     * @return
     */
    public boolean isEmpty(){
        return (top == -1);
    }

    /**
     * 是否满栈
     * @return
     */
    public boolean isFull(){
        return (top == maxSize-1);
    }

    public static void main(String[] args) {
        JohannStack johannStack = new JohannStack();
        Integer i = 1;
        while(i < 10){
            System.out.println("栈内压入数据 : "+johannStack.push(i++));
        }
        System.out.println("访问栈顶 : "+johannStack.peak());
        System.out.println("弹出栈顶数据 : "+johannStack.pop());
        System.out.println("访问栈顶 : "+johannStack.peak());
        System.out.println("栈内压入数据 : "+johannStack.push(10));
        System.out.println("访问栈顶 : "+johannStack.peak());
        while(johannStack.top > -1){
            System.out.println("弹出栈顶数据 : "+johannStack.pop());
        }
        System.out.println("弹出栈顶数据 : "+johannStack.pop());
    }
}
