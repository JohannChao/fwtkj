package com.johann.springboottk.algorithm;

import java.util.LinkedHashMap;
import java.util.Map;

/** LinkedHashMap提供访问排序模式，此时最近使用的键值将会移到表头
 * @ClassName: LinkedHashMapTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class LinkedHashMapTest {

    public static void main(String[] args) {
        /** accessOrder 排序模式- true表示访问顺序，false表示插入顺序 */
        LinkedHashMap<String,String> map = new LinkedHashMap(3,0.75F,true);
        map.put("1","Johann");
        map.put("2","Jessie");
        map.put("3","Peter");
        map.put("4","Stephen");
        for (Map.Entry<String,String> entry : map.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        map.put("4","NewStephen");
        map.get("2");
        for (Map.Entry<String,String> entry : map.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
