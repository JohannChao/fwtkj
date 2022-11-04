package com.johann.jianzhiOffer;
//请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
//
// 
//
// 示例 1： 
//
// 输入：s = "We are happy."
//输出："We%20are%20happy." 
//
// 
//
// 限制： 
//
// 0 <= s 的长度 <= 10000 
//
// Related Topics 字符串 👍 364 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class SolutionJianzhi05 {
    public String replaceSpace(String s) {
        if(s==null){
            return null;
        }
        //替换后的字符串
        StringBuilder sb = new StringBuilder(s.length());
        //如果原来的字符是空格，则在新的字符串中插入"%20"
        for(char c : s.toCharArray()){
            if(32==c){
                sb.append("%20");
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String s = "We  are  happy.";
        String s1 = s.replace(" ","%20");
        String s2 = new SolutionJianzhi05().replaceSpace(s);
        System.out.println(s1);
        System.out.println(s2);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
