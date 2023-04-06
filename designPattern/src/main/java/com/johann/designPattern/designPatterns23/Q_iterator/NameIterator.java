package com.johann.designPattern.designPatterns23.Q_iterator;

/** 迭代器实现类
 * @ClassName: NameIterator
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class NameIterator implements Iterator{
    private String[] names;
    private int index;

    public NameIterator(String[] names) {
        this.names = names;
    }

    @Override
    public boolean hasNext() {
        if (index >= names.length){
            return false;
        }
        return true;
    }

    @Override
    public Object next() {
        if (this.hasNext()){
            return names[index++];
        }
        return null;
    }
}