package com.johann.springbootredis.algorithm;

import java.util.HashMap;
import java.util.LinkedList;

/** 最不经常使用淘汰算法（Least Frequently Used）。LFU是淘汰一段时间内，使用次数最少的数据（使用次数最少的有多个时，最久未使用的数据会被淘汰）
 * @ClassName: LFUcahe
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class LFUcahe<K,V> {
    /** 缓存容量 */
    private int capacity;
    /** 缓存中数据使用最少的次数 */
    private int minFred;
    /** 存储缓存数据的HashMap*/
    private HashMap<K,Node> cache;
    /** 存储缓存数据使用次数的HashMap
     * [使用次数1,{缓存数据1,缓存数据2}],[使用次数2,{缓存数据3,缓存数据4}]
     * */
    private HashMap<Integer, LinkedList<Node>> fredMap;

    public LFUcahe(int capacity) {
        this.capacity=capacity;
        cache = new HashMap<>(capacity);
        fredMap = new HashMap<>();
        minFred=0;
    }

    public V get(K key) {
        if(capacity==0){
            return null;
        }
        Node node = cache.get(key);
        if(node==null){
            return null;
        }
        fredIncrease(node);
        return node.value;
    }

    /**
     * 维护当前缓存数据的使用次数+1
     * @param node
     */
    private void fredIncrease(Node node) {
        //当前缓存数据的使用次数
        int fred = node.getFred();
        //在当前缓存使用次数对应的列表中，将该缓存数据删除
        LinkedList<Node> fredList = fredMap.get(fred);
        fredList.remove(node);
        //如果当前这个缓存数据的使用次数最少，此时将这个缓存数据的使用次数+1时，也需要维护整个缓存中最小使用次数 minFred
        if(fred==minFred&&fredList.isEmpty()){
            minFred++;
        }
        //将当前缓存数据，加入到【原使用次数+1】对应的列表的表头（淘汰数据时，使用次数最少，且最久未使用的数据会被淘汰）
        fredList=fredMap.get(fred+1);
        if(fredList==null||fredList.size()==0){
            fredList=new LinkedList<>();
            fredMap.put(fred+1,fredList);
        }
        //维护节点中的当前缓存使用次数
        node.fredInc();
        fredList.addFirst(node);
    }

    /**
     * 更新缓存数据（新增或更新），并维护缓存使用次数HashMap
     * @param key
     * @param value
     */
    public void put(K key, V value) {
        if(capacity==0){
            return;
        }
        Node node = cache.get(key);
        //如果当前缓存数据已存在，更新缓存数据，并维护使用次数
        if (node != null) {
            node.value = value;
            fredIncrease(node);
        }else {
            node = new Node();
            node.key = key;
            node.value = value;
            //当前缓存即将超出上限，需要移除旧数据
            if (cache.size() == capacity) {
                //移除使用次数最少，且最久未使用的缓存数据
                LinkedList<Node> fredList = fredMap.get(minFred);
                Node last = fredList.removeLast();
                cache.remove(last.key,last);
            }
            //将新缓存数据加入到缓存HashMap中，并在使用次数HashMap中维护这条缓存
            cache.put(key, node);
            LinkedList<Node> fredList = fredMap.get(0);
            if (fredList == null) {
                fredList = new LinkedList<>();
                fredMap.put(0, fredList);
            }
            fredList.addFirst(node);
            //更新整个缓存HashMap的最小使用次数为 0
            minFred = 0;
        }
    }

    public static void main(String[] args) {
        LFUcahe<String,String> cache =new LFUcahe(3);
        cache.put("user_1","Johann");
        cache.put("user_2", "Jessie");
        System.out.println(cache.cache.keySet());
        cache.put("user_3","Peter");
        System.out.println(cache.cache.keySet());

        cache.put("user_4","Stephen");
        System.out.println(cache.cache.keySet());

        System.out.println("user_2   "+cache.get("user_2"));

        System.out.println("user_3   "+cache.get("user_3"));

        System.out.println("user_2   "+cache.get("user_2"));

        System.out.println("user_1   "+cache.get("user_1"));

        cache.put("user_5","Teddy");
        System.out.println(cache.cache.keySet());

        System.out.println("user_1   "+cache.get("user_1"));

        System.out.println("user_2   "+cache.get("user_2"));

        System.out.println("user_3   "+cache.get("user_3"));

        System.out.println("user_4   "+cache.get("user_4"));

        System.out.println("user_5   "+cache.get("user_5"));
    }

    private class Node{
        K key;
        V value;
        private int fred = 0;

        public int getFred() {
            return fred;
        }

        public void fredInc(){
            fred++;
        }
    }
}