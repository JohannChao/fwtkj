package com.johann.designPattern.dahao.observer;

/** 抽象观察者
 * @ClassName: IObserver
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public interface IObserver {
    void update(AbstractSubject subject,Object arg);
}
