package com.johann.springbootredis.algorithm;

import java.util.HashMap;

/** 最近最少使用淘汰算法（Least Recently Used）。LRU是淘汰最长时间没有被使用的数据
 *  可能之前这个缓存键被访问的很频繁，但是最近基本没有怎么访问，此时这个缓存键有可能会被淘汰掉。
 *
 *  实现方式：HashMap + 双向链表
 *  HashMap用于存储数据，双向链表维护键值对顺序
 * @ClassName: LRUcache
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class LRUcache<K,V>{

    private HashMap<K,LinkedNode> cache;
    /** 链表中元素数量 */
    private int elementCount;
    /** 初始化链表大小 */
    private int initialCapacity;
    /** 链表的虚拟头节点和尾节点 */
    private LinkedNode head, tail;

    public LRUcache(){
        this(8);
    }

    public LRUcache(int initialCapacity){
        //初始化的时候，维护虚拟头节点和尾节点
        head = new LinkedNode();
        tail = new LinkedNode();
        head.post = tail;
        tail.prev = head;

        this.initialCapacity = initialCapacity;
        this.elementCount = 0;
        cache = new HashMap(initialCapacity);
    }

    /**
     * 访问数据
     * @param key
     * @return
     */
    public V get(K key){
        LinkedNode node = cache.get(key);
        //当前键值对不存在
        if (node == null){
            return null;
        }
        //访问到这个缓存时，将键移动到链表头部
        moveNodeToHead(node);
        return node.value;
    }

    /**
     * 更新缓存数据（新增或更新）
     * @param key
     * @param value
     */
    public void put(K key,V value){
        LinkedNode node = cache.get(key);
        //如果当前键不存在
        if (node == null){
            //添加新的缓存之前，判断缓存是否超出上限，是否需要删除最不常用的缓存？
            if (elementCount == initialCapacity){
                //超出上限，需要删除旧缓存。在链表中删除节点，在HashMap中删除对应的缓存数据
                LinkedNode removeTail = removeTail();
                cache.remove(removeTail.key);
            }
            //创建新的节点
            LinkedNode newNode = new LinkedNode();
            newNode.key = key;
            newNode.value = value;
            //将节点加入到链表中，将节点加入到HashMap中。维护链表和缓存
            cache.put(key,addNodeToHead(newNode));
        }else{
            //如果当前键存在，则更新键值
            node.value = value;
            //将更新后的键移动到链表头部
            moveNodeToHead(node);
        }
    }

    /**
     * 当前节点移动到链表头部
     * @param currentNode
     * @return
     */
    private void moveNodeToHead(LinkedNode currentNode){
        //移除当前节点
        removeNode(currentNode);
        //在链表头部插入当前节点
        addNodeToHead(currentNode);
    }

    /**
     * 移除某个节点（断开其与前后节点的指向）
     * @param removeNode
     */
    private LinkedNode removeNode(LinkedNode removeNode){
        LinkedNode prevNode = removeNode.prev;
        LinkedNode postNode = removeNode.post;
        //待移除节点的前节点和后节点进行关联
        prevNode.post = postNode;
        postNode.prev = prevNode;
        //待移除节点的前后节点指向null
        removeNode.prev = null;
        removeNode.post = null;
        this.elementCount--;
        return removeNode;
    }

    /**
     * 在链表头部插入新节点
     * @param addedNode
     */
    private LinkedNode addNodeToHead(LinkedNode addedNode){
        // 1）链表虚拟头节点的后节点，其产生的变化是，它的前节点指向新节点
        LinkedNode headPost = head.post;
        headPost.prev = addedNode;
        // 2）链表虚拟头节点的后节点指向新节点
        head.post = addedNode;
        // 3）新节点的前节点指向虚拟头节点，新节点的后节点指向 虚拟头结点原来的后节点。
        addedNode.prev = head;
        addedNode.post = headPost;
        this.elementCount++;
        return addedNode;
    }

    /**
     * 移除尾节点
     * @return
     */
    private LinkedNode removeTail(){
        LinkedNode tailPrev = tail.prev;
        return removeNode(tailPrev);
    }

    public static void main(String[] args) {
        LRUcache<String,Object> cache = new LRUcache(5);
        cache.put("5","55");
        cache.put("4","44");
        cache.put("3","33");
        cache.put("2","22");
        cache.put("1","11");
        cache.get("5");
        cache.get("4");
        cache.get("3");
        cache.get("2");
        cache.put("0","00");
        System.out.println(cache.get("0"));
        System.out.println(cache.get("1"));
        System.out.println(cache.get("2"));
        System.out.println(cache.get("3"));
        System.out.println(cache.get("4"));
        System.out.println(cache.get("5"));
    }

    private class LinkedNode{
        K key;
        V value;
        LinkedNode prev;//当前节点的前节点
        LinkedNode post;//当前节点的后节点

    }
}
