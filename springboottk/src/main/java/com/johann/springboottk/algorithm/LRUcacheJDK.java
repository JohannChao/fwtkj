package com.johann.springboottk.algorithm;

import java.util.LinkedHashMap;
import java.util.Map;

/** 基于LinkedHashMap，实现LRU 机制
 * @ClassName: LRUcacheJDK
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class LRUcacheJDK<K,V> extends LinkedHashMap<K,V> {
    private int capacity;

    public LRUcacheJDK(int capacity, int initialCapacity, float loadFactor) {
        /** accessOrder 排序模式- true表示访问顺序，false表示插入顺序 */
        super(initialCapacity, loadFactor, true);
        this.capacity = capacity;
    }

    /**
     * 如果返回true，这个映射应该删除它最老的条目
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        if (size() > capacity) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        LRUcacheJDK<Integer, Integer> cache =
                new LRUcacheJDK<>(3, 3, 0.75F);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.put(4, 4);
        cache.traversal(cache);

        cache.get(3);
        cache.traversal(cache);

        cache.put(2, 5);
        cache.put(5, 6);
        cache.traversal(cache);

        cache.get(4);
        cache.traversal(cache);
    }

    public void traversal(Map<Integer,Integer> map){
        for (Map.Entry<Integer,Integer> entry : map.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
