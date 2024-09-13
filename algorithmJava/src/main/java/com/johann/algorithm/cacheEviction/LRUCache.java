package com.johann.algorithm.cacheEviction;

import java.util.HashMap;

/** 最近最少使用淘汰算法（Least Recently Used）。LRU是淘汰最长时间没有被使用的数据
 *  实现方式: 通常使用 链表 + 哈希表（哈希表用于存储数据，双向链表维护键值对顺序）。访问数据时将其移到链表头部,淘汰时移除链表尾部的数据。
 *
 *  优点: 能够保留最近使用的数据,对于时间局部性较强的应用效果好。
 *  缺点: 不考虑数据的访问频率,可能会淘汰之前经常被访问但最近未被使用的数据。
 *
 * @ClassName: LRUCache
 * @Author: Johann
 * @Version: 1.0
 **/
public class LRUCache<K,V>{

    private class LinkedNode{
        K key;
        V value;

        //当前节点的前节点
        LinkedNode prev;

        //当前节点的后节点
        LinkedNode next;

        LinkedNode(){
        }
        LinkedNode(K key,V value){
            this.key= key;
            this.value = value;
        }
    }

    private HashMap<K,LinkedNode> cache;
    /** 初始化链表大小 */
    private int initialCapacity;
    /** 链表的虚拟头节点和尾节点 */
    private LinkedNode head, tail;

    public LRUCache(){
        this(8);
    }

    public LRUCache(int initialCapacity){
        //初始化的时候，维护虚拟头节点和尾节点
        head = new LinkedNode();
        tail = new LinkedNode();
        head.next = tail;
        tail.prev = head;
        this.initialCapacity = initialCapacity;
        cache = new HashMap(initialCapacity);
    }

    /**
     * 访问数据
     * @param key
     * @return
     */
    public V get(K key){
        LinkedNode node = cache.get(key);
        // 当前键值对不存在
        if (node == null){
            return null;
        }
        // 访问到这个缓存时，将键移动到链表头部
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
        // 如果当前键不存在
        if (node == null){
            // 添加新的缓存之前，判断缓存是否超出上限，是否需要删除最不常用的缓存？
            if (cache.size() >= initialCapacity){
                // 超出上限，需要淘汰最久没有使用的缓存
                // 在链表中删除该节点
                LinkedNode removeTail = popTail();
                // 在HashMap中删除对应的缓存数据
                cache.remove(removeTail.key);
            }
            // 创建新的节点
            LinkedNode newNode = new LinkedNode(key, value);
            // 维护链表和缓存：将节点加入到HashMap中，并将节点添加到链表头部
            cache.put(key,addNodeToHead(newNode));
        }else{
            // 如果当前键存在，则更新键值
            node.value = value;
            // 并将更新后的节点添加到链表头部
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
        LinkedNode nextNode = removeNode.next;
        //待移除节点的前节点和后节点进行关联
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
        return removeNode;
    }

    /**
     * 在链表头部插入新节点
     * @param addedNode
     */
    private LinkedNode addNodeToHead(LinkedNode addedNode){
        // 先维护好当前节点的前后节点
        // 1，当前节点的前节点指向虚拟头节点
        addedNode.prev = head;
        // 2，当前节点的后节点指向虚拟头节点原来的后节点
        addedNode.next = head.next;

        // 3，虚拟头节点原来的后节点，其前节点需要指向当前节点
        head.next.prev = addedNode;
        // 4，虚拟头节点的后节点指向当前节点
        head.next = addedNode;
        return addedNode;
    }

    /**
     * 移除尾节点
     * @return
     */
    private LinkedNode popTail(){
        LinkedNode tailPrev = tail.prev;
        return removeNode(tailPrev);
    }

    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");
        System.out.println(cache.get(1)); // Output: One

        cache.put(4, "Four");
        System.out.println(cache.get(2)); // Output: null
        System.out.println(cache.get(3)); // Output: Three
        System.out.println(cache.get(4)); // Output: Four
    }
}
