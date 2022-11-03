package com.johann.jianzhiOffer;

/**
 * @ClassName: TempTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class TempTest {

    public static void main(String[] args) {
        int temp;
        int count = 0;
        int b = -1;
        System.out.println(Integer.MIN_VALUE);
        while ((temp = b << count) >= Integer.MIN_VALUE) {
            System.out.println("temp : "+temp);
            System.out.println("count"+count+" : "+temp);
            if (Integer.MIN_VALUE - temp > temp) {
                break;
            }
            count++;
        }
        System.out.println("countNum : "+count);
    }

}
