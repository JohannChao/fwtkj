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
        StringBuilder sb = new StringBuilder(s.length());
        for(char c : s.toCharArray()){
            if(32==c){
                sb.append("%20");
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
//leetcode submit region end(Prohibit modification and deletion)
