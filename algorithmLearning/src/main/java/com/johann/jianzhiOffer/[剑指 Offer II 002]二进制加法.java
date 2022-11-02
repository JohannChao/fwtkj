package com.johann.jianzhiOffer;
//给定两个 01 字符串 a 和 b ，请计算它们的和，并以二进制字符串的形式输出。
//
// 输入为 非空 字符串且只包含数字 1 和 0。 
//
// 
//
// 示例 1: 
//
// 
//输入: a = "11", b = "10"
//输出: "101" 
//
// 示例 2: 
//
// 
//输入: a = "1010", b = "1011"
//输出: "10101" 
//
// 
//
// 提示： 
//
// 
// 每个字符串仅由字符 '0' 或 '1' 组成。 
// 1 <= a.length, b.length <= 10^4 
// 字符串如果不是 "0" ，就都不含前导零。 
// 
//
// 
//
// 
// 注意：本题与主站 67 题相同：https://leetcode-cn.com/problems/add-binary/ 
//
// Related Topics 位运算 数学 字符串 模拟 👍 50 👎 0


import org.junit.Test;

//leetcode submit region begin(Prohibit modification and deletion)
class SolutionJianzhiII002 {
    public String addBinary(String a, String b) {
        //传入的两个字符的最大长度
        int lenMax = (a.length() > b.length() ? a.length() : b.length());
        //传入的两个字符的长度差值
        int lenDiff = (a.length() > b.length() ? a.length() - b.length() : b.length() - a.length());
        //进行加法后，新的字符数组的长度。考虑到两个二进制数相加，最终结果可能超出原来的二进制数的位数，此时选择 lenMax+1
        char[] addCharArray = new char[lenMax + 1];
        //从末端（即最低位）开始遍历字符串
        int index = lenMax - 1;
        //二进制对应位进行加法操作后的进位
        char carry = '0';
        //由新的字符数组生成字符串时的偏移量
        int offset = 0;
        //从后往前开始遍历字符串（从最低位到最高位）
        while (index >= 0) {
            /**
             * 如果当前字符是一个较短的那个字符，此时与长字符串的index处于相同的二进制位的，短字符串的index，应为 index-lenDiff。
             * 如果短字符串变更后的实际索引量[index-lenDiff]超出下限 0，此时用 '0' 来补充。
             *
             * 示例：
             *     a = "1", b="1111"
             *     此时 lenMax = 4,lenDiff = 3
             *     当b的index为3时，a的相同二进制位的实际索引量，应为 index-lenDiff=0；
             *     当b的index为2时，a的相同二进制位的实际索引量，应为 index-lenDiff=-1，超出下限，用'0'补充。
             *     a 索引与二进制位对应关系 【-3--'0', -2--'0', -1--'0', 0--'1'】
             *     b 索引与二进制位对应关系 【 0--'1',  1--'1',  2--'1', 3--'1'】
             */
            char aChar = lenMax > a.length() ? (index - lenDiff >= 0 ? a.charAt(index - lenDiff) : '0') : a.charAt(index);
            char bChar = lenMax > b.length() ? (index - lenDiff >= 0 ? b.charAt(index - lenDiff) : '0') : b.charAt(index);

            /**
             * 此处推荐一本书《编码的奥秘》
             * 1）当两个二进制位相同时，相加结果中当前的二进制位结果为 0 (1+1=10,0+0=0)。考虑到前一位进位的影响，则结果中当前二进制位的结果为进位值；
             *     addCharArray[index + 1] = carry;
             *     当前位的进位由加数决定。
             *     carry = (aChar == '1' ? '1' : '0');
             * 2）当两个二进制位不同时，相加的结果为 1。再加上前一位进位的影响，
             *     如果前一位进位为 1，则当前位结果为 0，当前位的进位为 1；
             *     如果前一位进位为 0，则当前位结果为 1，当前位的进位为 0；
             *     addCharArray[index + 1] = carry == '1' ? '0' : '1';
             *     carry = carry == '1' ? '1' : '0';
             */
            if (aChar == bChar) {
                addCharArray[index + 1] = carry;
                carry = aChar;
            } else {
                addCharArray[index + 1] = carry == '1' ? '0' : '1';
            }
            /**
             * 当遍历到长字符串的边界时，此时要考虑最高位相加结果产生进位的问题，多预留出来的一位用来填充进位结果。
             * 如果两个加数，最高位的相加结果没有产生进位，此时设置新字符数组的数组偏移量 offset = 1, count = lenMax，生成新字符串时不包含最高位；
             * 如果两个加数，最高位的相加结果产生进位，此时设置 offset = 0, count = lenMax+1。
             */
            if (index == 0) {
                addCharArray[index] = carry;
                offset = carry == '1' ? 0 : 1;
                lenMax = carry == '1' ? lenMax + 1 : lenMax;
            }
            //控制循环，从后往前进行循环遍历
            index--;
        }
        //返回新生成的字符串
        return new String(addCharArray, offset, lenMax);
    }

    public static void main(String[] args) {
        String a = "1";
        String b = "111";
        String addResult = new SolutionJianzhiII002().addBinary(a, b);
        System.out.println(addResult);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
