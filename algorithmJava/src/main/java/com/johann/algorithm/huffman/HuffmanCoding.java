package com.johann.algorithm.huffman;

/**
 * 哈夫曼编码
 * 哈夫曼编码是一种用于数据压缩的算法。它的主要目的是通过使用较短的编码来表示频繁出现的字符,而用较长的编码来表示不常出现的字符,从而减少整体数据的大小。
 *
 * 工作原理:
 *   - 统计频率: 首先,统计每个字符出现的频率。
 *   - 构建树: 从频率最低的字符开始,逐步构建一个二叉树。
 *   - 分配编码: 根据树的结构,为每个字符分配一个唯一的二进制编码。
 *
 * 哈夫曼编码的优点是它能根据数据的实际分布来优化编码,从而实现高效的压缩。
 * 它的缺点是需要同时传输或存储编码树,以便解码。
 **/
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Map;

public class HuffmanCoding {

    /**
     * 定义节点类
     */
    static class Node implements Comparable<Node> {
        char c;
        int freq;
        Node left;
        Node right;

        Node(char c, int freq, Node left, Node right) {
            this.c = c;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        // 实现Comparable接口,比较节点的频率
        @Override
        public int compareTo(Node other) {
            return this.freq - other.freq;
        }
    }

    /**
     * 构建Huffman树
     * @param text 输入文本
     * @return Huffman树的根节点
     */
    static Node buildHuffmanTree(String text) {
        // 统计每个字符出现频率
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }
        System.out.println("=========================");
        System.out.println("各个字符 出现的次数：");
        freqMap.forEach((k, v) -> System.out.println(k + " : " + v));
        System.out.println("=========================");

        // 将频率信息存储在优先级队列中
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue(), null, null));
        }

        // 循环建树
        while (pq.size() > 1) {
            // 删除前两个频率最小的节点
            Node left = pq.poll();
            Node right = pq.poll();

            // 创建父节点,频率为两个子节点的和
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.add(parent);
        }

        // 返回Huffman树的根节点
        return pq.poll();
    }

    /**
     * 生成Huffman编码
     * @param root Huffman树的根节点
     * @return 字符到Huffman编码的映射
     */
    static Map<Character, String> generateCodes(Node root) {
        Map<Character, String> huffmanCode = new HashMap<>();
        generateCodes(root, "", huffmanCode);
        return huffmanCode;

    }

    /**
     * 递归生成编码
     * @param node 当前节点
     * @param code 当前路径编码
     * @param huffmanCode 哈夫曼编码信息
     */
    static void generateCodes(Node node, String code, Map<Character, String> huffmanCode) {
        if (node == null) {
            return;
        }

        // 叶子节点表示一个字符及其编码
        if (node.c != '\0') {
            huffmanCode.put(node.c, code);
        } else {
            // 递归左右子树
            generateCodes(node.left, code + "0", huffmanCode);
            generateCodes(node.right, code + "1", huffmanCode);
        }
    }

    /**
     * Huffman编码-解码
     * @param huffmanCode Huffman编码信息
     * @param encodedString 压缩后的信息
     * @return 解码后的字符串
     */
    public static String decode(Map<Character, String> huffmanCode, String encodedString) {
        StringBuilder decodedString = new StringBuilder();
        StringBuilder currentCode = new StringBuilder();

        // 遍历压缩后的信息，只要能匹配到编码就解码。随后重置编码，继续匹配
        for (char c : encodedString.toCharArray()) {
            currentCode.append(c);

            // 判断编码是否匹配
            if (huffmanCode.containsValue(currentCode.toString())) {
                for (Map.Entry<Character, String> entry : huffmanCode.entrySet()) {
                    // 只要匹配到编码表中的编码，就解码
                    if (entry.getValue().equals(currentCode.toString())) {
                        decodedString.append(entry.getKey());
                        break;
                    }
                }
                // 重置编码
                currentCode.setLength(0);
            }
        }

        return decodedString.toString();
    }



    public static void main(String[] args) {
        String text = "AAABBBBBCCCCDDDEEEFFGHK";

        // 构建Huffman树
        Node root = buildHuffmanTree(text);

        // 生成Huffman编码
        Map<Character, String> huffmanCode = generateCodes(root);

        // 输出编码结果
        System.out.println("Huffman编码:");
        for (Map.Entry<Character, String> entry : huffmanCode.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("=========================");
        System.out.println("原文："+text);
        System.out.println("原文长度："+text.length()*8);
        System.out.println("压缩后的结果:");
        StringBuilder compressed = new StringBuilder();
        for (char c : text.toCharArray()) {
            compressed.append(huffmanCode.get(c));
        }
        System.out.println("length："+compressed.length()+"\ncompressed："+compressed.toString());

        System.out.println("=========================");
        System.out.println("解码后的结果:");
        System.out.println(decode(huffmanCode, compressed.toString()));
    }
}
