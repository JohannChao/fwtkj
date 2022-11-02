package com.johann.jianzhiOffer;
//请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
//
// 数值（按顺序）可以分成以下几个部分： 
//
// 
// 若干空格 
// 一个 小数 或者 整数 
// （可选）一个 'e' 或 'E' ，后面跟着一个 整数 
// 若干空格 
// 
//
// 小数（按顺序）可以分成以下几个部分： 
//
// 
// （可选）一个符号字符（'+' 或 '-'） 
// 下述格式之一： 
// 
// 至少一位数字，后面跟着一个点 '.' 
// 至少一位数字，后面跟着一个点 '.' ，后面再跟着至少一位数字 
// 一个点 '.' ，后面跟着至少一位数字 
// 
// 
//
// 整数（按顺序）可以分成以下几个部分： 
//
// 
// （可选）一个符号字符（'+' 或 '-'） 
// 至少一位数字 
// 
//
// 部分数值列举如下： 
//
// 
// ["+100", "5e2", "-123", "3.1416", "-1E-16", "0123"] 
// 
//
// 部分非数值列举如下： 
//
// 
// ["12e", "1a3.14", "1.2.3", "+-5", "12e+5.4"] 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "0"
//输出：true
// 
//
// 示例 2： 
//
// 
//输入：s = "e"
//输出：false
// 
//
// 示例 3： 
//
// 
//输入：s = "."
//输出：false 
//
// 示例 4： 
//
// 
//输入：s = "    .1  "
//输出：true
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 20 
// s 仅含英文字母（大写和小写），数字（0-9），加号 '+' ，减号 '-' ，空格 ' ' 或者点 '.' 。 
// 
//
// Related Topics 字符串 👍 397 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class SolutionJianzhi20 {
    public boolean isNumber(String s) {
        char c_0 = s.charAt(0);
        //字符不是以 "空格"、"+"、"-"、"."、"数字" 打头，直接排除
        boolean startFlag = (c_0 == 32 || c_0 == 43 || c_0 == 45 || c_0 == 46 || (c_0 > 47 && c_0 < 58));
        if (!startFlag){
            return false;
        }
        char[] chars = s.toCharArray();
        //出现小数点的标识
        boolean pointFlag = false;

        /**
         * 将满足“数值”条件的字符串一步一步组装起来，步骤如下：
         *     0 只包含空格
         *     1 （可能包含空格）--出现小数或整数中的【“+”、“-”、“.”】
         *     2 （可能包含空格）--（可能出现小数或整数中的【“+”、“-”、“.”】）--出现数字（数字后面再跟小数点时，需要判断之前是否有小数点）
         *     3 （可能包含空格）--（可能出现小数或整数中的【“+”、“-”、“.”】）--出现数字--出现e/E
         *     4 （可能包含空格）--（可能出现小数或整数中的【“+”、“-”、“.”】）--出现数字--出现e/E--出现“+”、“-”
         *     5 （可能包含空格）--（可能出现小数或整数中的【“+”、“-”、“.”】）--出现数字--出现e/E--（可能出现“+”、“-”）--出现数字
         *     6 （可能包含空格）--（可能出现小数或整数中的【“+”、“-”、“.”】）--出现数字--（可能出现e/E--（可能出现“+”、“-”）--出现数字）--出现空格
         *
         * 满足数值条件的是 2 5 6
         */
        //下列值，只能有一个为 true
        boolean structureLevel_0 = true;
        boolean structureLevel_1 = false;
        boolean structureLevel_2 = false;
        boolean structureLevel_3 = false;
        boolean structureLevel_4 = false;
        boolean structureLevel_5 = false;
        boolean structureLevel_6 = false;

        for(int i = 0;i < s.length();i++) {
            //处于步骤0，只包含空格
            if (structureLevel_0){
                //此时只要遇到空格，就还处于步骤0
                if (chars[i] == 32){
                    structureLevel_0 = true;
                    //处于步骤0，只要遇到 "+"、"-"、"." 打头，直接进入 步骤1
                }else if (chars[i] == 43 || chars[i] == 45 || chars[i] == 46){
                    //当前出现了小数点，更新小数点出现标识
                    if (chars[i] == 46){
                        pointFlag = true;
                    }
                    structureLevel_0 = false;
                    structureLevel_1 = true;
                    //处于步骤0，只要遇到 "数字" 打头，直接进入 步骤2
                }else if (chars[i] > 47 && chars[i] < 58){
                    structureLevel_0 = false;
                    structureLevel_2 = true;
                    //未到步骤1，却遇到其他字符，直接排除
                }else {
                    return false;
                }
            }

            //处于步骤1，（可能包含空格）--出现小数或整数中的【“+”、“-”、“.”】
            else if (structureLevel_1) {
                //处于步骤1，且上一个字符是 "."
                if (pointFlag){
                    //上一个字符是 "."，当前字符是"数字"，此时直接进入步骤2
                    if (chars[i] > 47 && chars[i] < 58){
                        structureLevel_1 = false;
                        structureLevel_2 = true;
                        //出现其他字符，直接排除
                    }else {
                        return false;
                    }
                    //处于步骤1，且上一个字符是 "+"、"-"
                }else {
                    //上一个字符是 "+"、"-"，当前字符是"数字"，此时直接进入步骤2
                    if (chars[i] > 47 && chars[i] < 58) {
                        structureLevel_1 = false;
                        structureLevel_2 = true;
                        //上一个字符是 "+"、"-"，当前字符是"."，此时仍旧处于步骤1
                    }else if (chars[i] == 46) {
                        //当前出现了小数点，更新小数点出现标识
                        pointFlag = true;
                        structureLevel_1 = true;
                        //出现其他字符，直接排除
                    }else {
                        return false;
                    }
                }
            }

            //处于步骤2，（可能包含空格）--（可能出现小数或整数中的【“+”、“-”、“.”】）--出现数字
            else if (structureLevel_2) {
                //当前字符是数字，仍处于步骤2
                if (chars[i] > 47 && chars[i] < 58) {
                    structureLevel_2 = true;
                    //当前字符是 e/E，直接进入步骤3
                }else if (chars[i] == 69 || chars[i] == 101){
                    structureLevel_2 = false;
                    structureLevel_3 = true;
                    //处于步骤2，却遇到了小数点，此时要判断之前是否有小数点
                }else if (chars[i] == 46){
                    //如果之前没有出现小数点，此处可以是小数点，此时仍处于步骤2
                    if (!pointFlag) {
                        //当前出现了小数点，更新小数点出现标识
                        pointFlag = true;
                        structureLevel_2 = true;
                        //如果之前出现了小数点，此时直接排除
                    }else {
                        return false;
                    }
                    //处于步骤2，却遇到了空格，直接进入步骤6
                }else if (chars[i] == 32){
                    structureLevel_2 = false;
                    structureLevel_6 = true;
                    //出现其他字符，直接排除
                }else {
                    return false;
                }
            }

            //处于步骤3，（可能包含空格）--（可能出现小数或整数中的【“+”、“-”、“.”】）--出现数字--出现e/E
            else if (structureLevel_3) {
                // e/E 后面遇到了"+"、"-"、"数字"，此时进入步骤4
                if (chars[i] == 43 || chars[i] == 45) {
                    structureLevel_3 = false;
                    structureLevel_4 = true;
                    // e/E 后面遇到了"+"、"-"、"数字"，此时进入步骤5
                } else if ((chars[i] > 47 && chars[i] < 58)) {
                    structureLevel_3 = false;
                    structureLevel_5 = true;
                    //出现其他字符，直接排除
                }else {
                    return false;
                }
            }

            //处于步骤4，（可能包含空格）--（可能出现小数或整数中的【“+”、“-”、“.”】）--出现数字--出现e/E--出现“+”、“-”
            else if (structureLevel_4){
                //处于步骤4，此时后面只能是数字，出现数字，进入步骤5
                if ((chars[i] > 47 && chars[i] < 58)) {
                    structureLevel_4 = false;
                    structureLevel_5 = true;
                    //出现其他字符，直接排除
                }else {
                    return false;
                }
            }

            //处于步骤5，（可能包含空格）--（可能出现小数或整数中的【“+”、“-”、“.”】）--出现数字--出现e/E--（可能出现“+”、“-”）--出现数字
            else if (structureLevel_5){
                //处于步骤5，出现数字，仍在步骤5
                if ((chars[i] > 47 && chars[i] < 58)) {
                    structureLevel_5 = true;
                    //出现空格，进入步骤 6
                }else if (chars[i] == 32){
                    structureLevel_5 = false;
                    structureLevel_6 = true;
                    //出现其他字符，直接排除
                }else {
                    return false;
                }
            }

            //处于步骤6，（可能包含空格）--（可能出现小数或整数中的【“+”、“-”、“.”】）--出现数字--（可能出现e/E--（可能出现“+”、“-”）--出现数字）--出现空格
            else if (structureLevel_6){
                //处于步骤6，此时后面只能跟空格，或者结束
                if (chars[i] == 32) {
                    structureLevel_6 = true;
                }else {
                    return false;
                }
            }
        }

        //满足数值条件的是 2 5 6
        if (structureLevel_2 || structureLevel_5 || structureLevel_6){
            return true;
        }else {
            return false;
        }

    }


    public boolean isNumber2(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        //去掉首位空格
        s = s.trim();
        //是否出现数字
        boolean numFlag = false;
        //是否出现小数点
        boolean dotFlag = false;
        boolean eFlag = false;
        for (int i = 0; i < s.length(); i++) {
            //判定为数字，则标记numFlag
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                numFlag = true;
                //小数点只可以出现再e之前，且只能出现一次.num  num.num num.都是被允许的
            } else if (s.charAt(i) == '.' && !dotFlag && !eFlag) {
                dotFlag = true;
                //判定为e，需要没出现过e，并且出过数字了
            } else if ((s.charAt(i) == 'e' || s.charAt(i) == 'E') && !eFlag && numFlag) {
                eFlag = true;
                //避免e以后没有出现数字
                numFlag = false;
                //判定为+-符号，只能出现在第一位或者紧接e后面
            } else if ((s.charAt(i) == '+' || s.charAt(i) == '-') && (i == 0 || s.charAt(i - 1) == 'e' || s.charAt(i - 1) == 'E')) {

                //其他情况，都是非法的
            } else {
                return false;
            }
        }
        //是否出现了数字
        return numFlag;
    }

    public static void main(String[] args) {
        new SolutionJianzhi20().isNumber("0..");
    }

}
//leetcode submit region end(Prohibit modification and deletion)
