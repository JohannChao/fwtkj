package com.johann.designPattern.dahao.adapter;

/**
 * @ClassName: ZyhAdapterDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ZyhAdapterDemo {

    public static void main(String[] args) {
        ICnPlug cnPlug = new ChinesePlugAdapter("国标插头");
        cnPlug.usePlug();
    }

}
