package com.johann.algorithm.cacheEviction;

import java.util.LinkedHashMap;
import java.util.Map;

/** 基于LinkedHashMap，实现LRU算法
 * @ClassName: LRUCacheByLinkedHashMap
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class LRUCacheByLinkedHashMap<K,V> extends LinkedHashMap<K,V> {
    private int capacity;

    public LRUCacheByLinkedHashMap(int capacity, int initialCapacity, float loadFactor) {
        /** accessOrder 排序模式- true表示访问顺序，false表示插入顺序 */
        super(initialCapacity, loadFactor, true);
        this.capacity = capacity;
    }

    /**
     * 重写removeEldestEntry方法. 当缓存大小超过指定容量时,返回true以删除最老的元素。
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }

    public static void main(String[] args) {
        LRUCacheByLinkedHashMap<Integer, Integer> cache =
                new LRUCacheByLinkedHashMap<>(3, 3, 0.75F);
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
        map.forEach((k,v)-> System.out.println(k + ": " + v));
        System.out.println("-----------------------");
    }
}
