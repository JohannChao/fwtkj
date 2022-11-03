package com.johann.jianzhiOffer;
//给定一个非负整数 n ，请计算 0 到 n 之间的每个数字的二进制表示中 1 的个数，并输出一个数组。
//
// 
//
// 示例 1: 
//
// 
//输入: n = 2
//输出: [0,1,1]
//解释: 
//0 --> 0
//1 --> 1
//2 --> 10
// 
//
// 示例 2: 
//
// 
//输入: n = 5
//输出: [0,1,1,2,1,2]
//解释:
//0 --> 0
//1 --> 1
//2 --> 10
//3 --> 11
//4 --> 100
//5 --> 101
// 
//
// 
//
// 说明 : 
//
// 
// 0 <= n <= 10⁵ 
// 
//
// 
//
// 进阶: 
//
// 
// 给出时间复杂度为 O(n*sizeof(integer)) 的解答非常容易。但你可以在线性时间 O(n) 内用一趟扫描做到吗？ 
// 要求算法的空间复杂度为 O(n) 。 
// 你能进一步完善解法吗？要求在C++或任何其他语言中不使用任何内置函数（如 C++ 中的 __builtin_popcount ）来执行此操作。 
// 
//
// 
//
// 
// 注意：本题与主站 338 题相同：https://leetcode-cn.com/problems/counting-bits/ 
//
// Related Topics 位运算 动态规划 👍 105 👎 0

//leetcode submit region begin(Prohibit modification and deletion)
class SolutionJianzhiII003 {
    public int[] countBits(int n) {
        /**
         * 首先看一下 0--15 之间的二进制，包含的1 有什么规律。
         * 二进制   包含1的个数
         * 0       0
         * 1       1           2^1
         *
         * 10      1
         * 11      2           2^2
         *
         * 1 00    1+0
         * 1 01    1+1
         * 1 10    1+1
         * 1 11    1+2         2^3
         *
         * 1 000   1+0
         * 1 001   1+1
         * 1 010   1+1
         * 1 011   1+1
         * 1 100   1+(1+0)
         * 1 101   1+(1+1)
         * 1 110   1+(1+1)
         * 1 111   1+(1+2)     2^4
         *
         * 规律就是：二进制每增加一位，就会在之前所有的二进制数前面添加一位，且是固定的 1。
         * 所以，可以用循环的方式，得出之后的二进制数包含 1 的个数，循环的入口就是，前两个数字[0,1]
         */

        /**
         * 第一步，先写一个循环的出口
            int[] countArray = new int[n + 1];
            while (n < 2) {
                switch (n) {
                    case 0:
                        countArray[n] = 0;
                        break;
                    case 1:
                        countArray[n] = 1;
                        break;
                    default:
                        ;
                }
                n++;
            }
         * 第二步，总结其他的二进制的规律。
         * 一个二进制数 N 中包含的 1 的个数，将这个二进制 N 的最高位由 1 变成 0，得到的一个二进制数 M。M中1的个数+1，就是 N 中1的个数。
         * 当我们从0开始循环遍历的时候，这个N里面1的个数，肯定是已经求出来的。现在问题是，如何根据 M 得到 N。
         * 举个例子：N = 1101,1100(8位)，想到 M 应该是 0101,1100。
         * 1101,1100
         * &
         * 0111,1111((1<<8-1))
         * =
         * 0101,1100
         * 即，M = N & ((1<<N的位数)-1)
            while (n >= 1 << 2 && n < 1 << 3) {
                countArray[n] = 1 + countArray[n & ((1 << 2) - 1)];
            }
            while (n >= 1 << 3 && n < 1 << 4) {
                countArray[n] = 1 + countArray[n & ((1 << 3) - 1)];
            }
         */
        int[] countArray = new int[n + 1];
        //当前循环中 i 的位数
        int moveCount = 1;
        //从 0~n 循环遍历
        for (int i = 0; i <= n; i++) {
            //循环入口
            if (i==0){
                countArray[i] = 0;
            }else if(i==1){
                countArray[i] = 1;
            } else {
                //确定循环 i 的二进制位数
                if (i >= 1 << moveCount && i < 1 << (moveCount + 1)) {
                    //当前二进制数中1的个数，等于这个二进制数砍掉最高位的另一个二进数中1的个数+1（听起来像是废话）
                    countArray[i] = 1 + countArray[i & ((1 << moveCount) - 1)];
                } else {
                    //如果 i 的二进制位数发生变化，记录该位数的moveCount+1，别忘了再执行一遍数组赋值操作
                    moveCount++;
                    countArray[i] = 1 + countArray[i & ((1 << moveCount) - 1)];
                }
            }
        }
        return countArray;

        /**
         * MD，如果砍最低位，岂不是更简单！！！
         */
    }

    public int[] countBits2(int n) {
        /**
         * 笨比方法
         */
        int[] countArray = new int[n + 1];
        while (n >= 0) {
            countArray[n] = Integer.bitCount(n);
            n--;
        }
        return countArray;
    }

    public static void main(String[] args) {
        int[] array1 = new SolutionJianzhiII003().countBits2(100000);
        int[] array2 = new SolutionJianzhiII003().countBits(100000);
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                System.out.println("array1[" + i + "] = " + array1[i]);
                System.out.println("array2[" + i + "] = " + array2[i]);
                return;
            }
        }
        System.out.println("Bingo");
//        int m = Integer.parseInt("11110011", 2);
//        int m1 = (1<<7);
//        System.out.println(Integer.toBinaryString(m1));
//        int m2 = (1<<8);
//        System.out.println(Integer.toBinaryString(m2));
//        int n = m&((1<<7)-1);
//        System.out.println(Integer.toBinaryString(n));
    }
}
//leetcode submit region end(Prohibit modification and deletion)
