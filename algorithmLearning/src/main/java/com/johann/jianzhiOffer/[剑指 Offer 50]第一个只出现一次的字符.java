package com.johann.jianzhiOffer;
//在字符串 s 中找出第一个只出现一次的字符。如果没有，返回一个单空格。 s 只包含小写字母。
//
// 示例 1: 
//
// 
//输入：s = "abaccdeff"
//输出：'b'
// 
//
// 示例 2: 
//
// 
//输入：s = "" 
//输出：' '
// 
//
// 
//
// 限制： 
//
// 0 <= s 的长度 <= 50000 
//
// Related Topics 队列 哈希表 字符串 计数 👍 271 👎 0


import java.util.*;

//leetcode submit region begin(Prohibit modification and deletion)
class SolutionJianzhi50 {
    public char firstUniqChar(String s) {
        char[] chars = s.toCharArray();
        //map<字符,字符出现的次数>，用于存放字符出现的次数
        HashMap<Character, Integer> charMap = new HashMap<Character, Integer>(2^8);
        //按照字符出现的顺序，在队列中依次存放所有出现过的字符，且只存放一次
        ArrayDeque<Character> charDeque = new ArrayDeque();
        //遍历字符数组
        for (char c : chars) {
            Integer count = charMap.get(c);
            //如果此前字符未出现过，将其放入队列中
            if (count == null) {
                count = 0;
                charDeque.add(c);
            }
            //更新map中字符出现的次数
            charMap.put(c,++count);
        }
        Character c;
        //遍历队列，依次判断队列中字符出现的次数。如果发现的次数为 1 字符，直接返回该字符；若队列遍历完没有这种字符，返回 ' '
        while((c=charDeque.poll())!=null){
            int count = charMap.get(c);
            if (count == 1){
                return c;
            }
        }
        return ' ';
    }
    public static void main(String[] args) {
        String s = "";
        char c = new SolutionJianzhi50().firstUniqChar(s);
        System.out.println(c);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
