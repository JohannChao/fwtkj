package com.johann.algorithm.cacheEviction;

import java.util.HashMap;
import java.util.LinkedList;

/** 最不经常使用淘汰算法（Least Frequently Used）。LFU是淘汰一段时间内，使用次数最少的数据（使用次数最少的有多个时，最久未使用的数据会被淘汰）
 *  实现原理：为每个数据项维护一个访问计数器, 淘汰时选择计数器值最小的数据。
 *
 *  优点: 能够保留被频繁访问的数据。
 *  缺点: 对于访问模式变化的情况适应较慢,可能会长期保留一些过去频繁使用但最近较少使用的数据。
 *
 * @ClassName: LFUcahe
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class LFUcahe<K,V> {

    private class Node{
        K key;
        V value;
        int frequency;

        Node(){}
        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.frequency = 1;
        }
    }

    /** 缓存容量 */
    private int capacity;

    /** 缓存中数据使用最少的次数 */
    private int minFrequency;

    /** 存储缓存数据的HashMap*/
    private HashMap<K,Node> cache;

    /** 存储缓存数据使用次数的 HashMap
     * [使用次数1,{缓存数据1,缓存数据2}],[使用次数2,{缓存数据3,缓存数据4}]
     * */
    private HashMap<Integer, LinkedList<Node>> frequencyMap;

    public LFUcahe(int capacity) {
        this.capacity = capacity;
        this.minFrequency = 1;
        this.cache = new HashMap<>(capacity);
        this.frequencyMap = new HashMap<>();
    }

    /**
     * 获取缓存数据。将缓存数据的使用次数+1，并将缓存在列表中的位置提到列表表头
     * @param key
     * @return
     */
    public V get(K key) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            updateNode(node);
            return node.value;
        }else {
            return null;
        }
    }

    /**
     * 将缓存数据的使用次数+1，并将缓存在列表中的位置提到列表表头
     * @param node
     */
    private void updateNode(Node node) {
        // 当前缓存数据的使用次数
        int frequency = node.frequency;
        // 在当前缓存使用次数对应的列表中，将该缓存数据删除
        LinkedList<Node> frequencyList = frequencyMap.get(frequency);
        frequencyList.remove(node);
        //如果当前这个缓存数据的使用次数最少，此时将这个缓存数据的使用次数+1时，也需要维护整个缓存中最小使用次数 minFrequency
        if(frequency == minFrequency && frequencyList.isEmpty()){
            minFrequency++;
        }
        //维护节点中的当前缓存使用次数
        node.frequency += 1;
        //node.frequency++;
        //将当前缓存数据，加入到【原使用次数+1】对应的列表的表头（淘汰数据时，使用次数最少，且最久未使用的数据会被淘汰）
        frequencyMap.computeIfAbsent(node.frequency,k -> new LinkedList<>()).addFirst(node);
    }

    /**
     * 更新缓存数据（新增或更新），并维护缓存使用次数HashMap
     * @param key
     * @param value
     */
    public void put(K key, V value) {
        if(capacity <= 0){
            return;
        }
        // 缓存中存在该 key，刷新缓存数据，并维护使用次数
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.value = value;
            updateNode(node);
        }else {
            // 缓存中不存在该 key，新增缓存数据
            Node node = new Node(key,value);
            // 当前缓存即将超出上限，需要移除旧数据
            if (cache.size() >= capacity) {
                // 移除使用次数最少，且最久未使用的缓存数据
                LinkedList<Node> minFrequencyList = frequencyMap.get(minFrequency);
                Node least = minFrequencyList.removeLast();
                cache.remove(least.key);
            }
            // 将新缓存数据加入到缓存HashMap中，并在使用次数HashMap中维护这条缓存
            cache.put(key, node);
            frequencyMap.computeIfAbsent(1,k -> new LinkedList<>()).addFirst(node);
            minFrequency = 1;
        }
    }

    public static void main(String[] args) {
        LFUcahe<String,String> lfuCache =new LFUcahe(3);
        lfuCache.put("user_1","Johann");
        lfuCache.put("user_2", "Jessie");
        lfuCache.frequencyMap.forEach((k,v)->{
            System.out.println("frequency: "+k);
            v.forEach(node->{
                System.out.println(node.key+"  "+node.value);
            });
        });
        System.out.println("-----------------------");

        lfuCache.put("user_3","Peter");
        lfuCache.frequencyMap.forEach((k,v)->{
            System.out.println("frequency: "+k);
            v.forEach(node->{
                System.out.println(node.key+"  "+node.value);
            });
        });
        System.out.println("-----------------------");

        lfuCache.put("user_4","Stephen");
        lfuCache.frequencyMap.forEach((k,v)->{
            System.out.println("frequency: "+k);
            v.forEach(node->{
                System.out.println(node.key+"  "+node.value);
            });
        });
        System.out.println("-----------------------");

        System.out.println("lfuCache.get(\"user_2\")   "+lfuCache.get("user_2"));

        System.out.println("lfuCache.get(\"user_3\")   "+lfuCache.get("user_3"));

        System.out.println("lfuCache.get(\"user_2\")   "+lfuCache.get("user_2"));

        System.out.println("lfuCache.get(\"user_1\")   "+lfuCache.get("user_1"));

        lfuCache.put("user_5","Teddy");

        lfuCache.frequencyMap.forEach((k,v)->{
            System.out.println("frequency: "+k);
            v.forEach(node->{
                System.out.println(node.key+"  "+node.value);
            });
        });
        System.out.println("-----------------------");
    }
}