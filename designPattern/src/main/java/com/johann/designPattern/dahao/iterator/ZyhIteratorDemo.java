package com.johann.designPattern.dahao.iterator;

/** 迭代器模式Demo
 * 迭代器模式（Iterator Pattern）,提供一种方法顺序访问一个聚合对象中各个元素, 而又无须暴露该对象的内部表示。
 * @ClassName: ZyhIteratorDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ZyhIteratorDemo {
    public static void main(String[] args) {
        IContainer container = new NameContainer();
        Iterator iterator = container.getIterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
