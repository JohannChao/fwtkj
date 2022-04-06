package com.johann.algorithm.sort;

/**
 * 排序算法
 *
 * @ClassName: SortTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class SortTest {

    /**
     * 1，冒泡排序
     * 冒泡排序步骤如下：
     *   1），将相邻的两个元素进行比较，将更大元素交换到索引靠后的位置。依次比较到最后一个索引，直至将最大的元素，交换到索引最后的位置。【冒泡排序内循环原理】
     *   2），再从第一个元素开始，依次比较到倒数第二个索引，将次大的元素，交换到索引倒数第二的位置。【外循环，逐步缩小边界】
     *   3），重复第二步，直到全部元素都排序完成。
     *
     *
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
        for (int i=size-1;i>0;i--){
            //内侧循环控制最大数之前的其他数排序
            // a.引入控制变量 isSort，当执行到某一轮的时候，没有发生元素交换，那么在之后的大循环中，也不会出现元素交换，直接跳出大循环
            boolean isSort = true;
            //冒泡排序内循环，将最大的数字，浮动到最大索引位置
            //for (int j=0;j<i;j++){
            for (int j=0;j<borderIndex;j++){
                if (ints[j]>ints[j+1]){
//                    ints[j] = ints[j]+ints[j+1];
//                    ints[j+1] = ints[j]-ints[j+1];
//                    ints[j] = ints[j]-ints[j+1];
                    swap(ints,j,j+1);
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

    /**
     * 2，选择排序
     * 快速排序步骤如下：
     *   1），从第二个元素开始，找到之后，最小的元素，将其与第一个元素进行比较。若第一个元素小，则交换这两个元素的位置。【选择排序内循环原理】
     *   2），再从第二个元素开始，重复第一步，直至第 N-1 个元素。【外循环，逐步缩小边界】
     *
     * 其实，选择排序和冒泡排序还是很类似的，都是两层循环。只不过，选择排序在中间比较步骤，不需要交换元素，只需要记录这个最小元素的索引即可。
     * 最终的交换步骤，是在与第一个元素进行比较的时候，才会进行，即元素只交换一次。
     *
     * @param ints
     */
    public static int[] selectSort(int[] ints){
        int size = ints.length;

        for (int i=0;i<size-1;i++){
            //当前待排序的数字
            int index = i+1;
            //int minimum = ints[index];
            //查找当前待排序数字之后的最小值，记录这个最小值的索引号
            for (int j=i+1;j<size;j++){
                if(ints[index] > ints[j]){
                    //minimum = ints[j];
                    index = j;
                }
            }
            //待排序数字之后其他数字中的最小值，小于待排序数字，则交换这两个数字索引号
            if(ints[i] > ints[index]){
                swap(ints,i,index);
            }
            System.out.println("第"+(i+1)+"轮，比较完成后，排序如下：");
            print(ints);
        }
        return ints;
    }

    /**
     * 插入排序
     * 插入排序还分为直接插入排序、二分插入排序、链表插入排序、希尔排序等等，这里示例的直接插入排序
     * @param ints
     */
    public static void insertSort(int[] ints){

//        int[] newInts = new int[ints.length];
//        for(int i=0;i<ints.length;i++){
//            if(i==0){
//                newInts[i] = ints[i];
//                //continue;
//            }
//            int j = i-1;
//            while(j>=0 && ints[i] < newInts[j]){
//                newInts[j+1] = newInts[j];
//                j--;
//            }
//            newInts[++j] = ints[i];
//            System.out.println("第"+(i+1)+"轮，比较完成后，排序如下：");
//            print(newInts);
//        }

        // 无需再重新开辟内存空间
        for(int i=1;i<ints.length;i++){
            //待插入的数据
            int temp = ints[i];
            //与左侧已经插入的有序数组进行比较
            int j = i-1;
            while(j>=0 && temp < ints[j]){
                ints[j+1] = ints[j];
                j--;
            }
            //将temp插入到空出来的位置
            ints[++j] = temp;

            System.out.println("第"+(i)+"轮，比较完成后，排序如下：");
            print(ints);
        }
    }




    public static void swap(int[] ints,int m,int n){
        ints[m] = ints[m]+ints[n];
        ints[n] = ints[m]-ints[n];
        ints[m] = ints[m]-ints[n];
    }



    public static void print(int[] ints){
        for (int i=0;i<ints.length;i++){
            System.out.print(ints[i]+" ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        int[] ints = {5,4,1,2,3,6,8,7};
        System.out.println("初始数组：");
        print(ints);

        //bubblingSort(ints);

        //selectSort(ints);

        insertSort(ints);
    }

}
