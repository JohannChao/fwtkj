package com.johann.binary;

/** 二进制转十进制
 * @author: Johann
 */
public class binaryToDecimal {

    public static void main(String[] args) {
        int decimalNumber = -100;
        System.out.println(integerConversion(decimalNumber));
        System.out.println(Integer.toString(decimalNumber, 2));
        System.out.println(Integer.toBinaryString(decimalNumber));

//        System.out.format("原始数字：%d , 取反后的数字：%d , 相反数为：%d \n",decimalNumber,~decimalNumber,getNegativeNumber(decimalNumber));
//        System.out.format("原始数字：%d , 取反后的数字：%d , 相反数为：%d \n",(-decimalNumber),~(-decimalNumber),getNegativeNumber(-decimalNumber));
    }

    /**
     * 二进制转十进制
     * @param decimalNumber
     * @return
     */
    public static String integerConversion(Integer decimalNumber){
        StringBuilder binaryString = new StringBuilder();
        boolean isNegative = false;
        if (isNegative = (decimalNumber < 0)){
            decimalNumber = getNegativeNumber(decimalNumber);
        }
        while (decimalNumber > 0){
            int remainder = decimalNumber % 2;
            decimalNumber = decimalNumber / 2;
            binaryString.insert(0,remainder);
        }
        if (isNegative){
            binaryString.insert(0,"-");
        }
        return binaryString.toString();
    }

    /**
     * 取相反数（与取反操作不同）
     * @param decimalNumber
     * @return
     */
    public static Integer getNegativeNumber(Integer decimalNumber){
        return ~decimalNumber+1;
    }

}
