package com.johann.UML;

/** 动物
 * @ClassName: Animal
 **/
public abstract class Animal {
    public String life;

    /**
     * 依赖关系，类A的某个成员方法的参数有类B，或者类B作为类A某个方法的返回值。
     * @param oxygen
     * @param water
     */
    public void metabolism(Oxygen oxygen,Water water){
        System.out.println("新陈代谢中：需要氧气和水。");
    };
    public abstract void reproduce();
}
