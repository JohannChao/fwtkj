package com.johann.jianzhiOffer;
//给定两个整数 a 和 b ，求它们的除法的商 a/b ，要求不得使用乘号 '*'、除号 '/' 以及求余符号 '%' 。
//
// 
//
// 注意： 
//
// 
// 整数除法的结果应当截去（truncate）其小数部分，例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2 
// 假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−2³¹, 2³¹−1]。本题中，如果除法结果溢出，则返回 231 − 1 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：a = 15, b = 2
//输出：7
//解释：15/2 = truncate(7.5) = 7
// 
//
// 示例 2： 
//
// 
//输入：a = 7, b = -3
//输出：-2
//解释：7/-3 = truncate(-2.33333..) = -2 
//
// 示例 3： 
//
// 
//输入：a = 0, b = 1
//输出：0 
//
// 示例 4： 
//
// 
//输入：a = 1, b = 1
//输出：1 
//
// 
//
// 提示: 
//
// 
// -2³¹ <= a, b <= 2³¹ - 1 
// b != 0 
// 
//
// 
//
// 注意：本题与主站 29 题相同：https://leetcode-cn.com/problems/divide-two-integers/ 
//
// 
//
// Related Topics 位运算 数学 👍 195 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class SolutionJianzhiII001 {
    public int divide(int a, int b) {
        if(a == Integer.MIN_VALUE && b == -1){
            //如果除数是Integer最小值，且被除数是-1，此时相除会溢出。
            return Integer.MAX_VALUE;
        }else if (a == 0){
            //如果被除数是0，直接返回 0
            return 0;
        }
        //判断最终的商是正数，还是负数
        boolean positiveFlag = (a>0&&b<0) || (a<0&&b>0);
        //将 a,b转为负数，防止负整数转正数溢出情况
        if (a > 0){
            a = ~(a-1);
        }
        if (b > 0){
            b = ~(b-1);
        }
        //商
        int quotient = 0;
        //取反后，如果 被除数 > 除数（即|a| < |b|），此时直接返回 0
        if (a > b) {
            return 0;
        //取反后，如果 被除数 = 除数（即|a| = |b|）或者 被除数 > 除数*2（即|a| -|b| < |b|），此时商暂时取 1。具体是+1还是-1根据符号判断
        }else if (a == b || a - b > b) {
            quotient = 1;
        //如果被除数 < 除数*2（即|a| -|b| >= |b|）
        }else {
            //左移进位统计（乘2）
            int carryCount = 1;
            //中间值
            int temp = 0;
            /**
             * 统计除数需要左移多少位，才会不小于被除数
             */
            while ((temp = (b << carryCount)) >= a){
                /**
                 * 如果 temp现在超过了 a 的一半，终止循环
                 * 此处不能用 temp + temp < a，如果a为Integer.MIN_VALUE时，这样会溢出，导致死循环
                 * 也可以用 a >> 1 > temp
                 */
                if (a - temp > temp) {
                    break;
                }
                //左移位数统计
                carryCount++;
            }
            //当前商值累加
            quotient += 1 << (carryCount);
            //中间数累加，此时中间数为一个极限值，即这个中间数再左移的话，就超过了被除数 a
            temp = b << carryCount;
            //循环左移位数
            while(carryCount > 0){
                //查看当前temp 与 被除数是否相等。如果 被除数刚好是除数的2^n倍，直接终止循环
                if (temp == a){
                    break;
                }else {
                    //中间数每次加上自己的一半，将新的中间数与被除数作比较。如果新的中间数仍大于被除数，则需要继续累加商和中间数，循环遍历下一轮
                    if (a - temp  < (b << (carryCount-1))){
                        quotient += 1 << (carryCount-1);
                        temp += b << (carryCount-1);
                    //中间数每次加上自己的一半，新的中间数等于被除数，则累加商，终止循环
                    }else if(a - temp == (b << (carryCount-1))){
                        quotient += 1 << (carryCount-1);
                        break;
                    }
                    carryCount--;
                }
            }
        }
        //商值取负数
        if (positiveFlag){
            quotient = ~(quotient-1);
        }
        return quotient;
    }

    public static void main(String[] args) {
//        int m = -5;
//        int n = m << 1;
//        int o = m >> 1;
//        int mf = ~(m-1);
//        System.out.println(n);
//        System.out.println(o);
//        System.out.println(mf);
//
//        int x = Integer.MIN_VALUE+1;
//        int y = ~(x-1);
//        System.out.println(x);
//        System.out.println(y);
//        System.out.println(~(1));
        int a = 1100540749;
        int b = -1090366779;
        System.out.println(a/b);
        System.out.println(new SolutionJianzhiII001().divide(a,b));

    }
}
//leetcode submit region end(Prohibit modification and deletion)
