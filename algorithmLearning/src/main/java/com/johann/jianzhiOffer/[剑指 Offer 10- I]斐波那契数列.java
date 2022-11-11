package com.johann.jianzhiOffer;
//写一个函数，输入 n ，求斐波那契（Fibonacci）数列的第 n 项（即 F(N)）。斐波那契数列的定义如下：
//
// 
//F(0) = 0, F(1)= 1
//F(N) = F(N - 1) + F(N - 2), 其中 N > 1. 
//
// 斐波那契数列由 0 和 1 开始，之后的斐波那契数就是由之前的两数相加而得出。 
//
// 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 2
//输出：1
// 
//
// 示例 2： 
//
// 
//输入：n = 5
//输出：5
// 
//
// 
//
// 提示： 
//
// 
// 0 <= n <= 100 
// 
//
// Related Topics 记忆化搜索 数学 动态规划 👍 420 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class SolutionJianzhi0101 {
    /**
     * 动态规划
     * 时间复杂度 O(n)，空间复杂度 O(1)
     *
     * (a + b) % p = (a % p + b % p) % p （1）
     * (a - b) % p = (a % p - b % p ) % p （2）
     * (a * b) % p = (a % p * b % p) % p （3）
     *  a ^ b % p = ((a % p)^b) % p （4）
     */
    public int fib(int n) {
        if (n < 2) {
            return n;
        }
        int mod = 1000000007;
        // 初始化时，now为n=1的值，pre为n=0的值。prepre为n=-1的值
        int prepre = 0,pre = 0,now = 1;
        //pre更新为now，prepre更新为pre，now更新为最新的前两个数相加
        for (int i = 2;i <= n;i++) {
            prepre = pre;
            pre = now;
            now = (prepre + pre)%mod;
        }
        return now;
    }

    /********************* 基础知识 ******************************/
    /**
     * 快速幂运算
     * 时间复杂度 O(log n)
     *   2^15
     * = 2 * (2^2)^7
     * = 2 * (2^2) * ((2^2)^2)^3
     * = 2 * (2^2) * ((2^2)^2) * ((2^2)^2)^2
     * = 2 * (2^2) * ((2^2)^2) * (((2^2)^2)^2)
     * = 2^(1+2+4+8)
     *
     * @param baseNumber
     * @param exponent
     * @return
     */
    public static int fastPower(int baseNumber,int exponent){
        if (exponent == 0 || baseNumber == 1) {
            return 1;
        }
        int result = 1;
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result *= baseNumber;
            }
            exponent >>= 1;
            baseNumber *= baseNumber;
        }
        return result;
    }

    /**
     * 矩阵的乘法，当前者(a)的列数，与后者(b)的行数相等时，矩阵乘法才有意义
     * c = a * b，其中c的行数由前者(a)决定，c的列数(b)由后者决定
     * @param a
     * @param b
     * @return
     */
    public int[][] matrixMultiplication(int[][] a,int[][] b){
        // a 的行数
        int x = a.length;
        // b 的列数
        int y = b[0].length;
        // a 的列数，b 的行数
        int z = b.length;
        // c=a*b
        int[][] c = new int[x][y];
        // 矩阵的乘法
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    c[i][j] += a[i][k]*b[k][j];
                }
            }
        }
        return c;
    }

    /**
     * 矩阵快速幂
     * @param x
     * @param n
     * @return
     */
    public int[][] matrixFastPower(int[][] x,int n){
        int[][] z = new int[x.length][x[0].length];
        // 在快速幂运算中，我们将返回的结果初始设置为 1，在矩阵快速运算中，将结果初始设置为单位矩阵（任何矩阵与单位矩阵相乘都等于本身）
        for (int i = 0; i < x.length; i++) {
            z[i][i] = 1;
        }
        // 矩阵的快速幂
        while (n > 0){
            if ((n & 1) ==1){
                z = matrixMultiplication(z,x);
            }
            n>>=1;
            x = matrixMultiplication(x,x);
        }
        return z;
    }
    /********************* 基础知识 ******************************/

    /********************** 斐波那契数矩阵快速幂算法 ******************************/
    /**
     * 针对斐波那契数，在矩阵相乘时取模
     * (a + b) % p = (a % p + b % p) % p （1）
     * (a - b) % p = (a % p - b % p ) % p （2）
     * (a * b) % p = (a % p * b % p) % p （3）
     *  a ^ b % p = ((a % p)^b) % p （4）
     * @param a
     * @param b
     * @return
     */
    public int[][] matrixMultiplicationFib(int[][] a,int[][] b){
        // 取模值
        int mod = 1000000007;
        // a 的行数
        int x = a.length;
        // b 的列数
        int y = b[0].length;
        // a 的列数，b 的行数
        int z = b.length;
        // c=a*b
        int[][] c = new int[x][y];
        // 矩阵的乘法
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    c[i][j] += (int)(((long) a[i][k] * b[k][j])%mod);
                    c[i][j] %= mod;
                }
            }
        }
        return c;
    }

    /**
     * 矩阵快速幂
     * @param x
     * @param n
     * @return
     */
    public int[][] matrixFastPowerFib(int[][] x,int n){
        int[][] z = new int[x.length][x[0].length];
        // 在快速幂运算中，我们将返回的结果初始设置为 1，在矩阵快速运算中，将结果初始设置为单位矩阵（任何矩阵与单位矩阵相乘都等于本身）
        for (int i = 0; i < x.length; i++) {
            z[i][i] = 1;
        }
        // 矩阵的快速幂
        while (n > 0){
            if ((n & 1) ==1){
                z = matrixMultiplicationFib(z,x);
            }
            n>>=1;
            x = matrixMultiplicationFib(x,x);
        }
        return z;
    }

    /**
     * 矩阵快速幂
     *
     * 矩阵 X 为: {f(n),f(n-1)}
     * 矩阵 Y 为:
     * {1,1}
     * {1,0}
     *
     * 矩阵 Z = X*Y 为: {Z[0][0],Z[0][1]}
     * Z[0][0] = f(n)+f(n-1)
     * Z[0][1] = f(n)
     * 又，f(n+1) = f(n)+f(n-1)
     * 矩阵 Z 为：{f(n+1),f(n)}
     * 所以, 矩阵 Z 为 {f(n+1),f(n)} = {f(1),f(0)} * Y^n
     *
     * 设 M = Y^n
     * 则：
     * f(n+1) = f(1)*M[0][0] +f(0)*M[1][0]
     * f(n) = f(1)*M[0][1] + f(0)*M[1][1]
     * 又：
     * f(0) = 0, f(1) = 1
     * 所以：f(n) = M[0][1]
     */
    public int fib2(int n) {
        if (n < 2){
            return n;
        }
        int[][] x = {{1,1},{1,0}};
        int[][] c = matrixFastPowerFib(x,n);
        return c[0][1];
    }
    /********************** 斐波那契数矩阵快速幂算法 ******************************/

    public static void main(String[] args) {
        //1 1 2 3 5 8 13 21 34 55 89 144
        int res = new SolutionJianzhi0101().fib(100);
        System.out.println(res);
        int res2 = new SolutionJianzhi0101().fib2(100);
        System.out.println(res2);

//        int[][] x = {{1,1},{1,0}};
//        new SolutionJianzhi0101().matrixFastPowerFib(x,3);

//        int[][] z = new int[2][2];
//        for (int i = 0; i < 2; i++) {
//            z[i][i] = 1;
//        }
//        for (int[] ints : z) {
//            for (int anInt : ints) {
//                System.out.print(anInt + "\t");
//            }
//            System.out.println();
//        }

        //System.out.println(13>>1);
        //System.out.println(fastPower(2,15));
    }
}
//leetcode submit region end(Prohibit modification and deletion)
