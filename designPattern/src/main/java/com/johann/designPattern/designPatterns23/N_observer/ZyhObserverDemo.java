package com.johann.designPattern.designPatterns23.N_observer;

/**
 * @ClassName: ZyhObserverDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ZyhObserverDemo {
    public static void main(String[] args) {
        AbstractSubject subject = new SubjectTeacher("赵一年级部主任");
        IObserver stu1 = new ObserverStudent("张大大");
        IObserver stu2 = new ObserverStudent("张小小");
        IObserver stu3 = new ObserverStudent("张胖胖");
        IObserver stu4 = new ObserverStudent("张瘦瘦");
        IObserver tc1 = new ObserverTeacher("刘二老师");
        IObserver tc2 = new ObserverTeacher("李四老师");

        subject.addObserver(stu1);
        subject.addObserver(stu2);
        subject.addObserver(stu3);
        subject.addObserver(stu4);
        subject.addObserver(tc1);
        subject.addObserver(tc2);
        subject.setSubjectState("去五教学楼领取新书本");

        subject.deleteObserver(tc2);
        subject.notifyObserver(null);

    }
}
