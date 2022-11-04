package com.johann.algorithm.sort;

/**
 * @ClassName: Sorting
 * @Description: TODO 排序算法
 * @Author: Johann
 **/
public class Sorting {

    /**
     *  冒泡排序。 时间复杂夫O(n*n),空间复杂度
     *
     *  1）比较相邻的元素。如果 【前面】 比 【后面】 大，就交换他们两个。
     *  2）从第一对比较到最后一对，比较完以后，此时最大的数排到了最后。
     *  3）针对所有的元素重复以上的步骤，除了最后一个。
     *  4）持续重复上面操作，直到参与比较的数字个数为 1，此时冒牌排序完成。
     *
     *  n+(n-1)+(n-2)+(n-3)+ ... +(3)+(2)+(1)
     *
     *
    * @Description: 冒泡排序
    * @Param: [ints] 
    * @return: void 
    * @Author: Johann 
    */
    public static void bubbleSort(int[] ints){
        int size = ints.length;

        for(int i = 1;i<size;i++){
            System.out.println("---------");
            for(int j = 0;j < size-i;j++){
                if(ints[j] > ints[j+1]){
//                    int k = ints[j];
//                    ints[j] = ints[j+1];
//                    ints[j+1] = k;
                    swap(ints,j,j+1);
                }
                bl(ints);
            }
       }
    }

    /** 
    * @Description: 冒泡排序升级，引入控制变量 isSort(当前大循环是否有交换元素，若无则 isSort = true)
    * @Param: [ints] {3，4，2，1，5，6，7，8}
    * @return: void 
    * @Author: Johann 
    */
    public static void bubbleSortUp(int[] ints){
        int size = ints.length;

        for(int i = 1;i<size;i++){
            // 引入控制变量 isSort，当执行到某一轮的时候，没有发生元素交换，那么在之后的大循环中，也不会出现元素交换，直接跳出大循环
            boolean isSort = true;
            for(int j = 0;j < size-i;j++){
                if(ints[j] > ints[j+1]){
                    swap(ints,j,j+1);
                    isSort = false;
                }
            }
            // 上一轮没有发生元素交换，直接跳出大循环
            if(isSort){
                break;
            }
        }
    }

    /**
     *  正常来讲，冒泡排序我们每比较一轮，有序序列就增加一个。即有序序列的个数等于比较的轮数。
     *  但是，当我们在比较这个数组 {3，4，2，1，5，6，7，8}的时候发现，还没有比较的时候，就已经有3个有序元素了。第一轮比较完，已经有5个有序元素了。
     *  按照我们之前的冒泡排序算法，后面的那些元素，我们也在比较。
     *  事实上，在冒泡排序算法中，外层记录的是比较轮数。内层循环记录的是无序序列的比较次数。
     *  因此，每次外层轮数比较完，我们可以确定最后一次交换元素的位置，在此位置之后元素有序。即下一轮开始比较的时候，比较的次数仅限于在这个位置之前的元素。
     *  这大大减少了内层的比较次数。提高了冒泡排序的效率
    */


    /** 
    * @Description: 冒泡排序二次升级，引入最后一次lastExchangeIndex(每次大循环，交换元素的最后位置，在此位置之后，元素有序，无需比较)
    * @Param: [ints] 
    * @return: void 
    * @Author: Johann 
    */
    public static void bubbleSortUpUp(int[] ints){
        //记录最后一次交换的位置
        int lastExchangeIndex = 0;
        //无序数列的边界，每次比较只需要比到这里为止
        int sortBorder = ints.length -1;

        for(int i = 1;i<ints.length;i++){
            // 引入控制变量 isSort，当执行到某一轮的时候，没有发生元素交换，那么在之后的大循环中，也不会出现元素交换，直接跳出大循环
            boolean isSort = true;
            for(int j = 0;j < sortBorder;j++){
                if(ints[j] > ints[j+1]){
                    swap(ints,j,j+1);
                    isSort = false;
                    lastExchangeIndex = j;
                }
            }
            sortBorder = lastExchangeIndex;
            // 上一轮没有发生元素交换，直接跳出大循环
            if(isSort){
                break;
            }
        }
    }


