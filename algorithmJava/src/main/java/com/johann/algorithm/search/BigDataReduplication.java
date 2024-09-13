package com.johann.algorithm.search;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;

/**
 * 海量数据去重
 */
public class BigDataReduplication {

    /**
     * 1，使用 BitMap 去重
     * 一个BitMap是一个由二进制位组成的数组，每个位可以表示一个特定元素的存在（1）或不存在（0）。
     *
     * BitMap去重原理：
     * 当需要去重的数据集是整数或可以通过某种方式映射到整数的范围时，可以使用BitMap。
     * 对于数据集中的每个元素，将其映射到位数组中的一个位置。如果元素值为n，则在位数组中的第n位设置为1，表示该元素存在。
     * 当遍历整个数据集后，位数组中为1的位置对应的数据就是去重后的结果。
     *
     * 优点：
     *   - 空间效率：BitMap非常节省空间，特别是当数据集的基数（不同元素的数量）远小于数据集的范围时。
     *   - 时间效率：去重操作的时间复杂度为O(n)，其中n是数据集的大小。
     * 缺点：
     *   - 数据类型限制：BitMap适用于整数类型或可以映射到整数的数据，对于非整数类型的数据处理较为复杂。
     *   - 内存限制：当数据集的范围非常大时，需要的内存空间也会相应增大，可能受到系统内存限制。（此时可以考虑使用布隆过滤器）
     */

    /**
     * 2，使用布隆过滤器去重
     * 布隆过滤器是一种空间效率很高的概率型数据结构，它利用位数组很简洁地表示一个集合，并能判断一个元素是否可能属于这个集合。
     *
     * 2-1，布隆过滤器的原理：
     * 基本概念：
     *   - 位数组：布隆过滤器基于一个位数组，初始时所有位都设置为0。
     *   - 哈希函数：布隆过滤器使用多个不同的哈希函数，将输入元素映射到位数组中的不同位置。
     * 添加元素：
     *   - 当要添加一个元素时，使用多个哈希函数根据该元素值计算多个位置，并将这些位置上的位设置为1。
     * 查询元素：
     *   - 查询一个元素是否在集合中时，同样使用这些哈希函数计算位置。如果所有计算出的位置上的位都是1，则布隆过滤器认为该元素可能在集合中。
     *   - 如果这些位置上有任何一位是0，则该元素一定不在集合中。
     * 误判率：
     *   - 随着添加的元素越来越多，位数组中的0会越来越少（哈希冲突，两个或多个不同的key倍映射到同一个哈希值），因此误判率会逐渐上升。
     *
     * 2-2，布隆过滤器的工作步骤：
     * 初始化：
     *   - 根据预期的元素数量和误判率，确定位数组的大小和哈希函数的数量（误判率）。
     * 添加元素：
     *   - 对每个要添加的元素，应用多个哈希函数，将计算出的位置上的位设置为1。
     * 查询元素：
     *   - 对要查询的元素，应用相同的哈希函数。如果所有计算出的位置上的位都是1，则可能存在；否则，一定不存在。
     *
     * 2-3，布隆过滤器的优缺点
     * 优点：
     *   - 空间效率：布隆过滤器非常节省空间，特别是当集合较大时。
     *   - 时间效率：添加和查询操作的时间复杂度为O(k)，其中k是哈希函数的数量。
     * 缺点
     *   - 误判率：布隆过滤器可能错误地认为某个元素在集合中。
     *   - 不能删除元素：布隆过滤器不支持删除单个元素的操作，因为这个元素可能与其他元素共享这个哈希值。
     */

    /**
     * 使用 Google Guava库提供的 BloomFilter
     */
    public static void bloomFilterReduplication() {
        // 预计插入元素的数量
        long expectedInsertions = 1000000000L;
        // 期望的误判率
        double fpp = 0.01;
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), expectedInsertions, fpp);
        // 添加数据
        bloomFilter.put("hello");
        bloomFilter.put("world");
        bloomFilter.put("java");

        // 如果 mightContain 方法返回 true，则表示该元素【可能在】集合中；如果返回 false，则表示该元素【一定不在】集合中。
        System.out.println(bloomFilter.mightContain("hello"));
        System.out.println(bloomFilter.mightContain("test"));
    }

    public static void main(String[] args) {
        bloomFilterReduplication();
    }
}
