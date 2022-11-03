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
         * 笨比方法
         */
//        int [] countArray = new int[n+1];
//        while (n >= 0){
//            countArray[n] = Integer.bitCount(n);
//            n--;
//        }
//        return countArray;

        /**
         * 0       0
         * 01      1
         * 10      1
         * 11      2        2^2
         * 1 00    1+0
         * 1 01    1+1
         * 1 10    1+1
         * 1 11    1+2      2^3
         * 1  000  1+0
         * 1  001  1+1
         * 1  010  1+1
         * 1  011  1+1
         * 1  100  1+(1+0)
         * 1  101  1+(1+1)
         * 1  110  1+(1+1)
         * 1  111  1+(1+2)  2^4
         */
        int[] countArray = new int[n + 1];
        while (n < 1 << 2) {
            switch (n) {
                case 0:
                    countArray[n] = 0;
                    break;
                case 1:
                case 2:
                    countArray[n] = 1;
                    break;
                case 3:
                    countArray[n] = 2;
                    break;
                default:
                    ;
            }
            n++;
        }

        while (n >= 1 << 2 && n < 1 << 3) {
            countArray[n] = 1 + countArray[n | ((1 << 2) - 1)];
        }
        while (n >= 1 << 3 && n < 1 << 4) {
            countArray[n] = 1 + countArray[n | ((1 << 3) - 1)];
        }

        int moveCount = 2;
        for (int i = 0; i <= n; i++) {
            if (0 <= i && i < 4) {
                switch (i) {
                    case 0:
                        countArray[i] = 0;
                        break;
                    case 1:
                    case 2:
                        countArray[i] = 1;
                        break;
                    case 3:
                        countArray[i] = 2;
                        break;
                    default:
                        ;
                }
            } else {
                //移动位数

            }
        }

        return null;
    }


    public static void main(String[] args) {
        //new SolutionJianzhiII003().countBits(10);

        int m = Integer.parseInt("11110011", 2);
        int n = m >> 1;
        System.out.println(Integer.toBinaryString(n));


//        int cap = 100000;
//        int n = cap - 1;
//
//        byte[] bs = Integer.toBinaryString(n).getBytes();
//
//        String sn = Integer.toBinaryString(n);
//        System.out.println("\n--- n>>1 ---");
//        System.out.println(sn);
//
//        String s1 = Integer.toBinaryString(n >>> 1);
//        System.out.println(s1);
//        n |= n >>> 1;
//        String sn1 = Integer.toBinaryString(n);
//        System.out.println(sn1);
//
//        System.out.println("\n--- n>>2 ---");
//        System.out.println(sn1);
//        String s2 = Integer.toBinaryString(n >>> 2);
//        System.out.println(s2);
//        n |= n >>> 2;
//        String sn2 = Integer.toBinaryString(n);
//        System.out.println(sn2);
//
//        System.out.println("\n--- n>>4 ---");
//        System.out.println(sn2);
//        String s4 = Integer.toBinaryString(n >>> 4);
//        System.out.println(s4);
//        n |= n >>> 4;
//        String sn4 = Integer.toBinaryString(n);
//        System.out.println(sn4);
//
//        System.out.println("\n--- n>>8 ---");
//        System.out.println(sn4);
//        String s8 = Integer.toBinaryString(n >>> 8);
//        System.out.println(s8);
//        n |= n >>> 8;
//        String sn8 = Integer.toBinaryString(n);
//        System.out.println(sn8);
//
//        System.out.println("\n--- n>>16 ---");
//        System.out.println(sn8);
//        String s16 = Integer.toBinaryString(n >>> 16);
//        System.out.println(s16);
//        n |= n >>> 16;
//        String sn16 = Integer.toBinaryString(n);
//        System.out.println(sn16);
//
//        System.out.println(n);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