    /** 
    * @Description: 鸡尾酒排序，又名双向冒泡排序 ，是冒泡排序的一种变形。该算法与冒泡排序的不同处在于排序时是以双向在序列中进行排序。
    * @Param: [array] 
    * @return: void 
    * @Author: Johann 
    */
    public static void cocktailSort(int [] array){

        int size = array.length;

        // 和冒泡排序一样，引入控制遍历 isSort
        boolean isSort = true;

        for(int i = 0;i < size/2;i++){
            // 小头，从左向右查找
            for(int j = i;j < size-i-1;j++){
                if(array[j] > array[j+1]){
                    swap(array,j,j+1);
                    isSort = false;
                }
            }
            if(isSort){
                break;
            }
            // 大头，从右向左查找
            for(int j = size-i-1;j > i;j--){
                if(array[j] < array[j-1]){
                    swap(array,j,j-1);
                    isSort = false;
                }
            }
            if (isSort){
                break;
            }
        }
    }

    /** 
    * @Description: 和冒泡排序一样，对鸡尾酒排序进行升级。尽量减少每轮比较的次数
    * @Param: [array] 
    * @return: void 
    * @Author: Johann 
    */
    public static void cocktailSortUp(int [] array){

        int size = array.length;

        // 和冒泡排序一样，引入控制遍历 isSort
        boolean isSort = true;

        // 右侧最后一次元素交换的位置
        int lastRightExchangeIndex = 0;
        // 左侧最后一次元素交换的位置
        int lastLeftExchangeIndex = 0;

        // 右侧无序数列的边界，每次比较只需要比到这里为止
        int rightSortBorder = size-1;
        // 左侧无序数列的边界，每次比较只需要比到这里为止
        int leftSortBorder = 0;

        for(int i = 0;i < size/2;i++){
            // 小头，从左向右查找
            for(int j = leftSortBorder;j < rightSortBorder;j++){
                if(array[j] > array[j+1]){
                    swap(array,j,j+1);
                    isSort = false;

                    lastRightExchangeIndex = j;
                }
            }
            rightSortBorder = lastRightExchangeIndex;
            if(isSort){
                break;
            }

            // 大头，从右向左查找
            for(int j = rightSortBorder;j > leftSortBorder;j--){
                if(array[j] < array[j-1]){
                    swap(array,j,j-1);
                    isSort = false;

                    lastLeftExchangeIndex = j;
                }
            }
            leftSortBorder = lastLeftExchangeIndex;
            if (isSort){
                break;
            }
        }
    }


