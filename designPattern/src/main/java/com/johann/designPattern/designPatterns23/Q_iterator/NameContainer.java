package com.johann.designPattern.designPatterns23.Q_iterator;

/** 容器实现类
 * @ClassName: NameContainer
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class NameContainer implements IContainer{
    public String[] names = {"Robert" , "John" ,"Julie" , "Lora"};

    @Override
    public Iterator getIterator() {
        return new NameIterator(names);
    }
}
