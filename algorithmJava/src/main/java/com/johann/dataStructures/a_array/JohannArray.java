package com.johann.dataStructures.a_array;

/**
 * @ClassName: JohannArray
 * @Description:
 * 一个数据结构必须具有以下基本功能：
 *    ①、如何插入一条新的数据项
 * 　 ②、如何寻找某一特定的数据项
 * 　 ③、如何删除某一特定的数据项
 * 　 ④、如何迭代的访问各个数据项，以便进行显示或其他操作
 *
 * @Author: Johann
 * @Version: 1.0
 **/
public class JohannArray {

    //定义一个数组
    private int [] intArray;
    //定义数组的实际长度
    private int elementLength;
    //定义数组的最大长度
    private int length;

    public JohannArray(){
        elementLength = 0;
        //给一个默认的数组长度 8
        length = 8;
        intArray = new int[length];
    }

    public JohannArray(int length){
        elementLength = 0;
        this.length = length;
        intArray = new int[length];
    }

    /**
     * 获取数组有效长度
     * @return
     */
    public int getElementLength(){
        return elementLength;
    }

    /**
     * 获取数组最大长度
     * @return
     */
    public int getLength(){
        return length;
    }

    /**
     * 遍历数组
     */
    public void traversal(){
        System.out.print("intArray : ");
        for (int i = 0; i < elementLength; i++){
            System.out.print(intArray[i]);
            if(i < elementLength-1){
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    /**
     * 给数组添加新的元素
     * @param value
     * @return
     */
    public boolean add(int value){
        if (elementLength == length){
            return false;
        }
        intArray[elementLength] = value;
        elementLength++;
        return true;
    }

    /**
     * 获取数组对应索引号的数据
     * @param index
     * @return
     */
    public int get(int index){
        if (index < 0 || index >= elementLength){
            System.out.println("数组越界");
        }
        return intArray[index];
    }

    /**
     * 查找元素，如果数组中存在该元素，返回该元素的索引号；若不存在返回 -1
     * @param value
     * @return
     */
    public int find(int value){
        for (int i = 0; i < elementLength; i++){
            if (intArray[i] == value){
                return i;
            }
        }
        return -1;
    }

    /**
     * 删除指定元素，若删除成功，则将该元素后面的其他元素依次向前移动，返回ture；若删除失败，返回false
     * @param value
     * @return
     */
    public boolean delete(int value){
        int index = find(value);

        if (index == -1){
            return false;
        }else {
            if (index == elementLength-1){
                intArray[index] = 0;
                elementLength--;
            }else {
                for (int i = index; i < elementLength-1; i++){
                    intArray[i] = intArray[i+1];
                }
                intArray[elementLength-1] = 0;
                elementLength--;
            }
        }
        return true;
    }

    /**
     * 使用新的值，替换旧的值
     * @param oldValue
     * @param newValue
     * @return
     */
    public boolean replace(int oldValue,int newValue){
        int index = find(oldValue);
        if(index == -1){
            return false;
        }else {
            intArray[index] = newValue;
        }
        return true;
    }

    public static void main(String[] args) {
        JohannArray array = new JohannArray();
        array.add(1);
        array.add(2);
        array.add(3);
        array.add(4);
        array.add(5);
        array.add(6);
        array.add(7);
        System.out.println("ElementLength : " + array.getElementLength());
        System.out.println("Length : " + array.getLength());
        System.out.println(array.get(6));
        System.out.println(array.get(7));
        System.out.println(array.find(9));
        array.traversal();
        array.delete(5);
        array.traversal();
        array.add(8);
        array.add(9);
        array.add(10);
        array.traversal();
        array.delete(1);
        array.traversal();
        array.replace(9,99);
        array.traversal();
    }
}
