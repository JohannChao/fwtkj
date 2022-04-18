package com.johann.dataStructures.f_hash;

import java.util.HashMap;

/**
 * @ClassName: JohannHash
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class JohannHash {

    public static void test(){
        HashMap hashMap = new HashMap(10);
        hashMap.put("1","美国");
        System.out.println(hashMap.get("1"));
        System.out.println("put zg :"+hashMap.putIfAbsent("1","中国"));
        System.out.println(hashMap.get("1"));
        System.out.println("put zg :"+hashMap.put("1","中国"));
        System.out.println(hashMap.get("1"));
    }

    public static void main(String[] args) {
        test();

    }
}