    /** 
    * 快速排序
     *  快速排序的基本思想是：
     *  1）从数组中选取一个数作为基准数。
     *  2）分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边。
     *  3）再对左右区间重复第二步，直到各区间只有一个数。
     *
     *  举例说明：现有数组 ints {5,4,2,9,7,1,100,99,87,66,54,45,98,3}，按照快速排序对其进行排序。这个序列的开始结束位置为 （start，end），其中 start = 0，end = ints.length-1 = 13
     *  1）选取基准数，将 ints[start] 作为基准数取出来 x = ints[start] = 5; i = start = 0; j = end = 13。此时 ints[0] 位置的值已经保存在 x，ints[0]位置可以填充其他值。
     *  2）从 j 开始，从后往前查找，直到找到等于或小于基准数 x 的数。运气不错，第一个数就是，ints[13]它的值是 3。
     *      将这个值填充到ints[0] 位置，此时 ints[0]值为3，ints[13]空了出来。 i++，以便于下面的查找，此时i == 1 。
     *  3）再从 i 开始，从前往后查找，直到找到大于基准数 x 的数字。找到符合条件的值 ints[3] =9，将这个值，填充到上次空出来的 ints[13] 位置。
     *      此时，ints[13]值为9，ints[3]位置空了出来。 j--，以便于下一步查找，此时j == 12 。
     *  4）此时，数组的情况是 {3,4,2, _,7,1,100,99,87,66,54,45,98,9}
     *  5）重复步骤2）选定的位置是 ints[5] == 1 【j从后往前】填充到了ints[3] ,ints[5] 位置空出来。 {3,4,2,1,7, _,100,99,87,66,54,45,98,9},此时 i == 4，j == 5
     *      重复步骤 3）选定的位置是 ints[4] == 7 【i从前往后】填充到 ints[5] ，ints[4]位置空出来。{3,4,2,1, _,7,100,99,87,66,54,45,98,9},此时 i == 4，j == 4
     *  6）此时 i==j==4，数组 ints[i]是空出来的，将基准数 5，填充到 ints[4]，此时数组为 {3,4,2,1,5,7,100,99,87,66,54,45,98,9}。此时 ints[4]前的数字都比基准数小，ints[4]后面的数字都比基准数大。
     *
     *  7)递归调用，对基准数前后序列再进行排序。前面的序列开始结束位置为 （start，i-1），后面的序列开始结束位置为（j+1,end）。
     *
    * @Param: [ints, s, e]
    * @return: void 
    * @Author: Johann 
    */
    public static void quickSort(int[] ints,int s,int e){
        if(s < e){
            int i = s;
            int j = e;
            int x = ints[s];

            while(i < j){
                while(i < j && ints[j] >= x){
                    j--;
                }
                //此处条件默认是 i < j && ints[j] < x
                if(i < j){
                    ints[i] = ints[j];
                    i++;
                }
                while(i < j && ints[i] < x){
                    i++;
                }
                //此处条件默认是 i < j && ints[i] > x
                if(i < j){
                    ints[j] = ints[i];
                    j--;
                }
                System.out.println("------------- "+i+"----"+j);
                bl(ints);
            }
            ints[i] = x;
            //System.out.println("-------------");
            //bl(ints);
            quickSort(ints,s,i-1);
            quickSort(ints,i+1,e);
        }
    }

    
    /**
    * 插入排序
     *  插入排序是简单排序中最快的排序算法，虽然时间复杂度仍然为O(n*n)，但是却比冒泡排序和选择排序快很多。
     * 1、将指针指向某个元素，假设该元素左侧的元素全部有序，将该元素抽取出来，
     *      然后按照从右往左的顺序分别与其左边的元素比较，遇到比其大的元素便将元素右移，
     *      直到找到比该元素小的元素或者找到最左面发现其左侧的元素都比它大，停止；
     *  2、此时会出现一个空位，将该元素放入到空位中，此时该元素左侧的元素都比它小，右侧的元素都比它大；
     *  3、指针向后移动一位，重复上述过程。每操作一轮，左侧有序元素都增加一个，右侧无序元素都减少一个。
     *
     *  举例：需要对数组[]a 9, 8, 5, 7, 2, 4, 3 排序
     * 1）首先假设数组第一个元素a[0] 9 是有序的。后面a[1-6] 是无序的。
     * 2）第一轮指针指向 a[1] 8，从a[1] 开始与其左边的元素进行对比，如果遇到比a[1]大的元素，则这个元素向后移动。
     *      直到找到比a[1]小的元素或者发现其左侧的元素都比a[1]大，停止。发现a[0] 9比a[1]大，a[0]值右移。即a[1] 变成了9。
     *      空出来的位置是 a[0] {_,9,5,7,2,4,3}，此时把 指针的值 8 放到空出来的 a[0] 位置。数组变成{8,9,5,7,2,4,3}
     * 3）重复第二步，此时指针向右移动到 a[2] 5,a[2]左侧的元素是有序的，将a[2]与其左侧元素对比，数组最终变成{5,8,9,7,2,4,3}
     * 4) 依次重复，指针指向 a[3],a[4],a[5],a[6]。最终数组排序结果为 {2,3,4,5,7,8,9}
     *
    * @Param: [ints] 
    * @return: void 
    * @Author: Johann 
    */
    public static void insertSort(int [] ints){

        for(int i = 1;i<ints.length;i++){
            int tmp = ints[i];
            int j = i;
            while(j > 0 && tmp < ints[j-1]){
                ints[j] = ints[j-1];
                j--;
            }
            if(i!=j){
                ints[j] = tmp;
            }
        }
    }

