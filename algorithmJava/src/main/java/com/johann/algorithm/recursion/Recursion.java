package com.johann.algorithm.recursion;

import java.util.Arrays;

/** 递归
 * @ClassName: Recursion
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class Recursion {


    /**
     * 斐波那契数列
     *  f(n) = f(n-1)+ f(n-2)
     *  其中，当 f(0)=1;f(1)=1;
     *
     */
    public static int fibonacciSequence(int n){
        if(n==0){
            return 0;
        }else if(n==1){
            return 1;
        }
        return fibonacciSequence(n-1)+fibonacciSequence(n-2);
    }

    /**
     * 以上的 斐波那契数列，就是用递归来实现的。
     * 什么是递归？即一个过程或函数在其定义或说明中有直接或间接调用自身的一种方法。
     *
     * 递归需要满足以下三个要素：
     *  1，边界条件，即递归的出口，到达边界跳出递归；
     *  2，递归前进段，当不满足边界条件时，执行何种操作；
     *  3，递归返回段，满足边界条件，返回什么。
     *
     */


    /**
     * 递归实现阶乘
     * 阶乘的定义为： 0!=1;1!=1;负数没有阶乘
     *
     * 边界条件：n=1,n=0
     * 递归前进段：f(n) = n*f(n-1)
     * 递归返回值：n=0，返回1;n=1，返回1
     *
     * @param n
     * @return
     */
    public static int factorial(int n){
        if (n==0){
            return 1;
        } else if (n==1){
            return 1;
        }
        return n*factorial(n-1);
    }


    /**
     *  汉诺塔问题（分治算法）
     *  如果有两个个盘子，两个盘子上面就是盘子1，下面是盘子2，那么我们只需要将盘子1先移动到B塔座上，然后将盘子2移动到C塔座，最后将盘子1移动到C塔座上。即完成2个盘子从A到C的移动。
     *
     *  所以无论有多少个盘子，我们都将其看做只有两个盘子。假设有 N 个盘子在塔座A上，我们将其看为两个盘子，其中(N-1)~1个盘子看成是一个盘子，最下面第N个盘子看成是一个盘子，
     *  那么解决办法为：
     * 　　①、先将A塔座的第(N-1)~1个盘子看成是一个盘子，放到中介塔座B上，然后将第N个盘子放到目标塔座C上。
     * 　　②、然后A塔座为空，看成是中介塔座，B塔座这时候有N-1个盘子，将第(N-2)~1个盘子看成是一个盘子，放到中介塔座A上，然后将B塔座的第(N-1)号盘子放到目标塔座C上。
     * 　　③、这时候A塔座上有(N-2)个盘子，B塔座为空，又将B塔座视为中介塔座，重复①，②步骤，直到所有盘子都放到目标塔座C上结束。
     *
     *  递归算法为：
     * 　　①、从初始塔座A上移动包含n-1个盘子到中介塔座B上。
     * 　　②、将初始塔座A上剩余的一个盘子（最大的一个盘子）放到目标塔座C上。
     * 　　③、将中介塔座B上n-1个盘子移动到目标塔座C上。
     *
     *
     * @param dish 盘子数
     * @param from A 初始位置
     * @param temp B 中间位置
     * @param to C 目标位置
     */
    public static void towerOfHanoi(int dish,String from,String temp,String to){
        if(dish == 1){
            System.out.println("把盘子"+dish+"从"+from+"放到"+to);
        }else {
            //把上面 n-1 个盘子，从 from 放到 temp
            towerOfHanoi(dish-1,from,to,temp);
            //把下面最大的盘子从 from 放到 to
            System.out.println("把盘子"+dish+"从"+from+"放到"+to);
            //再把剩下的 n-1个盘子从temp 放到 to
            towerOfHanoi(dish-1,temp,from,to);
        }
    }

    /**
     * 将两个有序的数组A，B中的元素插入到数组 C 中，且数组C中的元素有序
     * @param aArray
     * @param bArray
     * @return
     */
    public static Integer[] merge(Integer[] aArray,Integer[] bArray){
        Integer[] cArray = new Integer[aArray.length+bArray.length];
        int aNum = 0,bNum = 0,cNum = 0;

        //将数组A 和 B 中的元素依次进行比较，哪个小就把哪个元素放入到数组C中
        while(aNum<aArray.length && bNum<bArray.length){
            if (aArray[aNum] > bArray[bNum]){
                cArray[cNum] = bArray[bNum];
                //此时将数组C和数组B的索引后移
                cNum++;
                bNum++;
            }else {
                cArray[cNum] = aArray[aNum];
                //此时将数组C和数组A的索引后移
                cNum++;
                aNum++;
            }
        }

        //如果此时数组A中的元素已全部插入到C，则将B中的元素依次插入C中即可
        while(aNum == aArray.length && bNum<bArray.length){
            cArray[cNum] = bArray[bNum];
            //此时将数组C和数组B的索引后移
            cNum++;
            bNum++;
        }

        //如果此时数组B中的元素已全部插入到C，则将A中的元素依次插入C中即可
        while(aNum < aArray.length && bNum==bArray.length){
            cArray[cNum] = aArray[aNum];
            //此时将数组C和数组B的索引后移
            cNum++;
            aNum++;
        }

        return cArray;
    }

    /**
     * 归并排序（分治算法）
     *
     * 要对一个数组进行排序，可以先把这个数组分成两部分 A，B，分别对A，B进行排序，再把A，B进行合并。
     * 对 A部分 进行排序，可以把 A部分再分成 A1，A2，分别对A1和A2 进行排序，再合并 A1和A2.
     * 递归依次拆分，直到最小的一部分只有 1个元素，不满足 end > start，此时便是递归的出口所在。
     *
     * @param array
     * @param start
     * @param end
     */
    public static void mergeSort(Integer[] array,int start,int end){
        if(end > start){
            int mid = start + (end-start)/2;
            //左半部分数组排序
            mergeSort(array,start,mid);
            //右半部分数组排序
            mergeSort(array,mid+1,end);
            //合并左半部分和右半部分数组
            mergeLeftRight(array,start,mid,end);
        }
    }


    /**
     * 将数组的前半部分和后半部分合并起来。和上面的 merge() 方法一样
     * @param array
     * @param start
     * @param mid
     * @param end
     */
    public static void mergeLeftRight(Integer[] array,int start,int mid,int end){
        Integer[] temp = new Integer[end-start+1];
        //前半部分的起始，结束位置 start---mid
        int leftStart = start;
        //后半部分的起始，结束位置 mid+1---end
        int rightStart = mid+1;
        //合并后的 temp 索引
        int t = 0;

        while (leftStart <= mid && rightStart <= end){
            if(array[leftStart] < array[rightStart]){
                temp[t++] = array[leftStart++];
            }else{
                temp[t++] = array[rightStart++];
            }
        }

        while (rightStart <= end){
            temp[t++] = array[rightStart++];
        }

        while (leftStart <= mid){
            temp[t++] = array[leftStart++];
        }

        for (int i=0;i<temp.length;i++){
            array[start+i] = temp[i];
        }
    }

    /**
     * 递归实现乘方
     * @param x 底数
     * @param y 指数
     * @return
     */
    public static Integer power(Integer x,Integer y){

        if (x==0 || x==1){
            return x;
        }else {
            if (y==1){
                return x;
            }else if(y==0){
                return 1;
            }else {
                if(y%2 == 0){
                    return power(x,y/2)*power(x,y/2);
                }else{
                    return power(x,y/2)*power(x,y/2)*x;
                }
            }
        }
    }


    /**
     *  背包问题
     * @param weights
     * @param selects
     * @param total 总重量
     * @param index 可供选择的重量下标
     */
    public static void knapsack(int[] weights,boolean[] selects,int total,int index){
        //如果当前过程中总重量 > 0 或者 < 0 且 下角标超出可供选择的索引上限制【递归出口】
        if(total != 0 && index >= weights.length){
            System.out.println("失败+++++");
            return;//没找到解决办法，直接返回
        }

        if(total == 0){//总重量为0，则找到解决办法了 【递归出口】
            System.out.println(">>>>>>> 找到解决办法 ： ");
            for(int i = 0 ; i < index ; i++){
                if(selects[i] == true){
                    System.out.print(weights[i]+" ");
                }
            }
            System.out.println();
            return;
        }

        selects[index] = true;
        System.out.println("当前选择的索引下角标为："+index+"->"+true);
        System.out.println("之前剩余总重量："+total+"  当前数据项："+weights[index]+"  选择完当前数据项，剩余重量:"+(total-weights[index])+"   下一个index"+(index+1));
        knapsack(weights,selects,total-weights[index], index+1);


        selects[index] = false;
        System.out.println("当前选择的索引下角标为："+index+"->"+false);
        System.out.println("之前剩余总重量："+total+"  当前数据项："+weights[index]+"  未选择当前数据项，剩余重量:"+(total)+"   下一个index"+(index+1));
        knapsack(weights,selects,total, index+1);
    }

    /**
     * 递归实现排列组合
     * @param persons
     * @param selects
     * @param teamNumber 需要选择的队员数
     * @param index 从第几个队员开始选择
     */
    public static void combination(char[] persons,boolean[] selects, int teamNumber,int index){

        //如果当前过程中需要选择的队员数 > 0 或者 < 0 且 下角标超出可供选择的索引上限制(index超过组中人员总数)，表示未找到。【递归出口】
        if(teamNumber != 0 && index >= persons.length ){
            //System.out.println("当前选择失败！！！");
            return;
        }

        //当teamNumber=0时，找到一组【递归出口】
        if(teamNumber == 0){
            System.out.println(">>>>>>> 确定选择的数据项为 ： ");
            for(int i = 0 ; i < selects.length ; i++){
                if(selects[i] == true){
                    System.out.print(persons[i]+" ");
                }
            }
            System.out.println();
            return;
        }


        selects[index] = true;
        //System.out.println("当前选择的索引下角标为："+index+"->"+true);
        //System.out.println("当前还需要选择的人数："+teamNumber+"  当前数据项："+persons[index]+"  选择完当前数据项，还需要选择的人数:"+(teamNumber-1)+"   下一个index"+(index+1));
        combination(persons,selects,teamNumber-1, index+1);


        selects[index] = false;
        //System.out.println("当前选择的索引下角标为："+index+"->"+true);
        //System.out.println("当前还需要选择的人数："+teamNumber+"  当前数据项："+persons[index]+"  未选择当前数据项，还需要选择的人数:"+(teamNumber)+"   下一个index"+(index+1));
        combination(persons,selects,teamNumber, index+1);
    }


    public static void main(String[] args) throws Exception{

        //斐波那契数列
//        for(int i=1;i<20;i++){
//            System.out.println(fibonacciSequence(i));
//        }

        //阶乘
//        int i = 9;
//        System.out.println(i+"! = "+factorial(i));

        //汉诺塔
        //towerOfHanoi(3,"A","B","C");

        //合并有序数组
//        Integer[] a = {1,3,7,9,15,17};
//        Integer[] b = {2,4,6,8,10,12,14};
//        Integer[]c = merge(a,b);
//        for (int i=0;i<c.length;i++){
//            System.out.print(c[i]+" ");
//        }

        //归并排序
//        Integer[] array = {1,3,5,6,2,4,9,7,8,11,10,13,15,14};
//        //mergeSort2(array,0,array.length-1);
//        mergeSort(array,0,array.length-1);
//        System.out.println(Arrays.toString(array));

        //乘方
//        Integer x = 2,y = 10;
//        System.out.println(x+"^"+y+" = "+power(x,y));

        //背包问题
//        int[] weights = {1,3,5,6,9,10,8};
//        boolean[] selects = new boolean[weights.length];
//        int total = 23;
//        knapsack(weights,selects,total,0);

        //排列组合问题
        char[] persons = {'A','B','C','D','E','F'};
        boolean[] selects = new boolean[persons.length];
        int selectorNum = 3;
        combination(persons,selects,selectorNum,0);
    }

}
