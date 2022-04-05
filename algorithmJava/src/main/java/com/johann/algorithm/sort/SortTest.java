package com.johann.algorithm.sort;

/**
 * @ClassName: SortTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class SortTest {

    /**
     * 冒泡排序。
     * @param ints
     * @return
     */
    public static int[] bubblingSort(int[] ints){
        if (ints.length <= 1){
            return ints;
        }
        int size = ints.length;

        // b.引入一个新的边界索引，如果之后没有进行位置交换（即之后的数字都是有序的），那么下次内循环比较的时候，只需要到这个索引位置即可
        // borderIndex用于减少内循环的比较次数，而 isSort 用于减少外循环的比较次数
        int borderIndex = size-1;
        int lastIndex = 0;

        //外侧循环监控控制末端最大数
        for (int i=size-1;i>=0;i--){
            //内侧循环控制最大数之前的其他数排序
            // a.引入控制变量 isSort，当执行到某一轮的时候，没有发生元素交换，那么在之后的大循环中，也不会出现元素交换，直接跳出大循环
            boolean isSort = true;
            //for (int j=0;j<i;j++){
            for (int j=0;j<borderIndex;j++){
                if (ints[j]>ints[j+1]){
                    ints[j] = ints[j]+ints[j+1];
                    ints[j+1] = ints[j]-ints[j+1];
                    ints[j] = ints[j]-ints[j+1];
                    isSort = false;
                    lastIndex = j;
                }
            }
            borderIndex = lastIndex;
            System.out.println("第"+(size-i)+"轮，比较完成后，排序如下：");
            print(ints);
            //初始数组如下 {5,4,1,2,3,6,8,7}
            // 在不引入 isSort 控制变量的情况下，需要进行 8 轮排序，
            // 引入该控制变量后，只需要进行 3 次排序即可。
            if (isSort){
                break;
            }
        }
        return ints;
    }

    public static void print(int[] ints){
        for (int i=0;i<ints.length;i++){
            System.out.print(ints[i]+" ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        int[] ints = {5,4,1,2,3,6,8,7};
        print(ints);
        int[] newInts = bubblingSort(ints);
        System.out.println("最终排序结果如下：");
        print(newInts);
    }

}
