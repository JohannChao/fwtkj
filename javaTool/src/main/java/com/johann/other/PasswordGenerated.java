package com.johann.other;

import java.util.Random;
import java.util.Scanner;

/**
 * 随机密码生成器
 * @author Johann
 * @date 2022年10月18日
 */
public class PasswordGenerated {

    public static void main(String[] args) {
        String password = randomPassword(getPasswordLength(),containsSpecialCharacter());
        System.out.println("您随机出来的密码为:"+password.toString());
    }
 
    /**
     * 获取用户输入的密码位数
     * @return
     */
    @SuppressWarnings("resource")
    public static int getPasswordLength(){
        System.out.println("请输入您想获取的密码位数:");
        int input = new Scanner(System.in).nextInt();
        return input;
    }
    /**
     * 是否包含特殊字符
     * @return
     */
    @SuppressWarnings("resource")
    public static String containsSpecialCharacter(){
        System.out.println("是否包含特殊字符:yes/no(包含特殊字符默认回车)");
        String input = new Scanner(System.in).nextLine();
        return input;
    }
 
    /**
     * 随机出用户输入的密码位数的密码,从大小写字母,数字,特殊字符中取值
     * @param num
     * @param special_character
     * @return
     */
    public static String randomPassword(int num,String special_character){
        //创建char数组接收每一位随机出来的密码
        char[] passwor = new char[num];
        Random rand = new Random();
        int containsSc = 3;
        if("".equalsIgnoreCase(special_character)||"yes".equalsIgnoreCase(special_character)){
            containsSc = 4;
        }
        //特殊字符串
        char[] sc = {'!','@','#','$','%','&','(',')','+','-','{','}',':','?','_','='};
        //在ASCII码表中,48-57 数字,65-90 大写字母,97-122 小写字母
        for (int i = 0; i <passwor.length ; i++) {
            int choice = rand.nextInt(containsSc);
            //小写字母ASCII码表范围
            int lowercase = rand.nextInt(26)+65;
            //大写字母ASCII码表范围
            int uppercase = rand.nextInt(26)+97;
            //数字ASCII码表范围
            int figure = rand.nextInt(10)+48;
            //特殊字符选取
            int specialChar = rand.nextInt(16);
            //从大写字母.小写字母.数字.特殊字符中随机取值
            switch (choice){
                case 0:passwor[i]=(char)lowercase;break;
                case 1:passwor[i]=(char)uppercase;break;
                case 2:passwor[i]=(char)figure;break;
                case 3:passwor[i]=(char)sc[specialChar];break;
            }
        }
        String password = new String(passwor);
        return password;
    }
}