    /** 
    * @Description: 选择排序
    * @Param: [ints] 
    * @return: void 
    * @Author: Johann 
    */
    public static void selectSort(int [] ints){

        for(int i = 0;i < ints.length-1;i++){
            int min = i;
            //在未排序序列中找到 最小值的index
            for(int j=i+1;j <= ints.length-1;j++){
                if(ints[j] < ints[min]){
                    min = j;
                }
            }
            //将未排序序列的第一个数与最小值交换位置
            if(i != min){
                int temp = ints[i];
                ints[i] = ints[min];
                ints[min] = temp;
            }
        }
    }


    /**
     *
     *
    * @Description: 希尔排序
    * @Param: [arr] 
    * @return: void 
    * @Author: Johann 
    */
    public static void shellSort(int[] arr){
        int gap = 1;
        // 选取合适的增量 gap。此处选取的增量为 Hibbard增量
        // Hibbard的增量序列如下：
        //      1，3，7，15......
        //      通项公式 2^k-1
        //      利用此种增量方式的希尔排序，最坏时间复杂度是O（n^(3/2)）
        // Sedgewick的增量序列如下：
        //      1, 5, 19, 41, 109......
        //      通项公式 9*4^k - 9*2^k + 1 或者 4^k - 3*2^k + 1
        //      利用此种增量方式的希尔排序，最坏时间复杂度是O（n^(4/3)）
        while(gap < arr.length/2){
            gap = 2*gap;
            gap--;
        }

        // 确保增量最小为1
        while(gap > 0){
            int i = 0;
            // 每个固定的增量 gap ，分成了 gap 组。 所以此处 i < gap，即 i 标识组号
            // 举例：
            // 数组为 a[] {7,5,6,4,2,3,0,1}，gap 为 3
            // 此时 a[0],a[3],a[6]一组，a[1],a[4],a[7]一组，a[2],a[5]一组
            while(i < gap){
                // 下面两个循环容易理解，是对每一个组里面的元素，使用 插入排序。
                // 和上面的插入排序相比，此处的排序，同组内的相邻元素索引间距为 gap
                for(int j = i;j<arr.length;j+=gap){
                    int temp = arr[j];
                    int k = j;
                    while(k-gap >= 0 &&temp < arr[k-gap]){
                        arr[k] = arr[k-gap];
                        k -= gap;
                    }
                    if(k!=j){
                        arr[k] = temp;
                    }
                }
                // 错误的步骤，此处没有用到插入排序
//                int j = i;
//                while(j+gap < arr.length){
//                    if(arr[j] > arr[j+gap]){
//                        swap(arr,j,j+gap);
//                    }
//                    j+=gap;
//                }
                i++;
            }
            gap = gap/2;
        }
    }

    /** 
    * @Description: 希尔排序 【优化了组内的插入排序步骤】
    * @Param: [arr] 
    * @return: int[] 
    * @Author: Johann 
    */
    public static int[] shellSort2(int[] arr) {
        int gap = 1;
        while(gap < arr.length/2){
            gap = 2*gap-1;
        }
        while (gap > 0) {
            for (int i = gap; i < arr.length; i++) {
                int tmp = arr[i];
                int j = i - gap;
                while (j >= 0 && arr[j] > tmp) {
                    arr[j + gap] = arr[j];
                    j -= gap;
                }
                arr[j + gap] = tmp;
            }
            gap = (gap+1) / 2;
        }
        return arr;
    }

    public static void bl(int [] a){
        for(int i = 0;i<a.length;i++){
            System.out.print(a[i]+" ");
        }
        System.out.println();
    }
    
    /** 
    * @Description: 不借助中间变量，交换选定的数组值
    * @Param: [arry, m, n] 
    * @return: void 
    * @Author: Johann 
    */
    public static void swap(int[]arry,int m,int n){
        arry[m] = arry[m] + arry[n];
        arry[n] = arry[m] - arry[n];
        arry[m] = arry[m] - arry[n];
    }

    public static void main(String[] args) {
        //int a[] = {5,4,2,9,7,1,100,99,87,66,54,45,98,3};
        //bl(a);
        //bubbleSort(a);
        //quickSort(a,0,a.length-1);
        //insertSort(a);
        //selectSort(a);
        //shellSort(a);
        //bl(a);
        int []b = {3,4,2,1,5,6,7,8};
        bl(b);
        bubbleSortUpUp(b);
        bl(b);
        String aaa = null;
        System.out.println(aaa);
    }
}
