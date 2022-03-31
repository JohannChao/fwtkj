package com.johann.dataStructures.b_stack;

/**
 * 使用栈，来实现左右成对字符的匹配
 * @ClassName: CharMatchTest
 * @Description: 写过xml标签或者html标签的，我们都知道<必须和最近的>进行匹配，[ 也必须和最近的 ] 进行匹配。
 * @Author: Johann
 * @Version: 1.0
 **/
public class CharMatchTest {

    public static void main(String[] args) {
        String str = "{1[2<34]5}>";
        System.out.println(charMatchTest(str));
    }

    /**
     * 左右字符匹配
     * @param str
     * @return
     */
    public static boolean charMatchTest(String str){
        char[] chars = str.toCharArray();
        JohannExtendedStack stack = new JohannExtendedStack(3);
        for (char c: chars) {
            switch (c){
                case '<':
                case '[':
                case '{':
                    stack.push(c);
                    break;
                case '>':
                case ']':
                case '}':
                    if (!stack.isEmpty()){
                        char out = (char)stack.pop();
                        if (out=='<'&&c!='>'){
                            System.out.println("字符不匹配 "+out+" - "+c);
                            return false;
                        }else if(out=='['&&c!=']'){
                            System.out.println("字符不匹配 "+out+" - "+c);
                            return false;
                        }else if(out=='{'&&c!='}'){
                            System.out.println("字符不匹配 "+out+" - "+c);
                            return false;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return stack.isEmpty();
    }

}
