package com.johann.basic;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.LongBuffer;

/**
 * @ClassName: ByteOrderTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@SuppressWarnings("all")
public class ByteOrderTest {
    /**
     * 检测 Native 系统的字节序
     */
    public static void byteOrderTest(){
        // 本地系统是Intel 架构，此时输出的是 LITTLE_ENDIAN “小端序”
        System.out.println("The native byte order: " + ByteOrder.nativeOrder());
    }

    /**
     * 检测 JVM 的字节序
     * 此时输出的结果是 “AA-BB-CC-DD”
     * 由此可知，JVM底层使用的是大端序
     */
    public static void checkEndian(){
        int x = 0xAABBCCDD;

        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
        byteBuffer.putInt(x);
        System.out.println("The JVM byte order: "+byteBuffer.order());

        byte[] bytes = byteBuffer.array();
        for (byte aByte : bytes) {
            System.out.printf("%X-",aByte);
        }
        System.out.println();
    }

    /**
     * Java多字节序Buffer，字节序规则
     *
     * 如果多字节 Buffer 是通过数组（Array）创建的，那么它的字节序和底层系统的字节序一致。
     * 如果多字节 Buffer 是通过 ByteBuffer 创建的，那么它的字节序和 ByteBuffer 的字节序一致。
     */
    public static void checkByteBuffer(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);

        //修改当前缓冲区的字节顺序
        //byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        //修改完成后输出：The byte order for LongBuffer from ByteBuffer: LITTLE_ENDIAN

        LongBuffer lbFromByteBuffer = byteBuffer.asLongBuffer();
        System.out.println("The byte order for LongBuffer from ByteBuffer: " + lbFromByteBuffer.order());
        //The byte order for LongBuffer from ByteBuffer: BIG_ENDIAN

        long[] longs = new long[]{0xAA,0xBB,0xCC,0xDD};
        LongBuffer lbFromArray = LongBuffer.wrap(longs);
        System.out.println("The byte order for LongBuffer from Array: " + lbFromArray.order());
        //The byte order for LongBuffer from Array: LITTLE_ENDIAN
    }


    public static void main(String[] args) {
        byteOrderTest();
        checkEndian();
        checkByteBuffer();
    }
}
